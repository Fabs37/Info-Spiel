import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;


public class Desinfektion extends Tower {
    private Virus aim;
    
    public Desinfektion() {
        super("Tower\\Desinfektionsmittelspender.png");
        range = 100;
        damage = 0.1; // Damage pro act der DesinfectCloud
        cooldown = 200;
        cost = 500;
    }

    public void act() {
        aktCooldown--;
        if(!passive) {
            boolean check = true;
            if(aim != null) {
                if(getDistance(aim) < this.range) {
                    check = false;
                }
                if(aim.hp < 1) {
                    check = true;
                }
            }
            if(check) {
                
                List<Virus> virInRange = getVirenInRange();
                Virus furthestVirus = new Virus1a(); // nur damit der Compiler nicht meckert
                int furthestDst = 0;
                for(Actor av: virInRange) {
                    Virus aktVirus = (Virus) av;
                    if(aktVirus.dist > furthestDst) {
                        furthestVirus = aktVirus;
                    }
                }
                this.aim = furthestVirus;
                // System.out.println("Arzt.act: Checke Viren; Ergebnis: " + aim.toString());
            }

            // System.out.println("Arzt.act: " + aim.toString());
            try {
                updateEfficiency();
                int x = aim.getX();
                int y = aim.getY();
                turnTowards(x, y);
                setRotation(getRotation() - 90);
                if(aktCooldown < 0) {
                    // Schießen
                    getWorld().addObject(new DesinfectCloud(x, y, 150), this.getX(), this.getY());
                    aktCooldown = (int) (cooldown / efficiency);
                }
            } catch (Exception e) {
                // System.out.println("Virus hat keine Welt: " + aim.toString());
            }
        }
    }    
}

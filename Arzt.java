import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.util.Random;
import java.util.List;

public class Arzt extends Tower {
    public int type;
    private Virus aim;

    public Arzt() {
        Random r = new Random();
        this.type = r.nextInt(4);
        prepare();
    }

    public Arzt(int type) {
        this.type = type;
        prepare();
    }
    
    private void prepare() {
        switch(type) {
            case 0:
                setImagePath("Tower\\Ärztin_2.png");break;
            case 1:
                setImagePath("Tower\\Arzt_2.png");break;
            case 2:
                setImagePath("Tower\\Ärztin_3.png");break;
            case 3:
                setImagePath("Tower\\Arzt_3.png");break;
        }
        resize(62, 62);
        range = 200;
        damage = 320;
        cooldown = 150;
        cost = 1500;
    }

    public void act() {
        aktCooldown--;
        if(!passive) {
            boolean check = true;
            if(aim != null) {
                if(getDistance(aim) < this.range) {
                    check = false;
                }
                if(aim.hp < 1 || aim.getWorld() == null) {
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
                    getWorld().addObject(new Homing(aim, 3, this.damage), this.getX(), this.getY());
                    aktCooldown = (int) (cooldown / efficiency);
                }
            } catch (Exception e) {
                // System.out.println("Virus hat keine Welt: " + aim.toString());
            }
        }
    }
}
import java.util.Arrays;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public abstract class Virus extends ActorV2{
    protected double hp;
    public double dist; // Anzahl der Schritte, die das Virus schon gemacht hat
    public int damage; // Anzahl der Leben, die gelöscht werden, wenn das Virus durchkommt
    public double speed; // Anzahl der Schritte, die das Virus pro act macht

    protected int moneyPerKill;
    private int[][] absPath;
    private int checkpointsPassed; // Gibt an, das wievielte Element von absPath anvisiert wird.
    private boolean madeDamage;
    private Game world;

    public Virus() {
        GreenfootImage img = getImage();
        img.scale(62, 62);
        setImage(img);
        checkpointsPassed = 1;
        madeDamage = false;
    }

    protected void addedToWorld(World w) {
        // System.out.println("Ein wildes Virus ist erschienen!");
        world = (Game) getWorld();
        absPath = world.map.absVirusPath;
        // System.out.println("absPath: " + Arrays.deepToString(absPath));
    }
    
    public void act() {
        try {
            turnTowards(absPath[checkpointsPassed][1], absPath[checkpointsPassed][0]);
            move(speed);
            this.dist += speed;
            if(getX() == absPath[checkpointsPassed][1] && getY() == absPath[checkpointsPassed][0]) {
                checkpointsPassed++;
            }
        } catch (Exception e) {
            if(!madeDamage) {
                world.changeHP(this.damage);
                madeDamage = true;
            }
            world.removeObject(this);
        }
        
        if(this.hp < 1) {
            Game.changeMoney(this.moneyPerKill);
            world.removeObject(this);
        }
        try {
            if(isTouching(DesinfectCloud.class)) {
                this.hp -= DesinfectCloud.getDamage();
                // System.out.println("Virus.act(): Schaden durch Desinfi: " + Double.toString(DesinfectCloud.getDamage()) + ", Aktuelle HP: " + Double.toString(this.hp));
            }
        } catch (Exception e) {
            
        }
    }
}

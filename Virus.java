import java.util.Arrays;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public abstract class Virus extends ActorV2{
    private int hp;
    public int dist; // Anzahl der Schritte, die das Virus schon gemacht hat
    public int damage; // Anzahl der Leben, die gelöscht werden, wenn das Virus durchkommt
    public int speed; // Anzahl der Schritte, die das Virus pro act macht
    private int[][] absPath;
    private int checkpointsPassed; // Gibt an, das wievielte Element von absPath anvisiert wird.
    private boolean madeDamage;

    public Virus() {
        GreenfootImage img = getImage();
        img.scale(62, 62);
        setImage(img);
        checkpointsPassed = 1;
        madeDamage = false;
    }

    protected void addedToWorld(World w) {
        System.out.println("Ein wildes Virus ist erschienen!");
        Game world = (Game) getWorld();
        absPath = world.map.absVirusPath;
        System.out.println(Arrays.deepToString(absPath));
    }
    
    public void act() {
        try {
            turnTowards(absPath[checkpointsPassed][1], absPath[checkpointsPassed][0]);
            move(speed);
            if(getX() == absPath[checkpointsPassed][1] && getY() == absPath[checkpointsPassed][0]) {
                checkpointsPassed++;
            }
        } catch (Exception e) {
            Game w = (Game) getWorld();
            if(!madeDamage) {
                w.changeHP(this.damage);
                madeDamage = true;
            }
            w.removeObject(this);
        }
    };
}

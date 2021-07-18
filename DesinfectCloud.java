import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class DesinfectCloud extends Bullet {

    private int actsToLive;
    private int actsLeft;
    private int targetX;
    private int targetY;
    private static double damage = 0.05;
    private boolean reachedDestination;

    public DesinfectCloud(int x, int y, int actsToLive) {
        setImagePath("Tower\\Desinfektionsmittelklecks.png");
        resize(72, 72);
        targetX = x;
        targetY = y;
        this.actsToLive = actsToLive;
        this.actsLeft = actsToLive;
    }

    public static double getDamage() {
        return damage;
    }

    /* public void addedToWorld(World w) {
        System.out.println("DesinfCloud zu world geadded");
    } */

    public void act() {
        if(reachedDestination) {
            actsLeft--;
            double relTransparency = (double) ((double) (actsLeft) / (double) (actsToLive));
            double absTransparency = relTransparency * 255.0;
            // System.out.println(Double.toString(relTransparency) + "  " + Double.toString(absTransparency));
            setTransparency((int) absTransparency);
            // System.out.println("DesinfectCloud.act(): "  + Integer.toString(255-(int)(255*(actsLeft / actsToLive))) + "ActsLeft: " + Integer.toString(actsLeft) + "  " + Integer.toString(actsLeft/actsToLive));
            // System.out.println("DesinfectCloud.act(): " + Integer.toString(actsLeft) + " durch " + Integer.toString(actsToLive) + " ist " + Integer.toString(actsLeft/actsToLive));
            if(actsLeft == 0) {
                getWorld().removeObject(this);
            }
        } else {
            turnTowards(targetX, targetY);
            if(Math.abs(this.getX() - targetX) < 3 && Math.abs(this.getY() - targetY) < 3) {
                reachedDestination = true;
            } else {
                move(4);
            }
        }  
    }    
}

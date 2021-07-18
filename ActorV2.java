import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.Math;
public class ActorV2 extends Actor{
    public ActorV2(String imgPath) {
        super();
        setImage(new GreenfootImage(imgPath));
    }
    
    public ActorV2() {
        super();
    }
    
    public void setTransparency(int t) {
        GreenfootImage img = getImage();
        img.setTransparency(t);
        setImage(img);
    }
    
    public void hide() {
        setTransparency(0);
    }
    
    public void show() {
        setTransparency(255);
    }

    public void resize(int width, int height) {
        GreenfootImage img = getImage();
        img.scale(width, height);
        setImage(img);
    }

    public void setImagePath(String imgPath) {
        setImage(new GreenfootImage(imgPath));
    }

    public double getDistance(ActorV2 to) {
        double dx = this.getExactX() - to.getExactX();
        double dy = this.getExactY() - to.getExactY();
        return Math.sqrt(dx*dx + dy*dy);
    }

    /* 
    * Der folgende Code stammt von SmoothMover.java aus der Greenfoot-Lib.
    */

    private double exactX;
    private double exactY;

    /**
     * Move forward by the specified distance.
     * (Overrides the method in Actor).
     */
    @Override
    public void move(int distance)
    {
        move((double)distance);
    }
    
    /**
     * Move forward by the specified exact distance.
     */
    public void move(double distance)
    {
        double radians = Math.toRadians(getRotation());
        double dx = Math.cos(radians) * distance;
        double dy = Math.sin(radians) * distance;
        setLocation(exactX + dx, exactY + dy);
    }
    
    /**
     * Set the location using exact coordinates.
     */
    public void setLocation(double x, double y) 
    {
        exactX = x;
        exactY = y;
        super.setLocation((int) (x + 0.5), (int) (y + 0.5));
    }
    
    /**
     * Set the location using integer coordinates.
     * (Overrides the method in Actor.)
     */
    @Override
    public void setLocation(int x, int y) 
    {
        exactX = x;
        exactY = y;
        super.setLocation(x, y);
    }

    /**
     * Return the exact x-coordinate (as a double).
     */
    public double getExactX() 
    {
        return exactX;
    }

    /**
     * Return the exact y-coordinate (as a double).
     */
    public double getExactY() 
    {
        return exactY;
    }
}

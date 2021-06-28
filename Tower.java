import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public abstract class Tower extends ActorV2{
    private int xPos;
    private int yPos;
    public int cost;
    
    public Tower() {
        GreenfootImage img = getImage();
        img.scale(62, 62);
        setImage(img);    
    }
    
    public void act() {
        
    }
    
}

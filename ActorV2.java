import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class ActorV2 extends Actor{
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
}

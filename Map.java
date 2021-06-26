import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class Map extends ActorV2
{
    public Map(String path) {
        super();
        setImg(path);
    }
    
    public void setImg(String path) {
        setImage(new GreenfootImage(path));
    }
}

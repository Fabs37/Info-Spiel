import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class Map extends ActorV2 {
    public Boolean[][] validTowerPos;

    public Map(String path, Boolean[][] validTowerPos) {
        super();
        setImg(path);
        this.validTowerPos = validTowerPos;
    }
    
    public void setImg(String path) {
        setImage(new GreenfootImage(path));
    }
}

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class Map extends ActorV2 {
    public Boolean[][] validTowerPos;
    public int[][] virusPath;

    public Map(String path, Boolean[][] validTowerPos, int[][] virusPath) {
        super();
        setImg(path);
        this.validTowerPos = validTowerPos;
        this.virusPath = virusPath;
    }
    
    public void setImg(String path) {
        GreenfootImage img = new GreenfootImage(path);
        // img.setColor(new Color(255, 255, 255));
        // img.fillRect(0, 0, 960, 128);
        setImage(img);
    }
}

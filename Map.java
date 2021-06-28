import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class Map extends ActorV2 {
    public Boolean[][] validTowerPos;
    public int[][] absVirusPath;
    public int[] absSpawnPos;

    public Map(String path, Boolean[][] validTowerPos, int[][] virusPath) {
        super();
        setMap(path, validTowerPos, virusPath);
    }
    
    public void setMap(String path, Boolean[][] validTowerPos, int[][] virusPath) {
        GreenfootImage img = new GreenfootImage(path);
        // img.setColor(new Color(255, 255, 255));
        // img.fillRect(0, 0, 960, 128);
        setImage(img);
        this.validTowerPos = validTowerPos;

        int twidth = 64;
        this.absVirusPath = new int[virusPath.length][2];
        for(int y=0; y < virusPath.length; y++) {
            this.absVirusPath[y][0] = (int) (virusPath[y][0] * twidth + 2.5 * twidth);
            this.absVirusPath[y][1] = (int) (virusPath[y][1] * twidth + 0.5 * twidth);
        }

        this.absSpawnPos = new int[2];
        absSpawnPos[0] = absVirusPath[0][0];
        absSpawnPos[1] = absVirusPath[0][1];
    }
}

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List; import java.util.ArrayList;
import java.lang.Math;
import java.util.Arrays;
public class Tower extends ActorV2{
    protected int xPos;
    protected int yPos;
    protected Game w;
    public int cost;
    public int range;
    protected boolean passive; // für die Teile in der Seitenleiste
    protected int cooldown; //Anzahl der acts, die es dauert, bis der Turm wieder schießt
    protected int aktCooldown;
    public double damage;
    protected double efficiency;
    
    public Tower() {
        resize(62, 62);
        aktCooldown = 1;
    }

    public Tower(String path) {
        super(path);
        resize(62, 62);
        aktCooldown = 1;
    }
    
    public void setPassive(boolean passive) {
        this.passive = passive;
    }

    public void setGridPos(int x, int y) {
        xPos = x;
        yPos = y;
    }

    protected void updateEfficiency() {
        efficiency = Game.towerEfficiency[yPos][xPos];
    }
    
    protected List<Virus> getVirenInRange() {
        w = (Game) getWorld();
        List<Virus> allVirus = w.viren;
        // System.out.println(Arrays.toString(allVirus.toArray()));
        List<Virus> ret = new ArrayList<Virus>();
        for(Virus aktVirus: allVirus) {
            // System.out.println(aktVirus.toString());
            if(this.getDistance(aktVirus) <= this.range) {
                ret.add(aktVirus);
            }
        }

        return(ret);
    }
    
}

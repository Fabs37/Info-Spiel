import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class WaehrungsAnzeige extends ActorV2{
    GreenfootImage img;
    int val;
    
    public WaehrungsAnzeige() {
        val = 0;
        img = new GreenfootImage(128, 32);
        img.setFont(new Font("Consolas", 16));
        setMoney(0);
    }
    public void act() 
    {
        // Add your action code here.
    }
    public void setMoney(int newMoney) {
        val = newMoney;
        img.clear();
        img.drawImage(new GreenfootImage("GUI\\Waehrung.png"), 0, 0);
        img.drawString("x" + Integer.toString(val), 36, 16);
        setImage(img);
    }
}

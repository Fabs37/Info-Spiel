import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class DisplayText extends ActorV2{    
    GreenfootImage img;
    String txt;

    public DisplayText(String txt, Font f, int width) {
        // super();
        img = new GreenfootImage(128, 32);
        img.setFont(f);
        // setImage(img);
        setText(txt);
    }
    public void act() {
        // Add your action code here.
    }
    
    public void setText(String newTxt) {
        txt = newTxt;
        img.clear();
        // img.drawImage(new GreenfootImage("GUI\\Herz32px_1.png"), 0, 0);
        img.drawString(newTxt, 0, 16);
        setImage(img);
    }
}

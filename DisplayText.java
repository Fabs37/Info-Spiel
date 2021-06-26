import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class DisplayText extends ActorV2{    
    public DisplayText(String txt, Font f, Color c, int width) {
        super();
        GreenfootImage img = new GreenfootImage(width, f.getSize());
        img.setFont(f);
        img.setColor(c);
        setImage(img);
        setText(txt);
    }
    public void act() {
        // Add your action code here.
    }
    
    public void setText(String newTxt) {
        GreenfootImage img = getImage();
        img.clear();
        img.drawString(newTxt, 0, 0);
        setImage(img);
    }
}

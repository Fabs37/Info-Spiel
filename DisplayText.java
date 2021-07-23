import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class DisplayText extends ActorV2 {    
    GreenfootImage img;
    String txt;
    private Font f;
    private Color c;
    private int fixWidth;

    public DisplayText(String txt, Font f) {
        img = newImage(txt, f);
        this.f = f;
        this.c = Color.BLACK;
        this.fixWidth = 0;
        setText(txt);
    }

    public DisplayText(String txt, Font f, Color c) {
        img = newImage(txt, f);
        this.f = f;
        this.c = c;
        this.fixWidth = 0;
        setText(txt);
    }

    private GreenfootImage newImage(String txt, Font f) {
        return new GreenfootImage((int) (txt.length()*f.getSize()*0.5625)+1, (int) (f.getSize()*1.5+1));
    }

    public void setFixWidth(int w) {
        this.fixWidth = w;
    }
    
    public void setText(String newTxt) {
        txt = newTxt;
        img.clear();
        if(fixWidth == 0) {
            img = newImage(txt, f);
        } else {
            img = new GreenfootImage(fixWidth, (int) (f.getSize()*1.5+1));
        }
        
        // img.drawImage(new GreenfootImage("GUI\\Herz32px_1.png"), 0, 0);
        img.setFont(f);
        img.setColor(c);
        img.drawString(newTxt, 0, 16);
        setImage(img);
    }
}

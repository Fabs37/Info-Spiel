import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.reflect.*;

public class Game extends World {
    int twidth;
    private ActorV2[] hps;
    private int hp;
    private int money;
    private int runde;
    private WaehrungsAnzeige monCnt;
    private DisplayText rundenAnz;
    private Tower[][] placedTowers;
    private Map map;
    
    public Game() {
        super(960, 768, 1); prepare();
    }

    public void setHP(int newHP) {
        hp = newHP;
                
        
        int heartsToDelete = 10 - (((int) hp/10) + 1);
        // this.showText(Integer.toString(heartsToDelete) + ", " + Integer.toString(hp), 200, 200);
        // Tote Herzen unsichtbar machen
        for(int i=0; i<heartsToDelete; i++) {
            hps[9-i].setTransparency(55);
        }
        // restliche Herzen voll sichtbar machen    
        for(int i=heartsToDelete; i<10; i++) {
            hps[9-i].show();
        }
        // das Herz, das auf der Kippe steht, teiltransparent machen
        hps[9-heartsToDelete].setTransparency(55 + ((hp % 10) * 20));
    }
    public boolean addTower(Tower verschT) {
        verschT.setTransparency(100);
        addObject(verschT,(twidth/2),(twidth/2)+twidth);
        boolean setTower = false;

        if (Greenfoot.mouseMoved(null) || Greenfoot.mouseDragged(null) || Greenfoot.mousePressed(null)) {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if (mouse != null) {
                int x1 = mouse.getX() / twidth;
                int y1 = (mouse.getY() / twidth) - 2; // Offset für die obere Leiste
                System.out.println(Integer.toString(x1) + " " + Integer.toString(y1));
                int x2 = (x1*twidth)+(twidth/2); // absolute Positionen für den Turm
                int y2 = (y1*twidth)+(twidth/2) + 2*twidth;
                if(x1 < 13 && y1 < 12 && x1 > -1 && y1 > -1) {
                    verschT.setLocation(x2, y2);
                    if(mouse.getButton() == 1 && placedTowers[y1][x1] == null) {
                        verschT.setTransparency(255);
                        placedTowers[y1][x1] = verschT; 
                        // addObject(placedTowers[y1][x1], x2, y2);
                        setTower = true;
                        // System.out.println("Turm plaziert: x=" + Integer.toString(x1) + ", y=" + Integer.toString(y1));
                    }
                }
            }
        }
        return setTower;
    }

    public void act() {
        // setHP(hp-1);
        // setBackground("Maps\\Map1.png");
        monCnt.setMoney(money+1);
        money++;
        rundenAnz.setText("Runde " + Integer.toString(runde));
        runde++;

        if(addTower(new Testtower())) {
            System.out.println("Turm wurde plaziert!");
            // verschT.hide();
        }
        
        if(hp < 1) {
            showText("Verreckt!", 200, 300);
            Greenfoot.stop();
            Greenfoot.playSound("classic_hurt.wav");
        }
    }

    private void prepare() {
        //setBackground("Maps\\Map1.png");
        setPaintOrder(new Class[]{ActorV2.class, Tower.class, Virus.class, DisplayText.class, WaehrungsAnzeige.class, Map.class});
        map = new Map("Maps\\Map1.png");
        addObject(map, 480, 384);
        
        twidth = 64;
        hp = 100;
        runde = 1;
        money = 0;
        placedTowers = new Tower[10][13];

        
        monCnt = new WaehrungsAnzeige();
        addObject(monCnt, 500, 30);
        
        rundenAnz = new DisplayText("Runde 1", new Font("Consolas", 16), Color.BLACK, 200);
        addObject(rundenAnz, 700, 30);

        
        hps = new ActorV2[10];
        for(int i=0; i < 10; i++) {
            hps[i] = new ActorV2();
            hps[i].setImage("GUI\\Herz32px_1.png");
            addObject(hps[i], 30+35*i, 30);
        }
        
        
    }
}

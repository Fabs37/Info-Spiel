import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.reflect.*;

public class Game extends World {
    public int twidth; // Breite und Höhe der Felder (und der Viren und der Türme), hier 64
    private Tower tower2place; // Vorschau-Turm, der angezeigt wird, wenn ein Turm plaziert wird
    private boolean placingTower; // Zeigt an, ob gerade ein Turm plaziert wird
    private ActorV2[] hps; // Herzen-Anzeige oben links
    private int hp; // Die Anzahl der Leben
    private int money; // Die Anzahl der Impfdosen (Währung)
    private int runde;
    private ActorV2 moneyIcon; // Impfstoffsymbol in der Leiste oben
    private DisplayText moneyAnz; // Der Text rechts von moneyIcon
    private DisplayText rundenAnz; // funktioniert nicht
    private Tower[][] placedTowers; // speichert, welche Türme wo plaziert wurden
    public Map map; // Hintergrundbild, speichert auch die validTowerPos und die Strecke der Viren
    public Virus[] viren; // Alle Viren, die jemals gespawnt wurden
    public int virenAnzahl; // Zahl der Viren, die gespawnt wurden
    private int vCooldown; // TEST: Anzahl der acts, bis ein neues Virus gespawnt wird.
    
    public Game() {
        super(960, 768, 1); prepare();
    }

    public void changeHP(int deltaHP) {
        hp -= deltaHP;
        System.out.println("deltaHP: " + Integer.toString(deltaHP));
        if(hp <= 0) {
            showText("Verreckt!", 200, 300);
            Greenfoot.stop();
            Greenfoot.playSound("classic_hurt.wav");
        } else {
            int heartsToDelete = 10 - (((int) hp/10) + 1);
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
    }
    public boolean addTower() {
        boolean setTower = false;

        if (Greenfoot.mouseMoved(null) || Greenfoot.mouseDragged(null) || Greenfoot.mousePressed(null)) {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if (mouse != null) {
                int x1 = mouse.getX() / twidth;
                int y1 = (mouse.getY() / twidth) - 2; // Offset für die obere Leiste
                // System.out.println(Integer.toString(x1) + " " + Integer.toString(y1));
                int x2 = (x1*twidth)+(twidth/2); // absolute Positionen für den Turm
                int y2 = (y1*twidth)+(twidth/2) + 2*twidth;
                if(x1 < 13 && y1 < 12 && x1 > -1 && y1 > -1) {
                    if (this.map.validTowerPos[y1][x1]) {
                        tower2place.setLocation(x2, y2);
                        if(mouse.getButton() == 1 && placedTowers[y1][x1] == null) {
                            switch (tower2place.getClass().getName()) {
                                case "Testtower":
                                    placedTowers[y1][x1] = new Testtower();
                                    break;
                        
                                default:
                                    placedTowers[y1][x1] = new Testtower();
                                    break;
                            }
                            if(placedTowers[y1][x1].cost > this.money) {
                                placedTowers[y1][x1] = null;
                            } else {
                                tower2place.hide();
                                addObject(placedTowers[y1][x1], x2, y2);
                                this.money -= placedTowers[y1][x1].cost;
                                setTower = true;
                            }
                            // System.out.println("Turm plaziert: x=" + Integer.toString(x1) + ", y=" + Integer.toString(y1));
                        }
                    }
                }
            }
        }
        return setTower;
    }

    public void act() {
        // setBackground("Maps\\Map1.png");
        moneyAnz.setText("x" + Integer.toString(money));
        money++;
        rundenAnz.setText("Runde " + Integer.toString(runde));
        runde++;

        if(Greenfoot.isKeyDown("enter") && !placingTower) {
            tower2place = new Testtower();
            tower2place.setTransparency(100);
            addObject(tower2place,(twidth/2),(twidth/2)+2*twidth);
            placingTower = true;
        }
        if(placingTower) {
            if(addTower()) {
                // System.out.println("Turm wurde plaziert!");
                placingTower = false;
                removeObject(tower2place);
                // verschT.hide();
            }
        }

        vCooldown--;

        if(vCooldown == 0) {
            viren[virenAnzahl] = new Testvirus();
            addObject(viren[virenAnzahl], map.absSpawnPos[1], map.absSpawnPos[0]);

            virenAnzahl++;
            vCooldown = 50;
        }
    }

    private void prepare() {
        twidth = 64;
        hp = 100;
        runde = 1;
        money = 0;
        vCooldown = 50;

        setPaintOrder(new Class[]{ActorV2.class, Tower.class, Virus.class, DisplayText.class, Map.class});
        map = new Map("Maps\\Map1.png",
            new Boolean[][] { // Positionen, an denen Türme stehen dürfen
                {true, true, true, true, true, true, true, false, false, false, false, false, false, false},
                {true, true, true, true, true, true, true, true, false, false, false, false, false, false},
                {true, true, true, false, false, false, false, true, false, false, true, true, true},
                {true, true, true, false, true, true, false, true, false, true, true, false, false},
                {true, true, true, false, true, true, false, true, true, true, true, false, true},
                {true, false, false, false, true, true, false, true, true, true, true, false, true},
                {true, false, true, true, true, true, false, true, true, true, true, false, true},
                {true, false, true, false, true, true, false, false, false, false, true, false, true},
                {true, false, true, true, true, true, true, true, true, false, false, false, true},
                {true, false, true, true, true, true, true, true, true, true, true, true, true}
            }, new int[][] { // Die Zielpunkte auf dem Weg, den die Viren gehen werden
                {9, 1}, {5, 1}, {5, 3}, {2, 3}, {2, 6}, {7, 6}, {7, 9}, {8, 9}, {8, 11}, {3, 11}, {3, 12}
            }
        );
        addObject(map, 480, 384);

        viren = new Virus[1024];
        virenAnzahl = 0;
        
        placedTowers = new Tower[10][13];
        placingTower = false;

        // Die obere Leiste
        moneyIcon = new ActorV2("GUI\\Waehrung.png");
        addObject(moneyIcon, 520, 24);
        moneyAnz = new DisplayText("x0", new Font("Consolas", 16), 200);
        addObject(moneyAnz, 600, 30);
        rundenAnz = new DisplayText("Runde 1", new Font("Consolas", 16), 200);
        addObject(rundenAnz, 700, 30);

        hps = new ActorV2[10];
        for(int i=0; i < 10; i++) {
            hps[i] = new ActorV2("GUI\\Herz32px_1.png");
            addObject(hps[i], 30+35*i, 30);
        }
        
        
    }
}

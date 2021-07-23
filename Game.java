import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.reflect.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class Game extends World {
    public int twidth; // Breite und Höhe der Felder (und der Viren und der Türme), hier 64
    private Tower tower2place; // Vorschau-Turm, der angezeigt wird, wenn ein Turm plaziert wird
    private boolean placingTower; // Zeigt an, ob gerade ein Turm plaziert wird
    private ActorV2[] hps; // Herzen-Anzeige oben links
    private int hp; // Die Anzahl der Leben
    private static int money; // Die Anzahl der Impfdosen (Währung)
    private int runde;
    private ActorV2 moneyIcon; // Impfstoffsymbol in der Leiste oben
    private DisplayText moneyAnz; // Der Text rechts von moneyIcon
    private DisplayText rundenAnz; // funktioniert nicht
    private Tower[][] placedTowers; // speichert, welche Türme wo plaziert wurden
    public static double[][] towerEfficiency;
    public Map map; // Hintergrundbild, speichert auch die validTowerPos und die Strecke der Viren
    private int[][] rounds; // Viren, die in einer Runde gespawnt werden. Erstes Objekt der Unterarrays ist die Länge der Runde in acts, dann kommt die Anzahl der Viren nach Typ.
    public List<Virus> viren; // Alle Viren, die jemals gespawnt wurden
    public int virenAnzahl; // Zahl der Viren, die gespawnt wurden
    private int vCooldown; // TEST: Anzahl der acts, bis ein neues Virus gespawnt wird.
    private ActorV2[][] shop; // Die Leiste an der Seite, wo man Türme kaufen kann.
    private DisplayText alert; // Textfeld über dem Shop, in dem Meldungen angezeigt werden können.
    private ActorV2 continueButton;
    
    public Game() {
        super(960, 768, 1); prepare();
    }

    public void changeHP(int deltaHP) {
        hp -= deltaHP;
        // System.out.println("deltaHP: " + Integer.toString(deltaHP));
        if(hp <= 0) {
            alert.setLocation(71, 110);
            alert("Hops genommen!");
            Greenfoot.stop();
            Greenfoot.playSound("classic_hurt.wav");
            for(int i=0; i<10; i++) {hps[i].setTransparency(55);}
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
                        if(mouse.getButton() == 1) {
                            if(placedTowers[y1][x1] == null) {
                                switch (tower2place.getClass().getName()) {
                                    case "Praktikant":
                                        Praktikant p = (Praktikant) tower2place;
                                        placedTowers[y1][x1] = new Praktikant(p.gender);
                                        break;

                                    case "Arzt":
                                        Arzt a = (Arzt) tower2place;
                                        placedTowers[y1][x1] = new Arzt(a.type);
                                        break;
                                    
                                    case "Desinfektion":
                                        placedTowers[y1][x1] = new Desinfektion();
                                        break;

                                    case "Testzentrum":
                                        placedTowers[y1][x1] = new Testzentrum();
                                        break;

                                    case "Impfstofffabrik":
                                        placedTowers[y1][x1] = new Impfstofffabrik();
                                        break;
                            
                                    /* default:
                                        placedTowers[y1][x1] = new Testtower();
                                        break; */
                                }
                                if(placedTowers[y1][x1] == null) {
                                    setTower = true;
                                } else if(placedTowers[y1][x1].cost > money) {
                                    placedTowers[y1][x1] = null;
                                    alert("Zu wenig Impfstoffe!");
                                    alert.setLocation(96, 110);
                                } else {
                                    tower2place.hide();
                                    placedTowers[y1][x1].setPassive(true);
                                    placedTowers[y1][x1].setGridPos(x1, y1);
                                    addObject(placedTowers[y1][x1], x2, y2);
                                    changeMoney(placedTowers[y1][x1].cost * (-1));
                                    setTower = true;
                                }
                            } else if(tower2place.getClass().getName() == "Tower") {
                                removeObject(placedTowers[y1][x1]);
                                changeMoney(placedTowers[y1][x1].cost / 2); // 50% Rückerstattung
                                placedTowers[y1][x1] = null;
                                setTower = true;
                            }
                        }
                    }
                }
            }
        }
        return setTower;
    }
    public static int changeMoney(int deltaMoney) {
        money += deltaMoney;
        return money;
    }

    public void alert(String txt) {
        if(txt == "") {
            alert.hide();
        } else {
            alert.show();
            alert.setText(txt);
        }
    }

    public void act() {
        moneyAnz.setText("x" + Integer.toString(money));
        rundenAnz.setText("Runde " + Integer.toString(runde));

        if(placingTower) {
            if(addTower() || Greenfoot.isKeyDown("escape")) {
                placingTower = false;
                removeObject(tower2place);
                alert.hide();
            }
        }
        // Türme plazieren
        if(Greenfoot.mousePressed(null) && !placingTower) {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if(mouse != null) {
                if(mouse.getButton() == 1) {
                    for(ActorV2[] shopItem: shop) {
                        if(mouse.getActor() == (Actor) shopItem[0]) {
                            placingTower = true;
                            alert("Plaziere Turm (ESC zum Abbrechen)");
                            alert.setLocation(156, 110);
                            switch(shopItem[0].getClass().getName()) {
                                case "Praktikant":
                                    tower2place = new Praktikant(); break;
                                case "Arzt":
                                    tower2place = new Arzt(); break;
                                case "Desinfektion":
                                    tower2place = new Desinfektion(); break;
                                case "Testzentrum":
                                    tower2place = new Testzentrum(); break;
                                case "Impfstofffabrik":
                                    tower2place = new Impfstofffabrik(); break;
                                case "Tower":
                                    tower2place = new Tower("GUI\\Tower_sell.png");
                                    alert("Verkaufe Turm (ESC zum Abbrechen)");
                                    alert.setLocation(156, 110); break;
                            }
                            tower2place.setTransparency(100);
                            tower2place.setPassive(true);
                            addObject(tower2place,(twidth/2),(twidth/2)+2*twidth);
                        }
                    }
                }
            }
        }
        // Alle Türme aktivieren
        for(Tower[] column: placedTowers) {
            for(Tower tower: column) {
                if(tower != null) {
                    tower.setPassive(false);
                }
            }
        }

        // Map-Hintergrund ab einer bestimmten Runde ändern
        switch(runde) {
            case 10: // z.B.
                this.map.setImagePath("Maps\\Industrial + GUI 960x768x.png");
                break;
            default: break;
        }

        // Virenspawner
        if(rounds.length < runde) {
            alert("Du hast gewonnen!");
            alert.setLocation(86, 110);
            Greenfoot.stop();
        } else {
            if(vCooldown >= rounds[runde-1][0]) {
                if(viren.size() == 0) {
                    vCooldown = -1;
                    changeMoney(50); // Rundenbonus
                    continueButton.show();
                    runde++;
                }
            } else if(vCooldown == -1) {
                if(Greenfoot.mousePressed(null)) {
                    MouseInfo mouse = Greenfoot.getMouseInfo();
                    if(mouse != null) {
                        if(mouse.getButton() == 1 && mouse.getActor() == (Actor) continueButton) {
                            continueButton.hide();
                            vCooldown++;
                        }
                    }
                }
            } else {
                vCooldown++;
                if(vCooldown > -1) {
                    if(rounds[runde-1][1] != 0 && vCooldown % (rounds[runde-1][0] / rounds[runde-1][1]) == 0) {
                        Virus newVirus = new Virus1a();
                        viren.add(newVirus);
                        addObject(newVirus, map.absSpawnPos[1], map.absSpawnPos[0]);
                        virenAnzahl++;
                    }
                    if(rounds[runde-1][2] != 0 && vCooldown % (rounds[runde-1][0] / rounds[runde-1][2]) == 0) {
                        Virus newVirus = new Virus2b();
                        viren.add(newVirus);
                        addObject(newVirus, map.absSpawnPos[1], map.absSpawnPos[0]);
                        virenAnzahl++;
                    }
                    if(rounds[runde-1][3] != 0 && vCooldown % (rounds[runde-1][0] / rounds[runde-1][3]) == 0) {
                        Virus newVirus = new Virus3c();
                        viren.add(newVirus);
                        addObject(newVirus, map.absSpawnPos[1], map.absSpawnPos[0]);
                        virenAnzahl++;
                    }
                    if(rounds[runde-1][4] != 0 && vCooldown % (rounds[runde-1][0] / rounds[runde-1][4]) == 0) {
                        Virus newVirus = new Virus4d();
                        viren.add(newVirus);
                        addObject(newVirus, map.absSpawnPos[1], map.absSpawnPos[0]);
                        virenAnzahl++;
                    }
                }
            }
        }
        

        viren.sort(Comparator.comparing(a -> a.dist));
        for (Iterator<Virus> iterator = viren.iterator(); iterator.hasNext(); ) {
            Virus value = iterator.next();
            if (value.getWorld() == null) { // entweder tot oder weg, weil im Ziel
                iterator.remove();
            }
        }
        // System.out.println("game.viren: " + viren.toString());
    }

    private void prepare() {
        twidth = 64;
        hp = 100;
        runde = 1;
        money = 200;
        vCooldown = -1;

        setPaintOrder(new Class[]{ActorV2.class, Tower.class, Virus.class, DisplayText.class, Map.class});
        map = new Map("Maps\\Natural + GUI 960x768x.png",
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

        rounds = new int[][] {
            {         // RUNDE 1
                1220, // Anzahl der acts, die die Runde dauert (bedeutet: über diese Zeit verteilt werden alle Viren gespawnt)
                8,    // Anzahl der 1a-Viren, die insgesamt in der Runde gespawnt werden
                0,    // Anzahl der 2b-Viren
                0,    //            3c
                0     //            4d
            },

            {         // RUNDE 2
                1220,
                14,
                0,
                0,
                0
            },

            {        // RUNDE 3
                1220,
                26,	//a
                0,	//b
                0,	//c
                0,	//d
                0,	//e
                0,	//f
                0,	//g
                0 	//h
                    },
                {    	// RUNDE 4
                        1250,
                        38,	//a
                        0,	//b
                        0,	//c
                        0,	//d
                0,	//e
                0,	//f
                0,	//g
                0 	//h
                    },

                {        // RUNDE 5
                        1260,
                        49,	//a
                        2,	//b
                        0,	//c
                        0,	//d
                0,	//e
                0,	//f
                0,	//g
                0 	//h
                    },
            
                {    	// RUNDE 6
                        1290,
                        61,	//a
                        5,	//b
                        0,	//c
                        0,	//d
                0,	//e
                0,	//f
                0,	//g
                0 	//h
                    },

            
                {    	// RUNDE 7
                        1310,
                        79,	//a
                        8,	//b
                        0,	//c
                        0,	//d
                0,	//e
                0,	//f
                0,	//g
                0 	//h
                    },
            
                {    	// RUNDE 8
                        1340,
                        93,	//a
                        11,	//b
                        0,	//c
                        0,	//d
                0,	//e
                0,	//f
                0,	//g
                0 	//h
                    },
            
                {    	// RUNDE 9
                        1370,
                        101,	//a
                        14,	//b
                        0,	//c
                        0,	//d
                0,	//e
                0,	//f
                0,	//g
                0 	//h
                    },
            
                {    	// RUNDE 10
                        1420,
                        113,	//a
                        17,	//b
                        0,	//c
                        0,	//d
                0,	//e
                0,	//f
                0,	//g
                0 	//h
                    },
        };

        viren = new ArrayList<Virus>();
        virenAnzahl = 0;
        
        placedTowers = new Tower[10][13];
        placingTower = false;

        towerEfficiency = new double[10][13];
        for(int row = 0; row < towerEfficiency.length; row++) {
            for(int column = 0; column < towerEfficiency[row].length; column++) {
                towerEfficiency[row][column] = 1.0;
            }
        }

        // Die obere Leiste
        moneyIcon = new ActorV2("GUI\\Waehrung.png");
        addObject(moneyIcon, 520, 29);

        moneyAnz = new DisplayText("x0", new Font("Consolas", 16), Color.ORANGE);
        moneyAnz.setFixWidth(200);
        addObject(moneyAnz, 635, 30);

        rundenAnz = new DisplayText("Runde 1", new Font("Consolas", 16), Color.ORANGE);
        rundenAnz.setFixWidth(200);
        addObject(rundenAnz, 700, 30);

        hps = new ActorV2[10];
        for(int i=0; i < 10; i++) {
            hps[i] = new ActorV2("GUI\\Herz32px_1.png");
            addObject(hps[i], 30+35*i, 30);
        }

        continueButton = new ActorV2("GUI\\Play Button.png");
        continueButton.resize(108, 108);
        addObject(continueButton, 897, 63);
        
        // Seitenleiste: Shop

        shop = new ActorV2[20][3];
        int i = 0;
        for(Tower t: new Tower[]{new Praktikant(true), new Arzt(3), new Desinfektion(), new Testzentrum(), new Impfstofffabrik()}) {
            t.resize(64, 64);
            t.setPassive(true);
            shop[i][0] = t;
            addObject(shop[i][0], 896, 154+i*116);
            // shop[i][1] = new DisplayText(t.getClass().getName(), new Font("Consolas", 16), t.getClass().getName().length()*9);
            shop[i][1] = new DisplayText(t.getClass().getName(), new Font("Consolas", 15), Color.ORANGE);
            addObject(shop[i][1], 896, 197+i*116);
            // shop[i][2] = new DisplayText("Kosten: " + Integer.toString(t.cost), new Font("Consolas", 16), ("Kosten: " + Integer.toString(t.cost)).length()*9);
            shop[i][2] = new DisplayText("Kosten: " + Integer.toString(t.cost), new Font("Consolas", 15), Color.ORANGE);
            addObject(shop[i][2], 896, 215+i*116);

            i++;
        }
        shop[i][0] = new Tower("GUI\\Tower_sell.png");
        shop[i][0].resize(32, 32);
        addObject(shop[i][0], 896, 137+i*116);
        shop[i][1] = new DisplayText("Turm verkaufen", new Font("Consolas", 15), Color.ORANGE);
        addObject(shop[i][1], 896, 164+i*116);
        shop[i][2] = new DisplayText("50% Rückerstattung", new Font("Consolas", 11), Color.ORANGE);
        addObject(shop[i][2], 896, 175+i*116);

        alert = new DisplayText("", new Font("Consolas", 16), Color.WHITE);
        addObject(alert, 805, 110);
    }
}

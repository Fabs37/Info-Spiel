import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class Testzentrum extends Tower {
    Game g;
    private boolean efSet;
    public Testzentrum() {
        super("Tower\\Impfzelt_2.png");

        cost = 850;
        damage = 1.5; // In dem Fall der Effizienz-Multiplier
        efSet = false;
    }

    public void act() {
        if(!efSet) {
            if(!passive) {
                System.out.println("Getestet bei act: y = " + Integer.toString(yPos) + ", x = " + Integer.toString(xPos));
                int[][] relPos = {{0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}};
                for(int[] aktRelPos: relPos) {
                    try {
                        Game.towerEfficiency[yPos + aktRelPos[1]][xPos + aktRelPos[0]] *= damage;
                    } catch (Exception exc) {
                        
                    }
                }
            }
            efSet = true;
        }
    }
}

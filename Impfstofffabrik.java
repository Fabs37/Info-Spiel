import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Impfstofffabrik extends Tower {

    public Impfstofffabrik() {
        super("Tower\\Impfstofffabrik_1.png");

        this.cost = 1800;
        this.cooldown = 6;
        this.damage = 1; // Hier die Anzahl der Impfstoffe, die pro Produktionszyklus (siehe cooldown) dazukommen
    }

    public void act() {
        if(!passive) {
            aktCooldown--;
            if(aktCooldown < 1) {
                // updateEfficiency();
                Game.changeMoney((int) (damage));
                aktCooldown = cooldown;
            }
        }
    }    
}

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;

public class Praktikant extends Arzt {
    public Boolean gender;
    public Praktikant() {
        this.gender = new Random().nextBoolean();
        prepare();
    }

    public Praktikant(Boolean gender) {
        this.gender = gender;
        prepare();
    }

    private void prepare() {

        this.cost = 120;
        this.damage = 8;
        this.cooldown = 60;


        if(gender) {
            setImagePath("Tower\\Praktikantin_1.png");
        } else {
            setImagePath("Tower\\Praktikant_1.png");
        }
        resize(62, 62);
    }      
}

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class Homing extends Bullet {
    public Virus aim;
    public int speed;
    public double damage;
    
    
    public Homing(Virus aim, int speed, double damage) {
        this.aim = aim;
        this.speed = speed;
        this.damage = damage;
        GreenfootImage img = new GreenfootImage("Tower\\Spritze.png");
        img.rotate(90);
        setImage(img);
    }
    
    public void act() {
        if(aim.getWorld() == null) {
            this.getWorld().removeObject(this);
        } else {
            turnTowards(aim.getX(), aim.getY());
            move(speed);
            if(Math.abs(this.getX() - aim.getX()) < 2 && Math.abs(this.getY() - aim.getY()) < 2) {
                aim.hp -= this.damage;
                this.getWorld().removeObject(this);
            }
        }
    }    
}

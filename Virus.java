import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public abstract class Virus extends ActorV2{
    private int hp;
    public int[][] weg;
    public int dist;
    private int damage; // Anzahl der Leben, die gelöscht werden, wenn das Virus durchkommt
    
    public abstract void act();
    
}

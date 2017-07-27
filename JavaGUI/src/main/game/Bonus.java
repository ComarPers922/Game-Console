package main.game;

import java.awt.*;

public class Bonus extends GameObject
{
    private int healPoint = 0;
    private final int HP_SCALE = 10;
    public Bonus(Point position, Color color, int healPoint)
    {
        super(position, new Point(0,0), color);
        this.healPoint = healPoint;
        size = new Point(healPoint * HP_SCALE, healPoint * HP_SCALE);
    }

    public void heal(Player player)
    {
        player.heal(this);
        healPoint = 0;
        position = new Point(-10000,-1000);
    }

    public int getHealPoint()
    {
        return healPoint;
    }
}

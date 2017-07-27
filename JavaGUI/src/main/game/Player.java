package main.game;

import java.awt.*;

public class Player extends GameObject
{
    private final Bullet bullet;
    private final Thread coolDown;
    private int HP = 5;
    private volatile boolean readyToShoot = true;
    private final int HP_SCALE = 15;
    public Player(Point position, Color color, Rectangle border)
    {
        super(position, new Point(0,0), color);
        bullet = new Bullet(border, this);
        coolDown = new Thread(()->
        {
            while(true)
            {
                while(readyToShoot);
                try
                {
                    Thread.sleep(2500);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                readyToShoot = true;
            }
        });
        coolDown.start();
        size = new Point(HP * HP_SCALE,HP * HP_SCALE);
    }

    public int getHP()
    {
        return HP;
    }

    public void getHurt()
    {
        HP -= 2;
        size = new Point(HP * HP_SCALE,HP * HP_SCALE);
    }

    public void shoot()
    {
        if(!readyToShoot)
        {
            return;
        }
        readyToShoot = false;
        bullet.shoot(left);
        HP --;
        size = new Point(HP * HP_SCALE,HP * HP_SCALE);
    }

    public Bullet getBullet()
    {
        return bullet;
    }

    public void heal(Bonus bonus)
    {
        HP += bonus.getHealPoint();
        size = new Point(HP * HP_SCALE,HP * HP_SCALE);
    }
}

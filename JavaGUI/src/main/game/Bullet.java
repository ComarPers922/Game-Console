package main.game;

import java.awt.*;

public class Bullet extends GameObject
{
    private volatile Rectangle border;
    private final Player owner;

    private final Thread keepMoving;
    //private boolean readyToMove = true;
    public Bullet (Rectangle border, Player owner)
    {
        super(new Point(-100,-100), new Point(50,50), Color.RED);
        this.border = border;
        this.owner = owner;
        keepMoving = new Thread(() ->
        {
            while (true)
            {
                if (position.x < -50 || position.x > (this.border.width + 100))
                {
                    continue;
                }
                try
                {
                    Thread.sleep(10);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                this.move(new Point(isLeft() ? -100 : 100, 0));
            }
        });
        keepMoving.start();
    }
    public void hit()
    {
        position = new Point(-1000,-1000);
    }
    public Rectangle getBorder()
    {
        return border;
    }
    public void shoot(boolean left)
    {
        this.left = left;
        position = new Point(owner.position.x, owner.position.y + owner.size.y / 3);
    }

    public Player getOwner() {
        return owner;
    }
}

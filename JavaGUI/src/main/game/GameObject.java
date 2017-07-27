package main.game;

import java.awt.*;

/**
 * Created by ComarPers Leo on 7/17/2017.
 */
public class GameObject
{
    protected volatile Point position;

    private final Color color;

    protected Point size;

    protected boolean left = true;

    public Point getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }

    public GameObject(Point position, Point size, Color color)
    {
        this.position = position;
        this.color = color;
        this.size = size;
    }

    public Point getPosition()
    {
        return position;
    }

    public void move(Point point)
    {
        if(point.x < 0)
        {
            left = true;
        }
        else if(point.x > 0)
        {
            left = false;
        }
        position.move((int) (this.getPosition().x + point.x * .2),(int) (this.getPosition().y + point.y * .2));
    }

    public boolean isLeft()
    {
        return left;
    }

    public Rectangle getBounds()
    {
        return new Rectangle(position.x, position.y, size.x, size.y);
    }
}

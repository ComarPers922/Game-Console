package main.game;

import my.window.GameMainWindow;

import java.awt.*;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ComarPers Leo on 7/17/2017.
 */
public class GamePanel extends Panel
{
    private final ArrayList<GameObject> gameObjects = new ArrayList<>();
    private final ArrayList<Player> players = new ArrayList<>();
    private Bonus bonus = null;

    private Image bufferedImage;
    private Random random = new Random();

    public void addPlayer(Player player)
    {
        gameObjects.add(player);
        gameObjects.add(player.getBullet());
        players.add(player);
        update(getGraphics());
    }

    private void drawBufferedImage()
    {
        Image image = createImage(getWidth(),getHeight());
        Graphics graphics = image.getGraphics();
        if(GameMainWindow.isGameOver())
        {
            graphics.setFont(new Font("arial",Font.BOLD,180));
            graphics.setColor(Color.BLACK);
            graphics.drawString("Match Complete!",200,200);
            if(players.get(0).getHP() <= 0)
            {
                graphics.drawString("Winner:Player2!",200,400);
            }
            else if(players.get(1).getHP() <= 0)
            {
                graphics.drawString("Winner:Player1!",200,400);
            }
            else
            {
                graphics.drawString("Draw!",200,400);
            }
        }
        for (GameObject item : gameObjects)
        {
            if(item instanceof Player)
            {
                graphics.setColor(Color.GRAY);
                graphics.fillRect((item.isLeft() ? -item.getSize().x / 2 : item.getSize().x / 2) + item.getPosition().x, item.getPosition().y + 10, (int) (item.getSize().x * .8), (int) (item.getSize().y * .5));
                graphics.setFont(new Font("arial",Font.PLAIN,20));
                graphics.setColor(Color.GREEN);
                graphics.drawString(String.valueOf(((Player)item).getHP()),item.position.x,item.position.y);
            }
            graphics.setColor(item.getColor());
            graphics.fillArc(item.getPosition().x, item.getPosition().y, item.getSize().x, item.getSize().y, 0, 360);
            if(bonus != null)
            {
                graphics.fillArc(bonus.getPosition().x, bonus.getPosition().y, bonus.getSize().x, bonus.getSize().y, 0, 360);
            }
        }
        graphics.dispose();
        bufferedImage = image;
    }
    public void update(Graphics graphics)
    {
        for (Player item: players)
        {
            if(item.getHP() <= 0)
            {
                GameMainWindow.setGameOver();
            }
        }
        for(GameObject item : gameObjects)
        {
            if(item instanceof Bullet)
            {
                for (Player playerItem : players)
                {
                    if(item.getBounds().intersects(playerItem.getBounds()) && ((Bullet)item).getOwner() != playerItem)
                    {
                        ((Bullet)item).hit();
                        playerItem.getHurt();
                    }
                }
            }
        }
        try {
            if (bonus != null) {
                for (Player playerItem : players) {
                    if (playerItem.getBounds().intersects(bonus.getBounds())) {
                        bonus.heal(playerItem);
                        bonus = null;
                    }
                }
            }
        }
        catch (NullPointerException exception)
        {
            //exception.printStackTrace();
        }
        if(Math.abs(random.nextInt(10000)) > 9900 && bonus == null)
        {
            Point bonusPosition = new Point(Math.abs(random.nextInt(getWidth() - 100)), Math.abs(random.nextInt(getHeight() - 100)));
            bonus = new Bonus(bonusPosition, Color.ORANGE, Math.abs(random.nextInt(5)) + 1);
            for(Player item : players)
            {
                if(item.getBounds().intersects(bonus.getBounds()))
                {
                    bonus = null;
                    break;
                }
            }
        }
        drawBufferedImage();
        paint(graphics);
    }
    public void paint(Graphics g)
    {
        g.drawImage(bufferedImage,0,0,null);
    }
}

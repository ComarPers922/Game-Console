package my.window;

import main.game.GamePanel;
import main.game.MainFrame;
import main.game.NetworkHandler;
import main.game.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class GameWindow
{

    private static final GamePanel panel = new GamePanel();
    private static final MainFrame mainFrame = new MainFrame();
    private static Player player1;
    private static Player player2 ;
    private static final int SPEED = 100;
    //    private static final SerialReader reader1 = new SerialReader("COM3");
//    private static final SerialReader reader2 = new SerialReader("COM4");
    private static NetworkHandler networkHandlerForPlayer1;
    private static NetworkHandler networkHandlerForPlayer2;

    public GameWindow()
    {
        player1 = new Player(new Point(0,0),Color.BLACK, mainFrame.getBounds());
        player2 = new Player(new Point(0,0),Color.CYAN, mainFrame.getBounds());
        panel.setSize(mainFrame.getSize());
        mainFrame.add(panel);
        try
        {
            networkHandlerForPlayer1 = new NetworkHandler(7182);
            networkHandlerForPlayer1.setHandler(data -> {
                if(GameMainWindow.isGameOver())
                {
                    if(data.equals("RBX"))
                    {
                        GameMainWindow.exit();
                    }
                    return;
                }
                if (data.equals("RU"))
                {
                    player2.move(new Point(0, -SPEED));
                }
                else if (data.equals("RD"))
                {
                    player2.move(new Point(0, SPEED));
                }
                else if (data.equals("RL"))
                {
                    player2.move(new Point(-SPEED, 0));
                }
                else if (data.equals("RR"))
                {
                    player2.move(new Point(SPEED, 0));
                }
                if(data.equals("RBB"))
                {
                    player2.shoot();
                }
                panel.update(panel.getGraphics());
            });

            networkHandlerForPlayer2 = new NetworkHandler(7183);
            networkHandlerForPlayer2.setHandler(data -> {
                if(GameMainWindow.isGameOver())
                {
                    if(data.equals("LBX"))
                    {
                        GameMainWindow.exit();
                    }
                    return;
                }
                if (data.equals("LU"))
                {
                    player1.move(new Point(0, -SPEED));
                }
                else if (data.equals("LD"))
                {
                    player1.move(new Point(0, SPEED));
                }
                else if (data.equals("LL"))
                {
                    player1.move(new Point(-SPEED, 0));
                }
                else if (data.equals("LR"))
                {
                    player1.move(new Point(SPEED, 0));
                }
                if(data.equals("LBB"))
                {
                    player1.shoot();
                }
                panel.update(panel.getGraphics());
            });
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
        panel.addPlayer(player1);
        panel.addPlayer(player2);
//        reader2.setHandler(data -> {
//            if(data.equals("RU"))
//            {
//                player2.move(new Point(0,-10));
//            }
//            else if(data.equals("RD"))
//            {
//                player2.move(new Point(0,10));
//            }
//            else if(data.equals("RL"))
//            {
//                player2.move(new Point(-10,0));
//            }
//            else if(data.equals("RR"))
//            {
//                player2.move(new Point(10,0));
//            }
//            panel.update(panel.getGraphics());
//        });
//        reader1.setHandler(data -> {
//            if(data.equals("LU"))
//            {
//                player1.move(new Point(0,-10));
//            }
//            else if(data.equals("LD"))
//            {
//                player1.move(new Point(0,10));
//            }
//            else if(data.equals("LL"))
//            {
//                player1.move(new Point(-10,0));
//            }
//            else if(data.equals("LR"))
//            {
//                player1.move(new Point(10,0));
//            }
//            panel.update(panel.getGraphics());
//        });

        panel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e)
            {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(GameMainWindow.isGameOver())
                {
                    if(e.getKeyCode() == KeyEvent.VK_R)
                    {
                        GameMainWindow.exit();
                    }
                    return;
                }
                if(e.getKeyCode() == KeyEvent.VK_UP)
                {
                    player1.move(new Point(0,-SPEED));
                }
                else if(e.getKeyCode() == KeyEvent.VK_DOWN)
                {
                    player1.move(new Point(0,SPEED));
                }
                else if(e.getKeyCode() == KeyEvent.VK_LEFT)
                {
                    player1.move(new Point(-SPEED,0));
                }
                else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                {
                    player1.move(new Point(SPEED,0));
                }
                if(e.getKeyCode() == KeyEvent.VK_SPACE)
                {
                    player1.shoot();
                }

                if(e.getKeyCode() == KeyEvent.VK_W)
                {
                    player2.move(new Point(0,-SPEED));
                }
                else if(e.getKeyCode() == KeyEvent.VK_S)
                {
                    player2.move(new Point(0,SPEED));
                }
                else if(e.getKeyCode() == KeyEvent.VK_A)
                {
                    player2.move(new Point(-SPEED,0));
                }
                else if(e.getKeyCode() == KeyEvent.VK_D)
                {
                    player2.move(new Point(SPEED,0));
                }
                if(e.getKeyCode() == KeyEvent.VK_B)
                {
                    player2.shoot();
                }
                panel.update(panel.getGraphics());
            }

            @Override
            public void keyReleased(KeyEvent e)
            {

            }
        });
        mainFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e)
            {

            }

            @Override
            public void windowClosing(WindowEvent e)
            {

            }

            @Override
            public void windowClosed(WindowEvent e)
            {
                networkHandlerForPlayer1.close();
                networkHandlerForPlayer2.close();
            }

            @Override
            public void windowIconified(WindowEvent e)
            {

            }

            @Override
            public void windowDeiconified(WindowEvent e)
            {

            }

            @Override
            public void windowActivated(WindowEvent e)
            {

            }

            @Override
            public void windowDeactivated(WindowEvent e)
            {

            }
        });
        Thread refresh = new Thread(()->{
            if(GameMainWindow.isGameOver())
            {
                return;
            }
            while (true)
            {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                panel.update(panel.getGraphics());
            }
        });
        refresh.start();
        mainFrame.setVisible(true);
    }
    public void close()
    {
        networkHandlerForPlayer1.close();
        networkHandlerForPlayer2.close();
        mainFrame.setVisible(false);
        mainFrame.dispose();
    }
}

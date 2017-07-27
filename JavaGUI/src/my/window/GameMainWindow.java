package my.window;

/**
 * Created by ComarPers Leo on 7/17/2017.
 */
public class GameMainWindow
{
    private static volatile boolean gameOver = false;

    private static GameWindow gameWindow;

    public static void main(String[] args)
    {
        gameWindow = new GameWindow();
    }
    public static void setGameOver()
    {
        gameOver = true;
    }

    public static boolean isGameOver() {
        return gameOver;
    }

    public static void exit()
    {
        System.exit(0);
    }
}

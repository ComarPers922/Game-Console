package main.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by ComarPers Leo on 7/18/2017.
 */
public class NetworkHandler
{
    private final ServerSocket serverSocket;

    private Socket client;
    private EventHandler handler = null;

    private boolean closed = false;

    public void setHandler(EventHandler handler)
    {
        this.handler = handler;
    }

    public NetworkHandler(int port) throws IOException
    {
        serverSocket = new ServerSocket(port);

        Thread thread = new Thread(() ->
        {
            BufferedReader reader = null;
            try
            {
                client = serverSocket.accept();
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            }
            catch (IOException exception)
            {
                exception.printStackTrace();
                close();
            }

            while(!closed)
            {
                try
                {
                    while (!reader.ready())
                    {
                        if(!client.isConnected())
                        {
                            close();
                        }
                    }
                    /*char[] data = new char[client.getInputStream().available()];
                    reader.read(data);
                    String dataString = String.valueOf(data);*/
                    if(handler != null)
                    {
                        //handler.NewSerialInformationEvent(dataString);
                        handler.NewSerialInformationEvent(reader.readLine());
                    }
                }
                catch (IOException exception)
                {
                    exception.printStackTrace();
                    close();
                }
            }
            try
            {
                client.close();
                serverSocket.close();
                reader.close();
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                close();
            }
        });
        thread.start();
    }
    public void close()
    {
        closed = true;
        try
        {
            client.close();
            serverSocket.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
    public boolean isClosed()
    {
        return closed;
    }
    public static void main(String[] args)
    {
        try
        {
            NetworkHandler handler = new NetworkHandler(7182);
            handler.setHandler(data -> System.out.println(data));
            Scanner scanner = new Scanner(System.in);
            scanner.next();
        }
        catch(IOException exception)
        {
            exception.printStackTrace();
        }
    }
}

package main.game;

import gnu.io.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TooManyListenersException;

public class SerialReader implements SerialPortEventListener
{
    private final String portName;
    private static final int DATA_RATE = 9600;

    private BufferedReader inputStream;

    private EventHandler handler = null;

    public void setHandler(EventHandler handler)
    {
        this.handler = handler;
    }

    public SerialReader(String portName)
    {
        this.portName = portName;
        try
        {
            CommPortIdentifier identifier = CommPortIdentifier.getPortIdentifier(portName);
            SerialPort port = (SerialPort)identifier.open("123",1000);
            inputStream = new BufferedReader(new InputStreamReader(port.getInputStream()));
            port.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            port.addEventListener(this);
            port.notifyOnDataAvailable(true);
        }
        catch (NoSuchPortException exception)
        {
            exception.printStackTrace();
        }
        catch (PortInUseException exception)
        {
            exception.printStackTrace();
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
        catch (UnsupportedCommOperationException exception)
        {
            exception.printStackTrace();
        }
        catch (TooManyListenersException exception)
        {
            exception.printStackTrace();
        }
    }
    @Override
    public void serialEvent(SerialPortEvent serialPortEvent)
    {
        if(serialPortEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try
            {
                String data = inputStream.readLine();
                if (handler != null && !data.equals("OK"))
                {
                    handler.NewSerialInformationEvent(data);
                }
            } catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }
}
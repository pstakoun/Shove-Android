package com.stakoun.shove;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by Peter on 2016-05-06.
 */
public class ServerConnection
{
    private GameActivity gameActivity;
    private String host;
    private int port;
    private DatagramSocket udpClient;
    private InetAddress address;
    private byte[] data = new byte[1024];

    public ServerConnection(GameActivity gameActivity, String host, int port) throws Exception
    {
        this.gameActivity = gameActivity;
        this.host = host;
        this.port = port;
        udpClient = new DatagramSocket();
        InitAddressTask initAddressTask = new InitAddressTask();
        initAddressTask.execute();
        initAddressTask.get();
    }

    public void update(Player player)
    {
        new UpdateTask().execute(player);
    }

    private class InitAddressTask extends AsyncTask<Void, Void, Void>
    {
        protected Void doInBackground(Void... args)
        {
            try {
                address = InetAddress.getByName(host);
            } catch (IOException e) {
                Log.d("InitAddressTask", e.getMessage());
            }
            return null;
        }

    }

    private class UpdateTask extends AsyncTask<Player, Void, Void>
    {
        protected Void doInBackground(Player... args)
        {
            DatagramPacket in = new DatagramPacket(data, data.length);

            String str = args[0].toString();
            data = str.getBytes();

            DatagramPacket out = new DatagramPacket(data, data.length, address, port);

            try {
                udpClient.send(out);
            } catch (IOException e) {
                Log.d("UpdateTask", e.getMessage());
            }

            try {
                udpClient.receive(in);
            } catch (IOException e) {
                Log.d("UpdateTask", e.getMessage());
            }
            str = new String(in.getData(), 0, in.getLength());

            Log.d("UpdateTask", str);

            gameActivity.updatePlayers(str);

            return null;
        }

    }

}

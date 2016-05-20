package com.stakoun.shove;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

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
    private int numTasks;
    private int numSent;

    public ServerConnection(GameActivity gameActivity, String host, int port) throws Exception
    {
        this.gameActivity = gameActivity;
        this.host = host;
        this.port = port;
        udpClient = new DatagramSocket();
        udpClient.setSoTimeout(1);
        numTasks = 0;
        numSent = 0;
        InitAddressTask initAddressTask = new InitAddressTask();
        initAddressTask.execute();
        initAddressTask.get();
    }

    public void update(String str)
    {
        new UpdateTask().execute(str);
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

    private class UpdateTask extends AsyncTask<String, Void, Void>
    {
        protected Void doInBackground(String... args)
        {
            Log.d("UpdateTask", "Sent: "+args[0]+"\n"+(++numTasks)+" tasks "+numSent+++" sent");

            byte[] data = args[0].getBytes();

            DatagramPacket out = new DatagramPacket(data, data.length, address, port);
            try {
                udpClient.send(out);
            } catch (IOException e) {
                Log.d("UpdateTask", e.getMessage());
            }

            data = new byte[1024];

            DatagramPacket in = new DatagramPacket(data, data.length);
            long start = System.currentTimeMillis();
            String result = null;
            while (System.currentTimeMillis() - start < 20) {
                try {
                    udpClient.receive(in);
                    result = new String(in.getData(), 0, in.getLength());
                    Log.d("UpdateTask", "Received: "+result);
                } catch (IOException e) {
                    Log.d("UpdateTask", "Something bad happened");
                    break;
                }
            }

            if (result != null) {
                gameActivity.updatePlayers(result);
            }

            numTasks--;

            return null;
        }

    }

}

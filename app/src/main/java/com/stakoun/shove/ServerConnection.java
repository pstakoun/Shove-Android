package com.stakoun.shove;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * The ServerConnection class handles the transmission of information to and from the server.
 * 3.1a Using classes
 *
 * @author Peter Stakoun
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

    /**
     * The default constructor for the ServerConnection class.
     * 3.1b Using constructors
     *
     * @param gameActivity GameActivity in which the ServerConnection is stored
     * @param host server host
     * @param port server port
     * @throws Exception
     */
    public ServerConnection(GameActivity gameActivity, String host, int port) throws Exception
    {
        // Initialize instance variables
        this.gameActivity = gameActivity;
        this.host = host;
        this.port = port;

        // Initialize client socket
        udpClient = new DatagramSocket();
        udpClient.setSoTimeout(1);

        // Initialize debug variables
        numTasks = 0;
        numSent = 0;

        // Initialize server address
        InitAddressTask initAddressTask = new InitAddressTask();
        initAddressTask.execute();
        initAddressTask.get();
    }

    /**
     * The update method starts a new UpdateTask to send Player information to the server.
     *
     * @param str a String representation of the user's Player
     */
    public void update(String str)
    {
        new UpdateTask().execute(str);
    }

    /*
     * The InitAddressTask initializes the server address.
     */
    private class InitAddressTask extends AsyncTask<Void, Void, Void>
    {
        /*
         * The doInBackground method is called on the execution of the InitAddressTask and sets up the server address.
         */
        protected Void doInBackground(Void... args)
        {
            // Create InetAddress from given host
            try {
                address = InetAddress.getByName(host);
            } catch (IOException e) {
                Log.d("InitAddressTask", e.getMessage());
            }
            return null;
        }

    }

    /*
     * The UpdateTask class sends updated Player data to the server and receives any incoming packets.
     */
    private class UpdateTask extends AsyncTask<String, Void, Void>
    {
        /*
         * The doInBackground method is called on the execution of the UpdateTask and sends and receives packets.
         */
        protected Void doInBackground(String... args)
        {
            // Log contents of the packet, number of packets sent, and number of active tasks
            Log.d("UpdateTask", "Sent: "+args[0]+"\n"+(++numTasks)+" tasks "+numSent+++" sent");

            // Store packet contents as bytes
            byte[] data = args[0].getBytes();

            // Create outgoing packet
            DatagramPacket out = new DatagramPacket(data, data.length, address, port);

            // Send outgoing packet
            try {
                udpClient.send(out);
            } catch (IOException e) {
                Log.d("UpdateTask", e.getMessage());
            }

            // Reinitialize byte storage
            data = new byte[1024];

            // Create incoming packet
            DatagramPacket in = new DatagramPacket(data, data.length);

            // Store current time as start time
            long start = System.currentTimeMillis();
            String result = null;
            // Receive packets until finished or 20ms passed
            // 3.1d Using loops
            while (System.currentTimeMillis() - start < 20) {
                // Receive packet and store contents
                try {
                    udpClient.receive(in);
                    result = new String(in.getData(), 0, in.getLength());
                    Log.d("UpdateTask", "Received: "+result);
                } catch (IOException e) {
                    Log.d("UpdateTask", "Something bad happened");
                    break;
                }
            }

            // Update Players with new data from packet
            // 3.1e Using if
            if (result != null) {
                gameActivity.updatePlayers(result);
            }

            numTasks--;
            return null;
        }

    }

}

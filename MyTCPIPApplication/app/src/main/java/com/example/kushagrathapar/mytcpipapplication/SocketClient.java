package com.example.kushagrathapar.mytcpipapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Created by kushagrathapar on 10/13/15.
 */

public class SocketClient extends Activity {

    private Socket socket;

    private static final int SERVERPORT = 5000;
    private static final String SERVER_IP = "10.0.2.2";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        new Thread(new ClientThread()).start();
    }

    public int nextPoisson() {
        double lambda = 10;
        Random r = new Random();
        double elambda = Math.exp(-1* lambda);
        double product = 1;
        int count =  0;
        int result=0;
        while (product >= elambda) {
            product *= r.nextDouble();
            result = count;
            count++; // keep result one behind
        }
        return result;
    }

    public void onClick(View view) {
        try {

            byte[] bytes;
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            int i = 0, j;
            while (i <= 1000) {
                Queue<Packet> queue = new LinkedList<>();
                Packet packet = new Packet();
                int num = nextPoisson();
                packet.number = String.valueOf(num);
                bytes = String.valueOf(num).getBytes();
                packet.setData(bytes);
                Log.d("outstream", "The random num [" + num + "] in to the queue");
                for (j = 0; j < num; j++) {
                    queue.offer(packet);
                }
                if (queue.size() >= 12) {
                    int count = 0;
                    while (count < 12) {
                        packet = queue.poll();
                        try {
                            outputStreamWriter.write(packet.number);
                            outputStreamWriter.flush();
                        } catch (IOException e) {

                            e.printStackTrace();
                        }
                        count++;
                    }
                    Log.d("outstream", "The num: [" + num + "] is sent to the server");
                } else {
                    while (!queue.isEmpty()) {
                        packet = queue.poll();
                        try {
                            outputStreamWriter.write(packet.number);
                            outputStreamWriter.flush();
                        } catch (IOException e) {

                            e.printStackTrace();
                        }
                    }
                    Log.d("outstream", "the num: [" + packet.number + "] is sent to the server");
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Log.d("Outstream", "Client is finished sending the messages now.");
        }
    }

    class ClientThread implements Runnable {

        @Override
        public void run() {

            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

                socket = new Socket(serverAddr, SERVERPORT);


            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }
}

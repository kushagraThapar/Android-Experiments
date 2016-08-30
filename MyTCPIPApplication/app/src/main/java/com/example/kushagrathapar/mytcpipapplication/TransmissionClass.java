package com.example.kushagrathapar.mytcpipapplication;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Created by kushagrathapar on 11/12/15.
 */

class TransmissionClass implements Runnable {
    Packet packet;
    int nextPoisson(double lambda) {
        lambda=10;
        Random r = new Random();
        double elambda = Math.exp(-1*lambda);
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
    public void run() {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new
                    ServerSocket(5000);
        } catch (IOException e) {

            e.printStackTrace();
        }
        Socket socket = null;
        try {
            socket = serverSocket.accept();
        } catch (IOException e) {

            e.printStackTrace();
        }
        int i=0,j=0;
        while(i<=1000)
        {
            Queue<Packet> queue= new LinkedList<>();
            packet = new Packet();
            int num=nextPoisson(10);
            for(j=0;j<num;j++)
            {
                queue.offer(packet);
                Log.d("outstream", "the random num:  in to the queue");
            }
            if(queue.size()>=12)
            {
                while(i<=12)
                {
                    queue.poll();
                    try {
                        socket.getOutputStream().write(packet.getData());
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                    Log.d("outstream","the num: is sent to the client");
                }
            }
            else
            {while(!queue.isEmpty())
            {
                queue.poll();
                try {
                    socket.getOutputStream().write(packet.getData());
                } catch (IOException e) {

                    e.printStackTrace();
                }
                Log.d("outstream","the num:  is sent to the client");
            }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}

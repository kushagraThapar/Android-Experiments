package com.example.kushagrathapar.mytcpipapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kushagrathapar on 10/13/15.
 */

public class SocketServer extends Activity {

    private ServerSocket serverSocket;

    Handler updateConversationHandler;

    Thread serverThread = null;

    private ListView listView;

    public static final int SERVERPORT = 6000;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        listView = (ListView) findViewById(R.id.listView);

        updateConversationHandler = new Handler();

        this.serverThread = new Thread(new ServerThread());
        this.serverThread.start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ServerThread implements Runnable {

        public void run() {
            Socket socket = null;
            try {
                serverSocket = new ServerSocket(SERVERPORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!Thread.currentThread().isInterrupted()) {

                try {

                    socket = serverSocket.accept();

                    CommunicationThread commThread = new CommunicationThread(socket);
                    new Thread(commThread).start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class CommunicationThread implements Runnable {

        private Socket clientSocket;

        private BufferedReader input;

        public CommunicationThread(Socket clientSocket) {

            this.clientSocket = clientSocket;

            try {

                this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            List<String> numbers = new ArrayList<>();
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    char[] buffer = new char[2];
                    this.input.read(buffer);

                    String read = new String(buffer);
                    numbers.add(read);
                    Log.d("outstream", read);
                }
                updateConversationHandler.post(new updateUIThread(numbers));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Log.d("Outstream", "Server is finished receiving all the messages");
            }
        }

    }

    class updateUIThread implements Runnable {
        private List<String> list;

        public updateUIThread(List<String> stringList) {
            this.list = stringList;
        }

        @Override
        public void run() {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(new SocketServer(), R.layout.activity_server, list);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}

package com.example.kushagrathapar.mytcpipapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by kushagrathapar on 10/13/15.
 */

public class SocketServer extends Activity {

    private ServerSocket serverSocket;

    Handler updateConversationHandler;

    Thread serverThread = null;

    private TextView text;

    public static final int SERVERPORT = 6000;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        text = (TextView) findViewById(R.id.text2);

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

            while (!Thread.currentThread().isInterrupted()) {

                try {

                    File file = new File(
                            Environment.getExternalStorageDirectory(),
                            "test3.png");

                    ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                    FileOutputStream fos;
                    byte[] bytes;
                    try {
                        bytes = (byte[])ois.readObject();
                        fos = new FileOutputStream(file);
                        fos.write(bytes);
                        fos.close();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    String read = "File has been copied successfully";
                    updateConversationHandler.post(new updateUIThread(read));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    class updateUIThread implements Runnable {
        private String msg;

        public updateUIThread(String str) {
            this.msg = str;
        }

        @Override
        public void run() {
            text.setText(text.getText().toString()+"Client Says: "+ msg + "\n");
        }
    }
}

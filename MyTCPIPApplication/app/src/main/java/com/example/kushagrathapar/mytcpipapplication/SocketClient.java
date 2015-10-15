package com.example.kushagrathapar.mytcpipapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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

    public void onClick(View view) {
        try {
            File file = new File(
                    Environment.getExternalStorageDirectory(),
                    "newImage.png");

            byte[] bytes = new byte[(int) file.length()];
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file), 8192);
            bis.read(bytes, 0, bytes.length);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(bytes);
            oos.flush();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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

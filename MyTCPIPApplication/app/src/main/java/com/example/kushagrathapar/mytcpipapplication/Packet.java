package com.example.kushagrathapar.mytcpipapplication;

/**
 * Created by kushagrathapar on 11/12/15.
 */
public class Packet {

    public byte[] data;
    public String number;

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }
}

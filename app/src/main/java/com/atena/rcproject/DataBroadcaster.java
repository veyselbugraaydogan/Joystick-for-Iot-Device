package com.atena.rcproject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class DataBroadcaster implements GameChanger{

    private Socket socket;
    private String ip="192.168.43.137";
    private int port=8000;
    private int data=100;

    public DataBroadcaster(){
        socket = new Socket();
    }

    void broadcast(){

        try {
            socket.connect(new InetSocketAddress(ip, port), 1000);
            DataOutputStream DataOut = new DataOutputStream(socket.getOutputStream());
            DataOut.writeInt(data);
            DataOut.flush();

            socket.close();



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onTerminate() {

    }
}

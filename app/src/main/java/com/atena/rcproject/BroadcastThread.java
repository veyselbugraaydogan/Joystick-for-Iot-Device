package com.atena.rcproject;

import android.util.Log;
import android.view.SurfaceHolder;

public class BroadcastThread extends Thread {
    public final int MAX_FPS;
    private double avarageFPS;
    private ControlPanel controlPanel;
    private boolean running;
    private DataBroadcaster dataBroadcaster;


    public  void setRunning(boolean running){
        this.running = running;
    }


    public BroadcastThread(SurfaceHolder surfaceHolder, ControlPanel controlPanel, int MAX_FPS){
        super();
        this.controlPanel = controlPanel;
        this.dataBroadcaster=dataBroadcaster;
        this.MAX_FPS=MAX_FPS;

    }

    @Override
    public void run(){
        Log.v("UI THREAD", "STARTED");
        long startTime = 0;
        long timeMillis=1000/MAX_FPS;
        long waitTime;
        long frameCount=0;
        long totalTime=0;
        long targetTime=1000/MAX_FPS;

        while (running) {

            startTime = System.nanoTime();

            //Burada işlemler yapilacak
            controlPanel.broadcestData();

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;
            try {
                if (waitTime > 0) this.sleep(waitTime);
            } catch (Exception e) {

                e.printStackTrace();
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;

            if (frameCount == MAX_FPS) {

                avarageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                Log.v("AvarageFps = ", avarageFPS + "");

            }
        }
    }

}

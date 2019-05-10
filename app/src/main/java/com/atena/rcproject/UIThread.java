package com.atena.rcproject;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class UIThread extends Thread{
    public final int MAX_FPS;
    private double avarageFPS;
    private SurfaceHolder surfaceHolder;
    private boolean running;
    private ControlPanel controlPanel;
    public static Canvas canvas;


    public  void setRunning(boolean running){
        this.running = running;
    }


    public UIThread(SurfaceHolder surfaceHolder, ControlPanel controlPanel, int MAX_FPS){
        super();
        this.surfaceHolder=surfaceHolder;
        this.controlPanel = controlPanel;
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
            canvas = null;


            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.controlPanel.update(canvas);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

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

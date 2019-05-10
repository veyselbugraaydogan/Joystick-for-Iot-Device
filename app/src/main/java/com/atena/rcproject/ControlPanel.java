package com.atena.rcproject;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ControlPanel extends SurfaceView implements SurfaceHolder.Callback{

    private DataBroadcaster dataBroadcaster;
    private UI ui;

    private UIThread uiThread;
    private BroadcastThread broadcastThread;

    public ControlPanel(Context context) {
        super(context);
        getHolder().addCallback(this);

        uiThread = new UIThread(getHolder(),this,60);
        broadcastThread = new BroadcastThread(getHolder(),this,30);

        ui=new UI();
        dataBroadcaster = new DataBroadcaster();

        setFocusable(true);
    }

    void update(Canvas canvas){

        super.draw(canvas);
        ui.draw(canvas);

    }

    void broadcestData(){
        dataBroadcaster.broadcast();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.v("SURFACE CREATED", "SURFACE CREATED");
        uiThread = new UIThread(getHolder(),this,30);
        broadcastThread = new BroadcastThread(getHolder(),this,30);

        uiThread.setRunning(true);
        broadcastThread.setRunning(true);

        uiThread.start();
        broadcastThread.start();
        ui.onCreate();
        dataBroadcaster.onCreate();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        ui.onCreate();
        dataBroadcaster.onCreate();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        ui.onTerminate();
        dataBroadcaster.onTerminate();

        boolean retry = true;

        while(retry){
            try {
                uiThread.setRunning(false);
                uiThread.join();
                broadcastThread.setRunning(false);
                broadcastThread.join();
                //updater.setRunning(false);
                //updater.join();
            }catch(Exception e){
                e.printStackTrace();
            }
            retry = false;
        }

    }

    @Override
    public boolean onTouchEvent (MotionEvent event){

        ui.recieveTouch(event);

        return true;
        //return super.onTouchEvent(event);
    }


}

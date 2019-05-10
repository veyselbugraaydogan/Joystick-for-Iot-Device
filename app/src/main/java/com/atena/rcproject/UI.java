package com.atena.rcproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

public class UI implements GameChanger{

    private int joystickXPosition=Constants.SCREEN_WIDTH/2;
    private int joystickYPosition=Constants.SCREEN_HEIGHT/2;
    private Joystick joystick;
    private int toX=0,toY=0;

    private double maxPower=1023;
    private double minPower=400;

    private int joystictSize=500;


    public UI(){

        BitmapFactory bitmapFactory=new BitmapFactory();
        Bitmap jstouch = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.joystick_touch);
        Bitmap jstaban = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.joystick_taban);

        joystick = new Joystick(jstaban,jstouch,joystictSize,new Point(joystickXPosition,joystickYPosition));
    }

    public void draw(Canvas canvas){
        toX=joystickXPosition-Constants.SCREEN_WIDTH/2;
        toY=Constants.SCREEN_HEIGHT/2 - joystickYPosition;
        //Log.v("x,y","x : "+toX + "  y :" + toY );
        Log.v("Power", ":"+getPower(toY) );
        joystick.update(joystickXPosition,joystickYPosition);
        joystick.draw(canvas);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onTerminate() {


    }

    public void recieveTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            double x=event.getX()-Constants.SCREEN_WIDTH/2;
            double y=Constants.SCREEN_HEIGHT/2 - event.getY();
            if(Math.sqrt(x*x+y*y) < joystictSize/2) {

                joystickXPosition = (int) event.getX();
                joystickYPosition = (int) event.getY();

            }else {
                joystickXPosition = (int)(Math.cos(Math.toRadians(getAngle(x,y))) * (joystictSize / 2)+Constants.SCREEN_WIDTH/2);
                joystickYPosition = (int) (Math.sin(Math.toRadians(getAngle(x,y))) * -(joystictSize / 2)+Constants.SCREEN_HEIGHT/2);
            }
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {

            double x=event.getX()-Constants.SCREEN_WIDTH/2;
            double y=Constants.SCREEN_HEIGHT/2 - event.getY();

            if(Math.sqrt(x*x+y*y) > joystictSize/2) {

                joystickXPosition = (int)(Math.cos(Math.toRadians(getAngle(x,y))) * (joystictSize / 2)+Constants.SCREEN_WIDTH/2);
                joystickYPosition = (int) (Math.sin(Math.toRadians(getAngle(x,y))) * -(joystictSize / 2)+Constants.SCREEN_HEIGHT/2);
                //joystickXPosition += (joystictSize / 2);
               // joystickYPosition += (joystictSize / 2);

            }else{


                joystickXPosition=(int)event.getX();
                joystickYPosition=(int)event.getY();

            }

        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            joystickXPosition=Constants.SCREEN_WIDTH/2;
            joystickYPosition=Constants.SCREEN_HEIGHT/2;

        }
    }


    private double getPower(int toY){
        if(toY==0) return 0;
        return ((maxPower - minPower)/(joystictSize/2)*toY)+minPower;
    }

    public double getAngle(double x, double y) {
        if(x >= 0 && y >= 0)
            return Math.toDegrees(Math.atan(y / x));
        else if(x < 0 && y >= 0)
            return Math.toDegrees(Math.atan(y / x)) + 180;
        else if(x < 0 && y < 0)
            return Math.toDegrees(Math.atan(y / x)) + 180;
        else if(x >= 0 && y < 0)
            return Math.toDegrees(Math.atan(y / x)) + 360;
        return 0;
    }

}

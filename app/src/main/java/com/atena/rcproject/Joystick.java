package com.atena.rcproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

public class Joystick {
    private Rect tabanRect,stickRect;
    private Bitmap tabanImage,stickImage;
    private int size,stickSize;
    int x,y;

    public Joystick(Bitmap tabanImage, Bitmap stickImage, int size, Point point){

        this.tabanImage = tabanImage;
        this.stickImage=stickImage;
        this.size=size;
        x=point.x;
        y=point.y;

        tabanRect = new Rect((Constants.SCREEN_WIDTH-size)/2,(Constants.SCREEN_HEIGHT-size)/2,
                (Constants.SCREEN_WIDTH+size)/2,(Constants.SCREEN_HEIGHT+size)/2);


        stickSize = size/3;

        stickRect = new Rect((Constants.SCREEN_WIDTH-stickSize)/2,(Constants.SCREEN_HEIGHT-stickSize)/2,
                (Constants.SCREEN_WIDTH+stickSize)/2,(Constants.SCREEN_HEIGHT+stickSize)/2);


    }

    public Rect getTabanRect() {
        return tabanRect;
    }


    public void draw(Canvas canvas) {

        canvas.drawBitmap(tabanImage,null, tabanRect,null);
        canvas.drawBitmap(stickImage,null, stickRect,null);

    }



    public void update(int x,int y) {
        stickRect.left=x-stickSize/2;
        stickRect.right=x+stickSize/2;
        stickRect.top=y-stickSize/2;
        stickRect.bottom=y+stickSize/2;
    }



}

package com.example.anupam.dxball;



import android.graphics.Paint;

import java.security.PrivateKey;

/**
 * Created by Anupam on 8/14/2017.
 */
public class Ball {
    private int x;
    private int y;
    private int c;
    private int r;
    private  int dx;
    private int dy;
    private Paint paint;

    public  Ball(int x,int y,int col,int radius){
        this.x = x;
        this.y = y;
        c = col;
        r = radius;
        paint=new Paint();
        paint.setColor(c);
        dx = 0;
        dy = 0;
    }
    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    public int getC() {
        return c;
    }

    public int getRadius() {
        return r;
    }

    public Paint getPaint() {
        return paint;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public void setColor(int col){
        c = col;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void move(){
        x = x + dx;
        y = y + dy;
    }
}

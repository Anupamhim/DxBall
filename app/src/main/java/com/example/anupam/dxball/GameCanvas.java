package com.example.anupam.dxball;

/**
 * Created by Anupam on 8/14/2017.
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class GameCanvas extends View {

    Paint paint;
    float x=0,y=0;
    boolean firstTime = true;
    float barX=0,barY=0,barWidth=0,barHeight=0;

    public static int life = 3, CanvasX, CanvasY;
    private float touchScreenX = 0;

    public static boolean gameOver;
    public static boolean newLife;
    public static int  score = 0;
    public static int highScore = 0;

    ArrayList<Objects> bricks = new ArrayList<Objects>();
    float brickX = 0, brickY = 50;
    int BrickColor1, BrickColor2;
    int row = 0, ballSpeed = 5;//level should be added

    Ball ball;
    Brick bar;


    public GameCanvas(Context context) {
        super(context);
        paint = new Paint();
        life = 3;
        gameOver = false;
        newLife = false;
    }

    protected void onDraw(Canvas canvas)
    {



        if(firstTime)
        {
            firstTime = false;
            CanvasX = canvas.getWidth();
            CanvasY = canvas.getHeight();

            row = 4;
            BrickColor1 = Color.rgb(128,0,0);

            //adding brick to the object.................................
            for(int j = 0; j < row; j++){
                int color;
                for(int i = 0; i < 5 ; i++){
                    if(brickX >= canvas.getWidth()) {
                        brickX = 0;
                        brickY += 20;
                    }

                    color = BrickColor1;
                    bricks.add(new Objects(brickX, brickY, brickX+(canvas.getWidth()/5)-2, brickY+17,color));
                    brickX += canvas.getWidth() / 5;
                }
            }


            //adding the ball....................................
            ball = new Ball( canvas.getWidth()/2, canvas.getHeight()/2, Color.BLUE, 30);
            ball.setDx(ballSpeed);
            ball.setDy(-ballSpeed);


            //adding the bar.....................................
            /*barX = getWidth() / 2 - (barWidth / 2);
            barY = getHeight() - 20;
            barWidth = getWidth() / 2 + (barWidth / 2);
            barHeight = getHeight();*/
            barX = 250;
            barY = 800;
            barWidth = 450;
            barHeight = 850;
            bar = new Brick(barX, barY, barWidth, barHeight, Color.RED);

          /*  x  = canvas.getWidth() / 2;
            y  = canvas.getHeight() / 2;
            barX = 250;
            barY = 800;
            barWidth = 450;
            barHeight = 850;*/
        }

        /*calculateNextPos();

        canvas.drawRGB(255, 255, 255);
        paint.setColor(Color.RED);
        paint.setStyle(Style.FILL);*/

        canvas.drawRect(barX,barY,barWidth,barHeight, paint);

       // canvas.drawCircle(x,y, 30, paint);



        if(bricks.size() <= 0){
            gameOver = true;
            newLife = false;
            firstTime = false;

        }

        if(newLife && !firstTime) {
            newLife = false;

            if(life == 2 || life == 3){
                ball = new Ball(canvas.getWidth()/2,canvas.getHeight()-50, Color.rgb(128, 38, 165), 30);
            }

            else if(life == 1){
                ball = new Ball(canvas.getWidth()/2,canvas.getHeight()-50, Color.rgb(255, 10, 37), 30);
            }

            ball.setDx(ballSpeed);
            ball.setDy(-ballSpeed);
        }

        if(gameOver){
            Toast.makeText(getContext(), "Game Over ", Toast.LENGTH_LONG).show();

           // Toast.makeText(getContext(), "Score is "+score, Toast.LENGTH_LONG).show();
            Intent i = new Intent(getContext(), MainActivity.class);
            getContext().startActivity(i);
        }
        paint.setTextSize(30);
        paint.setFakeBoldText(true);
        paint.setColor(Color.BLACK);
        canvas.drawText("Score: "+score, 10, 30, paint);

        paint.setTextSize(30);
        paint.setFakeBoldText(true);
        paint.setColor(Color.BLACK);
        canvas.drawText("Life: "+life, 150, 30, paint);


        canvas.drawCircle(ball.getX(), ball.getY(), ball.getRadius(), ball.getPaint());

        canvas.drawRect(bar.getLeft(), bar.getTop(), bar.getRight(), bar.getBottom(), bar.getPaint());

        for(int i=0; i < bricks.size(); i++){
            canvas.drawRect(bricks.get(i).getLeft(),bricks.get(i).getTop(),bricks.get(i).getRight(),bricks.get(i).getBottom(),bricks.get(i).getPaint());
        }

        this.ballBrickCollision(canvas);
        this.ballBarCollision(canvas);
        this.ballBoundaryCollision(canvas);

        ball.move();
        //bar.moveBar(barX,barY);

        invalidate();
    }

    public void ballBoundaryCollision(Canvas canvas) {
        if((ball.getY() - ball.getRadius()) >= canvas.getHeight()){
            life -= 1;
            newLife = true;
        }

        if(life == 0){
            gameOver = true;
            firstTime = false;
        }
        else{
            if((ball.getX() + ball.getRadius()) >= canvas.getWidth() || (ball.getX() - ball.getRadius()) <= 0){
                ball.setDx(-ball.getDx());
            }

            if( (ball.getY() - ball.getRadius()) <= 0){
                ball.setDy(-ball.getDy());
            }
        }
    }

    public void ballBarCollision(Canvas canvas){
        if(((ball.getY() + ball.getRadius()) >= bar.getTop()) && ((ball.getY()+ball.getRadius()) <= bar.getBottom()) && ((ball.getX()) >= bar.getLeft()) && ((ball.getX()) <= bar.getRight())) {
            ball.setDy(-(ball.getDy()));
        }

    }

    public void ballBrickCollision(Canvas canvas){
        for(int i=0; i < bricks.size(); i++) {
            if (((ball.getY() - ball.getRadius()) <= bricks.get(i).getBottom()) && ((ball.getY() + ball.getRadius()) >= bricks.get(i).getTop()) && ((ball.getX()) >= bricks.get(i).getLeft()) && ((ball.getX()) <= bricks.get(i).getRight())) {
                score += 5;
                bricks.remove(i);
                ball.setDy(-(ball.getDy()));
            }
        }

    }



    public boolean onTouchEvent(MotionEvent e){

        touchScreenX = e.getX();
        if(touchScreenX>200)
        {
            barX = touchScreenX - 195;
            barWidth = barX + 200;
            bar.moveBar(barX,barWidth);
        }
        else
        {
            barX = touchScreenX-5 ;
            barWidth = barX + 200;
            bar.moveBar(barX,barWidth);
        }
       // bar.moveBar(barX,200);

        return true;
    }



}
package com.sibivarmanl.game_basic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by Sibi varman L on 6/18/2016.
 */
public class player extends GameObject{
    private Bitmap spritesheet;
    private int score;
    private int delayTime = 50;
    private boolean up;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;
    private Bitmap jump;
    public boolean reducer;

    public player(Bitmap res ,int w,int h,int numFrames){
        x = GamePanel.WIDTH/3;
        y = GamePanel.HEIGHT/2;
        dy = 0;
        score = 0;
        height = h;
        width = w;

        Bitmap[] images = new Bitmap[numFrames];
        spritesheet = res;

        for(int i=2,j=0;j<images.length;i++,j++){
            images[j]=Bitmap.createBitmap(spritesheet,i*width,0,width,height);
        }

        jump= Bitmap.createBitmap(spritesheet,16*width,height,width,height);
        animation.setFrames(images);
        animation.setDelay(delayTime);
        startTime = System.nanoTime();
        if(delayTime<50)
            delayTime=50;

    }

    void setUp(boolean b){up = b;}

    public void update(){
        long elapsed = (System.nanoTime()-startTime)/1000000;
        while (elapsed>100){
            score++;
            startTime = System.nanoTime();
            elapsed = 0;
        }

        animation.update();
        if(up){
            dy -= 7;
        }
        else{
            dy += 7;
        }

        if(dy>14){
            dy=14;
        }
        if(dy<-14){
            dy=-14;
        }

        y += dy*2;
    }
    public void draw(Canvas canvas){

        if(up){
            canvas.drawBitmap(jump,x,y,null);
            SmokePuffs.pf = false;
        }
        else {
            canvas.drawBitmap(animation.getImages(), x, y, null);
            SmokePuffs.pf = true;
        }
        if(reducer){
            y = y-28;
            reducer = false;
        }
    }

    public int getScore(){
        return score;
    }
    public boolean getPlaying(){
        return playing;
    }
    public void setPlaying(boolean b){
        playing = b;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}

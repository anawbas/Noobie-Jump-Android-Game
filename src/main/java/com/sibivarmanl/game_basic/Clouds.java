package com.sibivarmanl.game_basic;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by Sibi varman L on 6/20/2016.
 */
public class Clouds extends GameObject{
    private int score;
    private int speed;
    private Random rand = new Random();
    private Animation animation = new Animation();
    private Bitmap images;

    public Clouds(Bitmap bm,int x,int y,int w,int h,int s){
        super.x = x;
        super.y = y;
        width = w;
        height = h;
        score = s;
        speed = 7 + (int) (rand.nextDouble()*score/30);
        if(speed>40)speed = 40;
        images = bm;
    }
    public void update(){
        x -= speed;
    }
    public void draw(Canvas canvas){
        try {
            canvas.drawBitmap(images, x, y, null);
        }catch (Exception e){}
    }
}

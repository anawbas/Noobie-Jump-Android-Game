package com.sibivarmanl.game_basic;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Sibi varman L on 8/7/2016.
 */
public class Brick extends GameObject{
    private Bitmap img;

    public Brick(Bitmap res,int w,int h){
        img = res;
        dx = GamePanel.MOVESPEED;
        x = 0;
        y = (GamePanel.HEIGHT/2)+58;
        width = w + 50;
        height = h;
    }

    void update(){
        x = x+dx;
    }

    void draw(Canvas canvas){
        if(x > -GamePanel.WIDTH){
            canvas.drawBitmap(img,x,y,null);
            canvas.drawBitmap(img,x+50,y,null);
        }
    }
}

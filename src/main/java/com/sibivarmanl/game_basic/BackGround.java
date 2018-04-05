package com.sibivarmanl.game_basic;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Sibi varman L on 6/17/2016.
 */
public class BackGround {

    private Bitmap image;
    private int x,y,dx;

    public BackGround(Bitmap res){
        image = res;
        dx = GamePanel.MOVESPEED;
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(image,x,y,null);
        if(x<0){
            canvas.drawBitmap(image,x+GamePanel.WIDTH,y,null);
        }
    }
    public void update(){
        x+=dx;
        if(x<-GamePanel.WIDTH)
            x=0;
    }
}

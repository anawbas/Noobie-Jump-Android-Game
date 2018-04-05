package com.sibivarmanl.game_basic;

import android.graphics.Bitmap;

/**
 * Created by Sibi varman L on 6/18/2016.
 */
public class Animation {
    private Bitmap[] frames;
    private int currentFrame;
    private long startTime;
    private long delay;
    private boolean playedOnce;

    public void setFrames(Bitmap[] bitmaps){
        this.frames = bitmaps;
        currentFrame = 0;
        startTime = System.nanoTime();
    }
    public void setDelay(long d){
        delay = d;
    }
    public void update(){
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>delay){
            currentFrame++;
            startTime = System.nanoTime();
        }
        if(currentFrame == frames.length){
            currentFrame = 0;
            playedOnce = true;
        }
    }
    public Bitmap getImages(){
        return frames[currentFrame];
    }

}

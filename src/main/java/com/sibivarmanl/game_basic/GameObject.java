package com.sibivarmanl.game_basic;

import android.graphics.Rect;

/**
 * Created by Sibi varman L on 6/18/2016.
 */
public abstract class GameObject {

    protected int x;
    protected int y;
    protected int dx;
    protected int dy;
    protected int width;
    protected int height;

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public Rect getRectangle(){
        return new Rect(x , y, x+width,y+height);
    }
}

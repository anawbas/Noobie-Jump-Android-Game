package com.sibivarmanl.game_basic;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

/**
 * Created by Sibi varman L on 6/16/2016.
 */
public class MainThread extends Thread{
    private int FPS=24;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    protected boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder,GamePanel gamePanel){

        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;

    }
    @Override
    public void run(){
        long startTime;
        long timeMills;
        long waitTime;
        long frameCount=0;
        long targetTime=1000/FPS;

        while(true) {
            while (running) {
                startTime = System.nanoTime();
                canvas = null;

                try {
                    canvas = this.surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        this.gamePanel.update();
                        this.gamePanel.draw(canvas);
                    }
                } catch (Exception e) {
                } finally {
                    if (canvas != null) {
                        try {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                timeMills = (System.nanoTime() - startTime) / 1000000;
                waitTime = targetTime - timeMills;

                try {
                    this.sleep(waitTime);
                } catch (Exception e) {
                }

                frameCount++;
                if (frameCount == FPS) {
                    frameCount = 0;
                }
            }
            try {
                currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            gamePanel.reset();
        }
    }
    void setRunning(boolean b){
        running = b;
    }
}

package com.sibivarmanl.game_basic;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Sibi varman L on 6/16/2016.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public static final int WIDTH=856;
    public static final int HEIGHT=480;
    public static final int MOVESPEED=-5;
    protected static int best = 0;
    private static boolean upEnable;
    private static final int TOP = 1;
    private static final int LEFT = 2;
    private static final int BOTTOM = 3;
    private static final int RIGHT = 4;
    private long smokeStartTime;
    private long cloudStartTime;
    private BackGround bg;
    private MainThread thread;
    private player player;
    private Brick brick;
    private ArrayList<SmokePuffs> puffs;
    private ArrayList<Clouds> clouds;
    private Random rand = new Random();

    public GamePanel(Context context){
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder,int format,int width, int height){

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        int count = 0;
        while (retry&&count<1000){
            count++;
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
                thread = null;
            }catch (InterruptedException e){e.printStackTrace();}
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        bg = new BackGround(BitmapFactory.decodeResource(getResources(),R.drawable.grassbg1));
        player = new player(BitmapFactory.decodeResource(getResources(),R.drawable.character),48,56,3);
        brick = new Brick(BitmapFactory.decodeResource(getResources(), R.drawable.brick),550,300);
        puffs = new ArrayList<SmokePuffs>();
        cloudStartTime = System.nanoTime();
        smokeStartTime = System.nanoTime();
        clouds = new ArrayList<Clouds>();

        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(!player.getPlaying()){
                player.setPlaying(true);
            }
            else if(upEnable){
                player.setUp(true);
                upEnable = false;
            }
            else{
                player.setUp(false);
            }
            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP){
            player.setUp(false);
            return true;
        }

        return super.onTouchEvent(event);
    }

    public void reset(){
        if(player.getScore() > best){
            best = player.getScore();
        }
        bg = null;
        bg = new BackGround(BitmapFactory.decodeResource(getResources(),R.drawable.grassbg1));
        player.setPlaying(false);
        player = new player(BitmapFactory.decodeResource(getResources(),R.drawable.character),48,56,3);
        brick = null;
        brick = new Brick(BitmapFactory.decodeResource(getResources(),R.drawable.brick),550,300);
        clouds = null;
        clouds = new ArrayList<Clouds>();
        puffs = null;
        puffs = new ArrayList<SmokePuffs>();
        cloudStartTime = System.nanoTime();
        smokeStartTime = System.nanoTime();


        thread.setRunning(true);
    }

    public int collissionDetection(int i){
        float wy = ( clouds.get(i).getWidth()+100+player.getWidth()) * ( (clouds.get(i).getY()+(clouds.get(i).getY()+28)/2)-(player.getY()+(player.getY()+56)/2));
        float hx = (player.getHeight() + clouds.get(i).getHeight()) * (((clouds.get(i).getX() - 28)+(clouds.get(i).getX()+125)/2)-((player.getX())+(player.getX()+50)/2) );

        if (wy > hx)
            if (wy > -hx) {
                upEnable = true;
                return TOP;
            }
            else {
                return LEFT;
            }
        else
            if (wy > -hx) {
                return RIGHT;
            }
            else {
                return BOTTOM;
            }
    }
    public boolean collission(GameObject a,GameObject b){
        if(Rect.intersects(a.getRectangle(),b.getRectangle())){
            return true;
        }
        return false;
    }
    public void afterImpact(int side,int i){
        switch (side){
            case 1: {
                player.reducer = true;
                player.y = player.getY()-((player.getY()+56)-clouds.get(i).getY());
            }
            break;

        }
    }

    public void update(){

        if(player.getPlaying()) {
            if ((player.getY() < HEIGHT) && (player.getY() > -150)) {
                bg.update();
                brick.update();
                player.update();
                long cloudElapsed = (System.nanoTime() - cloudStartTime) / 1000000;
                if(collission(player,brick)){
                    player.y = brick.getY()-player.height;
                    upEnable = true;
                }
                if (cloudElapsed > (500 + player.getScore() / 4)) {
                    clouds.add(new Clouds(BitmapFactory.decodeResource(getResources(), R.drawable.cloud), WIDTH + 15, (int) (rand.nextDouble() * (HEIGHT * 0.75) + (HEIGHT * 0.25)), 85, 28, player.getScore()));
                    cloudStartTime = System.nanoTime();

                }

                for (int i = 0; i < clouds.size(); i++) {
                    clouds.get(i).update();
                    if (clouds.get(i).getX() < -100) {
                        clouds.remove(i);
                    }
                    if(collission(player,clouds.get(i))){
                        afterImpact(collissionDetection(i),i);
                    }
                }


                long elapsed = (System.nanoTime() - smokeStartTime) / 1000000;
                if (elapsed > 120) {
                    puffs.add(new SmokePuffs(player.getX(), player.getY() + 50));
                    smokeStartTime = System.nanoTime();
                }

                for (int i = 0; i < puffs.size(); i++) {
                    puffs.get(i).update();
                    if ((puffs.get(i).getX() + 30) < player.getX()) {
                        puffs.remove(i);
                    }
                }
            }
            else {
                    thread.setRunning(false);
        }
        }
    }
    @Override
    public void draw(Canvas canvas){

        final float scaleFactorX=(float)getWidth()/WIDTH;
        final float scaleFactorY=(float)getHeight()/HEIGHT;
        if(canvas != null) {

            final int savedstate=canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            brick.draw(canvas);
            player.draw(canvas);
            drawText(canvas);

            for(SmokePuffs sp: puffs){
                sp.draw(canvas);
            }
            for(Clouds cl: clouds){
                cl.draw(canvas);
            }
            canvas.restoreToCount(savedstate);

        }
    }

    public void drawText(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("SCORE: "+(player.getScore()),10,30,paint);
        canvas.drawText(("BEST: "+best),WIDTH-150,30,paint);

        if(!player.getPlaying()){
            Paint paint1 = new Paint();
            paint1.setTextSize(40);
            paint1.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            canvas.drawText("PRESS TO START", WIDTH/2,HEIGHT/3-20,paint1);

            paint1.setTextSize(25);
            paint1.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.ITALIC));
            canvas.drawText("HOLD TO JUMP",WIDTH/2,HEIGHT/3+30,paint1);
        }

        if(!thread.running){

                Paint paint2 = new Paint();
                paint2.setTextSize(50);
            paint2.setColor(Color.RED);
            paint2.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("OUT", WIDTH/2-50,HEIGHT/2,paint2);
            paint2.setColor(Color.BLACK);
                paint2.setTextSize(30);
            canvas.drawText("SCORE: " + player.getScore(), WIDTH/2-50,HEIGHT/2+40,paint2);
                canvas.drawText("LAST BEST:  "+best,WIDTH/2-50,HEIGHT/2+70,paint2);
        }
    }

}

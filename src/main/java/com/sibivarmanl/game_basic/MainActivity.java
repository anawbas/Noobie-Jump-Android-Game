package com.sibivarmanl.game_basic;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends Activity {

    MediaPlayer mediaPlayer;
    player play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new GamePanel(this));
        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        //accessing the sharedPreferences
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        GamePanel.best = sharedPreferences.getInt("highScore",00);
    }

    protected void onPause(){

        //saving the high score
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("highScore",GamePanel.best);
        editor.commit();

        //stoping the media player
        mediaPlayer.stop();
        System.exit(0);
    }

    protected void onStop(){

        //saving the high score
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("highScore",GamePanel.best);
        editor.commit();

        //stoping the media player
        mediaPlayer.stop();
        System.exit(0);
    }
}

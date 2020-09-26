package com.example.yinblog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mplayer;
    AudioManager audioManager;

    public void playAudio(View view){


        mplayer.start();
    }

    public void pause(View view){
        mplayer.pause();
    }

    public void unfavor(View view){
        ImageView blankStar = findViewById(R.id.blankStar);
        ImageView fullStar = findViewById(R.id.fullStar);

        blankStar.animate().alpha(1f).setDuration(1000);
        fullStar.animate().alpha(0f).setDuration(1000);
    }
    public void favor(View view){
        ImageView blankStar = findViewById(R.id.blankStar);
        ImageView fullStar = findViewById(R.id.fullStar);

        //blankStar.animate().alpha(0f).setDuration(1000);
        fullStar.animate()
                .alpha(1f)
                .rotation(100f)
                .setDuration(1000);
        blankStar.animate().rotation(100f).setDuration(1000);

        fullStar.animate().translationX(10).setDuration(2000);
        blankStar.animate().translationX(10).setDuration(2000);


       //fullStar.animate().scaleX(2f).scaleY(2f).setDuration(2000);
    }


    public void contextButtonClicked(View view){

        EditText myTextField = (EditText) findViewById(R.id.myTextField);

        Log.i("info",myTextField.getText().toString());

        Toast.makeText(MainActivity.this, myTextField.getText().toString(), Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mplayer = new MediaPlayer();
        mplayer = MediaPlayer.create(this, R.raw.bells);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        SeekBar volControl = (SeekBar) findViewById(R.id.seekBar);
        volControl.setMax(maxVolume);
        volControl.setProgress(curVolume);

        volControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Seeker value",Integer.toString(progress));

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });


//        VideoView videoView = (VideoView) findViewById(R.id.videoView);
//        MediaController mediaController = new MediaController(this);
//        videoView.setMediaController(mediaController);
//        videoView.setVideoPath("android.resource://"+ getPackageName(| + "/" + R.raw.demovideo);
//        videoView.start();

    }
}

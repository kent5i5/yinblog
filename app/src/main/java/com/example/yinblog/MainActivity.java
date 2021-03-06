package com.example.yinblog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mplayer;
    AudioManager audioManager;

    public class DownLoadWeatherData extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection connection = null;

            try {
                url = new URL(urls[0]);

                connection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = connection.getInputStream();

                int data = inputStream.read();

                while (data != -1){
                    char current = (char) data;
                    result += current;
                    data = inputStream.read();
                }
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jobject = new JSONObject(result);
                String weatherInfo = jobject.getString("weather");
                Log.i("Weather is ", weatherInfo );
                JSONArray arr = new JSONArray(weatherInfo);
                for(int i = 0; i < arr.length() ; i++){
                    JSONObject jsonPart = arr.getJSONObject(i);
                    Log.i("main",jsonPart.getString("main"));
                    Log.i("description",jsonPart.getString("description"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    public class DownLoadContent extends AsyncTask<String, Void, String> {

        // this methods is accessable anywhere in the package
        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try{
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1){
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return "fail";
        }
    }

    public class ImageDownloader extends  AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);

                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap bitmapImage = BitmapFactory.decodeStream(inputStream);
                return bitmapImage;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void downloadImage(View view) {
        ImageDownloader task = new ImageDownloader();
        Bitmap myImage;

        try {
            myImage = task.execute("https://i.pinimg.com/474x/37/97/3c/37973cc70fa371a32c4f9cbfca06ef9c.jpg").get();

            ImageButton imageButtonOne = (ImageButton)findViewById(R.id.bells);
            imageButtonOne.setImageBitmap(myImage);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void updateTimerView(long secondsleft){
        int minutes = (int) secondsleft /60;
        int seconds = (int)secondsleft - minutes *60;

        TextView timerTextView = (TextView) findViewById(R.id.timerTextView);
        timerTextView.setText(String.valueOf(secondsleft/1000));
    }

    public void playAudio(View view){


        mplayer.start();
    }

    public void pause(View view){
        mplayer.pause();
    }

    public void linearButtonTapped(View view){
        mplayer.stop();
        int id = view.getId();
        String tapId;
        tapId = view.getResources().getResourceEntryName(id);
        Log.i("button tap id ",  tapId);
        int resourceId = getResources().getIdentifier(tapId, "raw", getPackageName());

        mplayer= MediaPlayer.create(this, resourceId);
        mplayer.start();

        downloadImage(view);
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
        final SeekBar volControl = (SeekBar) findViewById(R.id.seekBar);

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


        //timer
//        final Handler handler = new Handler();
//        Runnable run = new Runnable() {
//            @Override
//            public void run() {
//                Log.i("Runnalbe has run ",  "a second has passed");
//
//                handler.postDelayed(this, 1000);
//            };
//        };
//
//        handler.post(run);

        new CountDownTimer(10000, 1000)
        {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.i("Second left", String.valueOf(millisUntilFinished/1000) );
                updateTimerView(millisUntilFinished);
            }

            @Override
            public void onFinish() {

            }
        }.start();


//        DownLoadWeatherData task = new DownLoadWeatherData();
//        task.execute("https://samples.openweathermap.org/data/2.5/weather?q=San_Francisco,us&appid=APIkey");


//        VideoView videoView = (VideoView) findViewById(R.id.videoView);
//        MediaController mediaController = new MediaController(this);
//        videoView.setMediaController(mediaController);
//        videoView.setVideoPath("android.resource://"+ getPackageName(| + "/" + R.raw.demovideo);
//        videoView.start();

    }
}

package com.example.yinblog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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
    }
}

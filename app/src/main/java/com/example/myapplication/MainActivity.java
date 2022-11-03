package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textViewHelloWorld;
    private Button buttonPrevious;
    private Button buttonNext;
    private ImageView imageViewFunny;
    private int[] imageIDArray={R.drawable.funny_1, R.drawable.funny_2, R.drawable.funny_3, R.drawable.funny_4,
            R.drawable.funny_5};;
    private int imageIDArrayCurrentIndex;

    public MainActivity(){
        imageIDArrayCurrentIndex=0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewHelloWorld=findViewById(R.id.text_hello_world);
        buttonPrevious=findViewById(R.id.button_previous);
        buttonNext=findViewById(R.id.button_next);
        imageViewFunny=findViewById(R.id.imageView);
        buttonPrevious.setOnClickListener(new MyButtonClickListener());
        buttonNext.setOnClickListener(new MyButtonClickListener());
    }

    private class MyButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(view==buttonNext)
            {
                imageIDArrayCurrentIndex++;
                if(imageIDArrayCurrentIndex>=imageIDArray.length) imageIDArrayCurrentIndex=0;
            }
            else
            {
                imageIDArrayCurrentIndex--;
                if(imageIDArrayCurrentIndex<0) imageIDArrayCurrentIndex=imageIDArray.length-1;
            }
            imageViewFunny.setImageResource(imageIDArray[imageIDArrayCurrentIndex]);
        }
    }
}
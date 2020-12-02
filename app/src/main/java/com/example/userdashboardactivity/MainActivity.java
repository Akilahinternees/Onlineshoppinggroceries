package com.example.userdashboardactivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    //SliderLayout sliderLayout;


    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ImageSlider imageSlider=findViewById(R.id.images_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.eight,"First"));
        slideModels.add(new SlideModel(R.drawable.seven,"Second"));
        slideModels.add(new SlideModel(R.drawable.two,"Third"));
        imageSlider.setImageList(slideModels,true);

        button=findViewById(R.id.btn_start);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Login.class);
                Pair[] pairs = new Pair[1];
                pairs[0]=new Pair<View,String>(findViewById(R.id.btn_start),"transition_login");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                    startActivity(intent,options.toBundle());
                }
                else{
                    startActivity(intent);
                }
            }
        });



        /*sliderLayout = findViewById(R.id.imageSlider);

        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setScrollTimeInSec(4); //set scroll delay in seconds :
        setSliderViews();*/

    }

    /*private void setSliderViews(){
        for(int i=0;i<=2;i++){
            DefaultSliderView sliderView= new DefaultSliderView(this);
            switch (i){
                case 0:
                    sliderView.setImageDrawable(R.drawable.eight);
                    sliderView.setDescription("Image one");
                    break;
                case 1:
                    sliderView.setImageDrawable(R.drawable.seven);
                    sliderView.setDescription("Image two");
                    break;
                case 2:
                    sliderView.setImageDrawable(R.drawable.two);
                    sliderView.setDescription("Image three");
                    break;

            }
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderView.setDescription("setDescription" + (i+1));
            final int finalI=i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    Toast.makeText(MainActivity.this,"this is slider"+(finalI+1),Toast.LENGTH_SHORT).show();
                }
            });
            sliderLayout.addSliderView(sliderView);
        }
    }*/
}
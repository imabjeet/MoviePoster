package com.example.movieposter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;

public class ShowPosterActivity extends AppCompatActivity {

    int width,height;
    RelativeLayout header;
    TextView header_text;
    ImageView btn_back;

    RelativeLayout.LayoutParams re_params;
    Typeface custom_font;

    String posterURL,title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_show_poster);

        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        width =displayMetrics.widthPixels;
        height=displayMetrics.heightPixels;


        custom_font = Typeface.createFromAsset(getAssets(), "fonts/arial.ttf");

        Intent i=getIntent();
        title=i.getStringExtra("name");
        posterURL=i.getStringExtra("posterURL");


        headerDesign();
        body();



    }


    private void headerDesign() {

        header = findViewById(R.id.header);
        header_text = findViewById(R.id.header_text);
        btn_back = findViewById(R.id.btn_back);


        header.getLayoutParams().height=(height*160)/1920;


        header_text.setText(title);
        header_text.setTextSize(17);
        header_text.setTypeface(custom_font);


        btn_back.getLayoutParams().width=(width*28)/1080;
        btn_back.getLayoutParams().height=(height*50)/1920;
        re_params=(RelativeLayout.LayoutParams) btn_back.getLayoutParams();
        re_params.setMargins((width*60)/1080,0,0,0);
        btn_back.setLayoutParams(re_params);


    }


    private void body() {

        ImageView poster=findViewById(R.id.poster);
        Glide.with(this).load(posterURL).into(poster);

    }

    public void back(View view)
    {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}

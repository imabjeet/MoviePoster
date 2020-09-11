package com.example.movieposter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int width,height;
    ListView listView;
    RelativeLayout header;
    TextView header_text;
    Typeface custom_font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        width =displayMetrics.widthPixels;
        height=displayMetrics.heightPixels;


        custom_font = Typeface.createFromAsset(getAssets(), "fonts/arial.ttf");


        headerDesign();
        body();

    }


    private void headerDesign() {

        header = findViewById(R.id.header);
        header_text = findViewById(R.id.header_text);


        header.getLayoutParams().height=(height*160)/1920;


        header_text.setTextSize(17);
        header_text.setTypeface(custom_font);


    }

    protected void body() {

        String array[] = {
                "Action-Adventure",
                "Animation",
                "Classic",
                "Comedy",
                "Drama",
                "Horror",
                "Family",
                "MyStery",
                "Scifi-Fantasy",
                "Western"
        };

        listView = findViewById(R.id.listView);

        final ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_view_item_view, R.id.item, array);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, ActivityDisplayMovieList.class);
                i.putExtra("name", adapter.getItem(position).toString());
                startActivity(i);

            }
        });

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

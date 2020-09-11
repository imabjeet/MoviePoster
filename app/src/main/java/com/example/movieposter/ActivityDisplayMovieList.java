package com.example.movieposter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ActivityDisplayMovieList extends AppCompatActivity {


    int width,height;
    RelativeLayout header;
    TextView header_text;
    ImageView btn_back;

    RelativeLayout.LayoutParams re_params;
    Typeface custom_font;


    String text;
    Context context;
    RecyclerView recyclerView;
    ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_display_movie_list);

        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        width =displayMetrics.widthPixels;
        height=displayMetrics.heightPixels;


        custom_font = Typeface.createFromAsset(getAssets(), "fonts/arial.ttf");

        context=ActivityDisplayMovieList.this;

        Intent i = getIntent();
        text=i.getStringExtra("name");
        progressBar=findViewById(R.id.progress_bar);
        recyclerView=findViewById(R.id.recyclerView);



        headerDesign();
        body();


    }



    private void headerDesign() {

        header = findViewById(R.id.header);
        header_text = findViewById(R.id.header_text);
        btn_back = findViewById(R.id.btn_back);


        header.getLayoutParams().height=(height*160)/1920;


        header_text.setText(text);
        header_text.setTextSize(17);
        header_text.setTypeface(custom_font);


        btn_back.getLayoutParams().width=(width*28)/1080;
        btn_back.getLayoutParams().height=(height*50)/1920;
        re_params=(RelativeLayout.LayoutParams) btn_back.getLayoutParams();
        re_params.setMargins((width*60)/1080,0,0,0);
        btn_back.setLayoutParams(re_params);


    }



    private void body() {



        new GetAllMoviePoster().execute();

    }



    public class GetAllMoviePoster extends AsyncTask {

        String result = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Object doInBackground(Object[] objects) {


            try {

                URL url = new URL("https://sampleapis.com/movies/api/"+ text.toLowerCase());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                InputStream in = con.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            try {
                JSONArray jsonArray=new JSONArray(result);
                RecyclerListAdapter adapter=new RecyclerListAdapter(context,jsonArray);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(adapter);

                progressBar.setVisibility(View.GONE);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    public void back(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

package com.example.movieposter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ViewHolder> {

    Context context;
    JSONArray jsonArray;
    int width,height;
    Typeface custom_font;

    public RecyclerListAdapter(Context context, JSONArray jsonArray)
    {
        this.context=context;
        this.jsonArray=jsonArray;


        DisplayMetrics displayMetrics = context.getResources()
                .getDisplayMetrics();
        width = displayMetrics.widthPixels; // width of the device
        height = displayMetrics.heightPixels; // width of the device

    }


    @NonNull
    @Override
    public RecyclerListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context) .inflate(R.layout.recyclerview_list_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerListAdapter.ViewHolder holder, int position) {

        final JSONObject jsonObject;
        String s="";

        try {
            jsonObject=jsonArray.getJSONObject(position);
            s=jsonObject.getString("title");

            Glide.with(context).load(jsonObject.getString("posterURL")).into(holder.poster);
            holder.title.setText(s);


            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(context,ShowPosterActivity.class);
                    String image="",name="";
                    try {
                        image=jsonObject.getString("posterURL");
                        name=jsonObject.getString("title");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    i.putExtra("name",name);
                    i.putExtra("posterURL",image);
                    context.startActivity(i);
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView poster;
        TextView title;
        LinearLayout item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/arial.ttf");

            item=itemView.findViewById(R.id.item);
            poster=itemView.findViewById(R.id.poster);
            title=itemView.findViewById(R.id.title);

            item.getLayoutParams().height=(height*200)/1920;

            poster.getLayoutParams().width=(width*150)/1080;
            poster.getLayoutParams().height=(height*150)/1920;

            title.setTypeface(custom_font);

        }
    }
}

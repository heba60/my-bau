package com.uni.bau.ui.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.bau.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;


public class StudentScudlaeAdapter extends RecyclerView.Adapter<StudentScudlaeAdapter.CustomViewHolder> {
    Activity mActivity;
    JSONArray anouncementModelsArray;
    public StudentScudlaeAdapter(Activity activity, JSONArray countries) {
        this.mActivity = activity;
        this.anouncementModelsArray = countries;
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scudle_adapter, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder,  int position) {
        try {

            JSONObject courseObject = anouncementModelsArray.getJSONObject(position);
            String webCorName = courseObject.optString("WEB_COR_NAME", "");
            double webCorHours = courseObject.optDouble("WEB_COR_HOURS", 0.0);
            String webCorInst = courseObject.optString("WEB_COR_INST", "");
            String webCorRoom = courseObject.optString("WEB_COR_ROOM", "");
            String webCorTime = courseObject.optString("WEB_COR_TIME", "");

            holder.first.setText(webCorName);
            holder.sec.setText( webCorHours+" ساعات ");
            holder.trhird.setText(webCorInst);
            holder.fourth.setText(webCorRoom);
            holder.five.setText(webCorTime);


         /*   holder.first.setText("اسم المادة : "+webCorName);
            holder.sec.setText("عدد الساعات : "+webCorHours+"");
            holder.trhird.setText("مدرس المادة : "+webCorInst);
            holder.fourth.setText("القاعة : "+webCorRoom);
            holder.five.setText("وقت المحاضرة : "+webCorTime);*/


            int whiteColor = ContextCompat.getColor(holder.itemView.getContext(), android.R.color.white);
         /*   holder.first.setTextColor(whiteColor);
            holder.sec.setTextColor(whiteColor);
            holder.trhird.setTextColor(whiteColor);
            holder.fourth.setTextColor(whiteColor);
            holder.five.setTextColor(whiteColor);*/




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (anouncementModelsArray == null) {
            return 0;
        }
        return anouncementModelsArray.length();
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView first;
        TextView sec;
        TextView trhird;
        TextView fourth;
        TextView five;
        RelativeLayout r_color;

        CustomViewHolder(View itemView) {
            super(itemView);
            first = itemView.findViewById(R.id.first);
            sec = itemView.findViewById(R.id.sec);
            trhird = itemView.findViewById(R.id.trhird);
            fourth = itemView.findViewById(R.id.fourth);
            five = itemView.findViewById(R.id.five);


            r_color = itemView.findViewById(R.id.r_color);

            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(230), rnd.nextInt(230), rnd.nextInt(230));
            r_color.setBackgroundColor(color);


        }
    }
}

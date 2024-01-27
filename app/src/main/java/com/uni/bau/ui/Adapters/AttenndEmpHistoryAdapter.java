package com.uni.bau.ui.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.bau.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;


public class AttenndEmpHistoryAdapter extends RecyclerView.Adapter<AttenndEmpHistoryAdapter.CustomViewHolder> {
    Activity mActivity;
    JSONArray anouncementModelsArray;
    public AttenndEmpHistoryAdapter(Activity activity, JSONArray countries) {
        this.mActivity = activity;
        this.anouncementModelsArray = countries;
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_attendens, parent, false);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder,  int position) {
        try {

            JSONObject attendanceObject = anouncementModelsArray.getJSONObject(position);
            String attendDate = attendanceObject.getString("ATTENDDATE");
            String attendDay = attendanceObject.getString("ATTENDDAY");
            String attendCome = attendanceObject.getString("ATTENDCOME");
            String attendGo = attendanceObject.getString("ATTENDGO");
            holder.first.setText("اليوم : "+attendDay);
            holder.sec.setText("التاريخ : "+attendDate);
            holder.trhird.setText("وقت القدوم : "+attendCome);
            holder.fourth.setText("وقت الخروج : "+attendGo);


            int whiteColor = ContextCompat.getColor(holder.itemView.getContext(), android.R.color.white);
            holder.first.setTextColor(whiteColor);
            holder.sec.setTextColor(whiteColor);
            holder.trhird.setTextColor(whiteColor);
            holder.fourth.setTextColor(whiteColor);

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
        CustomViewHolder(View itemView) {
            super(itemView);
            first = itemView.findViewById(R.id.first);
            sec = itemView.findViewById(R.id.sec);
            trhird = itemView.findViewById(R.id.trhird);
            fourth = itemView.findViewById(R.id.fourth);
        }
    }
}

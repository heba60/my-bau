package com.uni.bau.ui.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.bau.R;
import com.uni.bau.models.CalendarModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {
    private Context mContext;
    private List<CalendarModel> mList;
    String startDate ="";
    String endDate ="";
    public CalendarAdapter(Context mContext, List<CalendarModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.row_calendar , parent , false) );
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CalendarModel calendarModel = mList.get(position);
        holder.bind(calendarModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date [] = new String[0];
                try {
                    date = calendarModel.getDate().toString().split(" - ");
                    startDate = date[0];
                    endDate = date[1];
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ;


                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("beginTime",getMilliFromDate(startDate));
                intent.putExtra("allDay", false);
                intent.putExtra("rrule", "FREQ=DAILY");
                intent.putExtra("endTime", getMilliFromDate(endDate)+24*60*60*1000);
                intent.putExtra("title", mList.get(position).getEvent().toString());
                mContext.startActivity(intent);
            }
        });
    }

    public long getMilliFromDate(String dateFormat) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        try {
            date = formatter.parse(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Today is " + date);
        return date.getTime();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtEvent;
        TextView txtDate;
        TextView txtDay;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEvent = itemView.findViewById(R.id.txtEvent);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtDay = itemView.findViewById(R.id.txtDay);
        }

        public void bind(CalendarModel calendarModel) {
            if (calendarModel.getDate().toString().equals(""))
            {
                txtDate.setVisibility(View.GONE);
            }else {
                txtDate.setText(calendarModel.getDate());
            }
            if (calendarModel.getDay().toString().equals(""))
            {
                txtDay.setVisibility(View.GONE);
            }else {
                txtDay.setText(calendarModel.getDay());
            }
            txtEvent.setText(calendarModel.getEvent());
        }
    }
}

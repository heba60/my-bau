package com.uni.bau.ui.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.uni.bau.R;
import com.uni.bau.models.ScheduleModel;
import com.uni.bau.ui.Activites.ChatActivity;
import com.uni.bau.ui.Fragments.DrHomeFragment;
import com.uni.bau.ui.Fragments.HomeFragment;
import com.uni.bau.utilities.CustomTastyToast;
import com.uni.bau.utilities.Utils;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.CustomViewHolder> {
    Activity mActivity;
    List<ScheduleModel> scheduleModelsArrayList;

    public ScheduleAdapter(Activity activity, List<ScheduleModel> scheduleModelsArrayList) {
        this.mActivity = activity;
        this.scheduleModelsArrayList = scheduleModelsArrayList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_home, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        try {
            try {
                holder.classID.setText(mActivity.getString(R.string.class_id, scheduleModelsArrayList.get(position).getClassID()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                holder.tvName.setText(scheduleModelsArrayList.get(position).getLecName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                holder.drName.setText("الدكتور : " + scheduleModelsArrayList.get(position).getInstructor());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String day[] = scheduleModelsArrayList.get(position).getDays().split(" ");
                if (day.length == 1) {
                    holder.date.setText(replacer(day[0]));
                }
                if (day.length == 2) {
                    holder.date.setText(replacer(day[0]) + " " + replacer(day[1]));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                holder.time.setText(scheduleModelsArrayList.get(position).getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (new Utils().getUserData(mActivity).isDr()){
                            for (int counter = 0; counter < DrHomeFragment.lecutresModel.size(); counter++) {
                                if (scheduleModelsArrayList.get(position).getLecNum().toString().equals(DrHomeFragment.lecutresModel.get(counter).getNumber())) {
                                    Intent intent = new Intent(mActivity, ChatActivity.class);
                                    intent.putExtra("LECT_NAME", scheduleModelsArrayList.get(position).getLecName());
                                    intent.putExtra("LECT_ID", DrHomeFragment.lecutresModel.get(counter).getId().toString());
                                    mActivity.startActivity(intent);
                                }
                            }
                        }else {
                            for (int counter = 0; counter < HomeFragment.lecutresModel.size(); counter++) {
                                if (scheduleModelsArrayList.get(position).getLecNum().toString().equals(HomeFragment.lecutresModel.get(counter).getNumber())) {
                                    Intent intent = new Intent(mActivity, ChatActivity.class);
                                    intent.putExtra("LECT_NAME", scheduleModelsArrayList.get(position).getLecName());
                                    intent.putExtra("LECT_ID", HomeFragment.lecutresModel.get(counter).getId().toString());
                                    mActivity.startActivity(intent);
                                }
                            }
                        }

                    } catch (Exception e) {
                        CustomTastyToast.makeText(mActivity, "حدث خطا يرجى المحاولة لاحقا", CustomTastyToast.LENGTH_LONG, CustomTastyToast.ERROR).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String replacer(String text) {
        if (text.toString().equals("ر")) {
            return "اربعاء";
        } else if (text.toString().equals("ن")) {
            return "اثنين";
        }
        if (text.toString().equals("ح")) {
            return "احد";
        }
        if (text.toString().equals("ث")) {
            return "ثلاثاء";
        }
        if (text.toString().equals("خ")) {
            return "خميس";
        } else {
            return "خميس";
        }

//        text = text.replace("ر"," اربعاء ").replace("ن"," اثنين ").replace("ح"," احد ").replace("ث"," ثلاثاء ").replace("خ","خميس");
//        return text;
    }

    @Override
    public int getItemCount() {
        if (scheduleModelsArrayList == null) {
            return 0;
        }
        return scheduleModelsArrayList.size();
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView drName;
        TextView date;
        TextView classID;
        TextView time;

        CustomViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            drName = itemView.findViewById(R.id.drName);
            date = itemView.findViewById(R.id.date);
            classID = itemView.findViewById(R.id.classID);
            time = itemView.findViewById(R.id.time);

        }
    }
}

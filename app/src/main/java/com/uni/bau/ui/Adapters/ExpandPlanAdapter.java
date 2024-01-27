package com.uni.bau.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uni.bau.R;
import com.uni.bau.models.MarksModel;
import com.uni.bau.models.PlanInfo;

import java.util.List;
import java.util.Map;

public class ExpandPlanAdapter extends BaseExpandableListAdapter {

    private Map<String, PlanInfo> titList;
    private List<String> mTitles;
    private Map<String, List<MarksModel>> mList;
    private Context mContext;

    public ExpandPlanAdapter(Context mContext, Map<String, PlanInfo> titList, Map<String, List<MarksModel>> mList, List<String> mTitles) {
        this.titList = titList;
        this.mList = mList;
        this.mTitles = mTitles;
        this.mContext = mContext;
    }

    @Override
    public int getGroupCount() {
        return mTitles.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mList.get(mTitles.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return Long.parseLong("1" + i + "1" + i1);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        View viewMain = LayoutInflater.from(mContext).inflate(R.layout.row_exp_main_item, viewGroup, false);
        TextView txtTitle = viewMain.findViewById(R.id.txtTitle);
        TextView txtVal = viewMain.findViewById(R.id.txtVal);
        ImageView imgDone = viewMain.findViewById(R.id.imgDone);
        LinearLayout whiteBack = viewMain.findViewById(R.id.whiteBack);
        txtTitle.setText(mTitles.get(i));
        PlanInfo planInfo = titList.get(mTitles.get(i));
        if (planInfo.isItDone()){
            imgDone.setImageResource(R.drawable.green_tick);
        }else {
            imgDone.setImageResource(R.drawable.down_arrow);
        }
        if (b)
        {
            if (planInfo.isItDone()){

            }else {
                imgDone.setImageResource(R.drawable.uparrow_);
            }
            whiteBack.setBackgroundResource(R.drawable.downwhite_background);
        }else {
            whiteBack.setBackgroundResource(R.drawable.background_white_shape);
            if (planInfo.isItDone()){

            }else {
                imgDone.setImageResource(R.drawable.down_arrow);
            }
        }
        txtVal.setText(String.format("%s/%s", planInfo.getAllHours(), planInfo.getAchivedHours()));
        return viewMain;
    }

    @Override
    public View getChildView(int i, int i1, boolean lastChild, View view, ViewGroup viewGroup) {
        View viewChild = LayoutInflater.from(mContext).inflate(R.layout.row_exp_sub_item, viewGroup, false);
        TextView txtCourse = viewChild.findViewById(R.id.txtCourse);
        TextView txtMark = viewChild.findViewById(R.id.txtMark);
        View viewIndi = viewChild.findViewById(R.id.viewIndi);
        LinearLayout backgroundWhite = viewChild.findViewById(R.id.backgroundWhite);
        txtCourse.setText(mList.get(mTitles.get(i)).get(i1).getLectureName());
        String mark = mList.get(mTitles.get(i)).get(i1).getMark();
        if (mark.isEmpty() || mark.equals("0")) {
            txtMark.setText("");
        } else
            txtMark.setText(mark +"/100");

        if (lastChild)
        {
            viewIndi.setVisibility(View.GONE);
            backgroundWhite.setBackgroundResource(R.drawable.upwhite_background);
        }else {
            backgroundWhite.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
        return viewChild;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}

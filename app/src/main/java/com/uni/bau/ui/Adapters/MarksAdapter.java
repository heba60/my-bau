package com.uni.bau.ui.Adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.bau.R;
import com.uni.bau.models.MarksApiModel;
import com.uni.bau.models.MarksModel;

import java.util.List;

public class MarksAdapter extends RecyclerView.Adapter<MarksAdapter.ViewHolder> {
    private Context mContext;
    private List<MarksApiModel.Mark> mList;
    public MarksAdapter(Context mContext, List<MarksApiModel.Mark> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.row_marks , parent , false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mList.get(position));
        if(position==(getItemCount()-1)){
            holder.viewIndi.setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView lectureName;
        TextView mark;
        View viewIndi ;

        public void bind(MarksApiModel.Mark marksModel){
            lectureName.setText( Html.fromHtml(marksModel.getName()));
            mark.setText(Html.fromHtml(marksModel.getResult()));

        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lectureName = itemView.findViewById(R.id.txtLectureName);
            mark = itemView.findViewById(R.id.txtMark);
            viewIndi = itemView.findViewById(R.id.viewIndi);
        }
    }
}

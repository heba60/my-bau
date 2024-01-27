package com.uni.bau.ui.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.uni.bau.R;
import com.uni.bau.models.GetChatModel;
import com.uni.bau.utilities.Utils;

import java.util.List;


public class ChatMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int MSG_TYPE_Receives = 0;
    public static final int MSG_TYPE_Send = 1;
    private Context mContext;
    private List<GetChatModel.Datum> mChat;
    String userID = "";
    public ChatMessageAdapter(Context mContext, List<GetChatModel.Datum> mChat) {
        userID = new Utils().getUNILOGIN(mContext).getData().getId().toString();
        this.mChat = mChat;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == MSG_TYPE_Send) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_send_messages, viewGroup, false);
            return new ChatMessageAdapter.ViewHolderCurrent(view);
        }
        if (viewType == MSG_TYPE_Receives) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_receives_messages, viewGroup, false);
            return new ChatMessageAdapter.ViewHolderOther(view);
        }

        throw new RuntimeException();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        GetChatModel.Datum chat = mChat.get(position);
        if (holder instanceof ViewHolderCurrent) {
            try {

                if (chat.getFiles().isEmpty())
                {
                    ((ViewHolderCurrent) holder).tvMessage.setText(chat.getMsg());
                }else {
                    ((ViewHolderCurrent) holder).messageLinear.setVisibility(View.GONE);
                    ((ViewHolderCurrent) holder).chatImg.setVisibility(View.VISIBLE);
                    if (chat.getFiles().get(0).toString().contains(".jpg") || chat.getFiles().get(0).toString().contains(".png"))
                    {
                        try {
                            Picasso.get().load(chat.getFiles().get(0).toString()).error(R.drawable.gallery).into( ((ViewHolderCurrent) holder).chatImg);
                        } catch (Exception e) {
                            ((ViewHolderCurrent) holder).chatImg.setImageDrawable(mContext.getDrawable(R.drawable.gallery));
                        }
                    }else {
                        ((ViewHolderCurrent) holder).chatImg.setImageDrawable(mContext.getDrawable(R.drawable.icon_file_pdf));
                    }
                }
                ((ViewHolderCurrent) holder).tvName.setText(chat.getSenderName());
                ((ViewHolderCurrent) holder).chatImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent browserIntent = null;
                        try {
                            browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(chat.getFiles().get(0).toString()));
                            mContext.startActivity(browserIntent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                //
            } catch (Exception e) {

            }

        } else if (holder instanceof ViewHolderOther) {

            try {
                if (chat.getFiles().isEmpty())
                {
                    ((ViewHolderOther) holder).tvMessage.setText(chat.getMsg());
                }else {
                    ((ViewHolderOther) holder).messageLinear.setVisibility(View.GONE);
                    ((ViewHolderOther) holder).chatImg.setVisibility(View.VISIBLE);
                    if (chat.getFiles().get(0).toString().contains(".jpg") || chat.getFiles().get(0).toString().contains(".png"))
                    {
                        try {
                            Picasso.get().load(chat.getFiles().get(0).toString()).error(R.drawable.gallery).into( ((ViewHolderOther) holder).chatImg);
                        } catch (Exception e) {
                            ((ViewHolderOther) holder).chatImg.setImageDrawable(mContext.getDrawable(R.drawable.gallery));
                        }
                    }else {
                        ((ViewHolderOther) holder).chatImg.setImageDrawable(mContext.getDrawable(R.drawable.icon_file_pdf));
                    }

                }
                ((ViewHolderOther) holder).chatImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent browserIntent = null;
                        try {
                            browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(chat.getFiles().get(0).toString()));
                            mContext.startActivity(browserIntent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                ((ViewHolderOther) holder).tvName.setText(chat.getSenderName());
            } catch (Exception e) {

            }


        }
    }


    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolderCurrent extends RecyclerView.ViewHolder {

        public TextView tvMessage;
        public TextView tvTime, tvName;
        public ImageView circleImage;
        public ImageView chatImg;
        public LinearLayout messageLinear;
        public ViewHolderCurrent(View itemView) {
            super(itemView);

            tvMessage = itemView.findViewById(R.id.tvMessage);
            circleImage = itemView.findViewById(R.id.circleImage);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvName = itemView.findViewById(R.id.tvName);
            messageLinear = itemView.findViewById(R.id.messageLinear);
            chatImg = itemView.findViewById(R.id.chatImg);
        }
    }

    public class ViewHolderOther extends RecyclerView.ViewHolder {

        public TextView tvMessage;
        public TextView tvTime, tvName;
        public ImageView circleImage;
        public ImageView chatImg;
        public LinearLayout messageLinear;
        public ViewHolderOther(View itemView) {
            super(itemView);

            tvMessage = itemView.findViewById(R.id.tvMessage);
            circleImage = itemView.findViewById(R.id.circleImage);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvName = itemView.findViewById(R.id.tvName);
            messageLinear = itemView.findViewById(R.id.messageLinear);
            chatImg = itemView.findViewById(R.id.chatImg);
        }
    }

    public void notifiyList(List<GetChatModel.Datum> mChat) {
        this.mChat = mChat;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        if (mChat.get(position).getSenderId().equals(userID)) {
            return MSG_TYPE_Send;
        } else {
            return MSG_TYPE_Receives;
        }
    }
}


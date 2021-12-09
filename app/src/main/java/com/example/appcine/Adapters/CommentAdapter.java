package com.example.appcine.Adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appcine.Models.CommentModel;
import com.example.appcine.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context mContext;
    private List<CommentModel> mData;


    public CommentAdapter(Context mContext, List<CommentModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.message_item,parent,false);
        return new CommentViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {

        Glide.with(mContext).load(mData.get(position).getUimg()).into(holder.imgUser);
        holder.tvName.setText(mData.get(position).getUname());
        holder.tvContent.setText(mData.get(position).getContent());
        holder.tvDate.setText(timestampToString((Long)mData.get(position).getTimestamp()));


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{

        ImageView imgUser;
        TextView tvName,tvContent,tvDate;

        public CommentViewHolder(View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.cvUserAvatar);
            tvName = itemView.findViewById(R.id.tvNombreUsuario);
            tvContent = itemView.findViewById(R.id.tvUserOpinionComment);
            tvDate = itemView.findViewById(R.id.tvCommentDate);
        }
    }

    private String timestampToString(long time) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("hh:mm",calendar).toString();
        return date;
    }
}

package com.example.appcine.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appcine.Models.CommentModelClass;
import com.example.appcine.R;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private OnItemClickListenner listenner;

    public interface OnItemClickListenner {
        void onItemClick(View commentView,int position);
    }

    public void setOnItemClickListener(OnItemClickListenner listenner) {
        this.listenner = listenner;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView userName;
        public TextView userComment;

        public ViewHolder(View commentView) {
            super(commentView);
            userName = commentView.findViewById(R.id.tvNombreUsuario);
            userComment = commentView.findViewById(R.id.tvUserOpinionComment);

            commentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listenner != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listenner.onItemClick(commentView, position);
                        }
                    }
                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }

    private List<CommentModelClass> mComment;


    public CommentAdapter(List<CommentModelClass> commentList) {
        mComment = commentList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View commentView = inflater.inflate(R.layout.message_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(commentView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentModelClass comment = mComment.get(position);
        TextView tvUserName = holder.userName;
        tvUserName.setText(comment.getUname());
        TextView tvUserComment = holder.userComment;
        tvUserComment.setText(comment.getContent());
    }

    @Override
    public int getItemCount() {
        return mComment.size();
    }
}

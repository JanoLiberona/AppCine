package com.example.appcine.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.appcine.Models.LikedMovieModel;
import com.example.appcine.R;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private onItemClickListener listener;

    public interface onItemClickListener {
        void onItemClick(View itemview, int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name, rdate;
        public ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_txt);
            img = itemView.findViewById(R.id.imageView);
            rdate = itemView.findViewById(R.id.rdate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }

    private List<LikedMovieModel> mItem;
    private Context mContext;

    public CustomAdapter(Context mContext, List<LikedMovieModel> movieList) {
        this.mItem = movieList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View itemView = inflater.inflate(R.layout.movie_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LikedMovieModel likedMovieModel = mItem.get(position);

        holder.name.setText(mItem.get(position).getName());
        holder.rdate.setText(mItem.get(position).getRdate());
        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w500"+mItem.get(position).getImg())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }
}

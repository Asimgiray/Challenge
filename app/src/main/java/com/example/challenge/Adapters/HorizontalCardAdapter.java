package com.example.challenge.Adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.challenge.DataModels.SinglePost;
import com.example.challenge.FeedDetailActivity;
import com.example.challenge.MainActivity;
import com.example.challenge.R;

import java.util.List;

public class HorizontalCardAdapter extends RecyclerView.Adapter<HorizontalCardAdapter.ViewHolder> {

    private String TAG = HorizontalCardAdapter.class.getSimpleName();
    private boolean DEBUG = true;
    private int feedItemLayout;
    private List<SinglePost> postList;

    public HorizontalCardAdapter(int feedItemLayout, List<SinglePost> postList) {
        this.feedItemLayout = feedItemLayout;
        this.postList = postList;
    }

    @NonNull
    @Override
    public HorizontalCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(feedItemLayout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalCardAdapter.ViewHolder holder, int position) {

        SinglePost post = postList.get(position);
        holder.bindData(post);

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtHorizontalCardTitle;
        public ImageView imgHorizontalCardCover;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtHorizontalCardTitle = itemView.findViewById(R.id.txtHorizontalCardTitle);
            imgHorizontalCardCover = itemView.findViewById(R.id.imgHorizontalCardCover);
        }

        public void bindData(SinglePost post) {

            txtHorizontalCardTitle.setText(post.getTitle());
            Glide.with(itemView).load(post.getImageUrl()).centerCrop().placeholder(R.drawable.default_feed_image).into(imgHorizontalCardCover);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log("HorizontalCardClicked");
                    Log("HorizontalCardTitle : " + post.getTitle());
                    Log("HorizontalCardDesc : " + post.getDescription());
                    Log("HorizontalCardId : " + post.getId());
                    Log("HorizontalCardImage : " + post.getImageUrl());

                    Intent intent = new Intent(MainActivity.mainCtx, FeedDetailActivity.class);
                    intent.putExtra("PostTitle", post.getTitle());
                    intent.putExtra("PostDesc", post.getDescription());
                    intent.putExtra("PostImageUrl", post.getImageUrl());
                    MainActivity.mainCtx.startActivity(intent);

                }
            });

        }
    }

    private void Log(String message) {
        if (DEBUG)
            Log.d(TAG, message);
    }


}

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

public class GridCardAdapter extends RecyclerView.Adapter<GridCardAdapter.ViewHolder> {

    private String TAG = GridCardAdapter.class.getSimpleName();
    private boolean DEBUG = true;
    private int feedItemLayout;
    private List<SinglePost> postList;

    public GridCardAdapter(int feedItemLayout, List<SinglePost> postList) {
        this.feedItemLayout = feedItemLayout;
        this.postList = postList;
    }

    @NonNull
    @Override
    public GridCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(feedItemLayout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GridCardAdapter.ViewHolder holder, int position) {

        SinglePost post = postList.get(position);
        holder.bindData(post);

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgVerticalCardCover;
        public TextView txtVerticalCardTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtVerticalCardTitle = itemView.findViewById(R.id.txtVerticalCardTitle);
            imgVerticalCardCover = itemView.findViewById(R.id.imgVerticalCardCover);
        }

        public void bindData(SinglePost post) {

            txtVerticalCardTitle.setText(post.getTitle());
            Glide.with(itemView).load(post.getImageUrl()).centerCrop().placeholder(R.drawable.default_feed_image).into(imgVerticalCardCover);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log("GridCardClicked");
                    Log("GridCardTitle : " + post.getTitle());
                    Log("GridCardDesc : " + post.getDescription());
                    Log("GridCardId : " + post.getId());
                    Log("GridCardImage : " + post.getImageUrl());

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

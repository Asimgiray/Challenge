package com.example.challenge;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.challenge.Presenters.FeedDetailActivityPresenter;

public class FeedDetailActivity extends AppCompatActivity implements FeedDetailActivityPresenter.View {

    private String postTitle, postDesc, postImgUrl = null;

    private TextView txtFeedDetailTitle, txtFeedDetailDescription;
    private ImageView imgFeedDetailCover;
    private Context context;
    private FeedDetailActivityPresenter feedDetailActivityPresenter;

    private static String TAG = FeedDetailActivity.class.getSimpleName();
    private boolean DEBUG = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_detail);

        txtFeedDetailTitle = findViewById(R.id.txtFeedDetailTitle);
        txtFeedDetailDescription = findViewById(R.id.txtFeedDetailDescription);
        imgFeedDetailCover = findViewById(R.id.imgFeedDetailCover);

        context = this;
        feedDetailActivityPresenter = new FeedDetailActivityPresenter(this);

        postTitle = getIntent().getStringExtra("PostTitle");
        postDesc = getIntent().getStringExtra("PostDesc");
        postImgUrl = getIntent().getStringExtra("PostImageUrl");
        Log("SelectedPostTitle : " + postTitle);
        Log("SelectedPostDesc : " + postDesc);
        Log("SelectedPostImageUrl : " + postImgUrl);

        feedDetailActivityPresenter.setTitleResource(postTitle);
        feedDetailActivityPresenter.setDescResource(postDesc);
        feedDetailActivityPresenter.setImageResource(postImgUrl);


    }

    @Override
    public void setTitleText(String title) {
        txtFeedDetailTitle.setText(title);
    }

    @Override
    public void setDescText(String desc) {
        txtFeedDetailDescription.setText(desc);
    }

    @Override
    public void setImage(String imgUrl) {
        Glide.with(context).load(imgUrl).centerCrop().placeholder(R.drawable.default_feed_image).into(imgFeedDetailCover);
    }

    private void Log(String message) {
        if (DEBUG)
            Log.d(TAG, message);
    }
}
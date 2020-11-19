package com.example.challenge;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.challenge.Adapters.GridCardAdapter;
import com.example.challenge.Adapters.HorizontalCardAdapter;
import com.example.challenge.Adapters.VerticalCardAdapter;
import com.example.challenge.DataModels.Feed;
import com.example.challenge.DataModels.SinglePost;
import com.example.challenge.Presenters.MainActivityPresenter;
import com.example.challenge.Utils.CrashExceptionHandler;
import com.example.challenge.Utils.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements MainActivityPresenter.View {

    private static String TAG = MainActivity.class.getSimpleName();
    private boolean DEBUG = true;
    private SweetAlertDialog loadingAlert;
    public static Context mainCtx;
    private MainActivityPresenter presenter;
    private Feed feedInfo;
    private LinearLayout linWidgets;
    private RecyclerView rvHorizontalCard;
    private RecyclerView rvVerticalCard;
    private RecyclerView rvGridCard;

    private List<SinglePost> horizontalCardList = new ArrayList<>();
    private List<SinglePost> verticalCardList = new ArrayList<>();
    private List<SinglePost> gridCardList = new ArrayList<>();

    private HorizontalCardAdapter horizontalCardAdapter;
    private VerticalCardAdapter verticalCardAdapter;
    private GridCardAdapter gridCardAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainCtx = this;
        presenter = new MainActivityPresenter(this);

        Thread.setDefaultUncaughtExceptionHandler(new CrashExceptionHandler(mainCtx));

        linWidgets = findViewById(R.id.linWidgets);
        rvHorizontalCard = findViewById(R.id.rvHorizontalCard);
        rvVerticalCard = findViewById(R.id.rvVerticalCard);
        rvGridCard = findViewById(R.id.rvGridCard);


        RetrofitClient.getAPI().getFeed()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Feed>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        showAlertDialog();
                        Log("ConnectedService");
                    }

                    @Override
                    public void onNext(Feed feed) {
                        Log("GettingDataFromService");
                        feedInfo = feed;
                        presenter.defineWidgetOrder(feedInfo.getData().getWidgets());

                        presenter.getSliderWidgets(feedInfo.getData().getWidgets());
                        presenter.getCardWidgets(feedInfo.getData().getWidgets());
                        presenter.getGridWidgets(feedInfo.getData().getWidgets());
                        presenter.getHorizontalCardWidgets(feedInfo.getData().getWidgets());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log("ServiceError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        dismissAlertDialog();
                        Log("ServiceDataReturned");
                    }
                });
    }

    private void showAlertDialog() {
        loadingAlert = new SweetAlertDialog(mainCtx, SweetAlertDialog.PROGRESS_TYPE);
        loadingAlert.setTitle(mainCtx.getString(R.string.please_wait));
        loadingAlert.setContentText(mainCtx.getString(R.string.service_started));
        loadingAlert.setCancelable(false);
        loadingAlert.show();
    }

    private void dismissAlertDialog() {
        loadingAlert.dismiss();
    }

    @Override
    public void defineLayoutOrder(String[] orderList) {

        for (String s : orderList) {
            Log("WidgetsOrder: " + s);
        }

        int childCount = linWidgets.getChildCount();
        Log("NumberofChildLayouts: " + childCount);
        View[] children = new View[childCount];

        for (int i = 0; i < childCount; i++) {
            children[i] = linWidgets.getChildAt(i);
        }

        linWidgets.removeAllViews();

        for (String s : orderList) {
            switch (s) {
                case "slider":
                    linWidgets.addView(children[3]);
                    break;
                case "card":
                    linWidgets.addView(children[2]);
                    break;
                case "grid":
                    linWidgets.addView(children[1]);
                    break;
                case "horizantalCard":
                    linWidgets.addView(children[0]);
                    break;
            }
        }
    }

    @Override
    public void getSliderFeed(List<SinglePost> sliderList, int count) {
        Log("SliderDisplayedSize :" + count);
    }

    @Override
    public void getVerticalCardFeed(List<SinglePost> verticalCardList, int count) {
        Log("CardDisplayedSize :" + count);
        verticalCardAdapter = null;

        if (verticalCardList.size() > count) {
            for (int i = 0; i < count; i++) {
                this.verticalCardList.add(verticalCardList.get(i));
            }
        } else {
            this.verticalCardList = verticalCardList;
        }

        verticalCardAdapter = new VerticalCardAdapter(R.layout.feed_vertical_card_layout, this.verticalCardList);
        verticalCardAdapter.notifyDataSetChanged();
        rvVerticalCard.setLayoutManager(new LinearLayoutManager(mainCtx));
        rvVerticalCard.setItemAnimator(new DefaultItemAnimator());
        rvVerticalCard.setAdapter(verticalCardAdapter);
    }

    @Override
    public void getGridCardFeed(List<SinglePost> gridCardList, int count) {
        Log("GridDisplayedSize :" + count);
        gridCardAdapter = null;

        if (gridCardList.size() > count) {
            for (int i = 0; i < count; i++) {
                this.gridCardList.add(gridCardList.get(i));
            }
        } else {
            this.gridCardList = gridCardList;
        }

        gridCardAdapter = new GridCardAdapter(R.layout.feed_grid_card, this.gridCardList);
        gridCardAdapter.notifyDataSetChanged();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mainCtx, 2, GridLayoutManager.HORIZONTAL, false);
        rvGridCard.setLayoutManager(gridLayoutManager);
        rvGridCard.setItemAnimator(new DefaultItemAnimator());
        rvGridCard.setAdapter(gridCardAdapter);

    }

    @Override
    public void getHorizontalCardFeed(List<SinglePost> horizontalCardList, int count) {
        Log("HorizontalCardDisplayedSize :" + count);
        horizontalCardAdapter = null;

        if (horizontalCardList.size() > count) {
            for (int i = 0; i < count; i++) {
                this.horizontalCardList.add(horizontalCardList.get(i));
            }
        } else {
            this.horizontalCardList = horizontalCardList;
        }

        horizontalCardAdapter = new HorizontalCardAdapter(R.layout.feed_horizontal_card_layout, this.horizontalCardList);
        horizontalCardAdapter.notifyDataSetChanged();
        rvHorizontalCard.setLayoutManager(new LinearLayoutManager(mainCtx, LinearLayoutManager.HORIZONTAL, false));
        rvHorizontalCard.setItemAnimator(new DefaultItemAnimator());
        rvHorizontalCard.setAdapter(horizontalCardAdapter);
    }

    private void Log(String message) {
        if (DEBUG)
            Log.d(TAG, message);
    }
}
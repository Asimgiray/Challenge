package com.example.challenge.Presenters;

import android.util.Log;

import com.example.challenge.DataModels.SinglePost;
import com.example.challenge.DataModels.Widget;

import java.util.ArrayList;
import java.util.List;

public class MainActivityPresenter {


    private View view;
    private String TAG = MainActivityPresenter.class.getSimpleName();
    private boolean DEBUG = true;

    public MainActivityPresenter(View view) {
        this.view = view;
    }

    public void defineWidgetOrder(List<Widget> widgetList) {

        int order;
        String widgetType;
        String[] orderList = new String[4];
        for (int i = 0; i < widgetList.size(); i++) {
            order = widgetList.get(i).getOrder();
            widgetType = widgetList.get(i).getName();
            Log("Order: " + order + " WidgetType: " + widgetType);
            orderList[order - 1] = widgetType;
        }
        view.defineLayoutOrder(orderList);
    }

    public void getSliderWidgets(List<Widget> widgetList) {
        List<SinglePost> sliderFeeds = new ArrayList<>();
        int count = 0;

        for (int i = 0; i < widgetList.size(); i++) {
            if (widgetList.get(i).getName().equals("slider")) {
                count = widgetList.get(i).getCount();
                sliderFeeds.addAll(widgetList.get(i).getData());
            }
        }
        Log("SliderFeedTotalSize : " + sliderFeeds.size());
        view.getSliderFeed(sliderFeeds, count);
    }

    public void getCardWidgets(List<Widget> widgetList) {
        List<SinglePost> cardFeeds = new ArrayList<>();
        int count = 0;

        for (int i = 0; i < widgetList.size(); i++) {
            if (widgetList.get(i).getName().equals("card")) {
                count = widgetList.get(i).getCount();
                cardFeeds.addAll(widgetList.get(i).getData());
            }
        }
        Log("CardFeedTotalSize : " + cardFeeds.size());
        view.getVerticalCardFeed(cardFeeds, count);
    }

    public void getGridWidgets(List<Widget> widgetList) {
        List<SinglePost> gridFeeds = new ArrayList<>();
        int count = 0;

        for (int i = 0; i < widgetList.size(); i++) {
            if (widgetList.get(i).getName().equals("grid")) {
                count = widgetList.get(i).getCount();
                gridFeeds.addAll(widgetList.get(i).getData());
            }
        }
        Log("GridFeedTotalSize : " + gridFeeds.size());
        view.getGridCardFeed(gridFeeds, count);
    }

    public void getHorizontalCardWidgets(List<Widget> widgetList) {
        List<SinglePost> horizontalCardFeeds = new ArrayList<>();
        int count = 0;

        for (int i = 0; i < widgetList.size(); i++) {
            if (widgetList.get(i).getName().equals("horizantalCard")) {
                count = widgetList.get(i).getCount();
                horizontalCardFeeds.addAll(widgetList.get(i).getData());
            }
        }
        Log("HorizontalCardFeedTotalSize : " + horizontalCardFeeds.size());
        view.getHorizontalCardFeed(horizontalCardFeeds, count);
    }

    public interface View {

        void defineLayoutOrder(String[] orderList);

        void getSliderFeed(List<SinglePost> sliderList, int count);

        void getVerticalCardFeed(List<SinglePost> verticalCardList, int count);

        void getGridCardFeed(List<SinglePost> gridCardList, int count);

        void getHorizontalCardFeed(List<SinglePost> horizontalCardList, int count);

    }

    private void Log(String message) {
        if (DEBUG)
            Log.d(TAG, message);
    }
}

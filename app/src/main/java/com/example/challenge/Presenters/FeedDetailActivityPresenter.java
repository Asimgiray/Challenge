package com.example.challenge.Presenters;

public class FeedDetailActivityPresenter {

    private View view;

    public FeedDetailActivityPresenter(View view) {
        this.view = view;
    }

    public void setTitleResource(String title) {
        view.setTitleText(title);
    }

    public void setDescResource(String desc) {
        view.setDescText(desc);
    }

    public void setImageResource(String imgUrl) {
        view.setImage(imgUrl);
    }

    public interface View {

        void setTitleText(String title);

        void setDescText(String desc);

        void setImage(String imgUrl);

    }
}

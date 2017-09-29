package com.colin.plana.ui.home.remindtask;

/**
 * Created by colin on 2017/9/28.
 */

public class RemindPresenter implements RemindContract.Presenter {

    private RemindContract.View mView;

    public RemindPresenter(RemindContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


}

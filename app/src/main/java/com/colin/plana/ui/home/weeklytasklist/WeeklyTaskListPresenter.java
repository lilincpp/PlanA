package com.colin.plana.ui.home.weeklytasklist;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by colin on 2017/9/25.
 */

public class WeeklyTaskListPresenter implements WeeklyTaskListContract.Presenter {

    private WeeklyTaskListContract.View mView;

    public WeeklyTaskListPresenter(@NonNull WeeklyTaskListContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}

package com.colin.plana.ui.home.weeklytask;

import android.content.Context;

import com.colin.plana.entities.DailyTask;
import com.colin.plana.ui.base.BasePresenter;
import com.colin.plana.ui.base.BaseView;

import java.util.List;

/**
 * Created by colin on 2017/9/25.
 */

interface WeeklyTaskContract {

    interface View extends BaseView<Presenter> {

        Context getViewContext();

        void onFinish(List<DailyTask> weeklyTask,int today);

    }

    interface Presenter extends BasePresenter {

        void start();

    }
}

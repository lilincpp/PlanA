package com.colin.plana.ui.home.remindtask;

import android.content.Context;

import com.colin.plana.entities.DailyTask;
import com.colin.plana.ui.base.BasePresenter;
import com.colin.plana.ui.base.BaseView;

/**
 * Created by colin on 2017/9/28.
 */

public interface RemindContract {

    interface View extends BaseView<Presenter> {

        Context getViewContext();
    }


    interface Presenter extends BasePresenter {

    }
}

package com.colin.plana.ui.edit;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.colin.plana.ui.base.BasePresenter;
import com.colin.plana.ui.base.BaseView;

/**
 * Created by colin on 2017/9/26.
 */

interface EditTaskContract {

    interface View extends BaseView<Presenter> {

        void onSaveFinish();

        void onError(String msg);

        Context getViewContext();
    }

    interface Presenter extends BasePresenter {

        void save(String title, String content, int dailyNumber);
    }
}

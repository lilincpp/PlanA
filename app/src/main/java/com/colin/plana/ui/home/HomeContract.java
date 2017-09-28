package com.colin.plana.ui.home;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.colin.plana.ui.base.BasePresenter;
import com.colin.plana.ui.base.BaseView;

/**
 * Created by colin on 2017/9/28.
 */

public interface HomeContract {

    interface View extends BaseView<Presenter> {

        void moveToFragment(Fragment fragment);

        Context getContextView();

    }


    interface Presenter extends BasePresenter {
        void onClick(int itemId);

        void start();
    }
}

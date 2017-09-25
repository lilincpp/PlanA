package com.colin.plana.ui.home.weeklytasklist;

import com.colin.plana.ui.base.BasePresenter;
import com.colin.plana.ui.base.BaseView;

/**
 * Created by colin on 2017/9/25.
 */

public interface WeeklyTaskListContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        void start();

    }
}

package com.colin.plana.ui.home.weeklytask.dailytask;

import android.content.Context;

import com.colin.plana.entities.TaskEntity;
import com.colin.plana.ui.base.BasePresenter;
import com.colin.plana.ui.base.BaseView;

import java.util.List;

/**
 * Created by colin on 2017/9/25.
 */

public interface TaskListContract {

    interface View extends BaseView<Presenter> {

        void onChangeTaskTypeFinish();

        Context getViewContext();

    }

    interface Presenter extends BasePresenter {
        void changeTaskType(TaskEntity task, int type);

        void changeTaskType(List<TaskEntity> task, int type);
    }
}

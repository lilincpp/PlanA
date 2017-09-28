package com.colin.plana.ui.home.weeklytask.dailytask;

import android.content.Context;
import android.os.AsyncTask;

import com.colin.plana.database.TaskDatabaseHelper;
import com.colin.plana.entities.TaskEntity;

import java.lang.ref.WeakReference;

/**
 * Created by colin on 2017/9/25.
 */

public class TaskListPresenter implements TaskListContract.Presenter {

    private TaskListContract.View mView;

    public TaskListPresenter(TaskListContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void changeTaskType(TaskEntity task, int type) {
        ChangeTypeTask deleteTask = new ChangeTypeTask(mView.getViewContext(), type);
        deleteTask.execute(task);
    }

    private class ChangeTypeTask extends AsyncTask<TaskEntity, Void, Void> {
        private WeakReference<Context> contextWeakReference;
        private int targeType;

        public ChangeTypeTask(Context context, int targeType) {
            contextWeakReference = new WeakReference<Context>(context);
            this.targeType = targeType;
        }

        @Override
        protected Void doInBackground(TaskEntity... params) {
            TaskDatabaseHelper.changeTaskType(contextWeakReference.get(), params[0], targeType);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mView.onChangeTaskTypeFinish();
        }
    }
}

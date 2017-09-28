package com.colin.plana.ui.home;

import android.content.Context;
import android.os.AsyncTask;

import com.colin.plana.R;
import com.colin.plana.constants.TaskType;
import com.colin.plana.database.TaskDatabaseHelper;
import com.colin.plana.entities.DailyTask;
import com.colin.plana.entities.TaskEntity;
import com.colin.plana.ui.home.weeklytask.WeeklyTaskFragment;
import com.colin.plana.ui.home.weeklytask.WeeklyTaskPresenter;
import com.colin.plana.ui.home.weeklytask.dailytask.TaskListFragment;
import com.colin.plana.ui.home.weeklytask.dailytask.TaskListPresenter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by colin on 2017/9/28.
 */

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;

    public HomePresenter(HomeContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        WeeklyTaskFragment fragment = new WeeklyTaskFragment();
        new WeeklyTaskPresenter(fragment);
        mView.moveToFragment(fragment);
    }

    @Override
    public void onClick(int itemId) {
        LoadDataTask loadDataTask = new LoadDataTask(mView.getContextView());
        LoadTaskInfo info = new LoadTaskInfo();

        switch (itemId) {
            case R.id.nav_plan:
                WeeklyTaskFragment fragment = new WeeklyTaskFragment();
                new WeeklyTaskPresenter(fragment);
                mView.moveToFragment(fragment);
                return;
            case R.id.nav_file:
                info.type = TaskType.TYPE_FILE;
                info.name = "已归档";
                break;
            default:

                return;
        }

        loadDataTask.execute(info);

    }

    private class LoadDataTask extends AsyncTask<LoadTaskInfo, Void, DailyTask> {

        private WeakReference<Context> contextWeakReference;

        public LoadDataTask(Context context) {
            contextWeakReference = new WeakReference<Context>(context);
        }

        @Override
        protected DailyTask doInBackground(LoadTaskInfo... params) {
            ArrayList<TaskEntity> tasks = TaskDatabaseHelper.queryTaskList(contextWeakReference.get(), params[0].type);
            return new DailyTask(params[0].name, params[0].type, tasks);
        }

        @Override
        protected void onPostExecute(DailyTask task) {
            TaskListFragment fragment = TaskListFragment.newInstance(task);
            new TaskListPresenter(fragment);
            mView.moveToFragment(fragment);
        }
    }

    static class LoadTaskInfo {
        int type;
        String name;
    }
}

package com.colin.plana.ui.home;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.colin.plana.R;
import com.colin.plana.constants.TaskType;
import com.colin.plana.database.TaskDatabaseHelper;
import com.colin.plana.entities.DailyTask;
import com.colin.plana.entities.TaskEntity;
import com.colin.plana.ui.home.remindtask.RemindTaskFragment;
import com.colin.plana.ui.home.weeklytask.WeeklyTaskFragment;
import com.colin.plana.ui.home.weeklytask.WeeklyTaskPresenter;
import com.colin.plana.ui.home.weeklytask.dailytask.TaskListFragment;
import com.colin.plana.ui.home.weeklytask.dailytask.TaskListPresenter;
import com.colin.plana.utils.PixelUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by colin on 2017/9/28.
 */

public class HomePresenter implements HomeContract.Presenter {

    private static final String TAG = "HomePresenter";

    private HomeContract.View mView;
    private LoadTaskInfo mInfo = new LoadTaskInfo();

    public HomePresenter(HomeContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        Log.e(TAG, "start: " + mInfo.type);
        switch (mInfo.type) {
            case TaskType.TYPE_DOING:
                onClick(R.id.nav_plan);
                break;
            case TaskType.TYPE_REMIND:
                onClick(R.id.nav_remind);
                break;
            case TaskType.TYPE_FILE:
                onClick(R.id.nav_file);
                break;
            case TaskType.TYPE_DELETE:
                onClick(R.id.nav_ashcan);
                break;
            default:
                toWeeklyPlan();
                break;
        }
    }

    private void toWeeklyPlan() {
        WeeklyTaskFragment fragment = new WeeklyTaskFragment();
        new WeeklyTaskPresenter(fragment);
        mView.moveToFragment(fragment);
        mView.setToolBarTitle(mInfo.name);
        mView.setToolBarElevation(
                mView.getContextView().getResources().getDimension(R.dimen.elevation_0)
        );

    }

    private void toTaskList(DailyTask task) {
        TaskListFragment fragment = TaskListFragment.newInstance(task);
        mView.moveToFragment(fragment);
        mView.setToolBarTitle(mInfo.name);
        mView.setToolBarElevation(
                mView.getContextView().getResources().getDimension(R.dimen.elevation_4)
        );
    }

    private void toRemindPlan(DailyTask task) {
        RemindTaskFragment fragment = RemindTaskFragment.newInstance(task);
        mView.moveToFragment(fragment);
        mView.setToolBarTitle(mInfo.name);
        mView.setToolBarElevation(
                mView.getContextView().getResources().getDimension(R.dimen.elevation_4)
        );
    }

    @Override
    public void onClick(int itemId) {
        LoadDataTask loadDataTask = new LoadDataTask(mView.getContextView());

        switch (itemId) {
            case R.id.nav_plan:
                mInfo.type = TaskType.TYPE_DOING;
                mInfo.name = "计划";
                toWeeklyPlan();
                return;
            case R.id.nav_remind:
                mInfo.type = TaskType.TYPE_REMIND;
                mInfo.name = "提醒";
                break;
            case R.id.nav_file:
                mInfo.type = TaskType.TYPE_FILE;
                mInfo.name = "已归档";
                break;
            case R.id.nav_ashcan:
                mInfo.type = TaskType.TYPE_DELETE;
                mInfo.name = "垃圾桶";
                break;
            default:

                return;
        }
        loadDataTask.execute(mInfo);
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
            switch (task.getType()) {
                case TaskType.TYPE_FILE:
                case TaskType.TYPE_DELETE:
                    toTaskList(task);
                    break;
                case TaskType.TYPE_REMIND:
                    toRemindPlan(task);
                    break;
            }
        }
    }

    static class LoadTaskInfo {
        int type;
        String name;
    }
}

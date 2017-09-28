package com.colin.plana.ui.home.weeklytask;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.colin.plana.constants.DailyNumbers;
import com.colin.plana.constants.TaskType;
import com.colin.plana.database.TaskDatabaseHelper;
import com.colin.plana.entities.DailyTask;
import com.colin.plana.entities.TaskEntity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by colin on 2017/9/25.
 */

public class WeeklyTaskPresenter implements WeeklyTaskContract.Presenter {

    private static final String TAG = "WeeklyTaskPresenter";

    private WeeklyTaskContract.View mView;

    public WeeklyTaskPresenter(@NonNull WeeklyTaskContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        QueryTask queryTask = new QueryTask(mView.getViewContext());
        queryTask.execute();
    }

    private class QueryTask extends AsyncTask<Void, Void, List<DailyTask>> {

        private WeakReference<Context> mContextWeakReference;

        public QueryTask(Context context) {
            mContextWeakReference = new WeakReference<>(context);
        }

        @Override
        protected List<DailyTask> doInBackground(Void... voids) {
            List<TaskEntity> taskEntities = TaskDatabaseHelper.queryTaskList(mContextWeakReference.get(), TaskType.TYPE_DOING);
            List<DailyTask> dailyTasks = new ArrayList<>();
            for (int i = 0; i < DailyNumbers.DAILY_NUMBERS.length; ++i) {
                DailyTask dailyTask = new DailyTask(
                        DailyNumbers.DAILY_NAME[i], DailyNumbers.DAILY_NUMBERS[i], TaskType.TYPE_DOING
                );
                final int currentNumber = DailyNumbers.DAILY_NUMBERS[i];
                ArrayList<TaskEntity> taskList = new ArrayList<>();
                for (TaskEntity temp : taskEntities) {
                    if (temp.getDailyNumber() == currentNumber) {
                        taskList.add(temp);
                    }
                }
                dailyTask.setTaskEntities(taskList);
                dailyTasks.add(dailyTask);
            }
            return dailyTasks;
        }

        @Override
        protected void onPostExecute(List<DailyTask> taskEntities) {
            if (mView != null) {
                mView.onFinish(taskEntities, getToday());
            }
        }
    }

    private static final int getToday() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String today = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        switch (today) {
            case "1":
                return DailyNumbers.INDEX_SUNDAY;
            case "2":
                return DailyNumbers.INDEX_MONDAY;
            case "3":
                return DailyNumbers.INDEX_TUESDAY;
            case "4":
                return DailyNumbers.INDEX_WEDNESDAY;
            case "5":
                return DailyNumbers.INDEX_THURSDAY;
            case "6":
                return DailyNumbers.INDEX_FRIDAY;
            case "7":
                return DailyNumbers.INDEX_SATURDAY;
            default:
                return DailyNumbers.INDEX_MONDAY;
        }
    }

}

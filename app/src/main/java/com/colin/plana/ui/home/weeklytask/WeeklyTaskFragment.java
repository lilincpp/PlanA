package com.colin.plana.ui.home.weeklytask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.colin.plana.R;
import com.colin.plana.entities.DailyTask;
import com.colin.plana.entities.TaskEntity;
import com.colin.plana.ui.edit.EditTaskActivity;
import com.colin.plana.utils.CommonUtil;

import java.util.List;

/**
 * Created by colin on 2017/9/25.
 */

public class WeeklyTaskFragment extends Fragment implements WeeklyTaskContract.View, View.OnClickListener {

    private static final String TAG = "WeeklyTaskFragment";
    private TabLayout mTaskNames;
    private ViewPager mWeeklyTask;
    private LinearLayout mAddTask;
    private WeeklyTaskAapter mWeeklyTaskAapter;

    private WeeklyTaskContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weeklytask, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "### onResume()");
        mPresenter.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "### onPause()");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e(TAG, "onHiddenChanged=" + hidden);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_add_task:
                Intent intent = new Intent(getActivity(), EditTaskActivity.class);
                intent.putExtra(EditTaskActivity.INTENT_DAILY_TASK_KEY, getCurrentDailyTask());
                startActivity(intent);
                break;
        }
    }

    private DailyTask getCurrentDailyTask() {
        return mWeeklyTaskAapter.getDailyTask(mWeeklyTask.getCurrentItem());
    }

    private void initView(View view) {
        mTaskNames = (TabLayout) view.findViewById(R.id.tl_date);
        mWeeklyTask = (ViewPager) view.findViewById(R.id.vp_tasklist);
        mAddTask = (LinearLayout) view.findViewById(R.id.lay_add_task);
        mTaskNames.setupWithViewPager(mWeeklyTask);
        mAddTask.setOnClickListener(this);

    }

    @Override
    public void setPresenter(WeeklyTaskContract.Presenter presenter) {
        mPresenter = CommonUtil.checkNotNull(presenter);
    }

    @Override
    public Context getViewContext() {
        return getActivity();
    }

    @Override
    public void onFinish(List<DailyTask> weeklyTask, int today) {
        if (mWeeklyTaskAapter != null) {
            mWeeklyTaskAapter.setDailyTasks(weeklyTask);
        } else {
            mWeeklyTaskAapter = new WeeklyTaskAapter(getChildFragmentManager(), weeklyTask);
            mWeeklyTask.setAdapter(mWeeklyTaskAapter);
        }

        mWeeklyTask.setCurrentItem(today);
    }
}

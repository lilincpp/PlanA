package com.colin.plana.ui.home.remindtask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.colin.plana.R;
import com.colin.plana.entities.DailyTask;
import com.colin.plana.ui.edit.EditTaskActivity;
import com.colin.plana.ui.home.weeklytask.dailytask.TaskListFragment;

/**
 * Created by colin on 2017/9/28.
 */

public class RemindTaskFragment extends Fragment implements RemindContract.View, View.OnClickListener {

    private LinearLayout mAddTask;
    private RemindContract.Presenter mPresenter;
    private DailyTask mDailyTask;


    public static RemindTaskFragment newInstance(DailyTask task) {
        Bundle bundle = new Bundle();
        RemindTaskFragment remindTaskFragment = new RemindTaskFragment();
        bundle.putParcelable(TaskListFragment.TASK_KEY, task);
        remindTaskFragment.setArguments(bundle);
        new RemindPresenter(remindTaskFragment);
        return remindTaskFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remind, container, false);

        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_add_task:
                Intent intent = new Intent(getActivity(), EditTaskActivity.class);
                intent.putExtra(EditTaskActivity.INTENT_DAILY_TASK_KEY, mDailyTask);
                startActivity(intent);
                break;
        }
    }

    private void initView(View view) {
        mAddTask = (LinearLayout) view.findViewById(R.id.lay_add_task);
        mAddTask.setOnClickListener(this);
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mDailyTask = bundle.getParcelable(TaskListFragment.TASK_KEY);
        }
        showTaskList();
    }

    private void showTaskList() {
        TaskListFragment fragment = TaskListFragment.newInstance(mDailyTask);
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment).commit();
    }

    @Override
    public void setPresenter(RemindContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public Context getViewContext() {
        return getActivity();
    }
}

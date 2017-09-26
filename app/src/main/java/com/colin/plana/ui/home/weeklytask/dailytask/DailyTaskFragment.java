package com.colin.plana.ui.home.weeklytask.dailytask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.colin.plana.R;
import com.colin.plana.entities.DailyTask;
import com.colin.plana.entities.TaskEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by colin on 2017/9/25.
 */

public class DailyTaskFragment extends Fragment {

    private static final String BUNDLE_KEY = "DAILY_TASK_KEY";

    private RecyclerView mDailyTaskList;
    private TextView mTvEmpty;

    private DailyTask mDailyTask;
    private TaskListAdapter mTaskListAdapter;


    public static DailyTaskFragment newInstance(DailyTask dailyTask) {
        Bundle bundle = new Bundle();
        DailyTaskFragment dailyTaskFragment = new DailyTaskFragment();
        bundle.putParcelable(BUNDLE_KEY, dailyTask);
        dailyTaskFragment.setArguments(bundle);
        return dailyTaskFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dailytask, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mDailyTaskList = (RecyclerView) view.findViewById(R.id.rv_task);
        mTvEmpty = (TextView) view.findViewById(R.id.tv_empty);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {

        Bundle bundle = getArguments();
        if (bundle != null) {
            mDailyTask = bundle.getParcelable(BUNDLE_KEY);
            if (mDailyTask.isEmpty()) {
                showEmptyView();
            } else {
                showTasklist();
            }
        }
    }

    private void showEmptyView() {
        mDailyTaskList.setVisibility(View.GONE);
        mTvEmpty.setVisibility(View.VISIBLE);
    }

    private void showTasklist() {
        mTaskListAdapter = new TaskListAdapter(mDailyTask.getTaskEntities());
        mDailyTaskList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDailyTaskList.setAdapter(mTaskListAdapter);
        mDailyTaskList.addItemDecoration(new LastSpaceItemDecoration());

    }
}

package com.colin.plana.ui.home.weeklytask.dailytask;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.colin.plana.R;
import com.colin.plana.constants.TaskType;
import com.colin.plana.entities.DailyTask;

/**
 * Created by colin on 2017/9/25.
 */

public class TaskListFragment extends Fragment implements TaskListContract.View {

    private static final String BUNDLE_KEY = "DAILY_TASK_KEY";

    private RecyclerView mDailyTaskList;
    private TextView mTvEmpty;

    private DailyTask mDailyTask;
    private TaskListAdapter mTaskListAdapter;

    private TaskListContract.Presenter mPresenter;


    public static TaskListFragment newInstance(DailyTask dailyTask) {
        Bundle bundle = new Bundle();
        TaskListFragment dailyTaskFragment = new TaskListFragment();
        bundle.putParcelable(BUNDLE_KEY, dailyTask);
        dailyTaskFragment.setArguments(bundle);
        new TaskListPresenter(dailyTaskFragment);
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

        //只有当前活动的任务，才可以进行上下拖拽及左右滑动
        if (mDailyTask.getType() == TaskType.TYPE_DOING) {
            new ItemTouchHelper(new ItemTouchHelper.Callback() {
                @Override
                public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                    //拖拽只处理上下两个方向
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    //滑动只处理左右两个方向
                    final int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }

                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    int fromPosition = viewHolder.getAdapterPosition();
                    int toPosition = target.getAdapterPosition();
                    mTaskListAdapter.notifyItemMoved(fromPosition, toPosition);
                    return true;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    int position = viewHolder.getAdapterPosition();
                    mPresenter.changeTaskType(mTaskListAdapter.getTaskForPosition(position), TaskType.TYPE_FILE);
                    mTaskListAdapter.deleteTaskForPosition(position);
                    mTaskListAdapter.notifyItemRemoved(position);
                }
            }).attachToRecyclerView(mDailyTaskList);
        }
    }

    @Override
    public void setPresenter(TaskListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onChangeTaskTypeFinish() {

    }

    @Override
    public Context getViewContext() {
        return getActivity();
    }
}

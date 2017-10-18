package com.colin.plana.ui.home.weeklytask.dailytask;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.colin.plana.R;
import com.colin.plana.constants.TaskType;
import com.colin.plana.entities.DailyTask;
import com.colin.plana.entities.TaskEntity;
import com.colin.plana.ui.home.MainActivity;

/**
 * Created by colin on 2017/9/25.
 */

public class TaskListFragment extends Fragment implements TaskListContract.View, TaskListAdapter.onItemLongClickListener {

    private static final String TAG = "TaskListFragment";
    public static final String TASK_KEY = "DAILY_TASK_KEY";

    private RecyclerView mDailyTaskList;
    private TextView mTvEmpty;

    private DailyTask mDailyTask;
    private TaskListAdapter mTaskListAdapter;

    private TaskListContract.Presenter mPresenter;


    public static TaskListFragment newInstance(DailyTask dailyTask) {
        Bundle bundle = new Bundle();
        TaskListFragment dailyTaskFragment = new TaskListFragment();
        bundle.putParcelable(TASK_KEY, dailyTask);
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
            mDailyTask = bundle.getParcelable(TASK_KEY);
            if (mDailyTask == null || mDailyTask.isEmpty()) {
                showEmptyView();
            } else {
                showTasklist();
            }
        } else {
            showEmptyView();
        }
    }

    private void showEmptyView() {
        mDailyTaskList.setVisibility(View.GONE);
        mTvEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLongClick(int position) {
        Log.e(TAG, "onLongClick: " + position);
        if (getActivity() != null && (getActivity() instanceof MainActivity)) {
            ((MainActivity) getActivity()).changeMenuType(MainActivity.TYPE_MENU_LONG_CLICK);
        }
    }

    private void showTasklist() {
        mTaskListAdapter = new TaskListAdapter(mDailyTask.getTaskEntities());
        mDailyTaskList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDailyTaskList.setAdapter(mTaskListAdapter);

        mDailyTaskList.addItemDecoration(new LastSpaceItemDecoration());
        mTaskListAdapter.setOnItemLongClickListener(this);

        //只有当前活动的任务(计划、提醒列表)，才可以进行上下拖拽及左右滑动删除归档
        if (mDailyTask.getType() == TaskType.TYPE_DOING
                || mDailyTask.getType() == TaskType.TYPE_REMIND) {
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
                    final int position = viewHolder.getAdapterPosition();
                    final TaskEntity moveTask = mTaskListAdapter.getTaskForPosition(position);

                    mPresenter.changeTaskType(moveTask, TaskType.TYPE_FILE);
                    mTaskListAdapter.deleteTaskForPosition(position);
                    mTaskListAdapter.notifyItemRemoved(position);

                    Snackbar.make(mTvEmpty, "已将计划归档", Snackbar.LENGTH_SHORT).setAction("撤销", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPresenter.changeTaskType(moveTask, mDailyTask.getType());
                            mTaskListAdapter.addTaskForPosition(moveTask, position);
                            mTaskListAdapter.notifyItemInserted(position);
                        }
                    }).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {

                        }
                    }).setActionTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary)).show();
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


    private onMenuChangedListener mOnMenuChangedListener;

    public interface onMenuChangedListener {
        void onChange(int type);
    }
}

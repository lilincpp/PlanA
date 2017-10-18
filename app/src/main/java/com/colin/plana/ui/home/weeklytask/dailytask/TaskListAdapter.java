package com.colin.plana.ui.home.weeklytask.dailytask;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colin.plana.R;
import com.colin.plana.constants.TaskType;
import com.colin.plana.entities.TaskEntity;
import com.colin.plana.ui.home.MainActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by colin on 2017/9/26.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    private static final String TAG = "TaskListAdapter";
    private List<TaskEntity> taskEntities;
    private onItemLongClickListener mOnItemLongClickListener;
    private int mMenuType = MainActivity.TYPE_MENU_NORMAL;
    private int mType;

    public TaskListAdapter(List<TaskEntity> tasks, int type) {
        taskEntities = tasks;
        mType = type;
    }

    public TaskEntity getTaskForPosition(int position) {
        return taskEntities.get(position);
    }

    public void deleteTask(TaskEntity taskEntity) {
        taskEntities.remove(taskEntity);
    }

    public void addTaskForPosition(TaskEntity entity, int position) {
        Log.e(TAG, "addTaskForPosition: " + position + "," + entity.toString());
        taskEntities.add(position, entity);
    }

    public void setOnItemLongClickListener(onItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    public void setType(int type) {
        mMenuType = type;
        notifyDataSetChanged();
    }

    public Map<Integer, TaskEntity> getSelectedTasks() {
        Map<Integer, TaskEntity> selected = new HashMap<>();
        for (int i = 0; i < taskEntities.size(); ++i) {
            if (taskEntities.get(i).isSelected()) {
                selected.put(i, taskEntities.get(i));
            }
        }
        return selected;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final TaskEntity task = taskEntities.get(position);
        final String title = task.getTitle();
        final String content = task.getContent();

        if (mMenuType == MainActivity.TYPE_MENU_NORMAL) {
            task.setSelected(false);
        }

        holder.lay.setSelected(task.isSelected());

        if (TextUtils.isEmpty(title)) {
            holder.tvTitle.setVisibility(View.GONE);
            holder.divider.setVisibility(View.GONE);
        } else {
            holder.tvTitle.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(content)) {
            holder.tvInfo.setVisibility(View.GONE);
            holder.divider.setVisibility(View.GONE);
        } else {
            holder.tvInfo.setVisibility(View.VISIBLE);
        }

        holder.tvTitle.setText(title);
        holder.tvInfo.setText(content);

        holder.lay.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (mType != TaskType.TYPE_FILE) {
                    return true;
                }

                if (mMenuType == MainActivity.TYPE_MENU_NORMAL) {
                    if (mOnItemLongClickListener != null) {
                        mOnItemLongClickListener.onLongClick();
                    }
                    mMenuType = MainActivity.TYPE_MENU_LONG_CLICK;
                }

                task.setSelected(true);
                notifyItemChanged(position);
                return true;
            }
        });

        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMenuType == MainActivity.TYPE_MENU_LONG_CLICK) {
                    task.setSelected(!task.isSelected());
                    notifyItemChanged(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskEntities.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout lay;
        TextView tvInfo, tvTitle;
        View divider;

        public ViewHolder(final View itemView) {
            super(itemView);
            lay = (LinearLayout) itemView.findViewById(R.id.lay);
            tvInfo = (TextView) itemView.findViewById(R.id.tv_task_content);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_task_title);
            divider = itemView.findViewById(R.id.divider);

        }
    }


    interface onItemLongClickListener {
        void onLongClick();
    }
}

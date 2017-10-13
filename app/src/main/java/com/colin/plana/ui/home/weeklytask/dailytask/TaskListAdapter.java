package com.colin.plana.ui.home.weeklytask.dailytask;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.colin.plana.R;
import com.colin.plana.entities.TaskEntity;

import java.util.List;

/**
 * Created by colin on 2017/9/26.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    private List<TaskEntity> taskEntities;
    private onItemLongClickListener mOnItemLongClickListener;

    public TaskListAdapter(List<TaskEntity> tasks) {
        taskEntities = tasks;
    }

    public TaskEntity getTaskForPosition(int position) {
        return taskEntities.get(position);
    }

    public void deleteTaskForPosition(int position) {
        taskEntities.remove(position);
    }

    public void addTaskForPosition(TaskEntity entity, int position) {
        taskEntities.add(position, entity);
    }

    public void setOnItemLongClickListener(onItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final TaskEntity task = taskEntities.get(position);
        final String title = task.getTitle();
        final String content = task.getContent();
        if (TextUtils.isEmpty(title)) {
            holder.tvTitle.setVisibility(View.GONE);
            holder.divider.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(content)) {
            holder.tvInfo.setVisibility(View.GONE);
            holder.divider.setVisibility(View.GONE);
        }

        holder.tvTitle.setText(title);
        holder.tvInfo.setText(content);
    }

    @Override
    public int getItemCount() {
        return taskEntities.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvInfo, tvTitle;
        View divider;

        public ViewHolder(View itemView) {
            super(itemView);
            tvInfo = (TextView) itemView.findViewById(R.id.tv_task_content);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_task_title);
            divider = itemView.findViewById(R.id.divider);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemLongClickListener != null) {
                        mOnItemLongClickListener.onLongClick(getAdapterPosition());
                    }
                    return true;
                }
            });
        }
    }

    interface onItemLongClickListener {
        void onLongClick(int position);
    }
}

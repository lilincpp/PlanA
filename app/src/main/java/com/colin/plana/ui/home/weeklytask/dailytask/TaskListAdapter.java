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

    public TaskListAdapter(List<TaskEntity> tasks) {
        taskEntities = tasks;
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
        final String text =
                (TextUtils.isEmpty(title)
                        ? content :
                        (TextUtils.isEmpty(content) ?
                                title : (title + "\n" + content))
                );
        holder.tvInfo.setText(text);
    }

    @Override
    public int getItemCount() {
        return taskEntities.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            tvInfo = (TextView) itemView.findViewById(R.id.tv_task_info);
        }
    }
}

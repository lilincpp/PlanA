package com.colin.plana.ui.home.weeklytask;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.colin.plana.entities.DailyTask;
import com.colin.plana.ui.home.weeklytask.dailytask.DailyTaskFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by colin on 2017/9/25.
 */

public class WeeklyTaskAapter extends FragmentPagerAdapter {

    private List<DailyTask> dailyTasks = new ArrayList<>();

    public WeeklyTaskAapter(FragmentManager fm, List<DailyTask> tasks) {
        super(fm);
        this.dailyTasks = tasks;
    }

    @Override
    public Fragment getItem(int position) {
        return DailyTaskFragment.newInstance(dailyTasks.get(position));
    }

    @Override
    public int getCount() {
        return dailyTasks.size();
    }

    public DailyTask getDailyTask(int position) {
        return dailyTasks.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return dailyTasks.get(position).getName();
    }
}

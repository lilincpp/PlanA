package com.colin.plana.ui.home.weeklytasklist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.colin.plana.R;
import com.colin.plana.utils.CommonUtil;

/**
 * Created by colin on 2017/9/25.
 */

public class WeeklyTaskListFragment extends Fragment implements WeeklyTaskListContract.View {
    private TabLayout mTabLayoutTaskNames;
    private ViewPager mViewPagerTasklist;
    private LinearLayout mLinearLayoutAddTask;

    private WeeklyTaskListContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_showtask, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mTabLayoutTaskNames = view.findViewById(R.id.tl_date);
        mViewPagerTasklist = view.findViewById(R.id.vp_tasklist);
        mLinearLayoutAddTask = view.findViewById(R.id.lay_add_task);

        mTabLayoutTaskNames.setElevation(4);
    }

    @Override
    public void setPresenter(WeeklyTaskListContract.Presenter presenter) {
        mPresenter = CommonUtil.checkNotNull(presenter);
    }
}

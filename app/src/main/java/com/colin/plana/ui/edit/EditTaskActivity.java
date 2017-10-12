package com.colin.plana.ui.edit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colin.plana.R;
import com.colin.plana.entities.DailyTask;
import com.colin.plana.ui.view.menu.BottomMenuManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by colin on 2017/9/26.
 */

public class EditTaskActivity extends AppCompatActivity implements EditTaskContract.View, View.OnClickListener {

    private static final String TAG = "EditTaskActivity";

    public static final String INTENT_DAILY_TASK_KEY = "INTENT_DAILY_TASK_KEY";

    private EditText mEtTitle, mEtContent;
    private ImageButton mEditTaskType, mEditTaskStyle;
    private TextView mTvShowEditTime;
    private LinearLayout mBottomMenu;
    private LinearLayout mRootView;

    private BottomMenuManager mTypeMenu;

    private int mDailyNumber = -1, mType = -1;
    private EditTaskContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittask);

        initView();

        new EditTaskPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            DailyTask dailyTask = intent.getParcelableExtra(INTENT_DAILY_TASK_KEY);
            mDailyNumber = dailyTask.getDailyNumber();
            mType = dailyTask.getType();
        }
    }

    private void initView() {
        mEtTitle = (EditText) findViewById(R.id.et_title);
        mEtContent = (EditText) findViewById(R.id.et_content);

        mEditTaskType = (ImageButton) findViewById(R.id.ib_task_type);
        mEditTaskStyle = (ImageButton) findViewById(R.id.ib_task_style);
        mTvShowEditTime = (TextView) findViewById(R.id.tv_task_edit_time);

        mBottomMenu = (LinearLayout) findViewById(R.id.lay_bottom_menu);
        mRootView = (LinearLayout) findViewById(R.id.rootView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(mNavigationOnClickListener);
        mEtTitle.setOnEditorActionListener(mOnEditorActionListener);

        mEditTaskType.setOnClickListener(this);
        mEditTaskStyle.setOnClickListener(this);

        mTypeMenu = BottomMenuManager.make(mRootView, mBottomMenu);
        mTypeMenu.setOnMenuItemClickListener(mOnMenuItemClickListener);

        mTypeMenu.setAddIndex(2);
        mTypeMenu.createMenuItem(makeMenu());
    }

    private List<BottomMenuManager.MenuItem> makeMenu() {
        List<BottomMenuManager.MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new BottomMenuManager.MenuItem("运动", R.drawable.ic_directions_run_black_24dp));
        menuItems.add(new BottomMenuManager.MenuItem("提醒", R.drawable.ic_alarm_black_24dp));
        menuItems.add(new BottomMenuManager.MenuItem("面试", R.drawable.ic_add_alert_black_24dp));
        return menuItems;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_task_type:
                if (mTypeMenu.isShowing()) {
                    mTypeMenu.hide();
                } else {
                    mTypeMenu.show();
                }
                break;
            case R.id.ib_task_style:

                break;
        }
    }

    private BottomMenuManager.onMenuItemClickListener mOnMenuItemClickListener = new BottomMenuManager.onMenuItemClickListener() {
        @Override
        public void onClick(BottomMenuManager.MenuItem item) {
            switch (item.name) {
                case "面试":
                    mEtTitle.setText("面试");
                    mEtContent.setText(
                            "公司：\n"
                                    + "时间：\n"
                                    + "地点：\n"
                    );
                    break;
                case "运动":
                    mEtTitle.setText("运动");
                    mEtContent.setText(
                            "项目：\n"
                                    + "时间：\n"
                                    + "目标：\n"
                    );
                    break;
            }
            mTypeMenu.hide();
        }
    };

    //屏蔽EditText的回车键，并且让下一个EditText获得焦点
    private TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            Log.e(TAG, "onEditorAction: " + actionId);
            Log.e(TAG, "onEditorAction: " + EditorInfo.IME_ACTION_NEXT);

            //可以选择监听点击事件，也可以选择通过actionId来判断
            if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                mEtContent.requestFocus();
                return true;
            }
            return false;
        }
    };


    private View.OnClickListener mNavigationOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.save(mEtTitle.getText().toString(), mEtContent.getText().toString(), mDailyNumber, mType);
        }
    };

    @Override
    public void onBackPressed() {
        mPresenter.save(mEtTitle.getText().toString(), mEtContent.getText().toString(), mDailyNumber, mType);
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void setPresenter(EditTaskContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onSaveFinish() {
        finish();
    }

    @Override
    public void onError(String msg) {
        finish();
    }
}

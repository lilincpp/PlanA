package com.colin.plana.ui.edit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.colin.plana.R;
import com.colin.plana.entities.DailyTask;

/**
 * Created by colin on 2017/9/26.
 */

public class EditTaskActivity extends AppCompatActivity implements EditTaskContract.View {

    public static final String INTENT_DAILY_TASK_KEY = "INTENT_DAILY_TASK_KEY";

    private EditText mEtTitle, mEtContent;
    private int mDailyNumber = -1;
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
        }
    }

    private void initView() {
        mEtTitle = (EditText) findViewById(R.id.et_title);
        mEtContent = (EditText) findViewById(R.id.et_content);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(mNavigationOnClickListener);
    }


    private View.OnClickListener mNavigationOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.save(mEtTitle.getText().toString(), mEtContent.getText().toString(), mDailyNumber);
        }
    };

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

    }
}

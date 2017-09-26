package com.colin.plana.ui.edit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.colin.plana.R;

/**
 * Created by colin on 2017/9/26.
 */

public class EditTaskActivity extends AppCompatActivity {

    public static final String INTENT_DAILY_TASK_KEY = "INTENT_DAILY_TASK_KEY";


    private EditText mEtTitle, mEtContent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittask);

        initView();
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
            finish();
        }
    };

}

package com.colin.plana.ui.edit;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.colin.plana.constants.DailyNumbers;
import com.colin.plana.constants.TaskType;
import com.colin.plana.database.TaskDatabaseHelper;
import com.colin.plana.entities.TaskEntity;

import java.lang.ref.WeakReference;

/**
 * Created by colin on 2017/9/26.
 */

public class EditTaskPresenter implements EditTaskContract.Presenter {

    private static final String TAG = "EditTaskPresenter";

    private EditTaskContract.View mView;

    public EditTaskPresenter(EditTaskContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void save(String title, String content, int dailyNumber, int type) {
        if (checkTask(title, content) && TaskType.checkLegalType(type)) {
            SaveTask saveTask = new SaveTask(mView.getViewContext());
            saveTask.execute(new TaskEntity(title, content, dailyNumber, type));
        } else {
            Log.e(TAG, "参数不合法 ");
            mView.onError("参数不合法");
        }
    }

    private boolean checkTask(String title, String content) {
        if (!TextUtils.isEmpty(title) || !TextUtils.isEmpty(content))
            return true;
        return false;
    }

    private class SaveTask extends AsyncTask<TaskEntity, Void, Void> {

        private WeakReference<Context> contextWeakReference;

        public SaveTask(Context context) {
            contextWeakReference = new WeakReference<Context>(context);
        }

        @Override
        protected Void doInBackground(TaskEntity... params) {

            TaskDatabaseHelper.saveTask(contextWeakReference.get(),
                    params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mView.onSaveFinish();
        }
    }
}

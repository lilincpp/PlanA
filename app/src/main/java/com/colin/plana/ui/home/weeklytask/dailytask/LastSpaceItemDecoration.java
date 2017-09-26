package com.colin.plana.ui.home.weeklytask.dailytask;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by colin on 2017/9/26.
 */

public class LastSpaceItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int curPosition = parent.getChildAdapterPosition(view);
        if (curPosition == parent.getAdapter().getItemCount() - 1) {
            outRect.set(0, 0, 0, 50);
        }
    }


}

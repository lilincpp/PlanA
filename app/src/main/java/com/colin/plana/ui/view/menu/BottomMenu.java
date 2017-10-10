package com.colin.plana.ui.view.menu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.colin.plana.R;
import com.colin.plana.utils.PixelUtil;

/**
 * Created by colin on 2017/10/10.
 */

public class BottomMenu extends View {

    static final int MENU_STATE_NONE = 0x00;
    static final int MENU_STATE_UP = 0x01;
    static final int MENU_STATE_DOWN = 0x02;

    private Paint mPaint;
    private int mMenuHeight;
    private int mMenuWidth;
    private int mMenuElevation;

    private float mCurrentY;

    private int MENU_DEFALUT_HEIGHT;
    private int MENU_DEFALUT_WIDTH;
    private int MENU_DEFALUT_ELEVATION;

    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;

    private OnMenuStateChangeListener mOnMenuStateChangeListener;

    private enum MODE {
        NONE, DOWN, UP
    }


    private void init(Context context) {

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        SCREEN_WIDTH = metrics.widthPixels;
        SCREEN_HEIGHT = metrics.heightPixels;

        MENU_DEFALUT_HEIGHT = SCREEN_HEIGHT / 2;
        MENU_DEFALUT_WIDTH = SCREEN_WIDTH;

        MENU_DEFALUT_ELEVATION = PixelUtil.dpToPixel(context,
                getResources().getDimension(R.dimen.elevation_4));

        mMenuHeight = MENU_DEFALUT_HEIGHT;
        mMenuWidth = MENU_DEFALUT_WIDTH;
        mMenuElevation = MENU_DEFALUT_ELEVATION;

        setElevation(mMenuElevation);
    }


    public BottomMenu(Context context) {
        super(context);
        init(context);
    }

    public BottomMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BottomMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawRect(
                0, mCurrentY, mMenuWidth, mCurrentY + getHeight(), mPaint
        );
    }

    void show() {
        ValueAnimator animator = ValueAnimator.ofFloat(getHeight(), 0);
        animator.setDuration(640);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float value = (float) animation.getAnimatedValue();
                mCurrentY = value;
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (mOnMenuStateChangeListener != null) {
                    mOnMenuStateChangeListener.onStart(MENU_STATE_UP);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mOnMenuStateChangeListener != null) {
                    mOnMenuStateChangeListener.onEnd(MENU_STATE_UP);
                }
            }
        });
        animator.start();
    }

    void hide() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, getHeight());
        animator.setDuration(640);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float value = (float) animation.getAnimatedValue();
                mCurrentY = value;
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (mOnMenuStateChangeListener != null) {
                    mOnMenuStateChangeListener.onStart(MENU_STATE_DOWN);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mOnMenuStateChangeListener != null) {
                    mOnMenuStateChangeListener.onEnd(MENU_STATE_DOWN);
                }
            }
        });
        animator.start();
    }

    public void setOnMenuStateChangeListener(OnMenuStateChangeListener mOnMenuStateChangeListener) {
        this.mOnMenuStateChangeListener = mOnMenuStateChangeListener;
    }

    interface OnMenuStateChangeListener {
        void onStart(int state);

        void onEnd(int state);
    }

}

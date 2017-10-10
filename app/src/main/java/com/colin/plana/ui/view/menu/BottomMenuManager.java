package com.colin.plana.ui.view.menu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colin.plana.R;
import com.colin.plana.utils.PixelUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by colin on 2017/10/10.
 */

public final class BottomMenuManager {

    private static final String TAG = "BottomMenuManager";

    private ViewGroup mTargetParent = null;
    private View mTargerView = null;
    private View mRootView;
    private RecyclerView mMenuList;
    private Context mContext;

    private MenuListAdapter mMenuListAdapter;

    private boolean mIsShowing = false;
    private int mMenuHeight;
    private int mTargetViewHeight;
    private int mAddIndex = 0; //将菜单添加到哪个位置
    public List<MenuItem> menuItems = new ArrayList<>(); //菜单子项
    private onMenuItemClickListener mOnMenuItemClickListener;

    private BottomMenuManager(ViewGroup parent, View tagerView) {

        if (parent == null) {
            throw new IllegalArgumentException("非法参数");
        }
        mTargerView = tagerView;
        mTargetParent = parent;
        mContext = parent.getContext();

        mRootView = LayoutInflater.from(mContext).inflate(R.layout.menu_list, null, false);
        mMenuList = (RecyclerView) mRootView.findViewById(R.id.menu_content);

        mMenuListAdapter = new MenuListAdapter();
        mMenuList.setAdapter(mMenuListAdapter);
        mMenuList.setLayoutManager(new LinearLayoutManager(mContext));
        mRootView.setElevation(mContext.getResources().getDimension(R.dimen.elevation_4));
        measure();
    }

    private void measure() {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mRootView.measure(w, h);
        mMenuHeight = mRootView.getMeasuredHeight();

        mTargerView.measure(w, h);
        mTargetViewHeight = mTargerView.getMeasuredHeight();
    }


    public static BottomMenuManager make(ViewGroup parent, View view) {
//        final ViewGroup parent = findSuitableParent(view);
//        if (parent == null) {
//            throw new IllegalArgumentException("No suitable parent found from the given view. "
//                    + "Please provide a valid view.");
//        }
        return new BottomMenuManager(parent, view);
    }

    public void show() {
        if (mIsShowing) return;
        if (mRootView.getParent() != null) {
            mTargetParent.removeView(mRootView);
        }

        ObjectAnimator animator = ObjectAnimator.ofFloat(mRootView, "translationY", mMenuHeight, 0);
        animator.setDuration(320);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mIsShowing = true;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                params.gravity = Gravity.BOTTOM;
                mTargetParent.addView(mRootView, mAddIndex, params);
            }
        });
        animator.start();
    }

    public void hide() {
        if (!mIsShowing) return;
        mIsShowing = false;
        ObjectAnimator animator = ObjectAnimator.ofFloat(mRootView, "translationY", 0, mMenuHeight);
        animator.setDuration(320);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mIsShowing = false;
                if (mRootView.getParent() != null) {
                    mTargetParent.removeView(mRootView);
                }
            }
        });
        animator.start();
    }

    public boolean isShowing() {
        return mIsShowing;
    }

    public void createMenuItem(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
        measure();

    }

    public void setAddIndex(int index) {
        mAddIndex = index;
    }

    public void setOnMenuItemClickListener(onMenuItemClickListener mOnMenuItemClickListener) {
        this.mOnMenuItemClickListener = mOnMenuItemClickListener;
    }

    private static ViewGroup findSuitableParent(View view) {
        ViewGroup fallBack = null;

        do {
            if (view instanceof CoordinatorLayout) {
                return (ViewGroup) view;
            } else if (view instanceof FrameLayout) {
                if (view.getId() == android.R.id.content) {
                    return (ViewGroup) view;
                } else {
                    fallBack = (ViewGroup) view;
                }
            }

            if (view != null) {
                ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }

        } while (view != null);
        return fallBack;
    }


    public static class MenuItem {
        public String name;
        public int srcId;

        public MenuItem(String name, int srcId) {
            this.name = name;
            this.srcId = srcId;
        }
    }

    public interface onMenuItemClickListener {
        void onClick(MenuItem item);
    }

    class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.ViewHolder> {

        public MenuListAdapter() {

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.mIvSrc.setImageResource(menuItems.get(position).srcId);
            holder.mTvName.setText(menuItems.get(position).name);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnMenuItemClickListener != null) {
                        mOnMenuItemClickListener.onClick(menuItems.get(position));
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return menuItems.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView mIvSrc;
            TextView mTvName;
            public ViewHolder(View itemView) {
                super(itemView);
                mIvSrc = (ImageView) itemView.findViewById(R.id.iv_item_im);
                mTvName = (TextView) itemView.findViewById(R.id.tv_item_name);
            }
        }

    }

}

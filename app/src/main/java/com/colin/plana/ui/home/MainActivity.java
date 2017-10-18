package com.colin.plana.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.colin.plana.R;
import com.colin.plana.database.TaskDatabaseHelper;
import com.colin.plana.ui.home.weeklytask.WeeklyTaskFragment;
import com.colin.plana.ui.home.weeklytask.WeeklyTaskPresenter;
import com.colin.plana.ui.home.weeklytask.dailytask.TaskListFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeContract.View {

    private static final String TAG = "MainActivity";
    private HomeContract.Presenter mPresenter;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;

    private TaskListFragment.onMenuChangedListener onMenuChangedListener;

    public static final int TYPE_MENU_NORMAL = 0x01;
    public static final int TYPE_MENU_LONG_CLICK = 0x02;

    private int mMenuType = TYPE_MENU_NORMAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setSupportActionBar(mToolbar);
        initDrawerMenu();
        new HomePresenter(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else if (mMenuType == TYPE_MENU_LONG_CLICK) {
            changeMenuType(TYPE_MENU_NORMAL);
        } else {
            super.onBackPressed();
        }

    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (mMenuType == TYPE_MENU_NORMAL) {
            menu.findItem(R.id.action_delete_item).setVisible(false);
        } else {
            menu.findItem(R.id.action_delete_item).setVisible(true);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_all) {
            TaskDatabaseHelper.deleteAllTask(this);
            FragmentManager fragmentManager = getSupportFragmentManager();
            WeeklyTaskFragment weeklyTaskListFragment = new WeeklyTaskFragment();
            fragmentManager.beginTransaction().replace(R.id.container, weeklyTaskListFragment).commit();
            new WeeklyTaskPresenter(weeklyTaskListFragment);
            return true;
        } else if (id == R.id.action_delete_item) {
            if (onMenuChangedListener != null) {
                onMenuChangedListener.onMenuItemClick(id);
            }
            changeMenuType(TYPE_MENU_NORMAL);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        mPresenter.onClick(item.getItemId());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initView() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar);
    }

    private void initDrawerMenu() {
        mToggle =
                new ActionBarDrawerToggle(
                        this,
                        mDrawer,
                        mToolbar,
                        R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (mMenuType == TYPE_MENU_LONG_CLICK) {
                    changeMenuType(TYPE_MENU_NORMAL);
                }
                //由于我们使用自己的DrawerListener，因此为了不影响Drawer与Toolbar的联动（动画方面）
                //所以这里需要手动调用相关的回调。
                mToggle.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                mToggle.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mToggle.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                mToggle.onDrawerStateChanged(newState);
            }
        });
        mToggle.syncState();
    }


    private void changeToolbarNavigationAction() {
        if (mMenuType == TYPE_MENU_NORMAL) {
            initDrawerMenu();
        } else {
            //重新设置Navigation Icon及点击事件
            mToolbar.setNavigationIcon(R.drawable.ic_menu_gray);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeMenuType(TYPE_MENU_NORMAL);
                }
            });
        }

    }

    /**
     * 改变Toolbar的样式及相关UI
     */
    private void changeMenuStyle() {
        if (mMenuType == TYPE_MENU_NORMAL) {
            //正常菜单
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            mToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            mToolbar.setOverflowIcon(getDrawable(R.drawable.ic_more_vert_white_24dp));
        } else {
            //长按菜单
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorGray));
            mToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
            mToolbar.setOverflowIcon(getDrawable(R.drawable.ic_more_vert_gray_24dp));
        }
        //重新绘制Toolbar上的Menu选项
        invalidateOptionsMenu();
    }

    public void changeMenuType(int type) {
        mMenuType = type;
        changeMenuStyle();
        changeToolbarNavigationAction();
        if (onMenuChangedListener != null) {
            onMenuChangedListener.onChanged(type);
        }
    }

    public void registerMenuChangedListener(TaskListFragment.onMenuChangedListener listener) {
        onMenuChangedListener = listener;
    }


    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void moveToFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    public void setToolBarElevation(float pixel) {
        Log.e(TAG, "setToolBarElevation: " + pixel);
        mAppBarLayout.setElevation(pixel);
    }

    @Override
    public void setToolBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public Context getContextView() {
        return this;
    }
}

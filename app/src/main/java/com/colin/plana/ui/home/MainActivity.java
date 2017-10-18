package com.colin.plana.ui.home;

import android.content.Context;
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

import com.colin.plana.R;
import com.colin.plana.database.TaskDatabaseHelper;
import com.colin.plana.ui.home.weeklytask.WeeklyTaskFragment;
import com.colin.plana.ui.home.weeklytask.WeeklyTaskPresenter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeContract.View {

    private static final String TAG = "MainActivity";
    private HomeContract.Presenter mPresenter;
    private AppBarLayout mAppBarLayout;

    public static final int TYPE_MENU_NORMAL = 0x01;
    public static final int TYPE_MENU_LONG_CLICK = 0x02;

    private int mMenuType = TYPE_MENU_NORMAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        new HomePresenter(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(
                        this,
                        drawer,
                        toolbar,
                        R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar);
    }

    public void changeMenuType(int type) {
        mMenuType = type;
        changeMenuStyle();
    }

    /**
     * 改变Toolbar的样式及相关UI
     */
    private void changeMenuStyle() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mMenuType == TYPE_MENU_NORMAL) {
            //正常菜单
            toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
        } else {
            //长按菜单
            toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.colorWhite));
        }

        invalidateOptionsMenu();
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

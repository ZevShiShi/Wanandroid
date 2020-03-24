package com.zev.wanandroid.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.zev.wanandroid.R;
import com.zev.wanandroid.app.AppLifecyclesImpl;
import com.zev.wanandroid.di.component.DaggerMainComponent;
import com.zev.wanandroid.mvp.contract.MainContract;
import com.zev.wanandroid.mvp.model.entity.TabEntity;
import com.zev.wanandroid.mvp.presenter.MainPresenter;
import com.zev.wanandroid.mvp.ui.adapter.CustomFragmentAdapter;
import com.zev.wanandroid.mvp.ui.base.BaseMvpActivity;
import com.zev.wanandroid.mvp.ui.fragment.HomeFragment;
import com.zev.wanandroid.mvp.ui.fragment.MyFragment;
import com.zev.wanandroid.mvp.ui.fragment.NativeFragment;
import com.zev.wanandroid.mvp.ui.fragment.OfficialAccountFragment;
import com.zev.wanandroid.mvp.ui.fragment.ProjectFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/23/2020 12:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.vp_main)
    ViewPager vpMain;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private CustomFragmentAdapter mAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();

    private int[] selectIcon = {R.drawable.home_select, R.drawable.native_select
            , R.drawable.office_select, R.drawable.project_select, R.drawable.my_select};
    private int[] unSelectIcon = {R.drawable.home_unselect, R.drawable.native_unselect
            , R.drawable.office_unselect, R.drawable.project_unselect, R.drawable.my_unselect};
    private String[] titles = {"首页", "导航", "公众号", "项目", "我的"};

    public void addFragment(Fragment fragment) {
        if (fragment.isAdded()) {
            return;
        }
        fragmentList.add(fragment);
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String cookie = SPUtils.getInstance().getString("cookie");
        AppLifecyclesImpl.isLogin = ObjectUtils.isNotEmpty(cookie);

        setSwipeBackEnable(false);
        mAdapter = new CustomFragmentAdapter(getSupportFragmentManager());
        mAdapter.addFragment(HomeFragment.newInstance());
        mAdapter.addFragment(NativeFragment.newInstance());
        mAdapter.addFragment(OfficialAccountFragment.newInstance());
        mAdapter.addFragment(ProjectFragment.newInstance());
        mAdapter.addFragment(MyFragment.newInstance());

        vpMain.setAdapter(mAdapter);

        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            tabEntities.add(new TabEntity(titles[i], selectIcon[i], unSelectIcon[i]));
        }
        tabLayout.setTabData(tabEntities);

        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpMain.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tabLayout.setCurrentTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}

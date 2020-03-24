package com.zev.wanandroid.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.zev.wanandroid.R;
import com.zev.wanandroid.di.component.DaggerMyCollectComponent;
import com.zev.wanandroid.mvp.contract.MyCollectContract;
import com.zev.wanandroid.mvp.presenter.MyCollectPresenter;
import com.zev.wanandroid.mvp.ui.adapter.CustomFragmentAdapter;
import com.zev.wanandroid.mvp.ui.base.BaseMvpActivity;
import com.zev.wanandroid.mvp.ui.fragment.ChapterCollectFragment;
import com.zev.wanandroid.mvp.ui.fragment.LinkCollectFragment;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/19/2020 13:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MyCollectActivity extends BaseMvpActivity<MyCollectPresenter> implements MyCollectContract.View {


    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.vp_collect)
    ViewPager vpCollect;

    private String[] titles = {"文章", "网址"};
    private CustomFragmentAdapter mAdapter;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyCollectComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_collect; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mAdapter = new CustomFragmentAdapter(getSupportFragmentManager());
        mAdapter.addFragment(ChapterCollectFragment.newInstance());
        mAdapter.addFragment(LinkCollectFragment.newInstance());
        vpCollect.setAdapter(mAdapter);
        tabLayout.setViewPager(vpCollect, titles);
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
}

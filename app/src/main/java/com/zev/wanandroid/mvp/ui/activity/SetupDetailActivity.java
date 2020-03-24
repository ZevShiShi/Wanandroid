package com.zev.wanandroid.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.zev.wanandroid.R;
import com.zev.wanandroid.di.component.DaggerSetupDetailComponent;
import com.zev.wanandroid.mvp.contract.SetupDetailContract;
import com.zev.wanandroid.mvp.model.entity.SecondSetup;
import com.zev.wanandroid.mvp.model.entity.SetupEntity;
import com.zev.wanandroid.mvp.presenter.SetupDetailPresenter;
import com.zev.wanandroid.mvp.ui.adapter.CustomFragmentAdapter;
import com.zev.wanandroid.mvp.ui.base.BaseMvpActivity;
import com.zev.wanandroid.mvp.ui.fragment.SetupChildFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/29/2020 13:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SetupDetailActivity extends BaseMvpActivity<SetupDetailPresenter> implements SetupDetailContract.View {

    private SetupEntity entity;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.setup_tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.vp_setup)
    ViewPager vpSetup;

    private CustomFragmentAdapter mAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSetupDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_setup_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        entity = getIntent().getParcelableExtra("setup_data");
        if (entity == null) {
            finish();
            return;
        }
        tvTitle.setText(entity.getName());

        mAdapter = new CustomFragmentAdapter(getSupportFragmentManager());
        List<SecondSetup> children = entity.getChildren();
        List<String> titles = new ArrayList<>();
        for (SecondSetup entity : children) {
            mAdapter.addFragment(SetupChildFragment.newInstance(entity.id));
            titles.add(entity.name);
        }
        vpSetup.setAdapter(mAdapter);
        String[] names = titles.toArray(new String[titles.size()]);
        tabLayout.setViewPager(vpSetup, names);
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

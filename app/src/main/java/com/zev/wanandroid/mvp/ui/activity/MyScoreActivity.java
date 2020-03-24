package com.zev.wanandroid.mvp.ui.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.zev.wanandroid.R;
import com.zev.wanandroid.di.component.DaggerMyScoreComponent;
import com.zev.wanandroid.mvp.contract.MyScoreContract;
import com.zev.wanandroid.mvp.model.entity.MyScoreBean;
import com.zev.wanandroid.mvp.model.entity.MyScoreEntity;
import com.zev.wanandroid.mvp.presenter.MyScorePresenter;
import com.zev.wanandroid.mvp.ui.adapter.MyScoreAdapter;
import com.zev.wanandroid.mvp.ui.base.BaseMvpActivity;
import com.zev.wanandroid.mvp.ui.fragment.WebDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/12/2020 15:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MyScoreActivity extends BaseMvpActivity<MyScorePresenter> implements MyScoreContract.View {

    @BindView(R.id.tv_my_score)
    TextView tvMyScore;
    @BindView(R.id.rv_score)
    RecyclerView rvScore;

    private int totalScore;
    private int totalPage;
    private int page = 1;
    public static String HELP_URL = "https://www.wanandroid.com/blog/show/2653";

    private MyScoreAdapter mAdapter;
    private List<MyScoreBean> myScoreBeans = new ArrayList<>();


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyScoreComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_score; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.my_score);
        rvScore.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyScoreAdapter(R.layout.my_score_item);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(() -> {
            if (myScoreBeans.size() >= totalPage) {
                mAdapter.loadMoreEnd();
            } else {
                mPresenter.getMyScore(++page);
            }
        }, rvScore);
        mAdapter.disableLoadMoreIfNotFullPage();
        mAdapter.setNewData(myScoreBeans);
        rvScore.setAdapter(mAdapter);
        mPresenter.getMyScore(page);
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
    public void getMyScore(MyScoreEntity entity) {
        totalPage = entity.total;
        for (MyScoreEntity.Score s : entity.datas) {
            totalScore += s.getCoinCount();
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss", Locale.CHINESE);
            String time = f.format(new Date(s.getDate()));
            MyScoreBean bean = new MyScoreBean(s.getDesc(), time, "+" + s.getCoinCount());
            myScoreBeans.add(bean);
        }
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, totalScore);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(animation -> {
            tvMyScore.setText(String.valueOf(((int) animation.getAnimatedValue())));
        });
        valueAnimator.start();

        mAdapter.loadMoreComplete();
    }


    @OnClick(R.id.iv_help)
    public void onClickHelp(View view) {
        WebDialogFragment webDialogFragment = WebDialogFragment.newInstance(HELP_URL);
        webDialogFragment.show(getSupportFragmentManager(), "webFragment");
    }
}

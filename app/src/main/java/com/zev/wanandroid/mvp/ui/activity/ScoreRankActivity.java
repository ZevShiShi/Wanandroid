package com.zev.wanandroid.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.ConvertUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.zev.wanandroid.R;
import com.zev.wanandroid.di.component.DaggerScoreRankComponent;
import com.zev.wanandroid.mvp.contract.ScoreRankContract;
import com.zev.wanandroid.mvp.model.entity.Score;
import com.zev.wanandroid.mvp.model.entity.ScoreBean;
import com.zev.wanandroid.mvp.model.entity.ScoreEntity;
import com.zev.wanandroid.mvp.presenter.ScoreRankPresenter;
import com.zev.wanandroid.mvp.ui.adapter.ScoreRankAdapter;
import com.zev.wanandroid.mvp.ui.base.BaseMvpActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/08/2020 13:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ScoreRankActivity extends BaseMvpActivity<ScoreRankPresenter> implements ScoreRankContract.View {

    @BindView(R.id.rv_score_rank)
    RecyclerView rvScoreRank;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ScoreRankAdapter mAdapter;
    private List<ScoreBean> scoreList = new ArrayList<>();
    private int totalCount;
    private int high;
    private int page = 1;
    private boolean isOnce;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerScoreRankComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_score_rank; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("积分排行榜");
        toolbar.getLayoutParams().height = ConvertUtils.dp2px(40);
        rvScoreRank.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ScoreRankAdapter(R.layout.score_rank_item);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(() -> {
            rvScoreRank.postDelayed(() -> {
                if (scoreList.size() >= totalCount) {
                    mAdapter.loadMoreEnd();
                } else {
                    mPresenter.getScoreRank(++page);
                }
            }, 1000);
        }, rvScoreRank);
        mAdapter.disableLoadMoreIfNotFullPage();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {

        });
        mAdapter.setNewData(scoreList);
        rvScoreRank.setAdapter(mAdapter);
        mPresenter.getScoreRank(1);

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
    public void getScoreRank(ScoreEntity entity) {
        totalCount = entity.total;
        addScore(entity);
        mAdapter.loadMoreComplete();
        if (pbLoading.getVisibility() == View.VISIBLE)
            pbLoading.setVisibility(View.GONE);
    }

    private void addScore(ScoreEntity entity) {
        for (Score s : entity.datas) {
            ScoreBean bean = new ScoreBean(s.getRank(), s.getUserId(), s.getLevel()
                    , s.getCoinCount(), s.getUsername());
            if (!isOnce) {
                isOnce = true;
                high = entity.datas.get(0).getCoinCount();
            }
            bean.setHigh(high);
            switch (s.getRank()) {
                case 1:
                    bean.setShowGold(true);
                    break;
                case 2:
                    bean.setShowSliver(true);
                    break;
                case 3:
                    bean.setShowCopper(true);
                    break;
            }
            scoreList.add(bean);
        }
    }
}

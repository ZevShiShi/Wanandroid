package com.zev.wanandroid.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.zev.wanandroid.R;
import com.zev.wanandroid.di.component.DaggerMyShardComponent;
import com.zev.wanandroid.mvp.contract.MyShardContract;
import com.zev.wanandroid.mvp.model.entity.Chapter;
import com.zev.wanandroid.mvp.model.entity.MyShareEntity;
import com.zev.wanandroid.mvp.presenter.MyShardPresenter;
import com.zev.wanandroid.mvp.ui.adapter.ChapterAdapter;
import com.zev.wanandroid.mvp.ui.adapter.ChapterBean;
import com.zev.wanandroid.mvp.ui.base.BaseMvpActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/24/2020 13:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MyShardActivity extends BaseMvpActivity<MyShardPresenter> implements MyShardContract.View {

    @BindView(R.id.rv_shard)
    RecyclerView rvShard;

    private ChapterAdapter mAdapter;
    private List<ChapterBean> allChapter = new ArrayList<>();
    private int page = 1;
    private int total;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyShardComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_shard; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("我的分享");
        rvShard.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ChapterAdapter(R.layout.project_list_item);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(() -> {
            if (allChapter.size() >= total) {
                mAdapter.loadMoreEnd();
            } else {
                mPresenter.getMyShard(++page);
            }
        }, rvShard);
        mAdapter.disableLoadMoreIfNotFullPage();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ChapterBean bean = (ChapterBean) adapter.getData().get(position);
            ActivityUtils.startActivity(new Intent(MyShardActivity.this, WebActivity.class)
                    .putExtra("url", bean.getLink()));
        });
        mAdapter.setNewData(allChapter);
        rvShard.setAdapter(mAdapter);
        mPresenter.getMyShard(page);
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
    public void getMyShard(MyShareEntity entity) {
        total = entity.getShareArticles().getTotal();
        addChapter(entity.getShareArticles().getDatas());
        mAdapter.loadMoreComplete();
    }


    private void addChapter(List<Chapter> chapters) {
        if (ObjectUtils.isEmpty(chapters)) return;
        for (Chapter c : chapters) {
            ChapterBean bean = new ChapterBean(c.getAuthor(), c.getNiceDate()
                    , c.getSuperChapterName() + "·" + c.getChapterName(), c.getTitle(), c.getDesc());
            if (ObjectUtils.isEmpty(c.getTags())) {
                bean.setShowTag(false);
                bean.setTag("");
            } else {
                bean.setShowTag(true);
                bean.setTag(c.getTags().get(0).name);
            }
            if (ObjectUtils.isNotEmpty(c.getEnvelopePic())) {
                bean.setImgLink(c.getEnvelopePic());
            }
            bean.setCollect(c.isCollect());
            bean.setId(c.getId());
            bean.setLink(c.getLink());
            bean.setShowDesc(ObjectUtils.isNotEmpty(c.getDesc()));
            allChapter.add(bean);
        }
    }
}

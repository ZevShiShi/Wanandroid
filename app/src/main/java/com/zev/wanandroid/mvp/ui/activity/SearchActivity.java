package com.zev.wanandroid.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zev.wanandroid.R;
import com.zev.wanandroid.app.AppLifecyclesImpl;
import com.zev.wanandroid.di.component.DaggerSearchComponent;
import com.zev.wanandroid.mvp.contract.SearchContract;
import com.zev.wanandroid.mvp.model.entity.Chapter;
import com.zev.wanandroid.mvp.model.entity.ChapterEntity;
import com.zev.wanandroid.mvp.model.entity.HotSearchBean;
import com.zev.wanandroid.mvp.model.entity.HotSearchEntity;
import com.zev.wanandroid.mvp.presenter.SearchPresenter;
import com.zev.wanandroid.mvp.ui.adapter.ChapterAdapter;
import com.zev.wanandroid.mvp.ui.adapter.ChapterBean;
import com.zev.wanandroid.mvp.ui.adapter.HotSearchAdapter;
import com.zev.wanandroid.mvp.ui.base.BaseMvpActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/27/2020 13:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SearchActivity extends BaseMvpActivity<SearchPresenter> implements SearchContract.View {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rv_hot_search)
    RecyclerView rvHotSearch;
    @BindView(R.id.rv_search)
    RecyclerView rvSearch;
    @BindView(R.id.ll_search_chapter)
    LinearLayout llSearch;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    private HotSearchAdapter mHotSearchAdapter;
    private ChapterAdapter mChapterAdapter;
    private int page;
    private int total;
    private String mKey;
    private HotSearchBean historyBean;

    private List<HotSearchBean> hotSearchBeans = new ArrayList<>();
    private List<ChapterBean> allChapter = new ArrayList<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSearchComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_search; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        // hot search
        mHotSearchAdapter = new HotSearchAdapter(R.layout.hot_search_item);
        rvHotSearch.setLayoutManager(new LinearLayoutManager(this));
        mHotSearchAdapter.bindToRecyclerView(rvHotSearch);
        mHotSearchAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_clear:
                    AppLifecyclesImpl.getDiskLruCacheUtil().put("history_bean", new HotSearchBean());
                    adapter.getData().remove(position);
                    adapter.notifyDataSetChanged();
                    break;
            }
        });
        mHotSearchAdapter.setSelectCallback((groupPos, pos) -> {
            mKey = hotSearchBeans.get(groupPos).names.get(pos);
            etSearch.setText(mKey);
//            mPresenter.getSearch(page = 0, mKey);
        });
        List<HotSearchEntity> entities = AppLifecyclesImpl.getDiskLruCacheUtil().getObjectCache("hot_search");
        if (ObjectUtils.isEmpty(entities)) {
            mPresenter.getHotSearch();
        } else {
            showHotSearch(entities);
        }

        // search
        rvSearch.setLayoutManager(new LinearLayoutManager(this));
        mChapterAdapter = new ChapterAdapter(R.layout.project_list_item);
        mChapterAdapter.setEnableLoadMore(true);
        mChapterAdapter.setOnLoadMoreListener(() -> {
            if (allChapter.size() >= total) {
                mChapterAdapter.loadMoreEnd();
            } else {
                mPresenter.getSearch(++page, mKey);
            }
        }, rvSearch);
        mChapterAdapter.disableLoadMoreIfNotFullPage();
        mChapterAdapter.setOnItemClickListener((adapter, view, position) -> {
            ChapterBean bean = mChapterAdapter.getData().get(position);
            ActivityUtils.startActivity(new Intent(SearchActivity.this, WebExActivity.class)
                    .putExtra("url", bean.getLink())
                    .putExtra("id", bean.getId())
                    .putExtra("collect", bean.isCollect()));
        });

        mChapterAdapter.setLikeListener((like, pos) -> {

        });
        mChapterAdapter.setNewData(allChapter);
        rvSearch.setAdapter(mChapterAdapter);
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            mKey = etSearch.getText().toString();
            mPresenter.getSearch(page = 0, mKey);
        });

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchAction();
            }
            return false;
        });
    }

    private void showHotSearch(List<HotSearchEntity> entities) {
        hotSearchBeans.clear();
        HotSearchBean hotBean = new HotSearchBean("热门搜索", false);
        for (int i = 0; i < entities.size(); i++) {
            HotSearchEntity e = entities.get(i);
            hotBean.names.add(e.getName());
        }
        hotSearchBeans.add(hotBean);

        // 查看历史记录下是否有搜索
        historyBean = AppLifecyclesImpl.getDiskLruCacheUtil().getObjectCache("history_bean");
        if (ObjectUtils.isNotEmpty(historyBean) && ObjectUtils.isNotEmpty(historyBean.names)) {
            hotSearchBeans.add(historyBean);
        } else {
            historyBean = new HotSearchBean("历史搜索", true);
        }
        mHotSearchAdapter.setNewData(hotSearchBeans);
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
    public void getHotSearch(List<HotSearchEntity> entities) {
        showHotSearch(entities);
    }

    @Override
    public void getSearch(ChapterEntity entity) {
        addSearchChapter(entity);
        refreshLayout.finishRefresh();

    }

    @Override
    public void searchNoData() {
        mChapterAdapter.setEmptyView(R.layout.empty_layout, rvSearch);
    }

    private void addSearchChapter(ChapterEntity entity) {
        if (entity.getCurPage() == 1) {
            allChapter.clear();
            mChapterAdapter.notifyDataSetChanged();
        }
        total = entity.getTotal();
        addChapter(entity.getDatas());
        mChapterAdapter.loadMoreComplete();
    }

    @Override
    public void searchError() {
        mChapterAdapter.loadMoreFail();
        refreshLayout.finishRefresh(false);
        mChapterAdapter.setEmptyView(R.layout.empty_layout, rvSearch);
    }

    public void showSearchList(boolean showSearch) {
        llSearch.setVisibility(showSearch ? View.VISIBLE : View.GONE);
        rvHotSearch.setVisibility(showSearch ? View.GONE : View.VISIBLE);
    }

    @OnClick(R.id.tv_search)
    public void onSearchClick(View view) {
        searchAction();
    }

    private void searchAction() {
        mKey = etSearch.getText().toString();
        if (ObjectUtils.isEmpty(mKey)) return;
        if (!historyBean.names.contains(mKey)) {
            historyBean.names.add(mKey);
            AppLifecyclesImpl.getDiskLruCacheUtil().put("history_bean", historyBean);
            if (!hotSearchBeans.contains(historyBean)) {
                hotSearchBeans.add(historyBean);
            }
            mHotSearchAdapter.notifyDataSetChanged();
        }
        showSearchList(true);
        ChapterEntity entity = AppLifecyclesImpl.getDiskLruCacheUtil().getObjectCache("search_chapter_" + mKey);
        if (ObjectUtils.isEmpty(entity)) {
            mPresenter.getSearch(page = 0, mKey);
        } else {
            addSearchChapter(entity);
        }
    }


    @OnClick(R.id.search_back)
    public void searchBack(View view) {
        back();
    }

    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        if (rvHotSearch.getVisibility() == View.VISIBLE) {
            finish();
        } else {
            showSearchList(false);
        }
    }


    private void addChapter(List<Chapter> chapters) {
        if (ObjectUtils.isEmpty(chapters)) return;
        for (Chapter c : chapters) {
            // String author, String time, String type, String title, String desc, String tag
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
            bean.setShowNew(c.isFresh());
            bean.setShowTop(false);
            bean.setShowDesc(ObjectUtils.isNotEmpty(c.getDesc()));
            allChapter.add(bean);
        }
    }
}

package com.zev.wanandroid.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zev.wanandroid.R;
import com.zev.wanandroid.app.AppLifecyclesImpl;
import com.zev.wanandroid.app.EventBusTags;
import com.zev.wanandroid.app.common.EventBusData;
import com.zev.wanandroid.app.utils.GlideImageLoader;
import com.zev.wanandroid.di.component.DaggerHomeComponent;
import com.zev.wanandroid.mvp.contract.HomeContract;
import com.zev.wanandroid.mvp.model.entity.BannerEntity;
import com.zev.wanandroid.mvp.model.entity.Chapter;
import com.zev.wanandroid.mvp.model.entity.ChapterEntity;
import com.zev.wanandroid.mvp.model.entity.base.BaseEntity;
import com.zev.wanandroid.mvp.presenter.HomePresenter;
import com.zev.wanandroid.mvp.ui.activity.SearchActivity;
import com.zev.wanandroid.mvp.ui.activity.WebActivity;
import com.zev.wanandroid.mvp.ui.activity.WebExActivity;
import com.zev.wanandroid.mvp.ui.adapter.ChapterAdapter;
import com.zev.wanandroid.mvp.ui.adapter.ChapterBean;
import com.zev.wanandroid.mvp.ui.base.BaseMvpLazyFragment;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/25/2020 13:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HomeFragment extends BaseMvpLazyFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.rv_chapter)
    RecyclerView rvChapter;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    Banner banner;
    private boolean isScroll;
    private int totalCount;
    private int page;
    private int topCount;
    private ArrayList<ChapterBean> allChapter = new ArrayList<>();
    private ChapterAdapter mAdapter;
    private HomeWebFragment homeWebFragment;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }


    private void setupBanner(List<BannerEntity> entities) {
        if (ObjectUtils.isEmpty(entities)) return;
        banner = new Banner(getActivity());
        banner.setLayoutParams(new LinearLayout.LayoutParams(-1, ConvertUtils.dp2px(250)));
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        List<String> images = new ArrayList<>();
        for (int i = 0; i < entities.size(); i++) {
            images.add(entities.get(i).getImagePath());
        }
        //设置图片集合
        banner.setImages(images);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);

        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                isScroll = true;
                LogUtils.d(TAG, "onPageSelected position====%s", i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        banner.setOnBannerListener(position -> {
            if (!isScroll) {
                position = 0;
            }
            LogUtils.d(TAG, "position====%s", position);
            String url = entities.get(position).getUrl();
            int id = entities.get(position).getId();
            ActivityUtils.startActivity(new Intent(getActivity(), WebActivity.class)
                    .putExtra("url", url)
                    .putExtra("id", id));
        });
        mAdapter.setHeaderView(banner);
        mAdapter.setHeader(true);
    }

    @OnClick(R.id.rl_search)
    public void onSearchClick(View view) {
        ActivityUtils.startActivity(SearchActivity.class);
    }

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {

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

    }

    @Override
    public void onStop() {
        super.onStop();
        if (banner != null) {
            banner.stopAutoPlay();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (banner != null) {
            banner.startAutoPlay();
        }
    }

    @Override
    protected void lazyLoadData() {
        rvChapter.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ChapterAdapter(R.layout.project_list_item);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(() -> {
            rvChapter.postDelayed(() -> {
                if (allChapter.size() >= totalCount) {
                    mAdapter.loadMoreEnd();
                } else {
                    mPresenter.getChapterList(++page);
                }
            }, 1000);
        }, rvChapter);
        mAdapter.disableLoadMoreIfNotFullPage();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ChapterBean bean = mAdapter.getData().get(position);
            ActivityUtils.startActivity(new Intent(getActivity(), WebExActivity.class)
                    .putExtra("url", bean.getLink())
                    .putExtra("id", bean.getId())
                    .putExtra("collect", bean.isCollect()));

        });
        mAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            showWebPop(position);
            return false;
        });
        mAdapter.setLikeListener((like, pos) -> {
            ChapterBean bean = mAdapter.getData().get(pos);
            if (like) {
                mPresenter.addCollect(bean.getId());
            } else {
                mPresenter.unCollect(bean.getId());
            }
            bean.setCollect(like);
            mAdapter.refreshNotifyItemChanged(pos);
        });
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.getBanner();
            mPresenter.getChapterTop();
        });
        mAdapter.setNewData(allChapter);
        rvChapter.setAdapter(mAdapter);

        List<BannerEntity> bannerEntityList = AppLifecyclesImpl.getDiskLruCacheUtil().getObjectCache("banner");
        setupBanner(bannerEntityList);
        List<Chapter> chapterTop = AppLifecyclesImpl.getDiskLruCacheUtil().getObjectCache("chapterTop");
        addChapterTop(chapterTop);
        ChapterEntity entity = AppLifecyclesImpl.getDiskLruCacheUtil().getObjectCache("chapterList");
        addChapterList(entity);
        mPresenter.getBanner();
        mPresenter.getChapterTop();
    }

    private void showWebPop(int position) {
        homeWebFragment = HomeWebFragment.newInstance(allChapter, position);
        homeWebFragment.show(getChildFragmentManager(), "HomeWeb");
    }


    @Override
    public void showBanner(List<BannerEntity> entities) {
        setupBanner(entities);
    }

    @Override
    public void bannerFail(String msg) {
    }

    @Override
    public void getChapterList(ChapterEntity entity) {
        addChapterList(entity);
    }

    private void addChapterList(ChapterEntity entity) {
        if (ObjectUtils.isEmpty(entity) || ObjectUtils.isEmpty(entity.getDatas())) return;
        totalCount += entity.getTotal();
        addChapter(entity.getDatas(), false);
        mAdapter.loadMoreComplete();
        refreshLayout.finishRefresh();
    }

    @Override
    public void getChapterError(String msg) {
        mAdapter.loadMoreFail();
        refreshLayout.finishRefresh(false);
        mAdapter.setEmptyView(R.layout.empty_layout, rvChapter);
    }

    @Override
    public void getChapterTop(List<Chapter> chapters) {
        totalCount = chapters.size();
        addChapterTop(chapters);
        mPresenter.getChapterList(page = 0);
    }

    private void addChapterTop(List<Chapter> chapters) {
        if (ObjectUtils.isEmpty(chapters)) return;
        allChapter.clear();
        addChapter(chapters, true);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void addCollectChapter(BaseEntity entity) {
    }

    @Override
    public void unCollectChapter(BaseEntity entity) {
    }

    @Override
    public void collectError(String msg) {
    }

    private void addChapter(List<Chapter> chapters, boolean isTop) {
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
            bean.setShowTop(isTop);
            bean.setShowDesc(ObjectUtils.isNotEmpty(c.getDesc()));
            allChapter.add(bean);
        }
    }


    @Subscriber(tag = EventBusTags.UPDATE_COLLECT)
    public void onChangeZan(EventBusData data) {
        if (mAdapter != null) {
            for (int i = 0; i < mAdapter.getData().size(); i++) {
                ChapterBean bean = mAdapter.getData().get(i);
                if (data.id == bean.getId()) {
                    bean.setCollect(data.like);
                    break;
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }

}

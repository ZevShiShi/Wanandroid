package com.zev.wanandroid.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zev.wanandroid.R;
import com.zev.wanandroid.app.EventBusTags;
import com.zev.wanandroid.app.common.EventBusData;
import com.zev.wanandroid.di.component.DaggerChapterCollectComponent;
import com.zev.wanandroid.mvp.contract.ChapterCollectContract;
import com.zev.wanandroid.mvp.model.entity.Chapter;
import com.zev.wanandroid.mvp.model.entity.ChapterEntity;
import com.zev.wanandroid.mvp.model.entity.base.BaseEntity;
import com.zev.wanandroid.mvp.presenter.ChapterCollectPresenter;
import com.zev.wanandroid.mvp.ui.activity.WebActivity;
import com.zev.wanandroid.mvp.ui.adapter.ChapterAdapter;
import com.zev.wanandroid.mvp.ui.adapter.ChapterBean;
import com.zev.wanandroid.mvp.ui.base.BaseMvpLazyFragment;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/19/2020 13:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ChapterCollectFragment extends BaseMvpLazyFragment<ChapterCollectPresenter> implements ChapterCollectContract.View {


    @BindView(R.id.rv_collect)
    RecyclerView rvCollect;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    private ChapterAdapter mAdapter;
    private List<ChapterBean> allChapter = new ArrayList<>();
    private int total;
    private int page;
    private int removePos;
    private boolean isRefresh;


    public static ChapterCollectFragment newInstance() {
        ChapterCollectFragment fragment = new ChapterCollectFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerChapterCollectComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chapter_collect, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

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
    public void getMyCollect(ChapterEntity entity) {
        total = entity.getTotal();
        addChapter(entity.getDatas());
        mAdapter.loadMoreComplete();
        if (isRefresh) {
            refreshLayout.finishRefresh();//传入false表示刷新失败
            isRefresh = false;
        }
    }

    @Override
    public void getMyCollectError(String error) {
        refreshLayout.finishRefresh(false);
    }

    @Override
    public void unCollectByMy(BaseEntity entity) {
        EventBus.getDefault().post(new EventBusData(false
                , allChapter.get(removePos).getOriginId()), EventBusTags.UPDATE_COLLECT);
        allChapter.remove(removePos);
        if (allChapter.size() == 0) {
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.notifyItemRemoved(removePos);
        }
    }


    @Override
    protected void lazyLoadData() {
        rvCollect.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ChapterAdapter(R.layout.project_list_item);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(() -> {
            if (allChapter.size() >= total) {
                mAdapter.loadMoreEnd();
            } else {
                mPresenter.getMyCollect(++page);
            }
        }, rvCollect);
        mAdapter.disableLoadMoreIfNotFullPage();
        mAdapter.setNewData(allChapter);
        rvCollect.setAdapter(mAdapter);

        refreshLayout.setOnRefreshListener(refreshLayout -> {
            isRefresh = true;
            allChapter.clear();
            mPresenter.getMyCollect(page = 0);
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ChapterBean bean = mAdapter.getData().get(position);
            ActivityUtils.startActivity(new Intent(getActivity(), WebActivity.class)
                    .putExtra("url", bean.getLink())
                    .putExtra("id", bean.getId())
                    .putExtra("collect", true));
        });
        mAdapter.setLikeListener((like, pos) -> {
            removePos = pos;
            ChapterBean bean = mAdapter.getData().get(pos);// 去掉header
//            Timber.d("setLikeListener===" + bean.getTitle() + "===" + bean.isCollect());
            if (!like) {
                mPresenter.unCollect(bean.getId());
            }
        });
        mPresenter.getMyCollect(page);
    }


    private void addChapter(List<Chapter> chapters) {
        if (ObjectUtils.isEmpty(chapters)) return;
        for (Chapter c : chapters) {
            // String author, String time, String type, String title, String desc, String tag
            ChapterBean bean = new ChapterBean(c.getAuthor(), c.getNiceDate()
                    , c.getChapterName(), c.getTitle(), c.getDesc());
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
            bean.setOriginId(c.getOriginId());
            bean.setCollect(true);
            bean.setId(c.getId());
            bean.setLink(c.getLink());
            bean.setShowNew(false);
            bean.setShowTop(false);
            bean.setShowDesc(ObjectUtils.isNotEmpty(c.getDesc()));
            allChapter.add(bean);
        }
    }


}

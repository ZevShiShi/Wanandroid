package com.zev.wanandroid.mvp.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daimajia.swipe.SwipeLayout;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zev.wanandroid.R;
import com.zev.wanandroid.app.AppLifecyclesImpl;
import com.zev.wanandroid.app.utils.ClipboardUtils;
import com.zev.wanandroid.di.component.DaggerLinkCollectComponent;
import com.zev.wanandroid.mvp.contract.LinkCollectContract;
import com.zev.wanandroid.mvp.model.entity.LinkEntity;
import com.zev.wanandroid.mvp.presenter.LinkCollectPresenter;
import com.zev.wanandroid.mvp.ui.activity.WebActivity;
import com.zev.wanandroid.mvp.ui.base.BaseMvpLazyFragment;
import com.zev.wanandroid.mvp.ui.view.PopupWindowWithMask;

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
public class LinkCollectFragment extends BaseMvpLazyFragment<LinkCollectPresenter> implements LinkCollectContract.View {


    @BindView(R.id.rv_link)
    RecyclerView rvLink;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    private MyLinkAdapter mAdapter;
    private int delPos;


    public static LinkCollectFragment newInstance() {
        LinkCollectFragment fragment = new LinkCollectFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerLinkCollectComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_link_collect, container, false);
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
    public void getLink(List<LinkEntity> entities) {
        mAdapter.setNewData(entities);
        refreshLayout.finishRefresh();
    }

    @Override
    public void getLinkError(String msg) {
        refreshLayout.finishRefresh(false);
    }

    @Override
    public void deleteMyLink() {
        mAdapter.getData().remove(delPos);
        if (mAdapter.getData().size() == 0) {
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.notifyItemRemoved(delPos);
        }
    }

    @Override
    public void updateMyLink() {
        mPresenter.getMyLink();
    }

    @Override
    protected void lazyLoadData() {
        mAdapter = new MyLinkAdapter(R.layout.my_link_item);
        rvLink.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter.bindToRecyclerView(rvLink);
        refreshLayout.setOnRefreshListener(refreshLayout -> mPresenter.getMyLink());

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            LinkEntity entity = (LinkEntity) adapter.getData().get(position);
            switch (view.getId()) {
                case R.id.ll_link_item:
                    ActivityUtils.startActivity(new Intent(getActivity(), WebActivity.class)
                            .putExtra("url", entity.getLink())
                            .putExtra("id", entity.getId())
                            .putExtra("collect", true));
                    break;
                case R.id.tv_link_copy:
                    ClipboardUtils.copyText(entity.getLink());
                    ToastUtils.showShort("复制成功");
                    break;
                case R.id.tv_link_open:
                    Uri uri = Uri.parse(entity.getLink());
                    ActivityUtils.startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    break;
                case R.id.tv_link_edit:
                    openEditUrl(entity);
                    break;
                case R.id.tv_link_del:
                    delPos = position;
                    mPresenter.deleteMyLink(entity.getId());
                    break;
            }

            ((SwipeLayout) adapter.getViewByPosition(position, R.id.swipe_layout)).close();
        });

        List<LinkEntity> entities = AppLifecyclesImpl.getDiskLruCacheUtil().getObjectCache("collect_link");
        if (ObjectUtils.isEmpty(entities)) {
            mPresenter.getMyLink();
        } else {
            mAdapter.setNewData(entities);
        }
    }

    private void openEditUrl(LinkEntity entity) {
        View contentView = getLayoutInflater().inflate(R.layout.pop_url_edit, null);

        PopupWindowWithMask p = new PopupWindowWithMask(getActivity()) {
            @Override
            protected View setContentView() {
                return contentView;
            }

            @Override
            protected int setWidth() {
                return ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(40);
            }

            @Override
            protected int setHeight() {
                return ViewGroup.LayoutParams.WRAP_CONTENT;
            }
        };
        if (!p.isShowing()) {
            p.showAtLocation(getView(), Gravity.BOTTOM, 0, ConvertUtils.dp2px(20));
        }

        EditText etTitle = contentView.findViewById(R.id.et_link_title);
        etTitle.setText(entity.getName());
        EditText etUrl = contentView.findViewById(R.id.et_link_url);
        etUrl.setText(entity.getLink());

        String name = etTitle.getText().toString();
        String url = etUrl.getText().toString();

        contentView.findViewById(R.id.tv_no).setOnClickListener(v -> p.dismiss());
        contentView.findViewById(R.id.tv_yes).setOnClickListener(v -> {
            String newName = etTitle.getText().toString();
            String newUrl = etUrl.getText().toString();
            if (name.equals(newName) && url.equals(newUrl)) {
                p.dismiss();
            } else {
                mPresenter.updateMyLink(entity.getId(), newName, newUrl);
                p.dismiss();
            }
        });


    }


    private class MyLinkAdapter extends BaseQuickAdapter<LinkEntity, BaseViewHolder> {

        public MyLinkAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, LinkEntity item) {
            helper.setText(R.id.tv_name, item.getName());
            helper.setText(R.id.tv_link, item.getLink());
            helper.addOnClickListener(R.id.ll_link_item);
            helper.addOnClickListener(R.id.tv_link_copy);
            helper.addOnClickListener(R.id.tv_link_open);
            helper.addOnClickListener(R.id.tv_link_edit);
            helper.addOnClickListener(R.id.tv_link_del);
        }
    }
}


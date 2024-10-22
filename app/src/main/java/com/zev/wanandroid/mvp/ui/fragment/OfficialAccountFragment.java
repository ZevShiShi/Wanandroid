package com.zev.wanandroid.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ObjectUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.zev.wanandroid.R;
import com.zev.wanandroid.app.AppLifecyclesImpl;
import com.zev.wanandroid.di.component.DaggerOfficialAccountComponent;
import com.zev.wanandroid.mvp.contract.OfficialAccountContract;
import com.zev.wanandroid.mvp.model.entity.SetupEntity;
import com.zev.wanandroid.mvp.presenter.OfficialAccountPresenter;
import com.zev.wanandroid.mvp.ui.adapter.CustomFragmentAdapter;
import com.zev.wanandroid.mvp.ui.base.BaseMvpLazyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/25/2020 13:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class OfficialAccountFragment extends BaseMvpLazyFragment<OfficialAccountPresenter> implements OfficialAccountContract.View {


    @BindView(R.id.wx_tab_layout)
    SlidingTabLayout wxTabLayout;
    @BindView(R.id.vp_wx)
    ViewPager vpWx;

    CustomFragmentAdapter mAdapter;
    private List<String> titleList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();

    public static OfficialAccountFragment newInstance() {
        OfficialAccountFragment fragment = new OfficialAccountFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerOfficialAccountComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_official_account, container, false);
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
    protected void lazyLoadData() {
        List<SetupEntity> entities = AppLifecyclesImpl.getDiskLruCacheUtil().getObjectCache("wx_tab");
        addWxTab(entities);
        mPresenter.getWxTab();
    }

    @Override
    public void getWxTab(List<SetupEntity> entities) {
        addWxTab(entities);
    }

    private void addWxTab(List<SetupEntity> entities) {
        if (ObjectUtils.isEmpty(entities)) return;
        titleList.clear();
        fragmentList.clear();
        for (SetupEntity e : entities) {
            titleList.add(e.getName());
            fragmentList.add(WxChildFragment.newInstance(e.getId()));
        }
        String[] titles = titleList.toArray(new String[titleList.size()]);
        if (mAdapter == null) {
            mAdapter = new CustomFragmentAdapter(getChildFragmentManager());
            mAdapter.updateFragment(fragmentList);
            vpWx.setAdapter(mAdapter);
            wxTabLayout.setViewPager(vpWx, titles);
        } else {
            wxTabLayout.notifyDataSetChanged();
            mAdapter.updateFragment(fragmentList);
        }
    }
}

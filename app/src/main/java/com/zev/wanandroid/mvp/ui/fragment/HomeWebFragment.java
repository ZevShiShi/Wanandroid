package com.zev.wanandroid.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.ldoublem.thumbUplib.ThumbUpView;
import com.zev.wanandroid.R;
import com.zev.wanandroid.app.EventBusTags;
import com.zev.wanandroid.app.common.EventBusData;
import com.zev.wanandroid.di.component.DaggerHomeWebComponent;
import com.zev.wanandroid.mvp.contract.HomeWebContract;
import com.zev.wanandroid.mvp.model.entity.base.BaseEntity;
import com.zev.wanandroid.mvp.presenter.HomeWebPresenter;
import com.zev.wanandroid.mvp.ui.adapter.ChapterBean;
import com.zev.wanandroid.mvp.ui.adapter.CustomFragmentAdapter;
import com.zev.wanandroid.mvp.ui.base.BaseMvpDialogFragment;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 一个DialogFragment中包含ViewPager选项卡的界面
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/28/2020 17:03
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HomeWebFragment extends BaseMvpDialogFragment<HomeWebPresenter> implements HomeWebContract.View {

    @BindView(R.id.vp_home_web)
    ViewPager vpWeb;
    @BindView(R.id.tpv_zan)
    ThumbUpView zanView;

    private CustomFragmentAdapter mAdapter;
    private int mCurPos;
    private int mId;
    private ArrayList<ChapterBean> beans;
    private List<Fragment> fragmentList = new ArrayList<>();


    public static HomeWebFragment newInstance(ArrayList<ChapterBean> beans, int position) {
        HomeWebFragment f = new HomeWebFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        bundle.putParcelableArrayList("chapter", beans);
        f.setArguments(bundle);
        return f;
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeWebComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().getAttributes().windowAnimations = R.style.dialogAnim;
        getDialog().getWindow().getAttributes().gravity = Gravity.BOTTOM;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 半透明背景
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                return true;
            }
            return false;
        });
        super.onResume();
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_web, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        beans = getArguments().getParcelableArrayList("chapter");
        int pos = getArguments().getInt("pos", 0);
        if (ObjectUtils.isEmpty(beans)) {
            dismiss();
            return;
        }
//        vpWeb.getLayoutParams().width = ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(60);
        vpWeb.getLayoutParams().height = ScreenUtils.getScreenHeight() / 4 * 3;
        vpWeb.setPageMargin(30);
        mAdapter = new CustomFragmentAdapter(getChildFragmentManager());
        for (ChapterBean c : beans) {
            fragmentList.add(HomeWebPagerFragment.newInstance(c));
        }
        mAdapter.updateFragment(fragmentList);
        vpWeb.setAdapter(mAdapter);
        vpWeb.setCurrentItem(pos, true);
        mCurPos = pos;
        mId = beans.get(mCurPos).getId();
        boolean collect = beans.get(mCurPos).isCollect();
        if (collect) {
            zanView.setLike();
        } else {
            zanView.setUnlike();
        }

        vpWeb.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mCurPos = i;
                boolean collect = beans.get(i).isCollect();
                if (collect) {
                    zanView.setLike();
                } else {
                    zanView.setUnlike();
                }

                //加载到倒数第3个数据时，加载更多数据联动DialogFragemnt
                if (getParentFragment() != null && getParentFragment() instanceof HomeFragment) {
                    ((HomeFragment) getParentFragment()).scrollToPostion(i);
                    if (i == beans.size() - 6) {
                        ((HomeFragment) getParentFragment()).loadMore();
                        ToastUtils.showShort("更新更多数据==" + beans.size());
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        zanView.setOnThumbUp(like -> {
            mId = beans.get(mCurPos).getId();
            if (like) {
                mPresenter.addCollect(mId);
            } else {
                mPresenter.unCollect(mId);
            }
        });

        // 双击webview监听
//        gestureScanner = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
//            @Override
//            public boolean onDoubleTap(MotionEvent e) {
//                likeLayout.addLoveView(e.getRawX(), e.getRawY());
//                if (!collect) {
//                    mId = beans.get(mCurPos).getId();
//                    mPresenter.addCollect(mId);
//                    zanView.setLike();
//                }
//                return true;
//            }
//        });
//        flWeb.setOnTouchListener((v, event) -> gestureScanner.onTouchEvent(event));
    }


    public boolean isShow() {
        return getDialog() != null && getDialog().isShowing();
    }

    /**
     * 更新数据
     *
     * @param chapterBeans
     */
    public void notifyData(ArrayList<ChapterBean> chapterBeans) {
        beans.clear();
        fragmentList.clear();
        beans.addAll(chapterBeans);
        for (ChapterBean c : beans) {
            fragmentList.add(HomeWebPagerFragment.newInstance(c));
        }
        mAdapter.updateFragment(fragmentList);
        ToastUtils.showShort("更新更多数据==" + beans.size());
        if (vpWeb != null)
            vpWeb.setCurrentItem(mCurPos);
    }

    @Subscriber(tag = EventBusTags.REFRESH_WEB_COLLECT)
    public void eventCollect(boolean collect) {
        if (zanView != null) {
            zanView.setLike();
        }
    }

    @OnClick(R.id.iv_close)
    public void onCloseCLick(View view) {
        dismiss();
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
    public void addCollectChapter(BaseEntity entity) {
        EventBus.getDefault().post(new EventBusData(true, mId), EventBusTags.UPDATE_COLLECT);
    }

    @Override
    public void unCollectChapter(BaseEntity entity) {
        EventBus.getDefault().post(new EventBusData(false, mId), EventBusTags.UPDATE_COLLECT);
    }

    @Override
    public void collectError(String msg) {

    }

    @OnClick(R.id.iv_back)
    public void onBack(View view) {
        ((HomeWebPagerFragment) mAdapter.getData().get(mCurPos)).back();
    }


    @Override
    public void dismiss() {
        if (mAdapter != null) {
            mAdapter.clearAll();
        }
        super.dismiss();
    }
}

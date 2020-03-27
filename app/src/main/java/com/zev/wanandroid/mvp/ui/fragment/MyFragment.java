package com.zev.wanandroid.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zev.wanandroid.R;
import com.zev.wanandroid.app.AppLifecyclesImpl;
import com.zev.wanandroid.app.common.CustomData;
import com.zev.wanandroid.di.component.DaggerMyComponent;
import com.zev.wanandroid.mvp.contract.MyContract;
import com.zev.wanandroid.mvp.model.entity.UserinfoEntity;
import com.zev.wanandroid.mvp.presenter.MyPresenter;
import com.zev.wanandroid.mvp.ui.activity.LoginActivity;
import com.zev.wanandroid.mvp.ui.activity.MyCollectActivity;
import com.zev.wanandroid.mvp.ui.activity.MyScoreActivity;
import com.zev.wanandroid.mvp.ui.activity.MyShardActivity;
import com.zev.wanandroid.mvp.ui.activity.ScoreRankActivity;
import com.zev.wanandroid.mvp.ui.base.BaseMvpLazyFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/25/2020 13:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MyFragment extends BaseMvpLazyFragment<MyPresenter> implements MyContract.View {


    @BindView(R.id.ll_header)
    LinearLayout llHeader;
    @BindView(R.id.iv_profile)
    CircleImageView civProfile;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.tv_level)
    TextView tvLevel;

    @BindView(R.id.rv_my)
    RecyclerView rvMy;

    private MyAdapter mAdapter;
    private List<MyBean> myBeans = new ArrayList<>();


    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMyComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, container, false);
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
        UserinfoEntity entity = AppLifecyclesImpl.getDiskLruCacheUtil().getObjectCache("userinfo");
        if (ObjectUtils.isEmpty(entity)) {
            mPresenter.getUserInfo();
        } else {
            showUser(entity);
        }
        Collections.addAll(myBeans, new MyBean(R.drawable.myscore, "我的积分")
                , new MyBean(R.drawable.share, "我的分享")
                , new MyBean(R.drawable.collect, "我的收藏")
                , new MyBean(R.drawable.github, "开源项目")
                , new MyBean(R.drawable.aboutme, "关于")
                , new MyBean(R.drawable.settings, "设置"));
        mAdapter = new MyAdapter(R.layout.my_item_layout, myBeans);
        rvMy.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter.bindToRecyclerView(rvMy);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            switch (position) {
                case 0:
                    ActivityUtils.startActivity(MyScoreActivity.class);
                    break;
                case 1:
                    ActivityUtils.startActivity(MyShardActivity.class);
                    break;
                case 2:
                    ActivityUtils.startActivity(MyCollectActivity.class);
                    break;
                default:
                    break;
            }
        });

    }

    @OnClick(R.id.ll_profile)
    public void onSelectHeader(View view) {
        if (AppLifecyclesImpl.isLogin) {
            showAlbum();
        } else {
            startActivityForResult(new Intent(getActivity(), LoginActivity.class), CustomData.USER_REQUEST);
        }
    }


    private void showAlbum() {
        //参数很多，根据需要添加
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选PictureConfig.MULTIPLE : PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(true)// 是否裁剪
                .compress(true)// 是否压缩
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                //.selectionMedia(selectList)// 是否传入已选图片
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
//                .compressMaxKB()//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效
//                .compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效
//                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                .rotateEnabled(false) // 裁剪是否可旋转图片
                //.scaleEnabled()// 裁剪是否可放大缩小图片
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST && data != null) {// 图片选择结果回调

                List<LocalMedia> images = PictureSelector.obtainMultipleResult(data);
                LocalMedia img = images.get(0);
                if (img.isCompressed()) {
                    String path = img.getCompressPath();
                    if (new File(path).exists()) {
                        Bitmap bm = BitmapFactory.decodeFile(path);
                        civProfile.setImageBitmap(bm);
                        bm = ImageUtils.fastBlur(bm, 0.5f, 25f);
                        llHeader.setBackground(new BitmapDrawable(getResources(), bm));
                        SPUtils.getInstance().put("profile_image_path", path);
                    }
                }

            }
        } else if (resultCode == CustomData.USER_RESULT) {
            ToastUtils.showShort("hahaha");
            mPresenter.getUserInfo();
        }
    }

    @Override
    public void getUserInfo(UserinfoEntity entity) {
        showUser(entity);
    }

    private void showUser(UserinfoEntity entity) {
        tvId.setText(String.valueOf(entity.getUserId()));
        tvLevel.setText("等级：" + entity.getLevel() + "排名：" + entity.getRank());
        tvUsername.setText(entity.getUsername());
        String path = SPUtils.getInstance().getString("profile_image_path");
        if (ObjectUtils.isNotEmpty(path)) {
            Bitmap bm = BitmapFactory.decodeFile(path);
            civProfile.setImageBitmap(bm);
            bm = ImageUtils.fastBlur(bm, 0.5f, 25f);
            llHeader.setBackground(new BitmapDrawable(getResources(), bm));
        }
    }

    @Override
    public void getUserInfoError(String msg) {
//        ToastUtils.showShort(msg);
    }


    @OnClick(R.id.iv_score)
    public void onScoreClick(View view) {
        ActivityUtils.startActivity(ScoreRankActivity.class);
    }


    private class MyAdapter extends BaseQuickAdapter<MyBean, BaseViewHolder> {

        public MyAdapter(int layoutResId, @Nullable List<MyBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MyBean item) {
            helper.setImageResource(R.id.iv_my_icon, item.resId);
            helper.setText(R.id.tv_my_name, item.name);
        }
    }


    private class MyBean {
        public int resId;
        public String name;

        public MyBean(int resId, String name) {
            this.resId = resId;
            this.name = name;
        }
    }
}

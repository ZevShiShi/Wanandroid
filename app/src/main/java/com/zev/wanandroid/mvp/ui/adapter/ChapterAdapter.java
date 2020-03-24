package com.zev.wanandroid.mvp.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ldoublem.thumbUplib.ThumbUpView;
import com.zev.wanandroid.R;

import timber.log.Timber;

public class ChapterAdapter extends BaseQuickAdapter<ChapterBean, BaseViewHolder> {

    private boolean header;
    private OnLikeListener likeListener;

    public ChapterAdapter(int layoutResId) {
        super(layoutResId);
    }

    public void setLikeListener(OnLikeListener likeListener) {
        this.likeListener = likeListener;
    }


    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    @Override
    protected void convert(BaseViewHolder helper, ChapterBean item) {
        Timber.d("ChapterBean===" + item.getAuthor() + item.isShowNew());
        helper.setGone(R.id.tv_new, item.isShowNew());
        helper.setGone(R.id.tv_tags, item.isShowTag());
        helper.setGone(R.id.tv_chapter_top, item.isShowTop());
        helper.setGone(R.id.tv_chapter_desc, item.isShowDesc());

        if (item.isShowTag())
            helper.setText(R.id.tv_tags, item.getTag());
        helper.setText(R.id.tv_author, item.getAuthor());

        TextView tvDesc = helper.getView(R.id.tv_chapter_desc);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            helper.setText(R.id.tv_chapter_title, Html.fromHtml(item.getTitle(), Html.FROM_HTML_MODE_LEGACY));
            tvDesc.setText(Html.fromHtml(item.getDesc(), Html.FROM_HTML_MODE_LEGACY, new MImageGetter(tvDesc, mContext), null));
        } else {
            helper.setText(R.id.tv_chapter_title, Html.fromHtml(item.getTitle()));
            tvDesc.setText(Html.fromHtml(item.getDesc(), new MImageGetter(tvDesc, mContext), null));
        }
        helper.setText(R.id.tv_chapter_type, item.getType());
        helper.setText(R.id.tv_time, item.getTime());

        ImageView ivPro = helper.getView(R.id.iv_pro);
        if (ObjectUtils.isEmpty(item.getImgLink())) {
            ivPro.setVisibility(View.GONE);
        } else {
            ivPro.setVisibility(View.VISIBLE);
            Glide.with(mContext).asBitmap()
                    .load(item.getImgLink())
                    .thumbnail(0.8f)
                    .into(ivPro);
        }


        ThumbUpView zanView = helper.getView(R.id.tpv_zan);
        if (item.isCollect()) {
            zanView.setLike();
        } else {
            zanView.setUnlike();
        }
        zanView.setOnThumbUp(like -> {
            if (likeListener != null) {
                int position = isHeader() ? helper.getLayoutPosition() - 1 : helper.getLayoutPosition();
                likeListener.onLikeOrUnlike(like, position);
            }
        });

    }

    public class MImageGetter implements Html.ImageGetter {

        private Context c;
        private TextView container;

        public MImageGetter(TextView text, Context c) {
            this.c = c;
            this.container = text;
        }


        public Drawable getDrawable(String source) {
            final LevelListDrawable drawable = new LevelListDrawable();
            Glide.with(c).asBitmap().load(source)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            if (resource != null) {
                                BitmapDrawable bitmapDrawable = new BitmapDrawable(mContext.getResources(), resource);
                                drawable.addLevel(1, 1, bitmapDrawable);
                                drawable.setBounds(0, 0, resource.getWidth()
                                        , resource.getHeight());
                                drawable.setLevel(1);
                                container.invalidate();
                                container.setText(container.getText());
                            }
                        }
                    });
            return drawable;
        }
    }


    public interface OnLikeListener {

        void onLikeOrUnlike(boolean like, int pos);
    }
}

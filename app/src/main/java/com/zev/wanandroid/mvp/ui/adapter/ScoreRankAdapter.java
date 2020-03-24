package com.zev.wanandroid.mvp.ui.adapter;

import android.animation.ValueAnimator;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zev.wanandroid.R;
import com.zev.wanandroid.mvp.model.entity.ScoreBean;

public class ScoreRankAdapter extends BaseQuickAdapter<ScoreBean, BaseViewHolder> {


    public ScoreRankAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, ScoreBean item) {
        if (item.isShowGold()) {
            helper.setGone(R.id.iv_rank, true);
            helper.setGone(R.id.tv_rank, false);

            helper.setImageResource(R.id.iv_rank, R.drawable.gold);
        } else if (item.isShowSliver()) {
            helper.setGone(R.id.iv_rank, true);
            helper.setGone(R.id.tv_rank, false);
            helper.setImageResource(R.id.iv_rank, R.drawable.silver);

        } else if (item.isShowCopper()) {
            helper.setGone(R.id.iv_rank, true);
            helper.setGone(R.id.tv_rank, false);
            helper.setImageResource(R.id.iv_rank, R.drawable.copper);
        } else {
            helper.setGone(R.id.iv_rank, false);
            helper.setGone(R.id.tv_rank, true);
            helper.setText(R.id.tv_rank, item.getRank() + "");
        }


        helper.setText(R.id.tv_score_user, item.getUsername());
        helper.setText(R.id.tv_score, item.getCoinCount() + "");

//        ProgressBar pbScore = helper.getView(R.id.pb_score);
//        if (pbScore.getProgress() == 0) {
//
//        }

        int progress = (int) (item.getCoinCount() * 1f / item.getHigh() * 100);
//        Timber.d("progress=====" + progress);
        ValueAnimator animator = ValueAnimator.ofInt(0, progress);
        animator.setDuration(1000);
        animator.addUpdateListener(animation ->
                helper.setProgress(R.id.pb_score, ((int) animation.getAnimatedValue())));
        animator.start();
    }
}

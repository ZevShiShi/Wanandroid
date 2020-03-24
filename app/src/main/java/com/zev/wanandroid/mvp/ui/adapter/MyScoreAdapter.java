package com.zev.wanandroid.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zev.wanandroid.R;
import com.zev.wanandroid.mvp.model.entity.MyScoreBean;

public class MyScoreAdapter extends BaseQuickAdapter<MyScoreBean, BaseViewHolder> {

    public MyScoreAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyScoreBean item) {
        helper.setText(R.id.tv_add_score, item.scoreAdd);
        String descScore = item.desc.substring(item.desc.indexOf("："));
        helper.setText(R.id.tv_desc, "签到积分" + descScore);
        helper.setText(R.id.tv_time, item.time);
    }
}

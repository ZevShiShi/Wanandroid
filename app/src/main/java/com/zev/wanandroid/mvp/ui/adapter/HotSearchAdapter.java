package com.zev.wanandroid.mvp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zev.wanandroid.R;
import com.zev.wanandroid.mvp.model.entity.HotSearchBean;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class HotSearchAdapter extends BaseQuickAdapter<HotSearchBean, BaseViewHolder> {

    private SelectCallback selectCallback;


    public HotSearchAdapter(int layoutResId) {
        super(layoutResId);
    }

    public void setSelectCallback(SelectCallback selectCallback) {
        this.selectCallback = selectCallback;
    }


    @Override
    protected void convert(BaseViewHolder helper, HotSearchBean item) {
        helper.setText(R.id.tv_first_name, item.title);
        helper.setGone(R.id.tv_clear, item.showClear);
        helper.addOnClickListener(R.id.tv_clear);

        TagFlowLayout flowLayout = helper.getView(R.id.flow_layout);

        flowLayout.setAdapter(new TagAdapter<String>(item.names) {
            @Override
            public View getView(FlowLayout parent, int position, String data) {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.flow_text_view,
                        parent, false);
                tv.setText(data);
                return tv;
            }
        });

        flowLayout.setOnSelectListener(selectPosSet -> {
            if (!selectPosSet.isEmpty()) {
                Iterator<Integer> iterator = selectPosSet.iterator();
                if (iterator.hasNext()) {
                    int pos = iterator.next();
                    if (selectCallback != null)
                        selectCallback.OnSelected(helper.getLayoutPosition(), pos);
                }
            }
        });
    }


    public interface SelectCallback {
        void OnSelected(int groupPos, int pos);
    }
}

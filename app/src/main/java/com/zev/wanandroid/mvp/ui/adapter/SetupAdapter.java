package com.zev.wanandroid.mvp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zev.wanandroid.R;
import com.zev.wanandroid.mvp.model.entity.SecondSetup;
import com.zev.wanandroid.mvp.model.entity.SetupEntity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Iterator;

public class SetupAdapter extends BaseQuickAdapter<SetupEntity, BaseViewHolder> {

    private SelectCallback selectCallback;


    public SetupAdapter(int layoutResId) {
        super(layoutResId);
    }

    public void setSelectCallback(SelectCallback selectCallback) {
        this.selectCallback = selectCallback;
    }


    @Override
    protected void convert(BaseViewHolder helper, SetupEntity item) {
        helper.setText(R.id.tv_first_name, item.getName());
        TagFlowLayout flowLayout = helper.getView(R.id.flow_layout);
        flowLayout.setAdapter(new TagAdapter<SecondSetup>(item.getChildren()) {
            @Override
            public View getView(FlowLayout parent, int position, SecondSetup data) {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.flow_text_view,
                        parent, false);
                tv.setText(data.name);
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

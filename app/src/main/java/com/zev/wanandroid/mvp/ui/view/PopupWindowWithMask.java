package com.zev.wanandroid.mvp.ui.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.zev.wanandroid.mvp.ui.base.BasePopupWindowWithMask;

/**
 * 带蒙层的popupWindows
 */
public abstract class PopupWindowWithMask extends BasePopupWindowWithMask {

    public PopupWindowWithMask(Context context) {
        super(context);
    }

    public PopupWindowWithMask(Activity activity) {
        super(activity);
    }

    protected abstract View setContentView();

    protected abstract int setWidth();

    protected abstract int setHeight();


    @Override
    protected View initContentView() {
        View rootView = setContentView();
//        setupAllFontSize(rootView);
        return rootView;
    }

    @Override
    protected int initHeight() {
        return setHeight();
    }

    @Override
    protected int initWidth() {
        return setWidth();
    }


//    protected void setupAllFontSize(View view) {
//        if (view instanceof ViewGroup) {
//            ViewGroup vg = ((ViewGroup) view);
//            for (int i = 0; i < vg.getChildCount(); i++) {
//                View v = vg.getChildAt(i);
//                setupAllFontSize(v);
//            }
//        } else if (view instanceof TextView) {
//            float textSize = ((TextView) view).getTextSize();
//            ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX
//                    , textSize + App.getInstance().getFontScale() * ConvertUtils.sp2px(2));
//        }
//    }
}

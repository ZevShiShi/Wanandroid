package com.zev.wanandroid.mvp.ui.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.IBinder;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.zev.wanandroid.R;

public abstract class BasePopupWindowWithMask extends PopupWindow {
    protected Context context;
    protected boolean addMask = true;
    private static final String TAG = "BasePopupWindowWithMask";
    private View maskView;
    private WindowManager windowManager;

    public BasePopupWindowWithMask(Context context) {
        super(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.context = context;
        setContentView(initContentView());
        setHeight(initHeight());
        setWidth(initWidth());
        setOutsideTouchable(true);
        setFocusable(true);
        setTouchable(true);
        // 不挡住软键盘
//        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
//        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setAnimationStyle(R.style.dialogAnim);
        setBackgroundDrawable(new ColorDrawable());
    }

    protected abstract View initContentView();

    protected abstract int initHeight();

    protected abstract int initWidth();

    @Override
    public void showAsDropDown(View anchor) {
        if (!isShowing()) {
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing()) {
                    if (addMask) {
                        addMask(anchor.getWindowToken());
                    }
                    super.showAsDropDown(anchor, 0, 0, Gravity.CENTER);
                }
            } else {
                if (addMask) {
                    addMask(anchor.getWindowToken());
                }
                super.showAsDropDown(anchor, 0, 0, Gravity.CENTER);
            }
        }
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        if (!isShowing()) {
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing()) {
                    if (addMask) {
                        addMask(parent.getWindowToken());
                    }
                    super.showAtLocation(parent, gravity, x, y);
                }
            } else {
                if (addMask) {
                    addMask(parent.getWindowToken());
                }
                super.showAtLocation(parent, gravity, x, y);
            }
        }
    }


//    /**
//     * 添加蒙层
//     *
//     * @param f
//     */
//    private void backgroundAlpha(float f) {
//        if (context instanceof Activity) {
//            Activity ac = (Activity) context;
//            WindowManager.LayoutParams lp = ac.getWindow().getAttributes();
//            lp.alpha = f;
//            ac.getWindow().setAttributes(lp);
//        }
//    }


    private void addMask(IBinder token) {
        WindowManager.LayoutParams wl = new WindowManager.LayoutParams();
        wl.width = WindowManager.LayoutParams.MATCH_PARENT;
        wl.height = WindowManager.LayoutParams.MATCH_PARENT;
        wl.format = PixelFormat.TRANSLUCENT;//不设置这个弹出框的透明遮罩显示为黑色
        wl.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;//该Type描述的是形成的窗口的层级关系
        wl.token = token;//获取当前Activity中的View中的token,来依附Activity
        maskView = new View(context);
        maskView.setBackgroundResource(R.color.pop_bg);
        maskView.setFitsSystemWindows(false);
        maskView.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                removeMask();
                return true;
            }
            return false;
        });
        /**
         * 通过WindowManager的addView方法创建View，产生出来的View根据WindowManager.LayoutParams属性不同，效果也就不同了。
         * 比如创建系统顶级窗口，实现悬浮窗口效果！
         */
        windowManager.addView(maskView, wl);
    }


    /**
     * 移除蒙层
     */
    private void removeMask() {
        if (addMask) {
//            LogUtils.d(TAG, "removeMask");
//            backgroundAlpha(1.0f);
            if (null != maskView) {
                windowManager.removeViewImmediate(maskView);
                maskView = null;
            }
        }
    }


    /**
     * 是否添加蒙层
     *
     * @param addMask
     */
    public void setAddMask(boolean addMask) {
        this.addMask = addMask;
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed()) {
                    removeMask();
                    super.dismiss();
                }
            } else {
                removeMask();
                super.dismiss();
            }
        }

    }
}

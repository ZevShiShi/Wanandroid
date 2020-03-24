package com.zev.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.zev.wanandroid.di.module.WxChildModule;
import com.zev.wanandroid.mvp.contract.WxChildContract;

import com.jess.arms.di.scope.FragmentScope;
import com.zev.wanandroid.mvp.ui.fragment.WxChildFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/02/2020 13:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = WxChildModule.class, dependencies = AppComponent.class)
public interface WxChildComponent {
    void inject(WxChildFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WxChildComponent.Builder view(WxChildContract.View view);

        WxChildComponent.Builder appComponent(AppComponent appComponent);

        WxChildComponent build();
    }
}
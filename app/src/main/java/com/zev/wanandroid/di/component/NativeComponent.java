package com.zev.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.zev.wanandroid.di.module.NativeModule;
import com.zev.wanandroid.mvp.contract.NativeContract;

import com.jess.arms.di.scope.FragmentScope;
import com.zev.wanandroid.mvp.ui.fragment.NativeFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/25/2020 13:27
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = NativeModule.class, dependencies = AppComponent.class)
public interface NativeComponent {
    void inject(NativeFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        NativeComponent.Builder view(NativeContract.View view);

        NativeComponent.Builder appComponent(AppComponent appComponent);

        NativeComponent build();
    }
}
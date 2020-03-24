package com.zev.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.zev.wanandroid.di.module.SetupChildModule;
import com.zev.wanandroid.mvp.contract.SetupChildContract;

import com.jess.arms.di.scope.FragmentScope;
import com.zev.wanandroid.mvp.ui.fragment.SetupChildFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/29/2020 13:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = SetupChildModule.class, dependencies = AppComponent.class)
public interface SetupChildComponent {
    void inject(SetupChildFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SetupChildComponent.Builder view(SetupChildContract.View view);

        SetupChildComponent.Builder appComponent(AppComponent appComponent);

        SetupChildComponent build();
    }
}
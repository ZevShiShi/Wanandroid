package com.zev.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.zev.wanandroid.di.module.SetupModule;
import com.zev.wanandroid.mvp.contract.SetupContract;

import com.jess.arms.di.scope.FragmentScope;
import com.zev.wanandroid.mvp.ui.fragment.SetupFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/27/2020 13:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = SetupModule.class, dependencies = AppComponent.class)
public interface SetupComponent {
    void inject(SetupFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SetupComponent.Builder view(SetupContract.View view);

        SetupComponent.Builder appComponent(AppComponent appComponent);

        SetupComponent build();
    }
}
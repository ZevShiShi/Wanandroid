package com.zev.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.zev.wanandroid.di.module.SetupDetailModule;
import com.zev.wanandroid.mvp.contract.SetupDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.zev.wanandroid.mvp.ui.activity.SetupDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/29/2020 13:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SetupDetailModule.class, dependencies = AppComponent.class)
public interface SetupDetailComponent {
    void inject(SetupDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SetupDetailComponent.Builder view(SetupDetailContract.View view);

        SetupDetailComponent.Builder appComponent(AppComponent appComponent);

        SetupDetailComponent build();
    }
}
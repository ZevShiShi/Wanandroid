package com.zev.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.zev.wanandroid.di.module.SettingsModule;
import com.zev.wanandroid.mvp.contract.SettingsContract;

import com.jess.arms.di.scope.ActivityScope;
import com.zev.wanandroid.mvp.ui.activity.SettingsActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/30/2020 14:46
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SettingsModule.class, dependencies = AppComponent.class)
public interface SettingsComponent {
    void inject(SettingsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SettingsComponent.Builder view(SettingsContract.View view);

        SettingsComponent.Builder appComponent(AppComponent appComponent);

        SettingsComponent build();
    }
}
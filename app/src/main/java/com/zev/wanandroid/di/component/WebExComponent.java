package com.zev.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.zev.wanandroid.di.module.WebExModule;
import com.zev.wanandroid.mvp.contract.WebExContract;

import com.jess.arms.di.scope.ActivityScope;
import com.zev.wanandroid.mvp.ui.activity.WebExActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/24/2020 14:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = WebExModule.class, dependencies = AppComponent.class)
public interface WebExComponent {
    void inject(WebExActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WebExComponent.Builder view(WebExContract.View view);

        WebExComponent.Builder appComponent(AppComponent appComponent);

        WebExComponent build();
    }
}
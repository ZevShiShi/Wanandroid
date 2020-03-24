package com.zev.wanandroid.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.zev.wanandroid.di.module.WebModule;
import com.zev.wanandroid.mvp.contract.WebContract;
import com.zev.wanandroid.mvp.ui.activity.WebActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/18/2020 14:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = WebModule.class, dependencies = AppComponent.class)
public interface WebComponent {
    void inject(WebActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WebComponent.Builder view(WebContract.View view);

        WebComponent.Builder appComponent(AppComponent appComponent);

        WebComponent build();
    }
}
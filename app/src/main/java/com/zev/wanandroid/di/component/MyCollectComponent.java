package com.zev.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.zev.wanandroid.di.module.MyCollectModule;
import com.zev.wanandroid.mvp.contract.MyCollectContract;

import com.jess.arms.di.scope.ActivityScope;
import com.zev.wanandroid.mvp.ui.activity.MyCollectActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/19/2020 13:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MyCollectModule.class, dependencies = AppComponent.class)
public interface MyCollectComponent {
    void inject(MyCollectActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyCollectComponent.Builder view(MyCollectContract.View view);

        MyCollectComponent.Builder appComponent(AppComponent appComponent);

        MyCollectComponent build();
    }
}
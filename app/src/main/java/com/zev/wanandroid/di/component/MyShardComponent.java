package com.zev.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.zev.wanandroid.di.module.MyShardModule;
import com.zev.wanandroid.mvp.contract.MyShardContract;

import com.jess.arms.di.scope.ActivityScope;
import com.zev.wanandroid.mvp.ui.activity.MyShardActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/24/2020 13:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MyShardModule.class, dependencies = AppComponent.class)
public interface MyShardComponent {
    void inject(MyShardActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyShardComponent.Builder view(MyShardContract.View view);

        MyShardComponent.Builder appComponent(AppComponent appComponent);

        MyShardComponent build();
    }
}
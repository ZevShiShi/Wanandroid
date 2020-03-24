package com.zev.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.zev.wanandroid.di.module.MyScoreModule;
import com.zev.wanandroid.mvp.contract.MyScoreContract;

import com.jess.arms.di.scope.ActivityScope;
import com.zev.wanandroid.mvp.ui.activity.MyScoreActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/12/2020 15:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MyScoreModule.class, dependencies = AppComponent.class)
public interface MyScoreComponent {
    void inject(MyScoreActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyScoreComponent.Builder view(MyScoreContract.View view);

        MyScoreComponent.Builder appComponent(AppComponent appComponent);

        MyScoreComponent build();
    }
}
package com.zev.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.zev.wanandroid.di.module.ScoreRankModule;
import com.zev.wanandroid.mvp.contract.ScoreRankContract;

import com.jess.arms.di.scope.ActivityScope;
import com.zev.wanandroid.mvp.ui.activity.ScoreRankActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/08/2020 13:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ScoreRankModule.class, dependencies = AppComponent.class)
public interface ScoreRankComponent {
    void inject(ScoreRankActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ScoreRankComponent.Builder view(ScoreRankContract.View view);

        ScoreRankComponent.Builder appComponent(AppComponent appComponent);

        ScoreRankComponent build();
    }
}
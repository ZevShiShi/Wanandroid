package com.zev.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.zev.wanandroid.di.module.ChapterCollectModule;
import com.zev.wanandroid.mvp.contract.ChapterCollectContract;

import com.jess.arms.di.scope.FragmentScope;
import com.zev.wanandroid.mvp.ui.fragment.ChapterCollectFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/19/2020 13:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = ChapterCollectModule.class, dependencies = AppComponent.class)
public interface ChapterCollectComponent {
    void inject(ChapterCollectFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ChapterCollectComponent.Builder view(ChapterCollectContract.View view);

        ChapterCollectComponent.Builder appComponent(AppComponent appComponent);

        ChapterCollectComponent build();
    }
}
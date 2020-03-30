package com.zev.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.zev.wanandroid.di.module.HomeWebPagerModule;
import com.zev.wanandroid.mvp.contract.HomeWebPagerContract;

import com.jess.arms.di.scope.FragmentScope;
import com.zev.wanandroid.mvp.ui.fragment.HomeWebPagerFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/30/2020 13:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HomeWebPagerModule.class, dependencies = AppComponent.class)
public interface HomeWebPagerComponent {
    void inject(HomeWebPagerFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HomeWebPagerComponent.Builder view(HomeWebPagerContract.View view);

        HomeWebPagerComponent.Builder appComponent(AppComponent appComponent);

        HomeWebPagerComponent build();
    }
}
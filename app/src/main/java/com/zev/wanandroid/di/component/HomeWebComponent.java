package com.zev.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.zev.wanandroid.di.module.HomeWebModule;
import com.zev.wanandroid.mvp.contract.HomeWebContract;

import com.jess.arms.di.scope.FragmentScope;
import com.zev.wanandroid.mvp.ui.fragment.HomeWebFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/28/2020 17:03
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HomeWebModule.class, dependencies = AppComponent.class)
public interface HomeWebComponent {
    void inject(HomeWebFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HomeWebComponent.Builder view(HomeWebContract.View view);

        HomeWebComponent.Builder appComponent(AppComponent appComponent);

        HomeWebComponent build();
    }
}
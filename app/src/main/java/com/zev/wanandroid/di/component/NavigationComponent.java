package com.zev.wanandroid.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.zev.wanandroid.di.module.NavigationModule;
import com.zev.wanandroid.mvp.contract.NavigationContract;
import com.zev.wanandroid.mvp.ui.fragment.NavigationFragment;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/27/2020 13:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = NavigationModule.class, dependencies = AppComponent.class)
public interface NavigationComponent {
    void inject(NavigationFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        NavigationComponent.Builder view(NavigationContract.View view);

        NavigationComponent.Builder appComponent(AppComponent appComponent);

        NavigationComponent build();
    }
}
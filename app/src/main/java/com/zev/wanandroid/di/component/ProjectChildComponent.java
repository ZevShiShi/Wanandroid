package com.zev.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.zev.wanandroid.di.module.ProjectChildModule;
import com.zev.wanandroid.mvp.contract.ProjectChildContract;

import com.jess.arms.di.scope.FragmentScope;
import com.zev.wanandroid.mvp.ui.fragment.ProjectChildFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/04/2020 13:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = ProjectChildModule.class, dependencies = AppComponent.class)
public interface ProjectChildComponent {
    void inject(ProjectChildFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ProjectChildComponent.Builder view(ProjectChildContract.View view);

        ProjectChildComponent.Builder appComponent(AppComponent appComponent);

        ProjectChildComponent build();
    }
}
package com.zev.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.zev.wanandroid.di.module.OfficialAccountModule;
import com.zev.wanandroid.mvp.contract.OfficialAccountContract;

import com.jess.arms.di.scope.FragmentScope;
import com.zev.wanandroid.mvp.ui.fragment.OfficialAccountFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/25/2020 13:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = OfficialAccountModule.class, dependencies = AppComponent.class)
public interface OfficialAccountComponent {
    void inject(OfficialAccountFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        OfficialAccountComponent.Builder view(OfficialAccountContract.View view);

        OfficialAccountComponent.Builder appComponent(AppComponent appComponent);

        OfficialAccountComponent build();
    }
}
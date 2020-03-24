package com.zev.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.zev.wanandroid.di.module.LinkCollectModule;
import com.zev.wanandroid.mvp.contract.LinkCollectContract;

import com.jess.arms.di.scope.FragmentScope;
import com.zev.wanandroid.mvp.ui.fragment.LinkCollectFragment;


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
@Component(modules = LinkCollectModule.class, dependencies = AppComponent.class)
public interface LinkCollectComponent {
    void inject(LinkCollectFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        LinkCollectComponent.Builder view(LinkCollectContract.View view);

        LinkCollectComponent.Builder appComponent(AppComponent appComponent);

        LinkCollectComponent build();
    }
}
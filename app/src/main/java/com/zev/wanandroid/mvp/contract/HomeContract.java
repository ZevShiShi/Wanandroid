package com.zev.wanandroid.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.zev.wanandroid.mvp.model.entity.BannerEntity;
import com.zev.wanandroid.mvp.model.entity.Chapter;
import com.zev.wanandroid.mvp.model.entity.ChapterEntity;
import com.zev.wanandroid.mvp.model.entity.base.BaseArrayEntity;
import com.zev.wanandroid.mvp.model.entity.base.BaseEntity;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/25/2020 13:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface HomeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void showBanner(List<BannerEntity> entities);

        void bannerFail(String msg);

        void getChapterList(ChapterEntity entity);

        void getChapterError(String msg);

        void getChapterTop(List<Chapter> chapters);

        void addCollectChapter(BaseEntity entity);

        void unCollectChapter(BaseEntity entity);

        void collectError(String msg);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BaseArrayEntity<BannerEntity>> getBanner();

        Observable<BaseEntity<ChapterEntity>> getChapterList(int page);

        Observable<BaseArrayEntity<Chapter>> getTopChapter();


        Observable<BaseEntity> addCollectChapter(int id);


        Observable<BaseEntity> unCollectByChapter(int id);
    }
}

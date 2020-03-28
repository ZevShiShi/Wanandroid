package com.zev.wanandroid.mvp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 不会重新加载fragment实例的适配器
 * 通过show hide函数来显示对应的fragment
 */
public class CustomFragmentAdapter extends FragmentPagerAdapter {
    private FragmentManager fm;
    private List<Fragment> fragmentList = new ArrayList<>();

    public CustomFragmentAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }


    // 保证页面不会销毁
    @Override
    public Fragment instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container,
                position);
        fm.beginTransaction().show(fragment).commitAllowingStateLoss();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container, position, object);//
        Fragment fragment = (Fragment) object;
        fm.beginTransaction().hide(fragment).commitAllowingStateLoss();
    }

    /**
     * 适合用来不更新fragment
     *
     * @param fragment
     */
    public void addFragment(Fragment fragment) {
        if (fragment.isAdded()) {
            return;
        }
        fragmentList.add(fragment);
    }

    /**
     * 适合用来频繁更新fragment
     *
     * @param fragments
     */
    public void updateFragment(List<Fragment> fragments) {
        if (ObjectUtils.isEmpty(fragments)) return;
        fragmentList.clear();
        fragmentList.addAll(fragments);
        notifyDataSetChanged();
    }

}

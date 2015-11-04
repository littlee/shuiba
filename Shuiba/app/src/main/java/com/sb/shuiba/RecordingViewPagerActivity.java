package com.sb.shuiba;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by Administrator on 2015/9/25.
 */
public class RecordingViewPagerActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    private int count;
    String storyMaterialPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_recording);

        storyMaterialPath = getIntent().getStringExtra(RecordingFragment.EXTRA_STORY_ABSOLUTE_PATH);
        String partsOfStoryId = getIntent().getStringExtra(RecordingFragment.EXTRA_RECORDING_STORY_TITLE);
        int currentItem = Integer.parseInt(partsOfStoryId.substring(0, partsOfStoryId.indexOf(".")));
        count = new File(storyMaterialPath).list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".png");
            }
        }).length;

        mViewPager = (ViewPager)findViewById(R.id.viewpager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mViewPager.setPageTransformer(true, new DepthPageTransformer());

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(currentItem - 1);
        //count = ...;放此处则出错
        /*在初始化ViewPager时，应先给Adapter初始化内容后再将该adapter传给ViewPager，如果不这样处理，在更新
        adapter的内容后，应该调用一下adapter的notifyDataSetChanged方法，否则在ADT22以上使用会报这个错。  */
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @TargetApi(11)
        @Override
        public Fragment getItem(int position) {
            return RecordingFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return count;
        }
    }
}



package com.shuiba.sb.shuiba;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/22.
 */
public class Story {
    private  String mTitle;

    public Story(String title){
        mTitle = title;

    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    @Override
    public String toString() {
        return mTitle;
    }
}

package com.shuiba.sb.shuiba;

import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2015/9/22.
 */
public class ChildrenActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new ChildrenFragment();
    }
}

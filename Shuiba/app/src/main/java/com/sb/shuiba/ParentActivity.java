package com.sb.shuiba;

import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2015/9/22.
 */
public class ParentActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new ParentFragment();
    }
}

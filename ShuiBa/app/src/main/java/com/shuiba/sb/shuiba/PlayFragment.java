package com.shuiba.sb.shuiba;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Administrator on 2015/9/24.
 */
public class PlayFragment extends Fragment{
    private Button mPlayButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_play, container, false);
        mPlayButton = (Button)v.findViewById(R.id.playbutton);

        return v;
    }
}

package com.shuiba.sb.shuiba;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2015/9/25.
 */
public class RecordingFragment extends Fragment {
    public final static String EXTRA_RECORDEDSTORY_TITLE = "recordedstorytitle";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String recordedTitle = getActivity().getIntent().getStringExtra(EXTRA_RECORDEDSTORY_TITLE);
        Log.e(EXTRA_RECORDEDSTORY_TITLE,recordedTitle);
        getActivity().setTitle(recordedTitle);//????????????????没有显示出来
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recording, container, false);
        return v;
    }
}

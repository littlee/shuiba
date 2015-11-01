package com.sb.shuiba;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

/**
 * Created by Administrator on 2015/9/22.
 */
public class MainFragment extends Fragment{
    private Button mParentButton;
    private Button mChildrenButton;

    public static List<Story> list;
    public static String filesPath = null;
    MediaPlayer mPlayer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("角色选取");
        String externalPath = Environment.getExternalStorageDirectory().toString();
        Log.i("MainFragment", externalPath);
        filesPath = externalPath + "/files";
        list = DataProvider.getStories(filesPath);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mParentButton = (Button)view.findViewById(R.id.parentButton);
        mParentButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ParentActivity.class);
                startActivity(i);
            }
        });

        mChildrenButton =(Button)view.findViewById(R.id.childButton);
        mChildrenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ChildrenActivity.class);
                startActivity(i);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPlayer=new MediaPlayer();
        mPlayer=MediaPlayer.create(getActivity(),R.raw.dang);
        mPlayer.start();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (!mPlayer.isPlaying())
                    mPlayer.start();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPlayer != null){
            mPlayer.release();
            mPlayer=null;
        }
    }
}

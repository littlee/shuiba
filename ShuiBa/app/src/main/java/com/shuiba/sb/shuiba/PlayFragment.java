package com.shuiba.sb.shuiba;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.SequenceInputStream;

/**
 * Created by Administrator on 2015/9/24.
 */
public class PlayFragment extends Fragment{
    public static final String EXTRA_PLAY_STORY_TITLE = "extra_play_story_title";
    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;

    private Button mPlayButton = null;
    private MediaPlayer mPlayer = null;
    private SeekBar mSeekBar = null;
    private ImageView image=null;
    boolean mStarting = true;

    String selectedStoryTitle = null;
    String selectedStoryId = null;

    String[] audioFile = null;
    int audioId = 1;
    int length;
    private final int MAX = 3000;

    private void onPlay(boolean start) {
        if (start) {

            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {

        try {
            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(mFileName + "/" + audioId + ".3gp");
            mPlayer.prepare();
            mPlayer.start();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mSeekBar.setProgress(MAX * audioId / 3);
                    Log.i("PlayFragment", mFileName + "/" + audioId + ".3gp");
                    audioId++;
                    if (audioId <= length){
                        image.setImageDrawable(Drawable.createFromPath(mFileName + "/" + audioId + ".png"));
                        PlayFragment.this.startPlaying();
                    } else {
                        mStarting = true;
                        mPlayButton.setBackgroundResource(android.R.drawable.ic_media_play);
                        audioId = 1;
                        image.setImageDrawable(Drawable.createFromPath(mFileName + "/" + audioId + ".png"));
                        mSeekBar.setProgress(0);
                    }
                }
            });
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedStoryTitle = getActivity().getIntent().getStringExtra(PlayFragment.EXTRA_PLAY_STORY_TITLE);
        for (int i = 0; i < MainFragment.list.size(); i++) {
            Story story = MainFragment.list.get(i);
            if (story.getName().equals(selectedStoryTitle)) {
                selectedStoryId = story.getId();
                break;
            }
        }
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/files/" + selectedStoryId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_play, container, false);
        image=(ImageView)v.findViewById(R.id.imageview_play);
        image.setImageDrawable(Drawable.createFromPath(mFileName + "/" + "1.png"));

        audioFile = new File(mFileName).list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".3gp");
            }
        });
        Log.i("PlayFragment", audioFile[0]);


        mPlayButton = (Button)v.findViewById(R.id.playbutton);
//        mPlayButton.setBackgroundResource(android.R.drawable.ic_media_play);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取音频个数
                length = audioFile.length;

                //开始播放
                onPlay(mStarting);
                if (mStarting) {
                    mPlayButton.setBackgroundResource(android.R.drawable.ic_media_pause);
                } else {
                    mPlayButton.setBackgroundResource(android.R.drawable.ic_media_play);
                }
                mStarting = !mStarting;
            }
        });

        mSeekBar = (SeekBar)v.findViewById(R.id.play_seekbar);
        mSeekBar.setMax(MAX);

        return v;
    }
//    public  void merge(){
//        try {
//
//            String storyPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/files/001";
//            Log.i("PlayFragment", "storyPath");
//            File file1 = new File(storyPath + "1.3gp");
//            File file2 = new File(storyPath + "2.3gp");
//            File file3 = new File(storyPath + "merge.3gp");
//            Log.i("PlayFragment", "new File has been executed");
//            FileInputStream fistream1 = new FileInputStream(file1);
//            FileInputStream fistream2 = new FileInputStream(file2);
//            Log.i("PlayFragment", "new FileInputStream has been executed");//不能执行
//            SequenceInputStream sistream = new SequenceInputStream(fistream1, fistream2);
//            FileOutputStream fostream = new FileOutputStream(file3);
//
//            /*String temp;
//
//            while( ( temp = new BufferedReader(new InputStreamReader(sistream)).readLine()) != null)
//            {
//
//                new BufferedWriter(new OutputStreamWriter(fostream)).write(temp);
//            }*/
//
//            int count;
//            byte[] temp = new byte[4096];
//            while((count = sistream.read(temp)) != -1) {
//                fostream.write(temp, 0, count);
//            }
//
//
//            fostream.close();
//            sistream.close();
//            fistream1.close();
//            fistream2.close();
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

}

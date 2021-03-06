package com.sb.shuiba;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2015/9/25.
 */
public class RecordingFragment extends Fragment {
    public final static String EXTRA_RECORDING_STORY_TITLE = "recordingstorytitle";
    public final static String EXTRA_STORY_ABSOLUTE_PATH = "storyabsolutepath";
    public final static String EXTRA_STORY_ID = "storyID";
    public final static String EXTRA_PIC_ID = "picID";

    String recordedTitle = null;
    String storyAbsolutePath_Recording = null;
    MediaRecorder mediaRecorder = null;
    MediaPlayer mediaPlayer = null;
    String mFilename = null;

    String storyID = null;

    Button recordButton = null;
    Button auditionButton = null;
    private boolean mStartRecording = true;
    private boolean mStartAudition = true;

    private static int picId;

    private boolean isCreated = false;

    public static RecordingFragment newInstance(int picId) {
        RecordingFragment.picId = picId + 1;
        Bundle args = new Bundle();
        args.putInt(EXTRA_PIC_ID, RecordingFragment.picId);

        RecordingFragment fragment = new RecordingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated = true;

        recordedTitle = getActivity().getIntent().getStringExtra(EXTRA_RECORDING_STORY_TITLE);
        //获取传递过来的故事素材文件夹路径
        storyAbsolutePath_Recording = getActivity().getIntent().getStringExtra(EXTRA_STORY_ABSOLUTE_PATH);

//        mFilename = storyAbsolutePath_Recording + "/" + recordedTitle.substring(0, recordedTitle.indexOf(".")) + ".3gp";
        picId = getArguments().getInt(EXTRA_PIC_ID);
        mFilename = storyAbsolutePath_Recording + "/" + picId + ".3gp";
        storyID = getActivity().getIntent().getStringExtra(EXTRA_STORY_ID);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_recording, container, false);

        ImageView imageView = (ImageView)v.findViewById(R.id.pic_imageview);
//        String picPath = storyAbsolutePath_Recording + "/" + recordedTitle ;
        String picPath = storyAbsolutePath_Recording + "/" + picId + ".png";
        imageView.setImageDrawable(Drawable.createFromPath(picPath));

        recordButton = (Button)v.findViewById(R.id.retake);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onRecord(mStartRecording);
                if (mStartRecording) {
                    recordButton.setText("停止录音");

                } else {
                    recordButton.setText("录音/重录");
                }
                mStartRecording = !mStartRecording;
            }
        });
        auditionButton = (Button)v.findViewById(R.id.audition);

        auditionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!new File(mFilename).exists()) {
                    auditionButton.setEnabled(false);
                } else {
                    onPlay(mStartAudition);
                    if (mStartAudition) {
                        auditionButton.setText("暂停");
                    } else {
                        auditionButton.setText("试听");
                    }
                    mStartAudition = !mStartAudition;
                }
            }
        });

        return v;
    }

    private void startRecording() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        //电脑上无法查看录音文件

        mediaRecorder.setOutputFile(mFilename);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaRecorder.start();
    }

    private void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        auditionButton.setEnabled(true);
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void startPlaying(boolean start) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setScreenOnWhilePlaying(true);
            mediaPlayer.setDataSource(mFilename);
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    auditionButton.setText("试听");
                    mStartAudition = true;
                }
            });
        }catch (IOException e) {
            Log.e("RecordingFragment", "prepare() failed");
        }

    }

    private void stopPlaying() {
        mediaPlayer.release();
        mediaPlayer = null;
    }

    private void onPlay(boolean start){
        if (start) {
            startPlaying(start);
        } else {
            stopPlaying();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
            recordButton.setText("录音/重录");
            mStartRecording = !mStartRecording;
        }

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            auditionButton.setText("试听");
            mStartAudition = !mStartAudition;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!new File(mFilename).exists()) {
            auditionButton.setEnabled(false);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (!isCreated)
            return;
        if (!isVisibleToUser) {
            if (mediaRecorder != null) {
                mediaRecorder.release();
                mediaRecorder = null;
                mStartRecording = !mStartRecording;
            }
        }
    }
}

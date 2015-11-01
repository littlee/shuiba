package com.sb.shuiba;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Administrator on 2015/9/24.
 */
public class RecordFragment extends ListFragment{
    public static final String EXTRA_CURRENT_POSITON = "currentposition";
    public static final String EXTRA_STORY_TITLE = "storytitle";
    int partsOfStoryNumber;
    int audioOfStoryNumber;
    boolean flag;
    String storyAbsolutePath = null;
    String storyPath = null;
    String selectedStoryTitle = null;
    String selectedStoryId = null;

    String[] audioOfStory;
    String[] partsOfStory;
    File file;
    static String mFileName = null;
    MediaPlayer mediaPlayer;
    int audioId = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        int currentPosition = getActivity().getIntent().getIntExtra(EXTRA_CURRENT_POSITON, 0);//当默认返回-1时，由导航键返回当前活动出现错误
        //获取故事标题
        selectedStoryTitle = getActivity().getIntent().getStringExtra(EXTRA_STORY_TITLE);
        getActivity().setTitle(selectedStoryTitle);


        //获取故事素材文件夹
        storyPath = MainFragment.list.get(currentPosition).getId();

        //故事素材文件夹路径
        storyAbsolutePath = MainFragment.filesPath + "/" + storyPath;

        file = new File(storyAbsolutePath);
        partsOfStory = file.list(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".png");
            }
        });
        partsOfStoryNumber = partsOfStory.length;//故事素材个数
        Arrays.sort(partsOfStory);//进行排序

        audioOfStory = file.list(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".3gp");
            }
        });
        audioOfStoryNumber = audioOfStory.length;//素材录音音频个数
        //判断是否录制完成  即音频文件和素材文件个数是否相等
        if(partsOfStoryNumber == audioOfStoryNumber){
           flag=true;
        }
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,partsOfStory);
        setListAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ListView listView = (ListView)v.findViewById(android.R.id.list);
        registerForContextMenu(listView);

        Resources resources = getActivity().getResources();
        Drawable drawable = resources.getDrawable(R.drawable.storypage);
        v.setBackgroundDrawable(drawable);

        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent i = new Intent(getActivity(),RecordingActivity.class);
        //传递故事标题
        i.putExtra(RecordingFragment.EXTRA_RECORDING_STORY_TITLE,
                getListAdapter().getItem(position).toString());
        i.putExtra(RecordingFragment.EXTRA_STORY_ABSOLUTE_PATH, storyAbsolutePath);
        i.putExtra(RecordingFragment.EXTRA_STORY_ID, storyPath);
        startActivity(i);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.record_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_recordoraudition:
                startActivity(new Intent(getActivity(), RecordingActivity.class));
                return true;

            case R.id.menu_item_retake:
                startActivity(new Intent(getActivity(), RecordingActivity.class));
                return true;
        }
        return super.onContextItemSelected(item);
    }
    public void startPlaying() {

        try {

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(mFileName + "/" + audioId + ".3gp");
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    audioId++;
                    if (audioId <= partsOfStoryNumber) {
                        RecordFragment.this.startPlaying();
                    } else {
                        audioId = 1;
                    }
                }
            });
        } catch (IOException e) {

        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.cao, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.listen:
                if(flag == true){
                    for (int i = 0; i < MainFragment.list.size(); i++) {
                        Story story = MainFragment.list.get(i);
                        if (story.getName().equals(selectedStoryTitle)) {
                            selectedStoryId = story.getId();
                            break;
                        }
                    }
                    mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
                    mFileName += "/files/" + selectedStoryId;
                    RecordFragment.this.startPlaying();
                } else {
                    Toast.makeText(getActivity(), "全文未录制完成！", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        partsOfStoryNumber = file.list(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".png");
            }
        }).length;

        audioOfStoryNumber = file.list(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".3gp");
            }
        }).length;
        if(partsOfStoryNumber == audioOfStoryNumber){
            flag = true;
        } else {
            flag = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }
}

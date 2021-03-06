package com.sb.shuiba;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
    MediaPlayer mediaPlayer =null;
    int audioId = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        int currentPosition = getActivity().getIntent().getIntExtra(EXTRA_CURRENT_POSITON, 0);//当默认返回-1时，由导航键返回当前活动出现错误
        //获取故事标题
        selectedStoryTitle = getActivity().getIntent().getStringExtra(EXTRA_STORY_TITLE);
        getActivity().setTitle(selectedStoryTitle);


        //获取故事素材文件夹名
        if (MainFragment.list == null) {
            String externalPath = Environment.getExternalStorageDirectory().getPath();
            String filesPath = externalPath + "/files";
            MainFragment.list = DataProvider.getStories(filesPath);
        }
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
        if (partsOfStory != null) {
            partsOfStoryNumber = partsOfStory.length;//故事素材个数
        }
        Arrays.sort(partsOfStory);//进行排序

        audioOfStoryNumber = Story.getNumOfMaterialorAudio(storyAbsolutePath, ".3gp");//素材录音音频个数
        //判断是否录制完成  即音频文件和素材文件个数是否相等
        if(partsOfStoryNumber == audioOfStoryNumber){
           flag=true;
        }
        /*ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,partsOfStory);*/
        setListAdapter(new StoryAdapter(partsOfStory));

    }

    private class StoryAdapter extends ArrayAdapter<String> {
        public StoryAdapter(String[] storyTitle) {
            super(getActivity(), 0, storyTitle);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_record_fragment, null);
            }

            String storyTitle = getItem(position);
            TextView titleTextView = (TextView)convertView.findViewById(R.id.story_list_item__record_textView);
            titleTextView.setText("第" + storyTitle.substring(0,storyTitle.indexOf(".")) + "幕");

            return convertView;
        }
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
        Intent i = new Intent(getActivity(),RecordingViewPagerActivity.class);
        //传递故事标题
        i.putExtra(RecordingFragment.EXTRA_RECORDING_STORY_TITLE,
                getListAdapter().getItem(position).toString());
        //故事素材文件夹路径
        i.putExtra(RecordingFragment.EXTRA_STORY_ABSOLUTE_PATH, storyAbsolutePath);
        //故事素材文件夹名
        i.putExtra(RecordingFragment.EXTRA_STORY_ID, storyPath);

        startActivity(i);
    }

    public void startPlaying() {

        try {

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setScreenOnWhilePlaying(true);
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

        partsOfStoryNumber = Story.getNumOfMaterialorAudio(storyAbsolutePath, ".png");

        audioOfStoryNumber = Story.getNumOfMaterialorAudio(storyAbsolutePath, ".3gp");
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

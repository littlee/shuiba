package com.shuiba.sb.shuiba;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2015/9/24.
 */
public class RecordFragment extends ListFragment{
    public static final String EXTRA_CURRENT_POSITON = "currentposition";
    public static final String EXTRA_STORY_TITLE = "storytitle";

    String storyAbsolutePath = null;
    String storyPath = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int currentPosition = getActivity().getIntent().getIntExtra(EXTRA_CURRENT_POSITON, 0);//当默认返回-1时，由导航键返回当前活动出现错误
        //获取故事标题
        String selectedStoryTitle = getActivity().getIntent().getStringExtra(EXTRA_STORY_TITLE);
        getActivity().setTitle(selectedStoryTitle);


        //获取故事素材文件夹
        storyPath = MainFragment.list.get(currentPosition).getId();

        //故事素材文件夹路径
        storyAbsolutePath = MainFragment.filesPath + "/" + storyPath;

        File file = new File(storyAbsolutePath);
        String[] partsOfStory = file.list(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".png");
            }
        });
        Arrays.sort(partsOfStory);

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
        }        return super.onContextItemSelected(item);

    }
}

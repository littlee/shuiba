package com.shuiba.sb.shuiba;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2015/9/22.
 */
public class ChildrenFragment extends ListFragment{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("故事");
//        List<Story> list = new DataProvider().getStories(getActivity().getFilesDir().getPath());

        List<String> titles = new ArrayList<String>();
        Iterator<Story> it = MainFragment.list.iterator();
        while(it.hasNext()) {
            Story story = it.next();
            //如果已经录制好则添加标题
//            if (story.isDone())
            titles.add(story.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, titles);
        setListAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        Resources resources = getActivity().getResources();
        Drawable drawable = resources.getDrawable(R.drawable.listbg);
        v.setBackgroundDrawable(drawable);

        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent i = new Intent(getActivity(), PlayActivity.class);
        i.putExtra(PlayFragment.EXTRA_PLAY_STORY_TITLE,
                ((ArrayAdapter<String>)getListAdapter()).getItem(position));
        startActivity(i);

    }

    /*private class StoryAdapter extends ArrayAdapter<String> {
        public StoryAdapter(List<String> storyTitle) {
            super(getActivity(), 0, storyTitle);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_stroy_children, null);
            }

            TextView titleTextView = (TextView)convertView.findViewById(R.id.story_list_item__children_textView);
            titleTextView.setTextSize(24);

            return convertView;
        }
    }*/
}

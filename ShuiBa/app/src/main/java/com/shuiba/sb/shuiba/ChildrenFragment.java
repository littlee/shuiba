package com.shuiba.sb.shuiba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/9/22.
 */
public class ChildrenFragment extends ListFragment{



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("故事");
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,new Story().getStoryTitle(getActivity()));
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent i = new Intent(getActivity(), PlayActivity.class);
        i.putExtra(RecordingFragment.EXTRA_RECORDEDSTORY_TITLE,
                ((ArrayAdapter<String>)getListAdapter()).getItem(position));
        startActivity(i);
    }

    /*private class StoryAdapter extends ArrayAdapter<String> {
        public StoryAdapter(String[] Story) {

            super(getActivity(), 0, Story);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_story, null);
            }

            TextView titleTextView = (TextView)convertView.findViewById(R.id.story_list_item_textView);
            titleTextView.setText("hhhhhhh");

            return convertView;
        }
    }*/
}

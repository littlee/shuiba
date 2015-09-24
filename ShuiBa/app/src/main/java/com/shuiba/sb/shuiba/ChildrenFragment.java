package com.shuiba.sb.shuiba;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/9/22.
 */
public class ChildrenFragment extends ListFragment{

    String[] mStories = {"喜洋洋和灰太狼", "从前有座山，山上有座庙", "白雪公主"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("故事");
        StoryAdapter adapter = new StoryAdapter(mStories);
        setListAdapter(adapter);
    }

    private class StoryAdapter extends ArrayAdapter<String> {
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
    }
}

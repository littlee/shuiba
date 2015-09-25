package com.shuiba.sb.shuiba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ParentFragment extends ListFragment{
    private TextView mTitleTextView;

    String[] mStories = {"喜洋洋和灰太狼", "从前有座山，山上有座庙", "白雪公主"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//通知FragmentManager:ParentFragment需接收选项菜单方法回调

        getActivity().setTitle("故事录制");
        StoryAdapter adapter = new StoryAdapter(mStories);
        setListAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_parent_list, menu);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent i = new Intent(getActivity(), RecordActivity.class);
        startActivity(i);
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

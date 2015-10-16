package com.shuiba.sb.shuiba;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/24.
 */
public class RecordFragment extends ListFragment{
    public static final String EXTRA_STORY_TITLE = "storytitle";
    private ArrayList<Story> mStories = new ArrayList<Story>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String currentTitle = getActivity().getIntent().getStringExtra(EXTRA_STORY_TITLE);
        getActivity().setTitle(currentTitle);

        ArrayAdapter<Story> adapter =
                new ArrayAdapter<Story>(getActivity(), android.R.layout.simple_list_item_1,mStories);
        setListAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ListView listView = (ListView)v.findViewById(android.R.id.list);
        registerForContextMenu(listView);

        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent i = new Intent(getActivity(), RecordingActivity.class);
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

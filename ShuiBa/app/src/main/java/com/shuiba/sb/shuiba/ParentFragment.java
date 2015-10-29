package com.shuiba.sb.shuiba;


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParentFragment extends ListFragment{
    private TextView mTitleTextView;

    public CheckBox mCheckBox;


    private StoryAdapter adapter = null;
    int numberOfAudio;
    int numberOfMaterial;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//通知FragmentManager:ParentFragment需接收选项菜单方法回调
        getActivity().setTitle("故事录制");

        List<String> titles = new ArrayList<String>();
        Iterator<Story> it = MainFragment.list.iterator();
        while(it.hasNext()) {
            titles.add(it.next().getName());
        }

        adapter = new StoryAdapter(titles);
        setListAdapter(adapter);
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        Resources resources = getActivity().getResources();
        Drawable drawable = resources.getDrawable(R.drawable.listbg);
        v.setBackgroundDrawable(drawable);
        ListView listView = (ListView)v.findViewById(android.R.id.list);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            registerForContextMenu(listView);
        } else {
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

                }

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.storylist_item_context, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_item_delete:
                            //
                            mode.finish();

                            return true;
                        default:
                            return false;
                    }
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
        }

        return v;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.fragment_parent_list,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent i = new Intent(getActivity(), RecordActivity.class);
        i.putExtra(RecordFragment.EXTRA_CURRENT_POSITON, position);
        i.putExtra(RecordFragment.EXTRA_STORY_TITLE, ((ArrayAdapter<String>) getListAdapter()).getItem(position));
        startActivity(i);
    }

    private class StoryAdapter extends ArrayAdapter<String> {
        public StoryAdapter(List<String> storyTitle) {

            super(getActivity(), 0, storyTitle);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_story, null);
            }

            String storyTitle = getItem(position);
            TextView titleTextView = (TextView)convertView.findViewById(R.id.story_list_item_textView);
            titleTextView.setText(storyTitle);

            mCheckBox = (CheckBox)convertView.findViewById(R.id.story_list_item_checkbox);
            String storyMaterialPath = MainFragment.filesPath + "/" + MainFragment.list.get(position).getId();
            File file = new File(storyMaterialPath);
            numberOfAudio = file.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    return filename.endsWith(".3gp");
                }
            }).length;
            numberOfMaterial = file.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    return filename.endsWith(".png");
                }
            }).length;
            if (numberOfAudio == numberOfMaterial) {
                mCheckBox.setChecked(true);
            }

            return convertView;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }
}

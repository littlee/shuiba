package com.shuiba.sb.shuiba;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Administrator on 2015/9/22.
 */
public class MainFragment extends Fragment{
    private Button mParentButton;
    private Button mChildrenButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mParentButton = (Button)view.findViewById(R.id.parentButton);
        mParentButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ParentActivity.class);
                startActivity(i);
            }
        });

        mChildrenButton =(Button)view.findViewById(R.id.childButton);
        mChildrenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ChildrenActivity.class);
                startActivity(i);
            }
        });

        return view;
    }

}

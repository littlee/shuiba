package com.shuiba.sb.shuiba;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/22.
 */
public class Story {
    private  String mTitle;


    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    public ArrayList<String> getStoryTitle(Context context){
        InputStream inputStream = context.getResources().openRawResource(R.raw.title);

        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream,"GBK");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        ArrayList<String> titles = new ArrayList<String>();
        String line;
        try {
            int i = 1;
            while ((line = bufferedReader.readLine()) != null) {
                titles.add(i + "." + line);
                i++;
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return titles;
    }
}

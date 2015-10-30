package com.shuiba.sb.shuiba;

import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2015/10/18.
 */
public class DataProvider {
    private static List<Story> list = null;

    public DataProvider() {}

    public static List<Story> getStories(String path) {
        path += "/stories.txt";
        if (list != null) {
            return list;
        }
        list = new ArrayList<Story>();
        InputStreamReader isr = null;
        BufferedReader br = null;
        String line = null;
        try {
            isr = new InputStreamReader(new FileInputStream(path), "utf-8");
            br = new BufferedReader(isr);
            line = br.readLine();
            while (line != null) {
                Story s = new Story();
                String[] info = line.split(" ");
                s.setName(info[0]);
                s.setId(info[1]);
                s.setDone(Boolean.parseBoolean(info[2]));
                list.add(s);
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (br != null)
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

}

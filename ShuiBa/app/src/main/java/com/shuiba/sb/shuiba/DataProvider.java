package com.shuiba.sb.shuiba;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2015/10/18.
 */
public class DataProvider {
    private String path = null;

    public DataProvider() {}

    public DataProvider(String path) {
        this.path = path;
    }

    public List<Story> getStories() {
        List<Story> list = new ArrayList<Story>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String line = null;
        try {
            line = br.readLine();
            while (line != null) {
                Story s = new Story();
                String[] info = line.split(" ");
                s.setName(info[0]);
                s.setId(info[1]);
                list.add(s);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }

}

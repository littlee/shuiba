package com.sb.shuiba;

import java.io.File;
import java.io.FilenameFilter;

public class Story {
    private String name = null;
    private String sid = null;

    public Story() {}

    public String toString() {
        return this.name + ":" + this.sid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.sid;
    }

    public void setId(String id) {
        this.sid = id;
    }


    public static int getNumOfMaterialorAudio(String filePath, final String suffix) {
        int number = 0;
        File file = new File(filePath);
        String[] listedFiles = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(suffix);
            }
        });
        if (listedFiles != null) {
            number = listedFiles.length;
        }
        return number;
    }

}

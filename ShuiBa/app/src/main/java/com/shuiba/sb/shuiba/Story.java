package com.shuiba.sb.shuiba;
public class Story {
    private String name = null;
    private String sid = null;
    private String partsName = null;
    private boolean done = false;

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

    public Boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}

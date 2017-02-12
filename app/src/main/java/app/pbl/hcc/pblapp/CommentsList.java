package app.pbl.hcc.pblapp;

import java.util.ArrayList;

/**
 * Created by Luis on 2/9/2017.
 */

public class CommentsList {
    private ArrayList<Comment> list;

    public CommentsList() {
        list = new ArrayList<Comment>();
    }

    public CommentsList(ArrayList<Comment> list) {
        this.list = list;
    }

    public ArrayList<Comment> getList() {
        return list;
    }

    public void setList(ArrayList<Comment> list) {
        this.list = list;
    }
}

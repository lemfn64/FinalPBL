package app.pbl.hcc.pblapp;

/**
 * Created by Luis on 2/9/2017.
 */

public class Comment {
    private String author;
    private  String message;

    public Comment() {

    }

    public Comment(String message, String author) {
        this.message = message;
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

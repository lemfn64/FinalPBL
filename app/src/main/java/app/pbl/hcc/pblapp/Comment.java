package app.pbl.hcc.pblapp;

/**
 * object for a comment
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

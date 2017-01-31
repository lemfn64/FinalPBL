package app.pbl.hcc.pblapp;

/**
 * Created by Luis on 1/30/2017.
 */

public class Post {

    private int chapterCode;
    private String title;
    private String content;
    private String abstaract;
    private String author;

    public Post() {
    }

    public Post(int chapterCode, String title, String content, String abstaract, String author) {
        this.chapterCode = chapterCode;
        this.title = title;
        this.content = content;
        this.abstaract = abstaract;
        this.author = author;
    }

    public int getChapterCode() {
        return chapterCode;
    }

    public void setChapterCode(int chapterCode) {
        this.chapterCode = chapterCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAbstaract() {
        return abstaract;
    }

    public void setAbstaract(String abstaract) {
        this.abstaract = abstaract;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

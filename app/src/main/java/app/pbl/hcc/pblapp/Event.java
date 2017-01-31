package app.pbl.hcc.pblapp;

/**
 * Created by Luis on 1/30/2017.
 */

public class Event {
    private int chapterCode;
    private String author;
    private String title;
    private String content;
    private String place;
    private String time;
    private int date;

    public Event() {
    }

    public Event(String title, int chapterCode, String author, String content, String place, String time, int date) {
        this.title = title;
        this.chapterCode = chapterCode;
        this.author = author;
        this.content = content;
        this.place = place;
        this.time = time;
        this.date = date;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getChapterCode() {
        return chapterCode;
    }

    public void setChapterCode(int chapterCode) {
        this.chapterCode = chapterCode;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

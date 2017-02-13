package app.pbl.hcc.pblapp;

/**
 * object representation of a user
 */

public class User {
    private String email;
    private String password;
    private String name;
    private int position;
    private int chapterCode;

    public User(String email, String password, String name, int chapterCode, int position) {
        this.email= email;
        this. password=password;
        this.name = name;
        this.chapterCode= chapterCode;
        this.position = position;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChapterCode() {
        return chapterCode;
    }

    public void setChapterCode(int chapterCode) {
        this.chapterCode = chapterCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

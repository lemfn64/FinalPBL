package app.pbl.hcc.pblapp;

/**
 * object representation of a error report
 */

public class Report {
    private int rate;
    private String email;
    private String error;
    private String name;

    public Report() {
    }

    public Report(int rate, String email, String error, String name) {
        this.rate = rate;
        this.email = email;
        this.error = error;
        this.name = name;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

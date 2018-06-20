package alpha.dvpis.org.hmt.api;

/**
 * Created by kangup on 2017-02-16.
 */

public class Reminder {
    long date;
    String description;

    public Reminder(){

    }

    public Reminder(long date, String description){
        this.date = date;
        this.description = description;
    }

    public long getDate() {
        return date;
    }
    public void setDate(long date) {
        this.date = date;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}

package alpha.dvpis.org.hmt.api;

/**
 * Created by kangup on 2017-01-13.
 */

public class MeasurementSetting {

    /*int group;
    String description;
    long startDate;
    long lastDate;

    public int getGroup() {
        return group;
    }
    public void setGroup(int id) {
        this.group = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public long getStartDate() {
        return startDate;
    }
    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }
    public long getLastDate() {
        return lastDate;
    }
    public void setLastDate(long lastDate) {
        this.lastDate = lastDate;
    }*/

    int[] groups;
    String[] descriptions;
    long[] startDates;
    long[] lastDates;

    public MeasurementSetting(){

    }

    public MeasurementSetting(int size){
        groups = new int[size];
        descriptions = new String[size];
        startDates = new long[size];
        lastDates = new long[size];
    }

    public int[] getGroups() {
        return groups;
    }
    public void setGroups(int[] groups) {
        this.groups = groups;
    }
    public String[] getDescriptions() {
        return descriptions;
    }
    public void setDescriptions(String[] descriptions) {
        this.descriptions = descriptions;
    }
    public long[] getStartDates() {
        return startDates;
    }
    public void setStartDates(long[] startDates) {
        this.startDates = startDates;
    }
    public long[] getLastDates() {
        return lastDates;
    }
    public void setLastDates(long[] lastDates) {
        this.lastDates = lastDates;
    }
}

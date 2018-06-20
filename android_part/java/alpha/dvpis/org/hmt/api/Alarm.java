package alpha.dvpis.org.hmt.api;

import java.util.ArrayList;

/**
 * Created by kangup on 2016-12-29.
 */

public class Alarm {
    private long date;
    private int value;
    private int status;
    private String obs;

    private ArrayList<MeasurementType> mesurementType;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public ArrayList<MeasurementType> getMesurementType() {
        return mesurementType;
    }

    public void setMesurementType(ArrayList<MeasurementType> mesurementType) {
        this.mesurementType = mesurementType;
    }

}

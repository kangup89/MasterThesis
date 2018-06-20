package alpha.dvpis.org.hmt.api;

import java.util.ArrayList;

/**
 * Created by kangup on 2016-12-29.
 */

public class Measurement {
    private Long date;
    private int value;
    private ArrayList<MeasurementType> mesurementTypes;

    public Long getDate() {
        return date;
    }
    public void setDate(Long date) {
        this.date = date;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public ArrayList<MeasurementType> getMesurementTypes() {
        return mesurementTypes;
    }
    public void setMesurementTypes(ArrayList<MeasurementType> mesurementTypes) {
        this.mesurementTypes = mesurementTypes;
    }

}

package alpha.dvpis.org.hmt.api;

import java.util.ArrayList;

/**
 * Created by kangup on 2016-12-29.
 */

public class PrimaryUser {
    private String name;
    private ArrayList<Alarm> alarms;
    private ArrayList<MeasurementType> measurementTypes;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<Alarm> getAlarms() {
        return alarms;
    }
    public void setAlarms(ArrayList<Alarm> alarms) {
        this.alarms = alarms;
    }
    public ArrayList<MeasurementType> getMeasurementTypes() {
        return measurementTypes;
    }
    public void setMeasurementTypes(ArrayList<MeasurementType> measurementTypes) {
        this.measurementTypes = measurementTypes;
    }

}

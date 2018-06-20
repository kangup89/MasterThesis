package alpha.dvpis.org.hmt.api;

/**
 * Created by kangup on 2016-12-29.
 */

public class MeasurementType {
    private int group;
    private String name;
    private String description;
    private String measurementUnits;

    public int getGroup() {
        return group;
    }
    public void setGroup(int group) {
        this.group = group;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getMeasurementUnits() {
        return measurementUnits;
    }
    public void setMeasurementUnits(String measurementUnits) {
        this.measurementUnits = measurementUnits;
    }

}

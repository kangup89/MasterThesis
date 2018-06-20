package alpha.dvpis.org.hmt.unused;

import java.util.ArrayList;

import alpha.dvpis.org.hmt.api.Alarm;
import alpha.dvpis.org.hmt.api.SystemInformation;

/**
 * Created by kangup on 2017-01-13.
 */

public class QueryOutput {
    private String id;

    private String username;
    private String password;
    private String name;

    private int[] output_measurements;
    private String[] output_primaryUserNames;
    private SystemInformation systemInformation;
    private ArrayList<Alarm> output_alarms;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getOutput_measurements() {
        return output_measurements;
    }

    public void setOutput_measurements(int[] output_measurements) {
        this.output_measurements = output_measurements;
    }

    public String[] getOutput_primaryUserNames() {
        return output_primaryUserNames;
    }

    public void setOutput_primaryUserNames(String[] output_primaryUserNames) {
        this.output_primaryUserNames = output_primaryUserNames;
    }

    public SystemInformation getSystemInformation() {
        return systemInformation;
    }

    public void setSystemInformation(SystemInformation systemInformation) {
        this.systemInformation = systemInformation;
    }

    public ArrayList<Alarm> getOutput_alarms() {
        return output_alarms;
    }

    public void setOutput_alarms(ArrayList<Alarm> output_alarms) {
        this.output_alarms = output_alarms;
    }
}

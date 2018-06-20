package alpha.dvpis.org.hmt.unused;

import java.util.ArrayList;

import alpha.dvpis.org.hmt.api.PrimaryUser;
import alpha.dvpis.org.hmt.api.SystemInformation;

/**
 * Created by kangup on 2016-12-29.
 */

public class SecondaryUser {

    private String id;

    private String username;
    private String password;
    private ArrayList<PrimaryUser> primaryUsers;
    private SystemInformation systemInformation;

    public String[] getPrimaryNames(){
        String[] names = new String[primaryUsers.size()];
        int n = 0;
        for(PrimaryUser primaryUser : primaryUsers){
            names[n++] = primaryUser.getName();
        }
        return names;
    }

    public String getId(){
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUser() {
        return username;
    }
    public void setUser(String user) {
        this.username = user;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public ArrayList<PrimaryUser> getPrimaryUsers() {
        return primaryUsers;
    }
    public void setPrimaryUsers(ArrayList<PrimaryUser> primaryUsers) {
        this.primaryUsers = primaryUsers;
    }
    public SystemInformation getSystemInformation() {
        return systemInformation;
    }
    public void setSystemInformation(SystemInformation systemInformation) {
        this.systemInformation = systemInformation;
    }


}

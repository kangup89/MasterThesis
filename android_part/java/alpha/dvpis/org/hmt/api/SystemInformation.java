package alpha.dvpis.org.hmt.api;

/**
 * Created by kangup on 2016-12-29.
 */

public class SystemInformation {
    private long lastLogonDate;
    private long lastLogoutDate;
    private long sessionExpirationDate;
    private String techSupportNumber;
    private String routerSerialNumber;
    private String button;

    public long getLastLogonDate() {
        return lastLogonDate;
    }
    public void setLastLogonDate(long lastLogonDate) {
        this.lastLogonDate = lastLogonDate;
    }
    public long getLastLogoutDate() {
        return lastLogoutDate;
    }
    public void setLastLogoutDate(long lastLogoutDate) {
        this.lastLogoutDate = lastLogoutDate;
    }
    public long getSessionExpirationDate() {
        return sessionExpirationDate;
    }
    public void setSessionExpirationDate(long sessionExpirationDate) {
        this.sessionExpirationDate = sessionExpirationDate;
    }
    public String getTechSupportNumber() {
        return techSupportNumber;
    }
    public void setTechSupportNumber(String techSupportNumber) {
        this.techSupportNumber = techSupportNumber;
    }
    public String getRouterSerialNumber() {
        return routerSerialNumber;
    }
    public void setRouterSerialNumber(String routerSerialNumber) {
        this.routerSerialNumber = routerSerialNumber;
    }
    public String getButton() {
        return button;
    }
    public void setButton(String button) {
        this.button = button;
    }

}

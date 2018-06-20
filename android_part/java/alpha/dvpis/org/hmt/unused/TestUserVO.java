package alpha.dvpis.org.hmt.unused;

/**
 * Created by kangup on 2016-12-26.
 */

public class TestUserVO {

    public void TestUserVO(String id, String user, String password){
        this.id = id;
        this.user = user;
        this.password = password;
    }

    private String id;

    private String user;
    private String password;

    public String getId(){
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}

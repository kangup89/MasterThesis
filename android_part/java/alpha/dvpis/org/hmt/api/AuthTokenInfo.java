package alpha.dvpis.org.hmt.api;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kangup on 2016-12-26.
 */

public class AuthTokenInfo implements Parcelable {

    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;

    public AuthTokenInfo() {
    }

    public AuthTokenInfo(String access_token, String token_type, String refresh_token, int expires_in, String scope){
        this.access_token = access_token;
        this.token_type = token_type;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.scope = scope;
    }

    public AuthTokenInfo(Parcel in){
        this.access_token = in.readString();
        this.token_type = in.readString();
        this.refresh_token = in.readString();
        this.expires_in = in.readInt();
        this.scope = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.access_token);
        dest.writeString(this.token_type);
        dest.writeString(this.refresh_token);
        dest.writeInt(this.expires_in);
        dest.writeString(this.scope);
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<AuthTokenInfo> CREATOR = new Parcelable.Creator<AuthTokenInfo>() {
        public AuthTokenInfo createFromParcel(Parcel in) {
            return new AuthTokenInfo(in);
        }

        public AuthTokenInfo[] newArray(int size) {
            return new AuthTokenInfo[size];
        }
    };

    public String getAccess_token() {
        return access_token;
    }
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
    public String getToken_type() {
        return token_type;
    }
    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
    public String getRefresh_token() {
        return refresh_token;
    }
    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
    public int getExpires_in() {
        return expires_in;
    }
    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
    public String getScope() {
        return scope;
    }
    public void setScope(String scope) {
        this.scope = scope;
    }
    @Override
    public String toString() {
        return "AuthTokenInfo [access_token=" + access_token + ", token_type=" + token_type + ", refresh_token="
                + refresh_token + ", expires_in=" + expires_in + ", scope=" + scope + "]";
    }


}

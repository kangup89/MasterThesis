package com.kangup.dvpis.api;

public class AuthTokenInfo {

	/**
	 */
	private String access_token;
	/**
	 */
	private String token_type;
	/**
	 */
	private String refresh_token;
	/**
	 */
	private int expires_in;
	/**
	 */
	private String scope;
	/**
	 * @return
	 */
	public String getAccess_token() {
		return access_token;
	}
	/**
	 * @param  access_token
	 */
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	/**
	 * @return
	 */
	public String getToken_type() {
		return token_type;
	}
	/**
	 * @param  token_type
	 */
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	/**
	 * @return
	 */
	public String getRefresh_token() {
		return refresh_token;
	}
	/**
	 * @param  refresh_token
	 */
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	/**
	 * @return
	 */
	public int getExpires_in() {
		return expires_in;
	}
	/**
	 * @param  expires_in
	 */
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
	/**
	 * @return
	 */
	public String getScope() {
		return scope;
	}
	/**
	 * @param  scope
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}
	@Override
	public String toString() {
		return "AuthTokenInfo [access_token=" + access_token + ", token_type=" + token_type + ", refresh_token="
				+ refresh_token + ", expires_in=" + expires_in + ", scope=" + scope + "]";
	}
	
	
}

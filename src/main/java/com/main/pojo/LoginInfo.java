package com.main.pojo;
import javax.servlet.http.HttpServletRequest;

import com.common.CommonUtil;


/**
 * 装载所需要的业务信息
 * 例如登录的用户名、密码、登录日期等。
 * @author chenfuqiang
 *
 */
public class LoginInfo {
	private long companycode;
	private String userid;
	private String username;
	private String theyear;

	public LoginInfo(HttpServletRequest request) {
		String theyear = request.getParameter("theyear");
		if(!CommonUtil.isEmpty(theyear)) {
			this.setTheyear(theyear);
		}
	}

	//==================================GETTERS && SETTERS=============================//


	public long getCompanycode() {
		return companycode;
	}

	public void setCompanycode(long companycode) {
		this.companycode = companycode;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTheyear() {
		return theyear;
	}

	public void setTheyear(String theyear) {
		this.theyear = theyear;
	}
}

package com.machine.print.vo.ele.sf;

import java.io.Serializable;

/**
 * 收件人
 */
@SuppressWarnings("serial")
public class ElePrintRecipienterSF implements Serializable {
	/**
	 * 收件人姓名
	 */
	private String name="";
	/**
	 * 收件人电话
	 */
	private String telephone="";
	/**
	 * 收件人手机
	 */
	private String mobilePhone="";

	/**
	 * 收件人省
	 */
	private String provice="";
	/**
	 * 收件人市
	 */
	private String city="";
	/**
	 * 收件人区
	 */
	private String area="";
	/**
	 * 收件人详细地址
	 */
	private String detailAddress="";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getProvice() {
		return provice;
	}
	public void setProvice(String provice) {
		this.provice = provice;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
}

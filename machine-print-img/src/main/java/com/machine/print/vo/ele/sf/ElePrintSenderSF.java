package com.machine.print.vo.ele.sf;

import java.io.Serializable;

/**
 * 寄件人
 */
@SuppressWarnings("serial")
public class ElePrintSenderSF implements Serializable {
	/**
	 * 寄件人姓名
	 */
	private String name="";
	/**
	 * 寄件人电话
	 */
	private String telephone="";
	/**
	 * 寄件人手机
	 */
	private String mobilePhone="";

	/**
	 * 寄件人省
	 */
	private String provice="";
	/**
	 * 寄件人市
	 */
	private String city="";
	/**
	 * 寄件人区
	 */
	private String area="";
	/**
	 * 寄件人详细地址
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

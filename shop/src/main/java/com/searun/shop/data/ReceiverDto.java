package com.searun.shop.data;

import java.io.Serializable;


/**
  * 收货地址数据传输类
  * @类名: ReceiverDto.java
  * @描述: xxx
  * @作者: wangjinkui
  * @日期: 2015年4月20日 上午10:11:32
  * @版本:  V1.0
  * ============================================================================
  * 版权所有 2010-2014 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
  * ----------------------------------------------------------------------------
  * 提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
  * ----------------------------------------------------------------------------
  * 官方网站：http://www.sy56.com
  * ============================================================================
 */
public class ReceiverDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2992487624245164531L;

	private String id;

	private String name;// 收货人姓名
	
	private String areaPath;// 收货地区路径
	
	private String address;// 地址
	
	private String phone;// 电话
	
	private String mobile;// 手机
	
	private String zipCode;// 邮编
	
	private Boolean isDefault;// 是否默认
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAreaPath() {
		return areaPath;
	}

	public void setAreaPath(String areaPath) {
		this.areaPath = areaPath;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


}
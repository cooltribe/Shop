package com.searun.shop.data;

import java.io.Serializable;



/**
 * 发货点中心信息传输类
  * @类名: DeliveryCenterDto.java
  * @描述: xxx
  * @作者: wangjinkui
  * @日期: 2015年6月9日 上午8:46:29
  * @版本:  V1.0
  * ============================================================================
  * 版权所有 2010-2014 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
  * ----------------------------------------------------------------------------
  * 提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
  * ----------------------------------------------------------------------------
  * 官方网站：http://www.sy56.com
  * ============================================================================
 */
public class DeliveryCenterDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4336784222429267826L;

	private String id;

	private String name; //发货地名称
	
	private String address ; //发货地址
	
	private String area ; //发货地区
	
	private String contact; //仓库联系人
	
	private boolean isDefault; //是否默认
	
	private String memo; //备注
	
	private String mobile ; // 手机
	
	private String phone; //电话
	
	private String zipCode;//邮编
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	
}

package com.searun.shop.data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
  * 配送方式传输类
  * @类名: DeliveryTypeDto.java
  * @描述: xxx
  * @作者: wangjinkui
  * @日期: 2015年4月24日 下午1:16:19
  * @版本:  V1.0
  * ============================================================================
  * 版权所有 2010-2014 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
  * ----------------------------------------------------------------------------
  * 提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
  * ----------------------------------------------------------------------------
  * 官方网站：http://www.sy56.com
  * ============================================================================
 */
public class DeliveryTypeDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1019336442467219115L;
	
	private String id;

	private String name;// 配送方式名称
	
	/**、
	 * deliveryAgainstPayment 先付款后发货, cashOnDelivery 货到付款
	 */
	private String deliveryMethod;// 配送类型
	
	private Double firstWeight;// 首重量
	
	private Double continueWeight;// 续重量
	
	private String firstWeightUnit;// 首重单位
	
	private String continueWeightUnit;// 续重单位
	
	private BigDecimal firstWeightPrice;// 首重价格
	
	private BigDecimal continueWeightPrice;// 续重价格
	
	private String description;// 介绍
	
	private Integer orderList;// 排序
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getFirstWeight() {
		return firstWeight;
	}

	public void setFirstWeight(Double firstWeight) {
		this.firstWeight = firstWeight;
	}

	public Double getContinueWeight() {
		return continueWeight;
	}

	public void setContinueWeight(Double continueWeight) {
		this.continueWeight = continueWeight;
	}


	public String getFirstWeightUnit() {
		return firstWeightUnit;
	}

	public void setFirstWeightUnit(String firstWeightUnit) {
		this.firstWeightUnit = firstWeightUnit;
	}

	public String getContinueWeightUnit() {
		return continueWeightUnit;
	}

	public void setContinueWeightUnit(String continueWeightUnit) {
		this.continueWeightUnit = continueWeightUnit;
	}

	public BigDecimal getFirstWeightPrice() {
		return firstWeightPrice;
	}

	public void setFirstWeightPrice(BigDecimal firstWeightPrice) {
		this.firstWeightPrice = firstWeightPrice;
	}

	public BigDecimal getContinueWeightPrice() {
		return continueWeightPrice;
	}

	public void setContinueWeightPrice(BigDecimal continueWeightPrice) {
		this.continueWeightPrice = continueWeightPrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getOrderList() {
		return orderList;
	}

	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}

	public String getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
package com.searun.shop.data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
  * 支付配置
  * @类名: PaymentConfigDto.java
  * @描述: xxx
  * @作者: wangjinkui
  * @日期: 2015年4月24日 下午2:09:50
  * @版本:  V1.0
  * ============================================================================
  * 版权所有 2010-2014 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
  * ----------------------------------------------------------------------------
  * 提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
  * ----------------------------------------------------------------------------
  * 官方网站：http://www.sy56.com
  * ============================================================================
 */
public class PaymentConfigDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8784987271338525023L;
	
	private String id;

	//支付配置类型（预存款、线下支付、财付通、支付宝）（deposit, offline,tenpay,alipay）
	private String paymentConfigType;
	
	private String name;// 支付方式名称
	
	//支付手续费类型（按比例收费、固定费用）（scale, fixed）
	private String paymentFeeType;// 支付手续费类型
	
	private BigDecimal paymentFee;// 支付费用
	
	private String description;// 介绍
	
	private Integer orderList;// 排序
	
	private String configObjectStore;// 配置对象信息储存
	

	public String getPaymentConfigType() {
		return paymentConfigType;
	}

	public void setPaymentConfigType(String paymentConfigType) {
		this.paymentConfigType = paymentConfigType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPaymentFeeType() {
		return paymentFeeType;
	}

	public void setPaymentFeeType(String paymentFeeType) {
		this.paymentFeeType = paymentFeeType;
	}

	public BigDecimal getPaymentFee() {
		return paymentFee;
	}

	public void setPaymentFee(BigDecimal paymentFee) {
		this.paymentFee = paymentFee;
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

	public String getConfigObjectStore() {
		return configObjectStore;
	}

	public void setConfigObjectStore(String configObjectStore) {
		this.configObjectStore = configObjectStore;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
package com.searun.shop.data;

import java.io.Serializable;


/**
  * 订单日志传输类
  * @类名: OrderLog.java
  * @描述: xxx
  * @作者: wangjinkui
  * @日期: 2015年5月6日 上午9:02:39
  * @版本:  V1.0
  * ============================================================================
  * 版权所有 2010-2014 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
  * ----------------------------------------------------------------------------
  * 提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
  * ----------------------------------------------------------------------------
  * 官方网站：http://www.sy56.com
  * ============================================================================
 */
public class OrderLogDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6550737838923961753L;
	
	private String id;

	// 订单日志类型（订单创建、订单修改、订单支付、订单退款、订单发货、订单退货、订单完成、订单作废、订单取消、订单删除）
	//create, modify, payment, refund, shipping, reship, completed, invalid,cancel,delete,evaluation
	private String orderLogType;// 订单日志类型
	
	private String orderSn;// 订单编号
	
	private String operator;// 操作员
	
	private String info;// 日志信息


	public String getOrderLogType() {
		return orderLogType;
	}

	public void setOrderLogType(String orderLogType) {
		this.orderLogType = orderLogType;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
}
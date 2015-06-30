package com.searun.shop.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;




/**
 * 订单数据传输类
  * @类名: Order.java
  * @描述: xxx
  * @作者: wangjinkui
  * @日期: 2015年4月21日 上午9:39:12
  * @版本:  V1.0
  * ============================================================================
  * 版权所有 2010-2014 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
  * ----------------------------------------------------------------------------
  * 提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
  * ----------------------------------------------------------------------------
  * 官方网站：http://www.sy56.com
  * ============================================================================
 */
public class OrderDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7124859906957676718L;
	
	//订单id
	private String id;
	// 订单编号
	private String orderSn;
	
	// 订单状态（未处理、已处理、已完成、已取消、已删除、已作废、已收货、已评价）
		//unprocessed, processed, completed, cancel,delete,invalid, received, evaluation
		private String orderStatus;
		
		// 付款状态（未支付、部分支付、已支付、部分退款、全额退款）
		//unpaid, partPayment, paid, partRefund, refunded
		private String paymentStatus;
		
		// 配送状态（未发货、部分发货、已发货、部分退货、已退货）
			//unshipped, partShipped, shipped, partReshiped, reshiped
		private String shippingStatus;
		
		//支付单号等相关信息
		private String paymentSn;// 支付编号
	
	// 配送方式名称（未发货、部分发货、已发货、部分退货、已退货）
	private String deliveryTypeName;
	
	// 支付方式名称
	private String paymentConfigName;
	
	// 商品总价格
	private BigDecimal productTotalPrice;
	
	// 配送费用
	private BigDecimal deliveryFee;
	
	// 支付费用
	private BigDecimal paymentFee;
	
	// 订单总额
	private BigDecimal totalAmount;
	
	// 已付金额
	private BigDecimal paidAmount;
	
	// 商品重量
	private Double productWeight;
	
	// 商品重量单位（g, kg, t）
	private String productWeightUnit;
	
	// 商品总数
	private Integer productTotalQuantity;
	
	// 收货人姓名
	private String shipName;
	
	// 收货地区
	private String shipArea;
	
	// 收货地区路径
	private String shipAreaPath;
	
	// 收货地址
	private String shipAddress;
	
	// 收货邮编
	private String shipZipCode;
	
	// 收货电话
	private String shipPhone;
	
	// 收货手机
	private String shipMobile;
	
	//接收者ID
	private String receiverId;
	
	// 附言
	private String memo;
	
	//购物车
	private List<CartItemDto> cartItemDtos;
	
	// 配送方式
	private DeliveryTypeDto deliveryType;
	
	// 支付方式
	private PaymentConfigDto paymentConfig;
	
	/**
	 * 订单项
	 */
	private List<OrderItemDto> orderItems;

	// 订单日志
	private List<OrderLogDto> orderLogs;
	
	//物流动态
	private List<KuaidiTracking> trackings;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<KuaidiTracking> getTrackings() {
		return trackings;
	}

	public void setTrackings(List<KuaidiTracking> trackings) {
		this.trackings = trackings;
	}

	public List<OrderLogDto> getOrderLogs() {
		return orderLogs;
	}

	public void setOrderLogs(List<OrderLogDto> orderLogs) {
		this.orderLogs = orderLogs;
	}

	public String getPaymentSn() {
		return paymentSn;
	}

	public void setPaymentSn(String paymentSn) {
		this.paymentSn = paymentSn;
	}

	public List<OrderItemDto> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemDto> orderItems) {
		this.orderItems = orderItems;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(String shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public String getDeliveryTypeName() {
		return deliveryTypeName;
	}

	public void setDeliveryTypeName(String deliveryTypeName) {
		this.deliveryTypeName = deliveryTypeName;
	}

	public String getPaymentConfigName() {
		return paymentConfigName;
	}

	public void setPaymentConfigName(String paymentConfigName) {
		this.paymentConfigName = paymentConfigName;
	}

	public BigDecimal getProductTotalPrice() {
		return productTotalPrice;
	}

	public void setProductTotalPrice(BigDecimal productTotalPrice) {
		this.productTotalPrice = productTotalPrice;
	}

	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public BigDecimal getPaymentFee() {
		return paymentFee;
	}

	public void setPaymentFee(BigDecimal paymentFee) {
		this.paymentFee = paymentFee;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Double getProductWeight() {
		return productWeight;
	}

	public void setProductWeight(Double productWeight) {
		this.productWeight = productWeight;
	}

	public String getProductWeightUnit() {
		return productWeightUnit;
	}

	public void setProductWeightUnit(String productWeightUnit) {
		this.productWeightUnit = productWeightUnit;
	}

	public Integer getProductTotalQuantity() {
		return productTotalQuantity;
	}

	public void setProductTotalQuantity(Integer productTotalQuantity) {
		this.productTotalQuantity = productTotalQuantity;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public String getShipArea() {
		return shipArea;
	}

	public void setShipArea(String shipArea) {
		this.shipArea = shipArea;
	}

	public String getShipAreaPath() {
		return shipAreaPath;
	}

	public void setShipAreaPath(String shipAreaPath) {
		this.shipAreaPath = shipAreaPath;
	}

	public String getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}

	public String getShipZipCode() {
		return shipZipCode;
	}

	public void setShipZipCode(String shipZipCode) {
		this.shipZipCode = shipZipCode;
	}

	public String getShipPhone() {
		return shipPhone;
	}

	public void setShipPhone(String shipPhone) {
		this.shipPhone = shipPhone;
	}

	public String getShipMobile() {
		return shipMobile;
	}

	public void setShipMobile(String shipMobile) {
		this.shipMobile = shipMobile;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public List<CartItemDto> getCartItemDtos() {
		return cartItemDtos;
	}

	public void setCartItemDtos(List<CartItemDto> cartItemDtos) {
		this.cartItemDtos = cartItemDtos;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public DeliveryTypeDto getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(DeliveryTypeDto deliveryType) {
		this.deliveryType = deliveryType;
	}

	public PaymentConfigDto getPaymentConfig() {
		return paymentConfig;
	}

	public void setPaymentConfig(PaymentConfigDto paymentConfig) {
		this.paymentConfig = paymentConfig;
	}
	
	
}
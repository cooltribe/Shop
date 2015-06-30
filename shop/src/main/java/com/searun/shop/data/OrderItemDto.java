package com.searun.shop.data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
  * 订单项
  * @类名: OrderItemDto.java
  * @描述: xxx
  * @作者: wangjinkui
  * @日期: 2015年4月27日 下午8:01:18
  * @版本:  V1.0
  * ============================================================================
  * 版权所有 2010-2014 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
  * ----------------------------------------------------------------------------
  * 提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
  * ----------------------------------------------------------------------------
  * 官方网站：http://www.sy56.com
  * ============================================================================
 */
public class OrderItemDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7308674297835313783L;
	
	private String id;// ID
	
	private String productSn;// 商品货号
	
	private String productName;// 商品名称
	
	private BigDecimal productPrice;// 商品价格
	
	private String productHtmlFilePath;// 商品HTML静态文件路径
	
	private Integer productQuantity;// 商品数量
	
	private Integer deliveryQuantity;// 发货数量
	
	private Integer totalDeliveryQuantity;// 总发货量
	
	private ProductDto product;// 商品
	
	//正常交易   退款状态                   退货状态                退款完成        退货完成
	//normal applyRefund   applyReship   refund    reship
	private String orderItemStatus;// 各个订单项状态
	
	private BigDecimal refundAmount;// 申请退款金额
	
	private String orderId;//订单ID
	
	//"reshiped";//退款+退货
	//"refund";//仅退款
	private String command; //命令
	
	private Boolean isReceived; //是否收到货物
	
	private DeliveryCorpDto deliveryCorpDto;//物流公司
	
	private String reshipSn; //物流编号
	

	public String getProductSn() {
		return productSn;
	}

	public void setProductSn(String productSn) {
		this.productSn = productSn;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductHtmlFilePath() {
		return productHtmlFilePath;
	}

	public void setProductHtmlFilePath(String productHtmlFilePath) {
		this.productHtmlFilePath = productHtmlFilePath;
	}

	public Integer getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}

	public Integer getDeliveryQuantity() {
		return deliveryQuantity;
	}

	public void setDeliveryQuantity(Integer deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}

	public Integer getTotalDeliveryQuantity() {
		return totalDeliveryQuantity;
	}

	public void setTotalDeliveryQuantity(Integer totalDeliveryQuantity) {
		this.totalDeliveryQuantity = totalDeliveryQuantity;
	}

	public ProductDto getProduct() {
		return product;
	}

	public void setProduct(ProductDto product) {
		this.product = product;
	}

	public String getOrderItemStatus() {
		return orderItemStatus;
	}

	public void setOrderItemStatus(String orderItemStatus) {
		this.orderItemStatus = orderItemStatus;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Boolean isReceived() {
		return isReceived;
	}

	public void setReceived(Boolean isReceived) {
		this.isReceived = isReceived;
	}

	public DeliveryCorpDto getDeliveryCorpDto() {
		return deliveryCorpDto;
	}

	public void setDeliveryCorpDto(DeliveryCorpDto deliveryCorpDto) {
		this.deliveryCorpDto = deliveryCorpDto;
	}

	public String getReshipSn() {
		return reshipSn;
	}

	public void setReshipSn(String reshipSn) {
		this.reshipSn = reshipSn;
	}
	
	
}
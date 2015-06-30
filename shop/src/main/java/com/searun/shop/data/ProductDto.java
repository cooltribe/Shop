package com.searun.shop.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 商品传输类
  * @类名: ProductDto.java
  * @描述: xxx
  * @作者: wangjinkui
  * @日期: 2015年4月13日 下午3:43:42
  * @版本:  V1.0
  * ============================================================================
  * 版权所有 2010-2014 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
  * ----------------------------------------------------------------------------
  * 提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
  * ----------------------------------------------------------------------------
  * 官方网站：http://www.sy56.com
  * ============================================================================
 */
public class ProductDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8017523582255844878L;
	
	private String id;// ID
	
	private Date createDate;// 创建日期
	
	private Date modifyDate;// 修改日期
	
	private String productSn;// 货号
	
	private String name;// 商品名称
	
	private BigDecimal price;// 商品价格
	
	private BigDecimal marketPrice;// 市场价格
	
	private Double weight;// 商品重量
	
	private String weightUnit;// 重量单位
	
	private Integer store;// 商品库存数量
	
	private Integer freezeStore;// 被占用库存数
	
	private Integer point;// 积分
	
	private Boolean isMarketable;// 是否上架
	
	private Boolean isBest;// 是否为精品商品
	
	private Boolean isNew;// 是否为新品商品
	
	private Boolean isHot;// 是否为热销商品
	
	private String description;// 描述
	
	private String metaKeywords;// 页面关键词
	
	private String metaDescription;// 页面描述
	
	private String htmlFilePath;// HTML静态文件路径
	
	private String productImageListStore;// 商品图片路径存储
	
	private String orderType; //排序类型
	
	private String categoryId; //所属类别ID
	
	
	/**
	 * BestProduct  精品商品
	 * HotProduct 热销商品
	 * NewProduct 新品
	 */
	private String command;//操作类型
	
	private DeliveryCenterDto deliveryCenterDto;//发货地信息
	
	
	
	
	public DeliveryCenterDto getDeliveryCenterDto() {
		return deliveryCenterDto;
	}
	public void setDeliveryCenterDto(DeliveryCenterDto deliveryCenterDto) {
		this.deliveryCenterDto = deliveryCenterDto;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getProductSn() {
		return productSn;
	}
	public void setProductSn(String productSn) {
		this.productSn = productSn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getWeightUnit() {
		return weightUnit;
	}
	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}
	public Integer getStore() {
		return store;
	}
	public void setStore(Integer store) {
		this.store = store;
	}
	public Integer getFreezeStore() {
		return freezeStore;
	}
	public void setFreezeStore(Integer freezeStore) {
		this.freezeStore = freezeStore;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public Boolean getIsMarketable() {
		return isMarketable;
	}
	public void setIsMarketable(Boolean isMarketable) {
		this.isMarketable = isMarketable;
	}
	public Boolean getIsBest() {
		return isBest;
	}
	public void setIsBest(Boolean isBest) {
		this.isBest = isBest;
	}
	public Boolean getIsNew() {
		return isNew;
	}
	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}
	public Boolean getIsHot() {
		return isHot;
	}
	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMetaKeywords() {
		return metaKeywords;
	}
	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}
	public String getMetaDescription() {
		return metaDescription;
	}
	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}
	public String getHtmlFilePath() {
		return htmlFilePath;
	}
	public void setHtmlFilePath(String htmlFilePath) {
		this.htmlFilePath = htmlFilePath;
	}
	public String getProductImageListStore() {
		return productImageListStore;
	}
	public void setProductImageListStore(String productImageListStore) {
		this.productImageListStore = productImageListStore;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	@Override
	public String toString() {
		return "ProductDto [id=" + id + ", createDate=" + createDate + ", modifyDate=" + modifyDate + ", productSn="
				+ productSn + ", name=" + name + ", price=" + price + ", marketPrice=" + marketPrice + ", weight="
				+ weight + ", weightUnit=" + weightUnit + ", store=" + store + ", freezeStore=" + freezeStore
				+ ", point=" + point + ", isMarketable=" + isMarketable + ", isBest=" + isBest + ", isNew=" + isNew
				+ ", isHot=" + isHot + ", description=" + description + ", metaKeywords=" + metaKeywords
				+ ", metaDescription=" + metaDescription + ", htmlFilePath=" + htmlFilePath
				+ ", productImageListStore=" + productImageListStore + ", orderType=" + orderType + ", categoryId="
				+ categoryId + "]";
	}
	
	

}
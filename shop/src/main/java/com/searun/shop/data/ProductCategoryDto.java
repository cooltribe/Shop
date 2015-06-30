package com.searun.shop.data;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class ProductCategoryDto implements Serializable {
	private static final long serialVersionUID = 5836483648685121818L;
	private String id;
	private String name;// 分类名称

	private String metaKeywords;// 页面关键词

	private String metaDescription;// 页面描述

	private Integer orderList;// 排序

	private String path;// 树路径

	private String image; // 楼层图片配置
	private List<ProductCategoryDto> children;
	private Date createDate;// 创建日期
	private Date modifyDate;// 修改日期

	public List<ProductCategoryDto> getChildren() {
		return children;
	}

	public void setChildren(List<ProductCategoryDto> children) {
		this.children = children;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImage() {
		return this.image;
	}

	public String getMetaDescription() {
		return this.metaDescription;
	}

	public String getMetaKeywords() {
		return this.metaKeywords;
	}

	public String getName() {
		return this.name;
	}

	public Integer getOrderList() {
		return this.orderList;
	}

	public String getPath() {
		return this.path;
	}

	public void setImage(String paramString) {
		this.image = paramString;
	}

	public void setMetaDescription(String paramString) {
		this.metaDescription = paramString;
	}

	public void setMetaKeywords(String paramString) {
		this.metaKeywords = paramString;
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public void setOrderList(Integer paramInteger) {
		this.orderList = paramInteger;
	}

	public void setPath(String paramString) {
		this.path = paramString;
	}
}

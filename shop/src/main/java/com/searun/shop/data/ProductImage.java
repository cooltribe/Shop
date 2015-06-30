package com.searun.shop.data;

import java.io.Serializable;

/**
 * 商品图片传输类
  * @类名: ProductImage.java
  * @描述: xxx
  * @作者: wangjinkui
  * @日期: 2015年4月14日 上午11:51:30
  * @版本:  V1.0
  * ============================================================================
  * 版权所有 2010-2014 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
  * ----------------------------------------------------------------------------
  * 提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
  * ----------------------------------------------------------------------------
  * 官方网站：http://www.sy56.com
  * ============================================================================
 */
public class ProductImage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9014889407602053590L;

	private String id;
	
	private String bigProductImagePath;
	
	private String smallProductImagePath;
	
	private String sourceProductImagePath;
	
	private String thumbnailProductImagePath;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBigProductImagePath() {
		return bigProductImagePath;
	}

	public void setBigProductImagePath(String bigProductImagePath) {
		this.bigProductImagePath = bigProductImagePath;
	}

	public String getSmallProductImagePath() {
		return smallProductImagePath;
	}

	public void setSmallProductImagePath(String smallProductImagePath) {
		this.smallProductImagePath = smallProductImagePath;
	}

	public String getSourceProductImagePath() {
		return sourceProductImagePath;
	}

	public void setSourceProductImagePath(String sourceProductImagePath) {
		this.sourceProductImagePath = sourceProductImagePath;
	}

	public String getThumbnailProductImagePath() {
		return thumbnailProductImagePath;
	}

	public void setThumbnailProductImagePath(String thumbnailProductImagePath) {
		this.thumbnailProductImagePath = thumbnailProductImagePath;
	}
	
	
}

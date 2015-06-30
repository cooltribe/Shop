package com.searun.shop.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
  * 首页数据传输类
  * @类名: ProductCategoryDto.java
  * @描述: xxx
  * @作者: wangjinkui
  * @日期: 2015年4月13日 上午10:08:25
  * @版本:  V1.0
  * ============================================================================
  * 版权所有 2010-2014 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
  * ----------------------------------------------------------------------------
  * 提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
  * ----------------------------------------------------------------------------
  * 官方网站：http://www.sy56.com
  * ============================================================================
 */
public class HomeDataDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2583350462803994116L;

	/**
	 * 精点推荐
	 */
	private List<ProductDto> bestProducts;
	
	/**
	 * 热销商品
	 */
	private List<ProductDto> hotProducts;
	
	/**
	 * 新品上市
	 */
	private List<ProductDto> newProducts;
	
	/**
	 * 最新文章
	 */
	private List<ArticleDto> articleDtos;
	

	public List<ProductDto> getBestProducts() {
		return bestProducts;
	}

	public void setBestProducts(List<ProductDto> bestProducts) {
		this.bestProducts = bestProducts;
	}

	public List<ProductDto> getHotProducts() {
		return hotProducts;
	}

	public void setHotProducts(List<ProductDto> hotProducts) {
		this.hotProducts = hotProducts;
	}

	public List<ProductDto> getNewProducts() {
		return newProducts;
	}

	public void setNewProducts(List<ProductDto> newProducts) {
		this.newProducts = newProducts;
	}

	public List<ArticleDto> getArticleDtos() {
		return articleDtos;
	}

	public void setArticleDtos(List<ArticleDto> articleDtos) {
		this.articleDtos = articleDtos;
	}
	
	
}

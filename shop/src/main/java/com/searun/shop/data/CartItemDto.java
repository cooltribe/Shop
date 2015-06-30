package com.searun.shop.data;

import java.io.Serializable;


/**
 * 购物车数据传输类
  * @类名: CarItemDto.java
  * @描述: xxx
  * @作者: wangjinkui
  * @日期: 2015年4月16日 上午11:28:31
  * @版本:  V1.0
  * ============================================================================
  * 版权所有 2010-2014 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
  * ----------------------------------------------------------------------------
  * 提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
  * ----------------------------------------------------------------------------
  * 官方网站：http://www.sy56.com
  * ============================================================================
 */
public class CartItemDto implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7936797016639077011L;

	private Integer quantity;// 数量
	
	private ProductDto product;// 商品
	
	private MemberDto member;// 会员
	
	private String id;//id
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public ProductDto getProduct() {
		return product;
	}

	public void setProduct(ProductDto product) {
		this.product = product;
	}

	public MemberDto getMember() {
		return member;
	}

	public void setMember(MemberDto member) {
		this.member = member;
	}
	
	
}

package com.searun.shop.data;

import java.io.Serializable;
import java.util.List;


/**
  * 商品评价传输类
  * @类名: ReviewDto.java
  * @描述: xxx
  * @作者: wangjinkui
  * @日期: 2015年5月12日 上午11:17:05
  * @版本:  V1.0
  * ============================================================================
  * 版权所有 2010-2014 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
  * ----------------------------------------------------------------------------
  * 提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
  * ----------------------------------------------------------------------------
  * 官方网站：http://www.sy56.com
  * ============================================================================
 */
public class ReviewDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7636216105060097098L;

	private String content; // 评价内容
	
	private String ip; // 会员评价的IP
	
	private Boolean is_show;// 商品评价是否展示
	
	private String score;// 评价总分
	
	private String reviewImageListStore;// 晒单图片路径存储

	private MemberDto member;// 评价会员
	
	private ProductDto product;// 评价商品
	
	private String orderId;//订单ID
	
	private List<ImageDto> productImages;//评价图片
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Boolean getIs_show() {
		return is_show;
	}
	public void setIs_show(Boolean is_show) {
		this.is_show = is_show;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getReviewImageListStore() {
		return reviewImageListStore;
	}
	public void setReviewImageListStore(String reviewImageListStore) {
		this.reviewImageListStore = reviewImageListStore;
	}
	public MemberDto getMember() {
		return member;
	}
	public void setMember(MemberDto member) {
		this.member = member;
	}
	public ProductDto getProduct() {
		return product;
	}
	public void setProduct(ProductDto product) {
		this.product = product;
	}
	public List<ImageDto> getProductImages() {
		return productImages;
	}
	public void setProductImages(List<ImageDto> productImages) {
		this.productImages = productImages;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	

}

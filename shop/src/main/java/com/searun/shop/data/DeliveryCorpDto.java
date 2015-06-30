package com.searun.shop.data;
import java.io.Serializable;

/**
  * 物流公司传输类
  * @类名: DeliveryCorpDto.java
  * @描述: xxx
  * @作者: wangjinkui
  * @日期: 2015年5月26日 下午4:13:33
  * @版本:  V1.0
  * ============================================================================
  * 版权所有 2010-2014 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
  * ----------------------------------------------------------------------------
  * 提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
  * ----------------------------------------------------------------------------
  * 官方网站：http://www.sy56.com
  * ============================================================================
 */
public class DeliveryCorpDto implements Serializable {

	private static final long serialVersionUID = 10595703086045998L;
	
	private String id;
	
	private String code; //物流公司代码
	
	private String name;// 名称
	
	private String url;// 网址
	
	private Integer orderList;// 排序
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getOrderList() {
		return orderList;
	}

	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}
	
}
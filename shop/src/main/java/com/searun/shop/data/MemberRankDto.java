package com.searun.shop.data;

import java.io.Serializable;


/**
 * 会员等级数据传输类
 * ============================================================================
 * 版权所有 2010-2015 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.sy56.com
 * ============================================================================
 */

public class MemberRankDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3525160394195381901L;
	
	private String name;// 等級名称
	
	private Double preferentialScale;// 优惠百分比
	
	private Integer point;// 所需积分
	
	private Boolean isDefault;// 是否为默认等级
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPreferentialScale() {
		return preferentialScale;
	}

	public void setPreferentialScale(Double preferentialScale) {
		this.preferentialScale = preferentialScale;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	

}
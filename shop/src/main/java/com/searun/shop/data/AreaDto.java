package com.searun.shop.data;

import java.io.Serializable;
import java.util.List;

/**
  * 地区传输类
  * @类名: Area.java
  * @描述: xxx
  * @作者: wangjinkui
  * @日期: 2015年4月21日 下午6:12:30
  * @版本:  V1.0
  * ============================================================================
  * 版权所有 2010-2014 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
  * ----------------------------------------------------------------------------
  * 提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
  * ----------------------------------------------------------------------------
  * 官方网站：http://www.sy56.com
  * ============================================================================
 */
public class AreaDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6543610755108609979L;
	
	private String id;

	private String name;// 地区名称
	
	private String path;// 树路径
	
	private AreaDto parent;// 上级地区
	
	private List<AreaDto> children;// 下级地区
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public AreaDto getParent() {
		return parent;
	}

	public void setParent(AreaDto parent) {
		this.parent = parent;
	}

	public List<AreaDto> getChildren() {
		return children;
	}

	public void setChildren(List<AreaDto> children) {
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
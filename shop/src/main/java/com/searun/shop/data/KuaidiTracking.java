package com.searun.shop.data;

import java.io.Serializable;
import java.util.Date;

/**
 * 爱快递物流跟踪明细
  * @类名: Tracking.java
  * @描述: xxx
  * @作者: wangjinkui
  * @日期: 2015年4月7日 下午1:49:06
  * @版本:  V1.0
  * ============================================================================
  * 版权所有 2010-2014 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
  * ----------------------------------------------------------------------------
  * 提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
  * ----------------------------------------------------------------------------
  * 官方网站：http://www.sy56.com
  * ============================================================================
 */
public class KuaidiTracking implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -508681056673457232L;

	/**
	 * 时间
	 */
	private String time;
	
	/**
	 * 明细信息
	 */
	private String content;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
}

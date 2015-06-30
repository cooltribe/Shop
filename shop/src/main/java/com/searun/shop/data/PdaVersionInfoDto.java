package com.searun.shop.data;

import java.io.Serializable;

/**
  * 手机端版本信息数据传输类
  * @类名: PdaVersionInfoDto.java
  * @描述: xxx
  * @作者: wangjinkui
  * @日期: 2014年8月29日 上午10:07:54
  * @版本:  V1.0
  * ============================================================================
  * 版权所有 2010-2014 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
  * ----------------------------------------------------------------------------
  * 提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
  * ----------------------------------------------------------------------------
  * 官方网站：http://www.sy56.com
  * ============================================================================
 */
public class PdaVersionInfoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5238357294470788367L;

	/**
	 * 版本号
	 */
	private String version;
	
	/**
	 * 是否强制升级客户端
	 * (1 为是
	 *  0为否
	 * )
	 */
	private String isUpgrade;
	
	/**
	 * 客户端下载地址
	 */
	private String url;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getIsUpgrade() {
		return isUpgrade;
	}

	public void setIsUpgrade(String isUpgrade) {
		this.isUpgrade = isUpgrade;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
    
}

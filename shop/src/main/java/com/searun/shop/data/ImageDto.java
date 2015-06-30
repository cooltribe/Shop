package com.searun.shop.data;

import java.io.Serializable;

/**
 * 手机端图片信息
  * @类名: ImageDto.java
  * @描述: xxx
  * @作者: wangjinkui
  * @日期: 2014年8月27日 上午9:44:15
  * @版本:  V1.0
  * ============================================================================
  * 版权所有 2010-2014 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
  * ----------------------------------------------------------------------------
  * 提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
  * ----------------------------------------------------------------------------
  * 官方网站：http://www.sy56.com
  * ============================================================================
 */
public class ImageDto implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 2437338624942117706L;
    /**
     * 图片后缀（如png,则为png）
     */
    private String imageSuffix;
    
    /**
     * 图片的二进制流
     */
    private byte[] file;

	public String getImageSuffix() {
		return imageSuffix;
	}

	public void setImageSuffix(String imageSuffix) {
		this.imageSuffix = imageSuffix;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}
    
    
}

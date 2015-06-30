package com.searun.shop.data;

import java.io.Serializable;


/**
 * 实体类 - 文章
 * ============================================================================
 * 版权所有 2010-2015 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.sy56.com
 * ============================================================================
 */

public class ArticleDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2091076071376187170L;

	private String title;// 标题
	
	private String author;// 作者
	
	private String content;// 内容
	
	private String metaKeywords;// 页面关键词
	
	private String metaDescription;// 页面描述
	
	private Boolean isPublication;// 是否发布
	
	private Boolean isTop;// 是否置顶
	
	private Boolean isRecommend;// 是否为推荐文章
	
	private Integer pageCount;// 文章页数
	
	private String htmlFilePath;// HTML静态文件路径（首页）
	
	private Integer hits;// 点击数
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMetaKeywords() {
		return metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public Boolean getIsPublication() {
		return isPublication;
	}

	public void setIsPublication(Boolean isPublication) {
		this.isPublication = isPublication;
	}

	public Boolean getIsTop() {
		return isTop;
	}

	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	public Boolean getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Boolean isRecommend) {
		this.isRecommend = isRecommend;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public String getHtmlFilePath() {
		return htmlFilePath;
	}

	public void setHtmlFilePath(String htmlFilePath) {
		this.htmlFilePath = htmlFilePath;
	}

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}
	

}
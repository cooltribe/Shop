package com.searun.shop.entity;

import java.io.Serializable;

public class PdaRequest<T> implements Serializable {
	private static final long serialVersionUID = 932755814693221291L;
	private T data;
	private String memberType;
	private String originApp = "ANDROID";//ANDROID
	private PdaPagination pagination;//分页信息
	private String userName;
	private String uuId;

	public T getData() {
		return this.data;
	}

	public String getMemberType() {
		return this.memberType;
	}

	public String getOriginApp() {
		return this.originApp;
	}

	public PdaPagination getPagination() {
		return this.pagination;
	}

	public String getUserName() {
		return this.userName;
	}

	public String getUuId() {
		return this.uuId;
	}

	public void setData(T paramT) {
		this.data = paramT;
	}

	public void setMemberType(String paramString) {
		this.memberType = paramString;
	}

	public void setOriginApp(String paramString) {
		this.originApp = paramString;
	}

	public void setPagination(PdaPagination paramPdaPagination) {
		this.pagination = paramPdaPagination;
	}

	public void setUserName(String paramString) {
		this.userName = paramString;
	}

	public void setUuId(String paramString) {
		this.uuId = paramString;
	}

	public String toString() {
		return "PdaRequest [data=" + this.data + "]";
	}
}

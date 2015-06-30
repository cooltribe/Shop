package com.searun.shop.entity;

import java.util.List;

import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.searun.shop.data.CartItemDto;
import com.searun.shop.data.MemberDto;

public class NetWork {
	/**
	 * 获取分类信息
	 * 
	 * @param <T>
	 * @param pcd
	 * @return
	 */
//	public static <T> RequestParams getProductList(T t) {
//		// ProductCategoryDto pcd = new ProductCategoryDto();
//		PdaRequest<T> localPdaRequest = new PdaRequest<T>();
//		localPdaRequest.setData(t);
//		String str = new Gson().toJson(localPdaRequest);
//		Log.i("参数信息：", str);
//		RequestParams localRequestParams = new RequestParams();
//		localRequestParams.put("jsonString", str);
//		return localRequestParams;
//	}
	
	/**
	 * 传一般对象带用户名
	 * 
	 * @param <T>
	 * @param pcd
	 * @return
	 */
//	public static <T> RequestParams getProductList(T t, MemberDto md) {
//		// ProductCategoryDto pcd = new ProductCategoryDto();
//		PdaRequest<T> localPdaRequest = new PdaRequest<T>();
//		localPdaRequest.setData(t);
//		if (null != md) {
//			
//			localPdaRequest.setUuId(md.getId());
//		}
//		String str = new Gson().toJson(localPdaRequest);
//		Log.i("参数信息：", str);
//		RequestParams localRequestParams = new RequestParams();
//		localRequestParams.put("jsonString", str);
//		return localRequestParams;
//	}

	/**
	 * 传一般对象带用户名，带分页
	 * 
	 * @param <T>
	 * @param pcd
	 * @return
	 */
//	public static <T> RequestParams getProductList(T t, MemberDto md ,PdaPagination pp) {
//		// ProductCategoryDto pcd = new ProductCategoryDto();
//		PdaRequest<T> localPdaRequest = new PdaRequest<T>();
//		localPdaRequest.setData(t);
//		if (null != md) {
//			
//			localPdaRequest.setUuId(md.getId());
//		}
//		localPdaRequest.setPagination(pp);
//		String str = new Gson().toJson(localPdaRequest);
//		Log.i("参数信息：", str);
//		RequestParams localRequestParams = new RequestParams();
//		localRequestParams.put("jsonString", str);
//		return localRequestParams;
//	}
	
	/**
	 * 传一般对象带用户名，带分页
	 * 
	 * @param <T>
	 * @param pcd
	 * @return
	 */
	public static <T> RequestParams getParamsList(T t, MemberDto md ,PdaPagination pp) {
		// ProductCategoryDto pcd = new ProductCategoryDto();
		PdaRequest<T> localPdaRequest = new PdaRequest<T>();
		localPdaRequest.setData(t);
		if (null != md) {
			
			localPdaRequest.setUuId(md.getId());
		}
		if (null != pp) {
			
			localPdaRequest.setPagination(pp);
		}
		String str = new Gson().toJson(localPdaRequest);
		Log.i("参数信息：", str);
		RequestParams localRequestParams = new RequestParams();
		localRequestParams.put("jsonString", str);
		return localRequestParams;
	}
	
	/**
	 * 通用，传String，带Uuid
	 * @param string
	 * @param md
	 * @return
	 */
//	public static RequestParams getString(String string, MemberDto md) {
//		// ProductCategoryDto pcd = new ProductCategoryDto();
//		PdaRequest<String> localPdaRequest = new PdaRequest<String>();
//		localPdaRequest.setData(string);
//		localPdaRequest.setUuId(md.getId());
//		PdaPagination pp = new PdaPagination();
//		pp.setPageNumber(1);
//		pp.setAmount(10);
//		localPdaRequest.setPagination(pp);
//		String str = new Gson().toJson(localPdaRequest);
//		Log.i("参数信息：", str);
//		RequestParams localRequestParams = new RequestParams();
//		localRequestParams.put("jsonString", str);
//		return localRequestParams;
//	}
	/**
	 * 通用，传String，带Uuid
	 * @param string
	 * @param md
	 * @return
	 */
//	public static RequestParams getString1(String string, MemberDto md) {
//		// ProductCategoryDto pcd = new ProductCategoryDto();
//		PdaRequest<String> localPdaRequest = new PdaRequest<String>();
//		localPdaRequest.setData(string);
//		localPdaRequest.setUuId(md.getId());
////		PdaPagination pp = new PdaPagination();
////		pp.setPageNumber(1);
////		pp.setAmount(10);
////		localPdaRequest.setPagination(pp);
//		String str = new Gson().toJson(localPdaRequest);
//		Log.i("参数信息dizhi 1111111111111111：", str);
//		RequestParams localRequestParams = new RequestParams();
//		localRequestParams.put("jsonString", str);
//		return localRequestParams;
//	}
	
	/**
	 * 通用，传String，带Uuid
	 * @param string
	 * @param md
	 * @return
	 */
//	public static RequestParams getString2(String string) {
//		// ProductCategoryDto pcd = new ProductCategoryDto();
//		PdaRequest<String> localPdaRequest = new PdaRequest<String>();
//		localPdaRequest.setData(string);
////		PdaPagination pp = new PdaPagination();
////		pp.setPageNumber(1);
////		pp.setAmount(10);
////		localPdaRequest.setPagination(pp);
//		String str = new Gson().toJson(localPdaRequest);
//		Log.i("参数信息dizhi 1111111111111111：", str);
//		RequestParams localRequestParams = new RequestParams();
//		localRequestParams.put("jsonString", str);
//		return localRequestParams;
//	}
	
	
	/**
	 * 同步购物车
	 * 
	 * @param list
	 * @param md
	 * @return
	 */
	public static RequestParams getCart(List<CartItemDto> list, MemberDto md) {
		// ProductCategoryDto pcd = new ProductCategoryDto();
		PdaRequest<List<CartItemDto>> localPdaRequest = new PdaRequest<List<CartItemDto>>();
		localPdaRequest.setData(list);
		localPdaRequest.setUuId(md.getId());
		String str = new Gson().toJson(localPdaRequest);
		Log.i("同步购物车参数信息：", str);
		RequestParams localRequestParams = new RequestParams();
		localRequestParams.put("jsonString", str);
		return localRequestParams;
	}

	/**
	 * 参数为CartItemDto的RequestParams
	 * 
	 * @param cartItemDto
	 * @param md
	 * @return
	 */
//	public static RequestParams getCartItemDtoParams(CartItemDto cartItemDto, MemberDto md) {
//		PdaRequest<CartItemDto> localPdaRequest = new PdaRequest<CartItemDto>();
//		localPdaRequest.setData(cartItemDto);
//		localPdaRequest.setUuId(md.getId());
//		String str = new Gson().toJson(localPdaRequest);
//		Log.i("参数信息：", str);
//		RequestParams localRequestParams = new RequestParams();
//		localRequestParams.put("jsonString", str);
//		return localRequestParams;
//	}
}

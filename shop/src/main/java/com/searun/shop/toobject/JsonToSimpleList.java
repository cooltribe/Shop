package com.searun.shop.toobject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.protocol.ResponseDate;
import org.json.JSONException;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.searun.shop.data.CartItemDto;
import com.searun.shop.data.MemberDto;
import com.searun.shop.data.ProductDto;
import com.searun.shop.data.ProductImage;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.util.GsonUtils;

public class JsonToSimpleList {
	/**
	 * 解析json数据，得到具体的实体类
	 * @param <T>
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public static  <T> PdaResponse<List<T>> parserLoginJson(String json)
			throws JSONException {
		PdaResponse<List<T>> response = new PdaResponse<List<T>>();
		try {
			Type type = new TypeToken<PdaResponse<List<T>>>() {
			}.getType();
			response = GsonUtils.createCommonBuilder().create()
					.fromJson(json, type);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return response;
		}

		return response;
	}

}

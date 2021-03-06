package com.searun.shop.toobject;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONException;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.searun.shop.data.DeliveryTypeDto;
import com.searun.shop.data.MessageDto;
import com.searun.shop.data.OrderDto;
import com.searun.shop.data.PaymentConfigDto;
import com.searun.shop.data.ProductDto;
import com.searun.shop.data.ReviewDto;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.util.GsonUtils;

public class JsonToReviewList {
	/**
	 * 解析json数据，得到具体的实体类
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public static  PdaResponse<List<ReviewDto>> parserLoginJson(String json)
			throws JSONException {
		PdaResponse<List<ReviewDto>> response = new PdaResponse<List<ReviewDto>>();
		try {
			Type type = new TypeToken<PdaResponse<List<ReviewDto>>>() {
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

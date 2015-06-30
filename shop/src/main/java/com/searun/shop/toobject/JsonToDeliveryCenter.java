package com.searun.shop.toobject;

import java.lang.reflect.Type;

import org.json.JSONException;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.searun.shop.data.DeliveryCenterDto;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.util.GsonUtils;

public class JsonToDeliveryCenter {
	/**
	 * 解析json数据，得到具体的实体类
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public static  PdaResponse<DeliveryCenterDto> parserLoginJson(String json)
			throws JSONException {
		PdaResponse<DeliveryCenterDto> response = new PdaResponse<DeliveryCenterDto>();
		try {
			Type type = new TypeToken<PdaResponse<DeliveryCenterDto>>() {
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

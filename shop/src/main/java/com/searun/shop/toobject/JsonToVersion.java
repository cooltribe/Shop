package com.searun.shop.toobject;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.searun.shop.data.PdaVersionInfoDto;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.util.GsonUtils;

import org.json.JSONException;

import java.lang.reflect.Type;

public class JsonToVersion {
	/**
	 * 解析json数据，得到具体的实体类
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public static  PdaResponse<PdaVersionInfoDto> parserLoginJson(String json)
			throws JSONException {
		PdaResponse<PdaVersionInfoDto> response = new PdaResponse<PdaVersionInfoDto>();
		try {
			Type type = new TypeToken<PdaResponse<PdaVersionInfoDto>>() {
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

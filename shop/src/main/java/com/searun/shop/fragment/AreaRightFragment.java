package com.searun.shop.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.searun.shop.R;
import com.searun.shop.adapter.AreaDtoAdapter;
import com.searun.shop.application.ApplicationData;
import com.searun.shop.data.AreaDto;
import com.searun.shop.entity.NetWork;
import com.searun.shop.entity.PdaResponse;
import com.searun.shop.toobject.JsonToAreaList;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.util.StoreObject;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AreaRightFragment extends Fragment implements OnItemClickListener{
	private static AreaRightFragment instance;
	private ListView listView;
	private AreaDto areaDto;
	private SharedPreferences sp;

	public AreaRightFragment() {
		super();
	}
	@SuppressLint("ValidFragment")
	public AreaRightFragment(AreaDto areaDto) {
		super();
		this.areaDto = areaDto;
	}
	public static AreaRightFragment getInstance(AreaDto areaDto){
		if (null == instance) {
			instance = new AreaRightFragment(areaDto);
			instance.areaDto = areaDto;
		}
		return instance;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = null;
		try {
			view = inflater.inflate(R.layout.singel_listview, container, false);
			sp = getActivity().getSharedPreferences(ApplicationData.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE);
			listView = (ListView) view.findViewById(R.id.listview);
			areaList = new ArrayList<AreaDto>();
			getArea();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return view;
	}
	private void analyze(JSONObject jsonObject) throws JSONException{
		try {
			PdaResponse<List<AreaDto>> response = JsonToAreaList.parserLoginJson(jsonObject.toString());
			areaList = response.getData();
			if (null != areaList) {
				
				listView.setAdapter(new AreaDtoAdapter(areaList, getActivity()));
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// TODO Auto-generated method stub
						System.err.println("xxx" + position );
						sp.edit().putString("area", StoreObject.saveOAuth(areaList.get(position))).commit();
						getActivity().finish();
					}
				});
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		areaList.clear();
	}
	private void getArea(){
		try {
			HttpUtil.post("getAreas.action", NetWork.getParamsList(areaDto, null, null), new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					// TODO Auto-generated method stub
					super.onSuccess(statusCode, headers, response);
					Log.i("地区信息right:", response.toString());
						try {
							analyze(response);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
				@Override
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
					// TODO Auto-generated method stub
					super.onFailure(statusCode, headers, throwable, errorResponse);
				}
				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					super.onFinish();
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	ItemClick itemClick;
	private List<AreaDto> areaList;
//	public interface ItemClick{
//		public void intemClickListener(List<AreaDto> areaList);
////	}
//	@Override
//	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
//		super.onAttach(activity);
//		try {
//			itemClick = (ItemClick) activity;
//		} catch (ClassCastException e) {
//			// TODO Auto-generated catch block
//			e.toString()
////			throw  new ClassCastException(activity.toString() + "must be implement ItemClick");
//		}
//	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
			
		System.err.println("xxx" + position );
			sp.edit().putString("area", StoreObject.saveOAuth(areaList.get(position))).commit();
			getActivity().finish();
			
//			Intent intent = new Intent();
//			intent.putExtra("id", areaList.get(position).getId());
//			intent.putExtra("province", areaList.get(position).getName());
//			getActivity().setResult(Activity.RESULT_OK);
//			getActivity().finish();
	}
}

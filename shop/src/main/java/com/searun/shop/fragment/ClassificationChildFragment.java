package com.searun.shop.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.searun.shop.R;
import com.searun.shop.activity.ProductListActivity;
import com.searun.shop.activity.ProductListActivity1;
import com.searun.shop.adapter.ClassificationRightAdapter;
import com.searun.shop.data.ProductCategoryDto;
import com.searun.shop.view.MyListView;

@SuppressLint({ "ValidFragment" })
public class ClassificationChildFragment extends Fragment implements OnItemClickListener{
	private ProductCategoryDto pcd;
	private ListView lv;

	public ClassificationChildFragment(ProductCategoryDto pcd) {
		this.pcd = pcd;
	}

	public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(R.layout.child_fragment_classification, paramViewGroup, false);
		System.out.println("分类" + pcd.getName());
		findView(localView);
		
		return localView;
	}

	private void findView(View view) {
		lv = (ListView) view.findViewById(R.id.right_lv);
		lv.setAdapter(new ClassificationRightAdapter(pcd.getChildren(), getActivity()));
		lv.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		if (pcd.getChildren().get(position).getChildren().size() == 0) {
			Intent intent = new Intent(getActivity(), ProductListActivity.class);
			intent.putExtra("id", pcd.getChildren().get(position).getId());
			intent.putExtra("name", pcd.getChildren().get(position).getName());
			getActivity().startActivity(intent);
		}
//		Toast.makeText(getActivity(), pcd.getChildren().get(position).getName(), Toast.LENGTH_LONG).show();
	}
}

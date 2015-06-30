package com.searun.shop.activity;

import com.searun.shop.R;
import com.searun.shop.data.ArticleDto;
import com.searun.shop.view.CustomProgressDialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class ArticleActivity extends Activity implements OnClickListener {

	private Button back;
	private TextView title;
	private WebView webView;
	ArticleDto articleDto;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		    setContentView(R.layout.activity_agreement);
		    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		    findView();
	}
	 private void findView()
	  {
		articleDto = (ArticleDto) getIntent().getSerializableExtra("article");
	    back = ((Button)findViewById(R.id.back));
	    back.setOnClickListener(this);
	    title = ((TextView)findViewById(R.id.title));
	    title.setText(articleDto.getTitle());
	    findViewById(R.id.other).setVisibility(View.GONE);
	    webView = ((WebView)findViewById(R.id.agreement_webview));
	    webView.loadData(articleDto.getContent(), "text/html;charset=UTF-8", null);
	  }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
	}
}

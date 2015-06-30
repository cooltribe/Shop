package com.searun.shop.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.searun.shop.R;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.view.CustomProgressDialog;


import org.apache.http.Header;
import org.json.JSONObject;

public class AgreementActivity extends Activity
  implements View.OnClickListener
{
  private Button back;
  private CustomProgressDialog pro;
  private TextView title;
  private WebView webView;

  private void findView()
  {
	pro = CustomProgressDialog.createDialog(this);
	pro.setMessage("加载中，请稍后").show();;
	
    back = ((Button)findViewById(R.id.back));
    back.setOnClickListener(this);
    title = ((TextView)findViewById(R.id.title));
    title.setText("注册协议");
    webView = ((WebView)findViewById(R.id.agreement_webview));
  }

  private void getAgreement()
  {
    try {
		HttpUtil.post("getAgreement.action", null, new JsonHttpResponseHandler()
		{
		  public void onSuccess(int paramAnonymousInt, Header[] paramAnonymousArrayOfHeader, JSONObject paramAnonymousJSONObject)
		  {
		    super.onSuccess(paramAnonymousInt, paramAnonymousArrayOfHeader, paramAnonymousJSONObject);
		    Log.i("TAG", paramAnonymousJSONObject.toString());
		    AgreementActivity.this.webView.loadData(paramAnonymousJSONObject.optString("data"), "text/html;charset=UTF-8", null);
		  }
		  @Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				if (pro.isShowing()) {
					pro.cancel();
				}
			}
		});
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    case R.id.back:
    	finish();
    	break;
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    setContentView(R.layout.activity_agreement);
    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
    findView();
    getAgreement();
  }
}


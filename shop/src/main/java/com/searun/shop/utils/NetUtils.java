package com.searun.shop.utils;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.searun.shop.data.ProductDto;
import com.searun.shop.entity.PdaPagination;
import com.searun.shop.entity.PdaRequest;
import com.searun.shop.util.HttpUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 跟网络相关的工具类
 */
public class NetUtils {
    private static boolean value = false;
    private NetUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean checkURL(String url) {

        try {
//            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
//            int code = conn.getResponseCode();
//            System.out.println(">>>>>>>>>>>>>>>> " + code + " <<<<<<<<<<<<<<<<<<");
//            if (code != 200) {
//                value = false;
//            } else {
//                value = true;
//            }



            HttpPost post = new HttpPost(url + "searchProducts.action");
            HttpClient client = HttpUtil.getHttpClient();
            List<NameValuePair> list = new ArrayList<>();
            ProductDto pd = new ProductDto();
            pd.setMetaKeywords("猪");
            PdaPagination pg = new PdaPagination();
            pg.setAmount(10);
            pg.setPageNumber(1);
            PdaRequest<ProductDto> localPdaRequest = new PdaRequest<ProductDto>();
            localPdaRequest.setData(pd);
            localPdaRequest.setPagination(pg);
            String str = new Gson().toJson(localPdaRequest);
            list.add(new BasicNameValuePair("jsonString",str));
            post.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
            HttpResponse response = client.execute(post);
            int code = response.getStatusLine().getStatusCode();
            System.out.println(">>>>>>>>>>>>>>>> " + code + " <<<<<<<<<<<<<<<<<<");
            if (code == HttpStatus.SC_OK){
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
//                PdaResponse<List<ProductDto>> pdaResponse = JsonToProductList.parserLoginJson(response.toString());
                JSONObject object = new JSONObject(result);
                value = object.optBoolean("success",false);
                Log.d("结果结果结果1",value + "---" + result);
            }
        } catch (Exception e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.d("结果结果结果2",value + "------------------" );
        return value;
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity) {

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null)
            return false;
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

}


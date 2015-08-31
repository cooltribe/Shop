package com.searun.shop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.searun.shop.service.NetIsConnectedService;
import com.searun.shop.util.HttpUtil;
import com.searun.shop.utils.NetUtils;

public class LoadingActivity extends Activity {

    private Handler handle = new Handler() {
        public void handleMessage(android.os.Message msg) {
            try {
                switch (msg.what) {
                    case 1:
                        HttpUtil.setBaseUrl(HttpUtil.BASE_URL);

                        break;
                    case 2:
                        HttpUtil.setBaseUrl(HttpUtil.PRE_URL);
                        break;
                }
                startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                finish();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    };

    private void startService() {
        Intent intent = new Intent(LoadingActivity.this, NetIsConnectedService.class);
        startService(intent);
    }

    private void sendBroadcast() {
        Intent intent = new Intent("com.searun.broadcastreceivertest.MY_BROAD");
        intent.putExtra("name", "hello receiver");
        sendBroadcast();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//        startService();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (NetUtils.checkURL(HttpUtil.BASE_URL + "/apps/")) {
                            handle.sendEmptyMessage(1);
                        } else {
                            handle.sendEmptyMessage(2);
                        }
                    }
                }).start();
            }
        }, 3000);
    }
}

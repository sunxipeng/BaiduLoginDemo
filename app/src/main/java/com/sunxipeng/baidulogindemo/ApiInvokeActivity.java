/**
 * Copyright (c) 2011 Baidu.com, Inc. All Rights Reserved
 */
package com.sunxipeng.baidulogindemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.baidu.api.AsyncBaiduRunner;
import com.baidu.api.AsyncBaiduRunner.RequestListener;
import com.baidu.api.Baidu;
import com.baidu.api.BaiduException;
import com.baidu.api.Util;

import java.io.IOException;


/**
 * 
 * @author chenhetong(chenhetong@baidu.com)
 * 
 */
public class ApiInvokeActivity extends Activity {

    private Button apiButton;

    private EditText apiEditText;

    private Button apiAsyncButton;

    private EditText apiAsyncEditText;

    private Button logOutButton;

    private Baidu baidu = null;

    private Handler mHandler = null;

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_main);
        apiButton = (Button) findViewById(R.id.apiButton);
        apiEditText = (EditText) findViewById(R.id.apiTextView);
        apiAsyncButton = (Button) findViewById(R.id.apiAsyncButton);
        apiAsyncEditText = (EditText) findViewById(R.id.apiAsyncTextView);
        logOutButton = (Button) findViewById(R.id.logOutButton);
        mHandler = new Handler();
        Intent intent = getIntent();
        baidu = intent.getParcelableExtra("baidu");
        if (baidu != null) {
            baidu.init(this);
        }
        final String url = Baidu.LoggedInUser_URL;
        apiButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View paramView) {
                try {

                    String json = baidu.request(url, null, "GET");
                    if (json != null) {
                        apiEditText.setText(json);
                    }
                } catch (IOException e) {
                    Util.logd("api exception", e.toString());
                } catch (BaiduException e) {
                    Util.logd("baidu exception ", e.toString());
                }
            }
        });
        apiAsyncButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                AsyncBaiduRunner runner = new AsyncBaiduRunner(baidu);
                runner.request(url, null, "POST", new DefaultRequstListener());

            }
        });
        logOutButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View paramView) {
               baidu.clearAccessToken();
               Intent intent = new Intent(ApiInvokeActivity.this,MainActivity.class);
               startActivity(intent);
            }
        });
    }

    public class DefaultRequstListener implements RequestListener {

        /* (non-Javadoc)
         * @see com.baidu.android.RequestListener#onBaiduException(com.baidu.android.BaiduException)
         */
        @Override
        public void onBaiduException(BaiduException arg0) {

        }

        /* (non-Javadoc)
         * @see com.baidu.android.RequestListener#onComplete(java.lang.String)
         */
        @Override
        public void onComplete(final String value) {
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    apiAsyncEditText.setText(value);
                }
            });

        }

        /* (non-Javadoc)
         * @see com.baidu.android.RequestListener#onIOException(java.io.IOException)
         */
        @Override
        public void onIOException(IOException arg0) {

        }

    }
}

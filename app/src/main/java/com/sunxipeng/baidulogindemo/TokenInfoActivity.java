/**
 * Copyright (c) 2011 Baidu.com, Inc. All Rights Reserved
 */
package com.sunxipeng.baidulogindemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.baidu.api.AccessTokenManager;
import com.baidu.api.Baidu;

/**
 * 
 * @author chenhetong(chenhetong@baidu.com)
 * 
 */
public class TokenInfoActivity extends Activity {

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    private EditText accessTokenText;

    private EditText userText;

    private Baidu baidu = null;

    private Button toApiButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.token_main);
        Intent intent = getIntent();
        baidu = intent.getParcelableExtra("baidu");
        //初始化当前的环境
        if (baidu == null) {
            return;
        }
        baidu.init(this);
        AccessTokenManager atm = baidu.getAccessTokenManager();
        String accessToken = atm.getAccessToken();
        accessTokenText = (EditText) findViewById(R.id.accessText);
        toApiButton = (Button) findViewById(R.id.toApiButton);
        if (accessToken != null) {
            accessTokenText.setText(accessToken);
        }
        toApiButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showApiInfo();
            }
        });

    }

    private void showApiInfo() {
        Intent intent = new Intent(TokenInfoActivity.this, ApiInvokeActivity.class);
        intent.putExtra("baidu", baidu);
        startActivity(intent);
    }
}

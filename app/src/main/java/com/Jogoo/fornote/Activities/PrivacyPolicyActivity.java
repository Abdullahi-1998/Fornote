package com.Jogoo.fornote.Activities;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.Jogoo.fornote.R;

public class PrivacyPolicyActivity extends AppCompatActivity {
    private  String privacyFile = "privacypolicy.html";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        WebView webView = findViewById(R.id.webPrivacy);
        Toolbar toolbar = findViewById(R.id.toolbar_privacy);
        toolbar.setTitle("Privacy Policy");
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        webView.loadUrl("file:///android_asset/" + privacyFile);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
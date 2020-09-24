package com.Jogoo.fornote.Activities;


import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.Jogoo.fornote.R;

public class TermConditions extends AppCompatActivity {
    private String termsFile = "terms.html";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_conditions);
        Toolbar toolbar = findViewById(R.id.termsToolbar);
        WebView webView = findViewById(R.id.webViewTerms);

        toolbar.setTitle("Terms & Conditions");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        webView.loadUrl("file:///android_asset/" + termsFile);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
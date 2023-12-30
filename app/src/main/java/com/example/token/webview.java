package com.example.token;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class webview extends AppCompatActivity {
DatabaseReference db= FirebaseDatabase.getInstance().getReference("uploadPDF");
//String fname=getIntent().getStringExtra("key1");
    private static final String FILE_URL = "https://firebasestorage.googleapis.com/v0/b/token-f2543.appspot.com/o/24-03-2023%20%20%20%20%20DS?alt=media&token=c17a39a3-04f9-4828-b47b-9743f7613a11";



    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        mWebView = findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(webview.this, "Error loading document", Toast.LENGTH_SHORT).show();
            }
        });

        String html = "<html><body><iframe src=\"https://docs.google.com/viewer?url=" + FILE_URL + "&embedded=true\" width=\"100%\" height=\"100" +
                "" +
                "" +
                "%\" style=\"border: none;\"></iframe></body></html>";
        mWebView.loadDataWithBaseURL("https://docs.google.com/", html, "text/html", "UTF-8", null);
    }
}


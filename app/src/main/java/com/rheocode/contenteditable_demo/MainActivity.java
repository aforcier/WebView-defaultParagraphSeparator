package com.rheocode.contenteditable_demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.webview);

        if (mWebView != null) {
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            String htmlFile = getHtmlFromFile(this, "contenteditable.html");
            mWebView.loadDataWithBaseURL("file:///android_asset/", htmlFile, "text/html", "utf-8", "");

            enableWebDebugging(true);
        }
    }

    @SuppressLint("NewApi")
    private void enableWebDebugging(boolean enable) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.i("TAG", "Enabling web debugging");
            WebView.setWebContentsDebuggingEnabled(enable);
        }
    }

    public static String getHtmlFromFile(Activity activity, String filename) {
        try {
            AssetManager assetManager = activity.getAssets();
            InputStream in = assetManager.open(filename);
            return getStringFromInputStream(in);
        } catch (IOException e) {
            Log.e("TAG", e.getMessage());
            return null;
        }
    }

    public static String getStringFromInputStream(InputStream inputStream) throws IOException {
        InputStreamReader is = new InputStreamReader(inputStream);
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(is);
        String read = br.readLine();
        while (read != null) {
            sb.append(read);
            sb.append('\n');
            read = br.readLine();
        }
        return sb.toString();
    }
}

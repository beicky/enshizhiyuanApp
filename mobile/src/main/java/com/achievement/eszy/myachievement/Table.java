package com.achievement.eszy.myachievement;
/**
 * 尹耀强
 * 787760669@qq.com
 * 2018.5.9
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.jaeger.library.StatusBarUtil;
public class Table extends AppCompatActivity {
    private WebView webview;
    private WebSettings mWebSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(Table.this);
        setContentView(R.layout.activity_table);
        webview = (WebView) findViewById(R.id.webView2);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setSupportZoom(true);
        webview.setVerticalScrollbarOverlay(true);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        WebSettings webSettings = webview.getSettings();
        webview.getSettings().setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        final String a = getIntent().getStringExtra("data");
        final  String b= getIntent().getStringExtra("data2");
        System.out.println("============================================="+b);
        final  String c= getIntent().getStringExtra("data3");
        System.out.println(a);
        mWebSettings = webview.getSettings();
        webview.loadUrl("file:///android_asset/webtable.html");
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(Table.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }

        });
        //设置WebViewClient类
        webview.setWebViewClient(new WebViewClient() {
            //设置加载前的函数

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
                System.out.println("加载结束");
                webview.loadUrl("javascript:showtable('"+a+"','"+c+"','"+b+"')");
            }
        });
    }
}

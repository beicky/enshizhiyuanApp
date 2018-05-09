package com.achievement.eszy.myachievement;
/**
 * 尹耀强
 * 787760669@qq.com
 * 2018.5.9
 */
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.jaeger.library.StatusBarUtil;
public class MainActivity extends AppCompatActivity {
    private WebView mWebview;
    private WebSettings mWebSettings;
    private TextView beginLoading,endLoading,loading,mtitle;
    private Button but;
    private  WebView webview;
    private  String[] bb = null;
    private  ProgressDialog waitingDialog;
    private int con = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        StatusBarUtil.setTransparent(MainActivity.this);
        setContentView(R.layout.activity_main);
        webview = (WebView) findViewById(R.id.webView1);mWebview = (WebView) findViewById(R.id.webView1);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setSupportZoom(true);
// 设置出现缩放工具
        webview.getSettings().setBuiltInZoomControls(true);
//扩大比例的缩放
        webview.getSettings().setUseWideViewPort(true);
//自适应屏幕
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setLoadWithOverviewMode(true);
        beginLoading = (TextView) findViewById(R.id.text_beginLoading);
        endLoading = (TextView) findViewById(R.id.text_endLoading);
        loading = (TextView) findViewById(R.id.text_Loading);
        mtitle = (TextView) findViewById(R.id.title);
        mWebSettings = mWebview.getSettings();
        mWebview.loadUrl("http://221.234.72.4/jiaowu_eszy/");
        but = (Button)findViewById(R.id.button1);
        but.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "正在获取，请稍后！", Toast.LENGTH_SHORT).show();
                CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(webview.getContext());
                CookieManager cookieManager = CookieManager.getInstance();
                final String CookieStr = cookieManager.getCookie("http://221.234.72.4/jiaowu_eszy/");
                cookieManager.removeAllCookie();
                cookieSyncManager.sync();
               waitingDialog=
                        new ProgressDialog(MainActivity.this);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SendPost fa = new SendPost();
                        try {
                            System.out.println(CookieStr);
                            String[] aa = CookieStr.split(";");
                            bb = aa[0].split("=");
                            System.out.println(bb[1]);
                            String[] da = fa.fapostq(bb[1]);
                            waitingDialog.dismiss();
                            Intent intent =new Intent(MainActivity.this, Table.class);
                            intent.putExtra("data",da[1]);
                            intent.putExtra("data2",da[0]);
                            intent.putExtra("data3",da[2]);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                waitingDialog.setTitle("请稍后！");
                waitingDialog.setMessage("正在获取数据");
                waitingDialog.setIndeterminate(true);
                waitingDialog.setCancelable(false);
                waitingDialog.show();
                //user.setCookie_session(CookieStr);
                webview.setWebViewClient(new WebViewClient()
                {
                        {

                        }
                });
            }
        });
        //设置不用系统浏览器打开,直接显示在当前Webview
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //设置WebChromeClient类
        mWebview.setWebChromeClient(new WebChromeClient() {
            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                System.out.println("标题在这里");
                if ("Error".equals(title)){

                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Error")
                            .setMessage("服务器出现错误，和我没有任何关系，请等待服务器恢复！")
                            .setPositiveButton("确定", null)
                            .show();
                }
                mtitle.setText("非官方软件，仅供学习交流");
            }
            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    String progress = newProgress + "%";
                    loading.setText(progress);
                } else if (newProgress == 100) {
                    String progress = newProgress + "%";
                    loading.setText(progress);
                }
            }
        });
        //设置WebViewClient类
        mWebview.setWebViewClient(new WebViewClient() {
            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                System.out.println("开始加载了");
                beginLoading.setText("by.尹耀强 787760669@qq.com");
            }
            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
                if (con==0){
                    con++;
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("注意")
                            .setMessage("请在下方网页登陆你的账号，等待弹出‘不能创建对象’后，点击获取按钮")
                            .setPositiveButton("我知道了", null)
                            .show();
                    endLoading.setText("请在下方网页登陆你的账号，等待弹出‘不能创建对象’后，点击获取按钮！");

                }

            }
        });
    }
    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //销毁Webview
    @Override
    protected void onDestroy() {
        if (mWebview != null) {
            mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebview.clearHistory();
            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        super.onDestroy();
    }
}

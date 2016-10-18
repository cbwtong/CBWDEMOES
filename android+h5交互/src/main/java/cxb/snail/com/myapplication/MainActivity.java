package cxb.snail.com.myapplication;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public
class
MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private TextView titleview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.webView);
        titleview = (TextView) findViewById(R.id.jiaohu_title);
        initWeb();
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    void initWeb() {
        //设置编码
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        //支持JS
        mWebView.getSettings().setJavaScriptEnabled(true);
        //自定义处理器 重写方法,解决自动加载系统内置浏览器的问题
        mWebView.setWebViewClient(new WebViewClient());

        //缓存问题 有时没网情况下希望可以通过缓存文件打开
        //缓存模式
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //设置可以缓存
        mWebView.getSettings().setAppCacheEnabled(true);
        //设置缓存路径
        String cachePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/gefdemoweb";
        Log.e(null, cachePath);
        mWebView.getSettings().setAppCachePath(cachePath);

        //自定义监听
        mWebView.setWebChromeClient(new WebChromeClient() {
            //设置页面加载进度progressbar
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //get the newProgress and refresh progress bar
            }

            //获取当前webview正在加载的页面的title
            @Override
            public void onReceivedTitle(WebView view, String title) {
                titleview.setText(title);
            }
        });

        //载入js
        mWebView.loadUrl("file:///android_asset/test.html");
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Android调用Js方法，其中参数 'Android OK !!!' 可传入变量 效果如下：
                // mWebView.loadUrl("javascript:callH5('"+str+"')");
                mWebView.loadUrl("javascript:callH5('Android OK !!!')");
            }
        });

        //设置本地调用对象及其接口
        //第一个参数为实例化自定义的接口对象 第二个参数为提供给JS端调用使用的对象名
        mWebView.addJavascriptInterface(this, "myObj");
    }

    @JavascriptInterface
    public void callAndroid(String str) {
        //TODO js预留的接口实现 在此做一些html做不了的对android的操作
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
    }

    /**
     * 按返回键返回上一个加载的网页 避免像原生app一样是返回上一个activity
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()
                && event.getRepeatCount() == 0) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /* //websetting的其它设置
        setJavaScriptEnabled(true);  //支持js
        setPluginsEnabled(true);  //支持插件
        setUseWideViewPort(false);  //将图片调整到适合webview的大小
        setSupportZoom(true);  //支持缩放
        setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        supportMultipleWindows();  //多窗口
        setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存
        setAllowFileAccess(true);  //设置可以访问文件
        setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        webSettings.setBuiltInZoomControls(true); //设置支持缩放
        setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        setLoadsImagesAutomatically(true);  //支持自动加载图片*/

}

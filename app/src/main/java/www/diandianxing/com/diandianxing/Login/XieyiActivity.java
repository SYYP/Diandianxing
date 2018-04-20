package www.diandianxing.com.diandianxing.Login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import www.diandianxing.com.diandianxing.R;
import www.diandianxing.com.diandianxing.base.BaseActivity;
import www.diandianxing.com.diandianxing.utils.MyContants;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class XieyiActivity extends BaseActivity {
    private ImageView iv_callback;
    private TextView zhong;
    private TextView you;
    private WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContants.windows(this);
        setContentView(R.layout.activity_xieyi);
        initView();
        initWeb();

    }

    private void initView() {
        iv_callback = (ImageView) findViewById(R.id.iv_callback);
        zhong = (TextView) findViewById(R.id.zhong);
        you = (TextView) findViewById(R.id.you);
        webView= (WebView) findViewById(R.id.webView);
        iv_callback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
         zhong.setText("用户协议");
    }
    private void initWeb() {
        webView.loadUrl("http://47.93.45.38/server/html5/admin_agreement.html");

        WebSettings setTtings= webView.getSettings();
//设置可以加载JavaScript的代码
        setTtings.setJavaScriptEnabled(true);
//优先加载缓存
        setTtings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//不加载缓存
        setTtings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//是否支持缩放，true是支持 false是不支持
        setTtings.setSupportZoom(true);

//这个方法使用后，网页就会在自己浏览器中显示出来
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
//super.onProgressChanged(view, newProgress);
                if(newProgress==100){
//数据加载完毕

//这里面我们可以将进度条或者对话框dismiss掉
                }else{
//数据正在加载
//这里面我们将进度条或者对话框show出来

                }
            }


        });


    }
}

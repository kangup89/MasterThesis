package alpha.dvpis.org.hmt.unused;

/**
 * Created by kangup on 2016-11-07.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import alpha.dvpis.org.hmt.R;

public class VideoChat extends AppCompatActivity //extends CordovaActivity
{

    WebView webview;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //super.init();
        setContentView(R.layout.activity_videochat);

        webview = (WebView) findViewById(R.id.webView);

        // enable Cordova apps to be started in the background
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean("cdvStartInBackground", false)) {
            moveTaskToBack(true);
        }

        // Set by <content src="index.html" /> in config.xml
        //loadUrl(launchUrl);

        //super.loadUrl("file:///android_asset/www/index.html");

        webview.setWebViewClient(new WebViewClient());
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                VideoChat.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        request.grant(request.getResources());
                    }
                });
            }
        });
        WebSettings webSetting = webview.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setAllowFileAccessFromFileURLs(true);
        webSetting.setAllowUniversalAccessFromFileURLs(true);
        webSetting.setMediaPlaybackRequiresUserGesture(false);


        webview.loadUrl("file:///android_asset/www/index.html");
        /*webview.setWebViewClient(new WebViewClient() {
             @Override
             public boolean shouldOverrideUrlLoading(WebView v, String url) {
                 v.loadUrl(url);
                 return true;
             }
         });*/






        /*SystemWebViewEngine systemWebViewEngine = (SystemWebViewEngine) appView.getEngine();
        WebViewClient myWebViewClient = new MyWebViewClient(systemWebViewEngine);

        WebView webView = (WebView) systemWebViewEngine.getView();
        webView.setWebViewClient(myWebViewClient);

        super.loadUrl(launchUrl);*/



    }
}

/*class MyWebViewClient extends SystemWebViewClient {

    public MyWebViewClient(SystemWebViewEngine systemWebViewEngine) {
        super(systemWebViewEngine);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Uri uri = Uri.parse(url);

        if (uri.getScheme().equals("http") || uri.getScheme().equals("https")) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            view.getContext().startActivity(intent);
            return true;
        } else {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}*/

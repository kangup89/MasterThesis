package alpha.dvpis.org.hmt.fragment;

/**
 * Created by kangup on 2016-11-07.
 */

import android.media.AudioManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import alpha.dvpis.org.hmt.R;

public class VideoChatFragment extends Fragment
{
    WebView webview;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_videochat, container, false);

        webview = (WebView) view.findViewById(R.id.webView);

        this.getActivity().setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);

        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                getActivity().runOnUiThread(new Runnable() {
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

        webview.loadUrl("file:///android_asset/videochat/index.html");

        return view;
    }
}


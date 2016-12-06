package android.app.krap.neibo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by Android on 10/25/2016.
 */
public class Travel extends Fragment{

    WebView webview;
    ProgressBar progress_v;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.travel,null);
        webview =(WebView) v.findViewById(R.id.id_web);
        progress_v =(ProgressBar) v.findViewById(R.id.progress_v);


        progress_v.setMax(100);

        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new MyBrowser());
        webview.setWebChromeClient(new WebChromeClient());
        webview.loadUrl("http://35.162.179.154/neibobackend/public/abc");


        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //Make the bar disappear after URL is loaded, and changes string to Loading...


                progress_v.setProgress(progress);

            }
        });




       // id_web.loadDataWithBaseURL("", html, mimeType, encoding, "");

        return  v;
    }


    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            final String str_url = url;






            return true;
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            final String str_url = url;



        }


    }

}

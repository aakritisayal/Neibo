package android.app.krap.neibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

/**
 * Created by Android on 11/21/2016.
 */
public class Web_view extends Activity {
    RelativeLayout web_back;
    WebView webview;
    ProgressBar progress_view;
    String url_link;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);

        Intent it = getIntent();

        url_link=   it.getStringExtra("url_link");


        web_back = (RelativeLayout) findViewById(R.id.web_back);
        webview = (WebView) findViewById(R.id.webview);

        progress_view = (ProgressBar) findViewById(R.id.progress_view);
        progress_view.setMax(100);

        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new MyBrowser());
        webview.setWebChromeClient(new WebChromeClient());
        webview.loadUrl(url_link);


        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //Make the bar disappear after URL is loaded, and changes string to Loading...


                progress_view.setProgress(progress);

            }
        });


        web_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


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

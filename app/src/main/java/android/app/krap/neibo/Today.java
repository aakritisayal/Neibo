package android.app.krap.neibo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Android on 10/26/2016.
 */
public class Today extends Fragment {

    ImageView imageView;
    Document document;

    ImageView imageToday;
    static TextView txtName, txtMusic, txtEntr,txtTravel;
    String profile_image, username;
    static ExpandableHeightGridView gridview;
    static Dialog showDialog;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    static LinearLayout linearTopStories;
    static ArrayList<String> listTitle = new ArrayList<String>();
    static ArrayList<String> listdescription = new ArrayList<String>();
    static ArrayList<String> listdate_time = new ArrayList<String>();
    static ArrayList<String> listurl = new ArrayList<String>();

    static GetMusic getMusic;
    static GetTopNews getTopNews;
    static LinearLayout lnrEntertToday;
    static Activity activity;
    static GetEntrsults getEntrsults;

    WebView webview;
    ProgressBar progress_v;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.today, null);

        sp = getActivity().getSharedPreferences("shared_value", Context.MODE_MULTI_PROCESS);
        edt = sp.edit();

        activity = getActivity();

        showProgressDialog(getActivity());

        profile_image = sp.getString("profile_image", "");
        username = sp.getString("username", "");

        imageView = (ImageView) v.findViewById(R.id.imageView);
        imageToday = (ImageView) v.findViewById(R.id.imageToday);
        txtName = (TextView) v.findViewById(R.id.txtName);
        linearTopStories = (LinearLayout) v.findViewById(R.id.linearTopStories);
        txtMusic = (TextView) v.findViewById(R.id.txtMusic);
        gridview = (ExpandableHeightGridView) v.findViewById(R.id.gridview);
        txtEntr = (TextView) v.findViewById(R.id.txtEntr);
        lnrEntertToday = (LinearLayout) v.findViewById(R.id.lnrEntertToday);
        txtTravel =(TextView) v.findViewById(R.id.txtTravel);

        webview =(WebView) v.findViewById(R.id.id_web);
        progress_v =(ProgressBar) v.findViewById(R.id.progress_v);



        txtName.setText("Hi " + username);
        UrlImageViewHelper.setUrlDrawable(imageToday, profile_image, R.drawable.round_grey);

        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new MyBrowser());
        webview.setWebChromeClient(new WebChromeClient());
        webview.loadUrl("http://35.162.179.154/neibobackend/public/abc");


        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //Make the bar disappear after URL is loaded, and changes string to Loading...

                if(progress==20){
                    txtTravel.setVisibility(View.VISIBLE);
                }
                progress_v.setProgress(progress);

            }
        });

        boolean isViewVisible = webview.isShown();
        Log.e("visible", "" + isViewVisible);



        callNewsApi(getActivity());

        return v;
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



    public static class GetTopNews extends AsyncTask<String, String, String> {

        Activity acti;

        public GetTopNews(Activity activity) {

            acti = activity;
        }


        @Override
        protected String doInBackground(String... params) {
            String url = "http://www.bangkokpost.com/rss/data/topstories.xml";

            String response = Common_methods.getResponseGet(url);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            showDialog.dismiss();

            if (s != null) {
                XMLParser parse = new XMLParser();
                org.w3c.dom.Document doc = parse.getDomElement(s);
                NodeList nodeList = doc.getElementsByTagName("item");
                int length = nodeList.getLength();

                if (length != 0) {
                    for (int i = 0; i < 2; i++) {

                        Element terrif = (Element) nodeList.item(i);

                        String title = terrif.getElementsByTagName("title").item(0).getTextContent();


                        String description = terrif.getElementsByTagName("description").item(0).getTextContent();

                        String urllink = terrif.getElementsByTagName("link").item(0).getTextContent();

                        String pubDate = terrif.getElementsByTagName("pubDate").item(0).getTextContent();

                        //  String url = Common_methods.GetUrls(urllink);

                        LayoutInflater inflater = acti.getLayoutInflater();
                        View v = inflater.inflate(R.layout.news_detail, null);
                        TextView news_heading = (TextView) v.findViewById(R.id.news_heading);
                        TextView news_time = (TextView) v.findViewById(R.id.news_time);
                        ImageView news_icon = (ImageView) v.findViewById(R.id.news_icon);
                        TextView news_DETAIL = (TextView) v.findViewById(R.id.news_DETAIL);

                        news_heading.setText(title);
                        news_DETAIL.setText(description);
                        news_time.setText("Published On: " + pubDate);

                        linearTopStories.addView(v);

                        new Common_methods.BackTask(acti, news_icon, urllink).execute();

                    }

                }
            } else {
                Toast.makeText(acti, s, Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            showDialog.show();

//            Common_methods.showDialog.show();
        }
    }


    public static class GetMusic extends AsyncTask<String, String, String> {

        Activity acti;

        public GetMusic(Activity activity) {

            acti = activity;
        }


        @Override
        protected String doInBackground(String... params) {
            String url = "http://www.tmz.com/category/music/rss.xml";

            String response = Common_methods.getResponseGet(url);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            showDialog.dismiss();

            //Common_methods.showDialog.dismiss();

            if (s != null) {
                XMLParser parse = new XMLParser();
                org.w3c.dom.Document doc = parse.getDomElement(s);
                NodeList nodeList = doc.getElementsByTagName("item");
                int length = nodeList.getLength();

                if (length != 0) {

                    txtMusic.setText("Music");
                    txtMusic.setVisibility(View.VISIBLE);

                    listTitle.clear();
                    listdescription.clear();
                    listdate_time.clear();
                    listurl.clear();

                    for (int i = 0; i < 4; i++) {

                        Element terrif = (Element) nodeList.item(i);

                        String title = terrif.getElementsByTagName("title").item(0).getTextContent();
                        listTitle.add(title);

                        String description = terrif.getElementsByTagName("description").item(0).getTextContent();
                        listdescription.add(description);

                        String urllink = terrif.getElementsByTagName("link").item(0).getTextContent();
                        listurl.add(urllink);

                        String pubDate = terrif.getElementsByTagName("dc:date").item(0).getTextContent();

                        if(pubDate.equals("")){
                            listdate_time.add(" ");
                        }
                        else{
                            listdate_time.add(pubDate);
                        }





//                        LayoutInflater inflater = acti.getLayoutInflater();
//                        View v = inflater.inflate(R.layout.news_detail, null);
//                        TextView news_heading = (TextView) v.findViewById(R.id.news_heading);
//                        TextView news_time = (TextView) v.findViewById(R.id.news_time);
//                        ImageView news_icon = (ImageView) v.findViewById(R.id.news_icon);
//                        TextView news_DETAIL = (TextView) v.findViewById(R.id.news_DETAIL);
//
//                        news_heading.setText(title);
//                        news_DETAIL.setText(description);
//                        news_time.setText("Published On: " + pubDate);
//
//                        linearTopStories.addView(v);
//
//                        new Common_methods.BackTask(acti, news_icon, urllink).execute();

                    }

                    gridview.setExpanded(true);
                    Show_music adapter = new Show_music(acti, listTitle, listdescription, listdate_time, listurl);
                    adapter.notifyDataSetChanged();
                    gridview.setAdapter(adapter);


                }
            } else {
                Toast.makeText(acti, s, Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog.show();
        }
    }


    public static void callNewsApi(Activity activity) {


        ConnectionDetector cd = new ConnectionDetector(activity);
        try {
            Boolean isInternetPresent = cd.isConnectingToInternet(); // true or false

            if (isInternetPresent) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    getTopNews = new GetTopNews(activity);
                    getTopNews.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                    getMusic = new GetMusic(activity);
                    getMusic.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                    getEntrsults = new GetEntrsults();
                    getEntrsults.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                } else {

                    getTopNews = new GetTopNews(activity);
                    getTopNews.execute();

                    getMusic = new GetMusic(activity);
                    getMusic.execute();

                    getEntrsults = new GetEntrsults();
                    getEntrsults.execute();
                }


            } else {
                Common_methods.Alert(activity);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void showProgressDialog(Activity activity) {
        showDialog = new Dialog(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View v = inflater.inflate(R.layout.custom_loading_icon, null);

//        ImageView imgGif =(ImageView) v.findViewById(R.id.animlogo);
//        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imgGif);
//        Glide.with(ct).load(R.raw.new_logo_gifs).into(imageViewTarget);

        showDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        showDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        showDialog.setContentView(v);
        showDialog.setCancelable(true);

    }

    public static void cancelTodayApi(Activity activity) {

        if (getMusic != null) {
            getMusic.cancel(true);

        }

        if (getTopNews != null) {
            getTopNews.cancel(true);

        }

        if (getEntrsults != null) {
            getEntrsults.cancel(true);
        }

        if (showDialog != null) {
            showDialog.dismiss();
        }


    }

    public static class GetEntrsults extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = "http://bangkok.coconuts.co/rss.xml";

            String response = Common_methods.getResponseGet(url);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            showDialog.dismiss();

            //progressBar1.setVisibility(View.GONE);

            //   Common_methods.showDialog.dismiss();
            if (s != null) {

                XMLParser parse = new XMLParser();
                org.w3c.dom.Document doc = parse.getDomElement(s);
                NodeList nodeList = doc.getElementsByTagName("item");
                int length = nodeList.getLength();

                if (length != 0) {
                    txtEntr.setText("Entertaiment");
                    txtEntr.setVisibility(View.VISIBLE);
                    lnrEntertToday.removeAllViews();

                    for (int i = 0; i < 4; i++) {

                        Element terrif = (Element) nodeList.item(i);

                        String title = terrif.getElementsByTagName("title").item(0).getTextContent();
                        listTitle.add(title);

                        String description = terrif.getElementsByTagName("description").item(0).getTextContent();


                        String urllink = terrif.getElementsByTagName("link").item(0).getTextContent();


                        //      String url = Common_methods.GetUrls(urllink);


                        LayoutInflater inflater = activity.getLayoutInflater();
                        View v = inflater.inflate(R.layout.custom_entrtnmt_layout, null);
                        TextView txtTitle = (TextView) v.findViewById(R.id.txtTitle);
                        TextView txtbody = (TextView) v.findViewById(R.id.txtbody);
                        ImageView idimage = (ImageView) v.findViewById(R.id.idimage);

                        txtTitle.setText(title);
                        txtbody.setText(description);


                        lnrEntertToday.addView(v);

                        new Common_methods.BackTask(activity, idimage, urllink).execute();

                    }

//                   Search_results adapter = new Search_results(getActivity(),listTitle,listDescription,listUrl);
//                    rycCompany.setAdapter(adapter);


                }

            } else {
                Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            showDialog.show();

            //   progressBar1.setVisibility(View.VISIBLE);

//            Common_methods.showProgressDialog(acti);
//           Common_methods.showDialog.show();

        }


    }


}



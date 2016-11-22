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
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    static TextView txtName, txtMusic;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.today, null);

        sp = getActivity().getSharedPreferences("shared_value", Context.MODE_MULTI_PROCESS);
        edt = sp.edit();

        showProgressDialog(getActivity());

        profile_image = sp.getString("profile_image", "");
        username = sp.getString("username", "");

        imageView = (ImageView) v.findViewById(R.id.imageView);
        imageToday = (ImageView) v.findViewById(R.id.imageToday);
        txtName = (TextView) v.findViewById(R.id.txtName);
        linearTopStories = (LinearLayout) v.findViewById(R.id.linearTopStories);
        txtMusic = (TextView) v.findViewById(R.id.txtMusic);
        gridview = (ExpandableHeightGridView) v.findViewById(R.id.gridview);


        txtName.setText("Hi " + username);
        UrlImageViewHelper.setUrlDrawable(imageToday, profile_image, R.drawable.round_grey);

        //  new BackTask(holder, array.get(position).get("link")).execute();

        //  new BackTask("http://www.bangkokpost.com/vdo/thailand/1135173/exhibition-honours-the-king").execute();


        callNewsApi(getActivity());

        return v;
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
                    for (int i = 0; i < nodeList.getLength(); i++) {

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

                    listTitle.clear();
                    listdescription.clear();
                    listdate_time.clear();
                    listurl.clear();

                    for (int i = 0; i < nodeList.getLength(); i++) {

                        Element terrif = (Element) nodeList.item(i);

                        String title = terrif.getElementsByTagName("title").item(0).getTextContent();
                        listTitle.add(title);

                        String description = terrif.getElementsByTagName("description").item(0).getTextContent();
                        listdescription.add(description);

                        String urllink = terrif.getElementsByTagName("link").item(0).getTextContent();
                        listurl.add(urllink);

                        String pubDate = terrif.getElementsByTagName("dc:date").item(0).getTextContent();
                        listdate_time.add(pubDate);

                        gridview.setExpanded(true);
                        Show_Music adapter = new Show_Music(acti, listTitle, listdescription, listdate_time, listurl);

                        gridview.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

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

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
                    getTopNews  = new GetTopNews(activity);
                    getTopNews.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                    getMusic = new GetMusic(activity);
                    getMusic.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                }


                else{

                    getTopNews = new GetTopNews(activity);
                    getTopNews.execute();

                    getMusic = new GetMusic(activity);
                    getMusic.execute();
                }







            } else {
                Common_methods.Alert(activity);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static class Show_Music extends BaseAdapter {

        Activity activity;
        ArrayList<String> listTitle = new ArrayList<String>();
        ArrayList<String> listdescription = new ArrayList<String>();
        ArrayList<String> listdate_time = new ArrayList<String>();
        ArrayList<String> listurl = new ArrayList<String>();

        private LayoutInflater layoutInflater;


        public Show_Music(Activity act, ArrayList<String> title, ArrayList<String> description, ArrayList<String> date_time, ArrayList<String> url) {

            layoutInflater = LayoutInflater.from(act);

            this.activity = act;

            listTitle.clear();
            listdescription.clear();
            listdate_time.clear();
            listurl.clear();

            listTitle = title;
            listdescription = description;
            listdate_time = date_time;
            listurl = url;


        }

        @Override
        public int getCount() {
            return listTitle.size();
        }

        @Override
        public Object getItem(int position) {
            return listTitle.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.news_detail, null);
                holder = new ViewHolder();

                holder.news_heading = (TextView) convertView.findViewById(R.id.news_heading);
                holder.news_time = (TextView) convertView.findViewById(R.id.news_time);
                holder.news_DETAIL = (TextView) convertView.findViewById(R.id.news_DETAIL);
                holder.news_icon = (ImageView) convertView.findViewById(R.id.news_icon);


                convertView.setTag(holder);
                holder.news_heading.setId(position);


            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //Set the text here!

            String img_id = listTitle.get(position);


            holder.news_heading.setText(listTitle.get(position));
            // holder.news_DETAIL.setText(listdescription.get(position));
            holder.news_time.setText(listdate_time.get(position));


            new Common_methods.BackTask(activity, holder.news_icon, listurl.get(position)).execute();

//            holder.txtshop_address.setText("City : " + addressarr.get(position));
//            holder.id_ofr_button.setText(offers_arr.get(position));
//
//            String imageUrlarrr = imageUrlarr.get(position);
//            String img_log = shop_iconarr.get(position);


            return convertView;
        }

        class ViewHolder {

            TextView news_heading;
            TextView news_DETAIL;
            TextView news_time;

            ImageView news_icon;


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
public static void cancelTodayApi(Activity activity){

    if (getMusic != null){
        getMusic.cancel(true);

    }

    if (getTopNews != null){
        getTopNews.cancel(true);

    }

    if(showDialog!=null){
        showDialog.dismiss();
    }


}

}



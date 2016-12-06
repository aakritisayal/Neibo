package android.app.krap.neibo;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;


/**
 * Created by Android on 10/25/2016.
 */
public class Entertainment extends Fragment{

 // static   LinearLayout lnrEntertainment;
    org.jsoup.nodes.Document document;
    ImageView idimage;
    String imgurl;
    String DEBUG_TAG ="Touch Event";

    SwipeRefreshLayout swipeRefreshLayout;

   static    ArrayList<String> listTitle = new ArrayList<>();
  static    ArrayList<String> listDescription = new ArrayList<>();
  static     ArrayList<String> listUrl = new ArrayList<>();
ListView listView;

  static    LinearLayout lnrEntertainment;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
     ProgressBar progressBar1;

   static   Dialog showDialog;

    int sizedescription,sizeUrl;

    RecyclerView rycCompany;
    static Activity activity;

   static Getrsults getResults;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.entertainment, null);

        activity = getActivity();

      //  sp = getActivity().getPreferences("shared_entertainment",getActivity().MODE_PRIVATE);

        lnrEntertainment =(LinearLayout) v.findViewById(R.id.lnrEntertainment);

       // activity =getActivity();
    //    progressBar1 = (ProgressBar) v.findViewById(R.id.progressBar1);
      //  rycCompany =(RecyclerView) v.findViewById(R.id.rycCompany);

        showProgressDialog(activity);




      //  listView =(ListView) v.findViewById(R.id.listView);

      //  swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);


        // scrollnested =(NestedScrollView) v.findViewById(R.id.scrollnested);
     //   entrnmnt_recycler_view =(RecyclerView) v.findViewById(R.id.entrnmnt_recycler_view);

     //   Common_methods.showProgressDialog(getActivity());



       // swipeRefreshLayout = (PullRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);


      callApi();

//        Getresults results = new Getresults();
//        results.execute();

        return  v;
    }




    public  void callApi(){


        ConnectionDetector cd = new ConnectionDetector(getActivity());
        try {
            Boolean isInternetPresent = cd.isConnectingToInternet(); // true or false

            if (isInternetPresent) {



                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
                    getResults = new Getrsults();
                    getResults.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                }


                else{
                    getResults = new Getrsults();
                    getResults.execute();
                }





            } else {
                Common_methods.Alert(getActivity());


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static class Getrsults extends AsyncTask<String,String,String>{

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
            if(s!=null){

                XMLParser parse = new XMLParser();
                Document doc = parse.getDomElement(s);
                NodeList nodeList = doc.getElementsByTagName("item");
                int length = nodeList.getLength();

                if(length!=0){

                    lnrEntertainment.removeAllViews();

                    for (int i = 0; i < nodeList.getLength(); i++) {

                        Element terrif = (Element) nodeList.item(i);

                        String title = terrif.getElementsByTagName("title").item(0).getTextContent();
                        listTitle.add(title);

                        String description = terrif.getElementsByTagName("description").item(0).getTextContent();
                        listDescription.add(description);

                        String urllink = terrif.getElementsByTagName("link").item(0).getTextContent();
                        listUrl.add(urllink);

                  //      String url = Common_methods.GetUrls(urllink);



                        LayoutInflater inflater = activity.getLayoutInflater();
                        View v = inflater.inflate(R.layout.custom_entrtnmt_layout, null);
                        TextView txtTitle = (TextView) v.findViewById(R.id.txtTitle);
                        TextView txtbody = (TextView) v.findViewById(R.id.txtbody);
                        ImageView idimage = (ImageView) v.findViewById(R.id.idimage);

                        txtTitle.setText(title);
                        txtbody.setText(description);



                        lnrEntertainment.addView(v);

                       new Common_methods.BackTask(activity,idimage,urllink).execute();

                    }

//                   Search_results adapter = new Search_results(getActivity(),listTitle,listDescription,listUrl);
//                    rycCompany.setAdapter(adapter);




                }

            }

            else{
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

    public static void showProgressDialog(Activity activity){
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


    public static void cancelEntApi(Activity activity){

        if (getResults != null){
            getResults.cancel(true);

        }



        if(showDialog!=null){
            showDialog.dismiss();
        }


    }


}

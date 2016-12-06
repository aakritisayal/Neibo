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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by Android on 10/26/2016.
 */
public class Near_byplaces extends Fragment {

 public    static String Client_ID = "FYXPZ5NE3NHEKEA2UVQGAGXHM42ERS1MTVVCPCS14VGI1HU2";
 public    static String Client_SECRET = "0WKFY2TTRENTWNMBAQSNRZKSPYIGOHCJDI0SSG2CTQN2P1KE";
    static Dialog showDialog;
    static ExpandableHeightGridView gridviewNear;



    static  String query ="";

    SharedPreferences shared;
    SharedPreferences.Editor editor;

   static String latitude, longitude;

   static GetNearByplaces getNearByplaces;
    static  ArrayList<String> listTitle = new ArrayList<String>();
    static   ArrayList<String> listcategory = new ArrayList<String>();
    static ArrayList<String> listdistance = new ArrayList<String>();
    static ArrayList<String> listrating = new ArrayList<String>();
    static  ArrayList<String> listtips = new ArrayList<String>();
    static  ArrayList<String> listurl = new ArrayList<String>();
    static  ArrayList<String> listcategory_id = new ArrayList<String>();
static  Activity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.near_by_places, null);

        activity= getActivity();

        gridviewNear = (ExpandableHeightGridView) v.findViewById(R.id.gridviewNear);

        showProgressDialog();




        shared = getActivity().getSharedPreferences("shared_location", Context.MODE_MULTI_PROCESS);
        editor = shared.edit();

        latitude = shared.getString("latitude", "");
        longitude = shared.getString("longitude", "");


        callNearByApi(getActivity());


//        PlacePickerSdk.with(new PlacePickerSdk.Builder(getActivity())
//                .consumer(CONSUMER_KEY, CONSUMER_SECRET)
//                .imageLoader(new PlacePickerSdk.ImageLoader() {
//                    @Override
//                    public void loadImage(Context context, ImageView v, String url) {
//                        Glide.with(context)
//                                .load(url)
//                                .placeholder(R.drawable.category_none)
//                                .dontAnimate()
//                                .into(v);
//                    }
//                })
//                .build());

        return v;
    }


    public static class GetNearByplaces extends AsyncTask<String, String, String> {



        @Override
        protected String doInBackground(String... params) {

            //   String url = "https://api.foursquare.com/v2/venues/explore?client_id=" + Client_ID + "&client_secret=" + Client_SECRET + "&v=20130815%20&near=chandigarh";

            String url = "https://api.foursquare.com/v2/venues/explore?client_id=" + Client_ID + "&client_secret=" + Client_SECRET + "&v=" + Common_methods.getDate() + "&ll="+latitude+ ","+longitude+   "&query="+query+"";

            url = url.replaceAll(" ", "%20");

            String response = Common_methods.getResponseGet(url);

            return response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog.show();


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            showDialog.dismiss();

            if (s != null) {

                try {
                    JSONObject object = new JSONObject(s);

                    JSONObject meta = object.getJSONObject("meta");

                    String code = meta.getString("code");

                    if (code.equals("200")) {

                        JSONObject response = object.getJSONObject("response");
                        JSONArray groups = response.getJSONArray("groups");

                        if (groups.length() != 0) {

                            listTitle.clear();
                            listcategory.clear();
                            listdistance.clear();
                            listrating.clear();
                            listtips.clear();
                            listurl.clear();
                            listcategory_id.clear();


                            for (int i = 0; i < groups.length(); i++) {

                                JSONObject obj = groups.getJSONObject(i);

                                JSONArray items = obj.getJSONArray("items");
                                if (items.length() != 0) {
                                    for (int j = 0; j < items.length(); j++) {
                                        JSONObject objvenue = items.getJSONObject(j);

                                        JSONObject venue = objvenue.getJSONObject("venue");

                                        String strTips =objvenue.optString("tips");
                                        if(!strTips.equals("")){
                                            JSONArray tips = objvenue.getJSONArray("tips");


                                            if (tips.length() != 0) {
                                                for (int l = 0; l < 1; l++) {
                                                    JSONObject objTips = tips.getJSONObject(l);
                                                    String text = objTips.getString("text");
                                                    listtips.add(text);
                                                    String canonicalUrl = objTips.optString("canonicalUrl");
                                                    if (canonicalUrl.equals("null") || canonicalUrl.equals("null")) {
                                                        listurl.add("Not specified");
                                                    } else {
                                                        listurl.add(canonicalUrl);
                                                    }
                                                }
                                            } else {
                                                listtips.add("Not specified");
                                                listurl.add("Not specified");
                                            }
                                        }

                                        else {
                                            listtips.add("Not specified");
                                            listurl.add("Not specified");
                                        }





                                        String name = venue.getString("name");
                                        listTitle.add(name);

                                        String id = venue.getString("id");
                                        listcategory_id.add(id);

                                        String rating = venue.optString("rating");

                                        if(rating.equals("")){
                                            listrating.add("_._");
                                        }
                                        else{
                                            listrating.add(rating);
                                        }



                                        listdistance.add(" ");
                                        JSONArray categories = venue.getJSONArray("categories");
                                        if (categories.length() != 0) {


                                            for (int k = 0; k < 1; k++) {

                                                JSONObject jsonObject = categories.getJSONObject(k);
                                                String nameCategory = jsonObject.getString("name");
                                                listcategory.add(nameCategory);


                                            }

                                        } else {

                                            listTitle.add("Category not specified");
                                        }


                                    }


                                }


                            }

                            gridviewNear.setExpanded(true);
                            Show_ListResults adapter = new Show_ListResults(activity, listTitle, listcategory, listdistance, listrating, listtips, listurl, listcategory_id);

                            gridviewNear.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }

                    } else {
                        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
            }

        }
    }


    public void showProgressDialog() {
        showDialog = new Dialog(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.custom_loading_icon, null);

//        ImageView imgGif =(ImageView) v.findViewById(R.id.animlogo);
//        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imgGif);
//        Glide.with(ct).load(R.raw.new_logo_gifs).into(imageViewTarget);

        showDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        showDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        showDialog.setContentView(v);
        showDialog.setCancelable(true);

    }


    public static void callNearByApi(Activity activity) {



        ConnectionDetector cd = new ConnectionDetector(activity);
        try {
            Boolean isInternetPresent = cd.isConnectingToInternet(); // true or false

            if (isInternetPresent) {

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
                    getNearByplaces  = new GetNearByplaces();
                    getNearByplaces.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);



                }


                else{

                    getNearByplaces = new GetNearByplaces();
                    getNearByplaces.execute();

                }







            } else {
                Common_methods.Alert(activity);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    public static void cancelNear_api(){

        if (getNearByplaces != null){
            getNearByplaces.cancel(true);

        }



        if(showDialog!=null){
            showDialog.dismiss();
        }


    }



}

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

    static String Client_ID = "FYXPZ5NE3NHEKEA2UVQGAGXHM42ERS1MTVVCPCS14VGI1HU2";
    static String Client_SECRET = "0WKFY2TTRENTWNMBAQSNRZKSPYIGOHCJDI0SSG2CTQN2P1KE";
    static Dialog showDialog;
    static ExpandableHeightGridView gridviewNear;

    static ArrayList<String> listTitle = new ArrayList<>();
    static ArrayList<String> listCategory = new ArrayList<>();
    static ArrayList<String> listDescription = new ArrayList<>();
    static ArrayList<String> listUrl = new ArrayList<>();

    static String date;

    SharedPreferences shared;
    SharedPreferences.Editor editor;

   static String latitude, longitude;

   static GetNearByplaces getNearByplaces;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.near_by_places, null);
        gridviewNear = (ExpandableHeightGridView) v.findViewById(R.id.gridviewNear);

        showProgressDialog();

        Calendar ci = Calendar.getInstance();
        String AM_PM;

        date = "" + ci.get(Calendar.YEAR) + (ci.get(Calendar.MONTH) + 1) + ci.get(Calendar.DAY_OF_MONTH);


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

        Activity activi;

        public GetNearByplaces(Activity activity) {
            this.activi = activity;
        }

        @Override
        protected String doInBackground(String... params) {

            //   String url = "https://api.foursquare.com/v2/venues/explore?client_id=" + Client_ID + "&client_secret=" + Client_SECRET + "&v=20130815%20&near=chandigarh";

            String url = "https://api.foursquare.com/v2/venues/explore?client_id=" + Client_ID + "&client_secret=" + Client_SECRET + "&v=" + date + "&ll="+latitude+ ","+longitude+   "&query=near by places";

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

                            for (int i = 0; i < groups.length(); i++) {

                                JSONObject obj = groups.getJSONObject(i);

                                JSONArray items = obj.getJSONArray("items");
                                if (items.length() != 0) {
                                    for (int j = 0; j < items.length(); j++) {
                                        JSONObject objvenue = items.getJSONObject(j);

                                        JSONObject venue =objvenue.getJSONObject("venue");

                                        JSONArray tips =objvenue.getJSONArray("tips");

                                        if(tips.length()!=0){
                                            for (int l=0;l<1;l++){
                                                JSONObject objTips =tips.getJSONObject(l);
                                                String text =objTips.getString("text");
                                                listDescription.add(text);
                                                String canonicalUrl =objTips.optString("canonicalUrl");
                                                if(canonicalUrl.equals("null") || canonicalUrl.equals("null")){
                                                    listUrl.add("Not specified");
                                                }
                                                else{
                                                    listUrl.add(canonicalUrl);
                                                }
                                            }
                                        }

                                        else{
                                            listDescription.add("Not specified");
                                            listUrl.add("Not specified");
                                        }


                                        String name = venue.getString("name");
                                        listTitle.add(name);


                                        JSONArray categories = venue.getJSONArray("categories");
                                        if (categories.length() != 0) {


                                            for (int k = 0; k < 1; k++) {

                                                JSONObject jsonObject = categories.getJSONObject(k);
                                                String nameCategory = jsonObject.getString("name");
                                                listCategory.add(nameCategory);


                                            }

                                        } else {

                                            listTitle.add("Category not specified");
                                        }


                                    }


                                }



                            }

                            gridviewNear.setExpanded(true);
                            Show_NearBy adapter = new Show_NearBy(activi, listTitle,listCategory, listDescription, listUrl);

                            gridviewNear.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }

                    } else {
                        Toast.makeText(activi, s, Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(activi, s, Toast.LENGTH_SHORT).show();
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
        showDialog.setCancelable(false);

    }


    public static void callNearByApi(Activity activity) {



        ConnectionDetector cd = new ConnectionDetector(activity);
        try {
            Boolean isInternetPresent = cd.isConnectingToInternet(); // true or false

            if (isInternetPresent) {

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
                    getNearByplaces  = new GetNearByplaces(activity);
                    getNearByplaces.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);



                }


                else{

                    getNearByplaces = new GetNearByplaces(activity);
                    getNearByplaces.execute();

                }







            } else {
                Common_methods.Alert(activity);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }



        GetNearByplaces places = new GetNearByplaces(activity);
        places.execute();
    }


    public static class Show_NearBy extends BaseAdapter {

        Activity activity;
        ArrayList<String> listTitle = new ArrayList<String>();
        ArrayList<String> listdescription = new ArrayList<String>();
        ArrayList<String> listCategory = new ArrayList<String>();
        ArrayList<String> listurl = new ArrayList<String>();

        private LayoutInflater layoutInflater;


        public Show_NearBy(Activity act, ArrayList<String> title,ArrayList<String> category, ArrayList<String> description, ArrayList<String> url) {

            layoutInflater = LayoutInflater.from(act);

            this.activity = act;

            listTitle.clear();
            listdescription.clear();
            listCategory.clear();
            listurl.clear();

            listTitle = title;
            listdescription = description;
            listCategory =category;
            //  listdate_time = date_time;
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
                convertView = layoutInflater.inflate(R.layout.custom_nearby, null);
                holder = new ViewHolder();

                holder.heading = (TextView) convertView.findViewById(R.id.heading);
                holder.sub_heading = (TextView) convertView.findViewById(R.id.sub_heading);
                holder.sub_description = (TextView) convertView.findViewById(R.id.sub_description);
                holder.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);


                convertView.setTag(holder);
                holder.heading.setId(position);

                Log.e("images", "" + position);


            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //Set the text here!

            String img_id = listTitle.get(position);


            holder.heading.setText(listTitle.get(position));
             holder.sub_heading.setText(listCategory.get(position));
              holder.sub_description.setText(listdescription.get(position));


            new Common_methods.BackTask(activity, holder.img_icon, listurl.get(position)).execute();

//            holder.txtshop_address.setText("City : " + addressarr.get(position));
//            holder.id_ofr_button.setText(offers_arr.get(position));
//
//            String imageUrlarrr = imageUrlarr.get(position);
//            String img_log = shop_iconarr.get(position);


            return convertView;
        }

        class ViewHolder {

            TextView heading;
            TextView sub_heading;
            TextView sub_description;

            ImageView img_icon;


        }
    }


}

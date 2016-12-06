package android.app.krap.neibo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wefika.horizontalpicker.HorizontalPicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Android on 10/25/2016.
 */
public class Search_activity extends FragmentActivity {

    ArrayList<String> value = new ArrayList<>();
    ViewPager pager_fragment;
    ArrayList<Fragment> fragment = new ArrayList<>();
    HorizontalPicker picker;
    String response, responseCategories;
    String latitude, longitude;
    SharedPreferences shared;
    SharedPreferences.Editor editor;

    String query = "";
    ArrayList<String> listTitle = new ArrayList<String>();
    ArrayList<String> listcategory = new ArrayList<String>();
    ArrayList<String> listdistance = new ArrayList<String>();
    ArrayList<String> listrating = new ArrayList<String>();
    ArrayList<String> listtips = new ArrayList<String>();
    ArrayList<String> listurl = new ArrayList<String>();
    ArrayList<String> listcategory_id = new ArrayList<String>();


    ArrayList<String> listcategories = new ArrayList<String>();

    ListView listView;
    Get_search_results getResults;
    GetCategories getCategories;

    Dialog showDialog;
    ImageView back_img;
    LinearLayout linearsearch;
    AutoCompleteTextView edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);


        shared = getSharedPreferences("shared_location", Context.MODE_MULTI_PROCESS);
        editor = shared.edit();

        showProgressDialog(Search_activity.this);

        latitude = shared.getString("latitude", "");
        longitude = shared.getString("longitude", "");

        pager_fragment = (ViewPager) findViewById(R.id.pager_fragment);
        picker = (HorizontalPicker) findViewById(R.id.picker);
        listView = (ListView) findViewById(R.id.listView);
        back_img = (ImageView) findViewById(R.id.back_img);
        edtSearch = (AutoCompleteTextView) findViewById(R.id.edtSearch);
        linearsearch = (LinearLayout) findViewById(R.id.linearsearch);


        picker.setSelectedItem(2);


        callApi();
        callGetCategoriesApi();

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                cancelApi();
            }
        });


        picker.setOnItemSelectedListener(new HorizontalPicker.OnItemSelected() {
            @Override
            public void onItemSelected(int index) {

                //  Toast.makeText(getActivity(), String.valueOf(index), Toast.LENGTH_SHORT).show();

                if (index == 0) {
                    cancelApi();
                    query = "Top Pics";
                    callApi();

                }
                if (index == 1) {

                    query = "Nearest";
                    cancelApi();
                    callApi();

                }
                if (index == 2) {


                    cancelApi();
                    query = "Trending";
                    callApi();


                }
                if (index == 3) {

                    cancelApi();
                    query = "Food";
                    callApi();
                }
                if (index == 4) {

                    cancelApi();
                    query = "Coffee";
                    callApi();
                }

                if (index == 5) {


                    cancelApi();
                    query = "Nightlife";
                    callApi();

                }
                if (index == 6) {


                    cancelApi();
                    query = "Fun";
                    callApi();

                }
                if (index == 7) {


                    cancelApi();
                    query = "Shopping";
                    callApi();

                }

                if (index == 8) {


                    cancelApi();
                    query = "Breakfast";
                    callApi();

                }

                if (index == 9) {


                    cancelApi();
                    query = "Lunch";
                    callApi();

                }
                if (index == 10) {


                    cancelApi();
                    query = "Dinner";
                    callApi();

                }
                if (index == 11) {


                    cancelApi();
                    query = "Desert";
                    callApi();

                }

            }
        });

        linearsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query = edtSearch.getText().toString();

                if (query.equals("")) {
                    Toast.makeText(Search_activity.this, "Please enter text to search.", Toast.LENGTH_SHORT).show();
                } else {
                    cancelApi();
                    callApi();
                }
            }
        });


//        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
//
//                    if(edtSearch.getText().toString().equals("")){
//
//                        Toast.makeText(getActivity(), "Enter Text befor Submitt", Toast.LENGTH_SHORT).show();
//                    }
//
//                    else{
//
//                        Intent it = new Intent(getActivity(),Search_activity.class);
//                        startActivity(it);
//                    }
//
//                }
//                return false;
//            }
//        });

    }


    public class Get_search_results extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            String url = "https://api.foursquare.com/v2/venues/explore?client_id=" + Near_byplaces.Client_ID + "&client_secret=" + Near_byplaces.Client_SECRET + "&v=" + Common_methods.getDate() + "&ll=" + latitude + "," + longitude + "&query=" + query + "";

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

                                        String strTips = objvenue.optString("tips");
                                        if (!strTips.equals("")) {
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
                                        } else {
                                            listtips.add("Not specified");
                                            listurl.add("Not specified");
                                        }


                                        String name = venue.getString("name");
                                        listTitle.add(name);

                                        String id = venue.getString("id");
                                        listcategory_id.add(id);

                                        String rating = venue.optString("rating");

                                        if (rating.equals("")) {
                                            listrating.add("_._");
                                        } else {
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


                            Show_ListResults adapter = new Show_ListResults(Search_activity.this, listTitle, listcategory, listdistance, listrating, listtips, listurl, listcategory_id);

                            listView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }

                    } else {
                        Toast.makeText(Search_activity.this, s, Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(Search_activity.this, s, Toast.LENGTH_SHORT).show();
            }


        }
    }


//    public static class Show_ListResults extends BaseAdapter {
//
//        Activity activity;
//        ArrayList<String> listTitle = new ArrayList<String>();
//        ArrayList<String> listcategory = new ArrayList<String>();
//        ArrayList<String> listdistance = new ArrayList<String>();
//        ArrayList<String> listrating = new ArrayList<String>();
//        ArrayList<String> listtips = new ArrayList<String>();
//        ArrayList<String> listurl = new ArrayList<String>();
//        ArrayList<String> listcategory_id = new ArrayList<String>();
//
//        private LayoutInflater layoutInflater;
//
//
//        public Show_ListResults(Activity act, ArrayList<String> title, ArrayList<String> category, ArrayList<String> distance, ArrayList<String> rating, ArrayList<String> tips, ArrayList<String> url, ArrayList<String> category_id) {
//
//            layoutInflater = LayoutInflater.from(act);
//
//            this.activity = act;
//            this.notifyDataSetChanged();
//
//
//            listTitle.clear();
//            listcategory.clear();
//            listdistance.clear();
//            listrating.clear();
//            listtips.clear();
//            listdistance.clear();
//            listurl.clear();
//            listcategory_id.clear();
//
//
//            listTitle = title;
//            listcategory = category;
//            listdistance = distance;
//            listrating = rating;
//            listtips = tips;
//            listdistance = distance;
//            listurl = url;
//            listcategory_id = category_id;
//
//
//        }
//
//        @Override
//        public int getCount() {
//            return listTitle.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return listTitle.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            final ViewHolder holder;
//            if (convertView == null) {
//                convertView = layoutInflater.inflate(R.layout.custom_search_cells, null);
//                holder = new ViewHolder();
//
//                holder.txt1 = (TextView) convertView.findViewById(R.id.txt1);
//                holder.txt2 = (TextView) convertView.findViewById(R.id.txt2);
//                holder.txt3 = (TextView) convertView.findViewById(R.id.txt3);
//                holder.txt4 = (TextView) convertView.findViewById(R.id.txt4);
//                holder.txt5 = (TextView) convertView.findViewById(R.id.txt5);
//                holder.logo_img = (ImageView) convertView.findViewById(R.id.logo_img);
//                holder.linearCels = (LinearLayout) convertView.findViewById(R.id.linearCels);
//
//
//                convertView.setTag(holder);
//                holder.linearCels.setId(position);
//
//
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//            //Set the text here!
//
//            String img_id = listTitle.get(position);
//
//
//            holder.txt1.setText(listTitle.get(position));
//            holder.txt2.setText(listcategory.get(position));
//            holder.txt3.setText(listdistance.get(position));
//            holder.txt4.setText(listrating.get(position));
//            holder.txt5.setText(listtips.get(position));
//
//            // holder.news_DETAIL.setText(listdescription.get(position));
//            //  holder.news_time.setText(listdate_time.get(position));
//
//
//            new Common_methods.BackTask(activity, holder.logo_img, listurl.get(position)).execute();
//
////            holder.txtshop_address.setText("City : " + addressarr.get(position));
////            holder.id_ofr_button.setText(offers_arr.get(position));
////
////            String imageUrlarrr = imageUrlarr.get(position);
////            String img_log = shop_iconarr.get(position);
//
//
//            return convertView;
//        }
//
//        class ViewHolder {
//
//            TextView txt1;
//            TextView txt2;
//            TextView txt3;
//            TextView txt4;
//            TextView txt5;
//            ImageView logo_img;
//            LinearLayout linearCels;
//
//
//        }
//    }


    public void callApi() {


        ConnectionDetector cd = new ConnectionDetector(Search_activity.this);
        try {
            Boolean isInternetPresent = cd.isConnectingToInternet(); // true or false

            if (isInternetPresent) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    getResults = new Get_search_results();
                    getResults.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


                } else {
                    getResults = new Get_search_results();
                    getResults.execute();


                }


            } else {
                Common_methods.Alert(Search_activity.this);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showProgressDialog(Activity activity) {
        showDialog = new Dialog(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View v = inflater.inflate(R.layout.custom_loading_icon, null);


        showDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        showDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        showDialog.setContentView(v);
        showDialog.setCancelable(true);

    }

    public void cancelApi() {

        if (getResults != null) {
            getResults.cancel(true);
            getCategories.cancel(true);

        }


        if (showDialog != null) {
            showDialog.dismiss();
        }


    }

    public class GetCategories extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            String url = "https://api.foursquare.com/v2/venues/categories?client_id=" + Near_byplaces.Client_ID + "&client_secret=" + Near_byplaces.Client_SECRET + "&v=" + Common_methods.getDate() + "&ll=" + latitude + "," + longitude + "";

            responseCategories = Common_methods.getResponseGet(url);


            return responseCategories;
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
                        JSONArray categories = response.getJSONArray("categories");

                        if (categories.length() != 0) {

                            listcategories.clear();

                            for (int i = 0; i < categories.length(); i++) {

                                JSONObject objject = categories.getJSONObject(i);
                                String name = objject.getString("name");
                                listcategories.add(name);

                                JSONArray arraycategories = objject.getJSONArray("categories");
                                if (arraycategories.length() != 0) {

                                    for (int j = 0; j < arraycategories.length(); j++) {
                                        JSONObject objSubCat = arraycategories.getJSONObject(j);
                                        String subcategories = objSubCat.getString("name");
                                        listcategories.add(subcategories);

                                    }
                                }

                            }


                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Search_activity.this, android.R.layout.simple_spinner_dropdown_item, listcategories);
                            //Getting the instance of AutoCompleteTextView

                            edtSearch.setThreshold(1);//will start working from first character
                            edtSearch.setAdapter(adapter);

                            edtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    query = (String) parent.getItemAtPosition(position);
                                    cancelApi();
                                    callApi();

                                }
                            });
                        }


                    } else {
                        Toast.makeText(Search_activity.this, s, Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(Search_activity.this, s, Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog.show();
        }
    }


    public void callGetCategoriesApi() {

        ConnectionDetector cd = new ConnectionDetector(Search_activity.this);
        try {
            Boolean isInternetPresent = cd.isConnectingToInternet(); // true or false

            if (isInternetPresent) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    getCategories = new GetCategories();
                    getCategories.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


                } else {
                    getCategories = new GetCategories();
                    getCategories.execute();


                }


            } else {
                Common_methods.Alert(Search_activity.this);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

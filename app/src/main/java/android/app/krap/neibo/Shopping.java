package android.app.krap.neibo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 10/26/2016.
 */
public class Shopping extends Fragment {


    SharedPreferences shared;
    SharedPreferences.Editor editor;

    static String latitude, longitude, strSortBy;
    static Dialog showDialog;
    static ArrayList<String> listBrand = new ArrayList<String>();
    static ArrayList<String> listPrice = new ArrayList<String>();
    static ArrayList<String> listActualPrice = new ArrayList<String>();
    static ArrayList<String> listDscntprcnt = new ArrayList<String>();
    static ArrayList<String> listItmDesc = new ArrayList<String>();
    static ArrayList<String> listShpng_icon = new ArrayList<String>();
    static ArrayList<String> listUrl = new ArrayList<String>();
    static ArrayList<String> listOtherImages = new ArrayList<String>();
    static ArrayList<String> listtracking_link = new ArrayList<String>();

    static ExpandableHeightGridView list_items_grid;

    static Activity activity;

    static Get_shopping get_SHOPPING;
    static LinearLayout lnrSort;
    static SortItems sortItems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shopping, null);

        activity = getActivity();
        showProgressDialog(getActivity());
        shared = getActivity().getSharedPreferences("shared_location", Context.MODE_MULTI_PROCESS);
        editor = shared.edit();

        latitude = shared.getString("latitude", "");
        longitude = shared.getString("longitude", "");

        list_items_grid = (ExpandableHeightGridView) v.findViewById(R.id.list_items_grid);
        lnrSort = (LinearLayout) v.findViewById(R.id.lnrSort);

        callApi();

        return v;
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


    public static class Get_shopping extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {


            String url = "http://35.162.179.154/neibobackend/public/api/importcsv";
            String response = Common_methods.getResponseRestGet(activity, url);

//            String url = "https://api.foursquare.com/v2/venues/explore?client_id=" + Near_byplaces.Client_ID + "&client_secret=" + Near_byplaces.Client_SECRET + "&v=" + Common_methods.getDate() + "&ll=" + latitude + "," + longitude + "&query=Shopping";
//
//            url = url.replaceAll(" ", "%20");
//
//            String response = Common_methods.getResponseGet(url);

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

            showResponse(s);


        }
    }

    public static class Show_ShopResults extends BaseAdapter {


        Activity activity;
        ArrayList<String> Brand = new ArrayList<String>();
        ArrayList<String> Price = new ArrayList<String>();
        ArrayList<String> ActualPrice = new ArrayList<String>();
        ArrayList<String> Dscntprcnt = new ArrayList<String>();
        ArrayList<String> ItmDesc = new ArrayList<String>();
        ArrayList<String> Shpng_icon = new ArrayList<String>();
        ArrayList<String> Url = new ArrayList<String>();
        ArrayList<String> OtherImages = new ArrayList<String>();
        ArrayList<String> tracking_link = new ArrayList<String>();


        private LayoutInflater layoutInflater;


        public Show_ShopResults(Activity act, ArrayList<String> arrBrand, ArrayList<String> arrPrice, ArrayList<String> arrActualPrice, ArrayList<String> arrDscntprcnt, ArrayList<String> arrItmDesc, ArrayList<String> arrShpng_icon, ArrayList<String> arrUrl, ArrayList<String> arrOtherImages, ArrayList<String> arrtracking_link) {

            layoutInflater = LayoutInflater.from(act);

            this.activity = act;
            this.notifyDataSetChanged();


            Brand = arrBrand;
            Price = arrPrice;
            ActualPrice = arrActualPrice;
            Dscntprcnt = arrDscntprcnt;
            ItmDesc = arrItmDesc;
            Shpng_icon = arrShpng_icon;
            Url = arrUrl;
            OtherImages = arrOtherImages;
            tracking_link = arrtracking_link;


        }

        @Override
        public int getCount() {
            return ItmDesc.size();
        }

        @Override
        public Object getItem(int position) {
            return ItmDesc.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.custom_shopping, null);
                holder = new ViewHolder();


                holder.txtBrand = (TextView) convertView.findViewById(R.id.txtBrand);
                holder.txtPrice = (TextView) convertView.findViewById(R.id.txtPrice);
                holder.txtActualPrice = (TextView) convertView.findViewById(R.id.txtActualPrice);
                holder.txtDscntprcnt = (TextView) convertView.findViewById(R.id.txtDscntprcnt);
                holder.txtItmDesc = (TextView) convertView.findViewById(R.id.txtItmDesc);
                holder.img_shpng_icon = (ImageView) convertView.findViewById(R.id.img_shpng_icon);
                holder.iDdealClick = (LinearLayout) convertView.findViewById(R.id.iDdealClick);


                convertView.setTag(holder);
                holder.iDdealClick.setId(position);


            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //Set the text here!


            holder.txtBrand.setText(listBrand.get(position));
            holder.txtPrice.setText(listPrice.get(position));
            holder.txtActualPrice.setText(listActualPrice.get(position));
            holder.txtDscntprcnt.setText(listDscntprcnt.get(position));
            holder.txtItmDesc.setText(listItmDesc.get(position));

            // holder.news_DETAIL.setText(listdescription.get(position));
            //  holder.news_time.setText(listdate_time.get(position));

            UrlImageViewHelper.setUrlDrawable(holder.img_shpng_icon, listUrl.get(position));

            holder.iDdealClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int id = holder.iDdealClick.getId();

                    String link = tracking_link.get(id);

                    Intent it = new Intent(activity, Web_view.class);
                    it.putExtra("url_link", link);
                    activity.startActivity(it);
                }
            });


            return convertView;
        }

        class ViewHolder {

            TextView txtBrand;
            TextView txtPrice;
            TextView txtActualPrice;
            TextView txtDscntprcnt;
            TextView txtItmDesc;
            ImageView img_shpng_icon;
            LinearLayout iDdealClick;


        }
    }

    public void callApi() {


        ConnectionDetector cd = new ConnectionDetector(getActivity());
        try {
            Boolean isInternetPresent = cd.isConnectingToInternet(); // true or false

            if (isInternetPresent) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    get_SHOPPING = new Get_shopping();
                    get_SHOPPING.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                } else {
                    get_SHOPPING = new Get_shopping();
                    get_SHOPPING.execute();
                }


            } else {
                Common_methods.Alert(getActivity());


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void cancelEntApi() {

        if (get_SHOPPING != null) {
            get_SHOPPING.cancel(true);

        }


        if (showDialog != null) {
            showDialog.dismiss();
        }


    }

    public static void sortDialog(Context ct) {

        final CharSequence[] items = {"Low to High", "High to Low", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ct);
        builder.setTitle("Sort by");
        builder.setCancelable(true);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Low to High")) {

                    strSortBy = "ASC";
                    callSortApi(activity);


                } else if (items[item].equals("High to Low")) {

                    strSortBy = "DESC";
                    callSortApi(activity);


                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    public static void showResponse(String s) {

        if (s != null) {

            try {
                JSONArray array = new JSONArray(s);

                if (array.length() != 0) {


                    listBrand.clear();
                    listPrice.clear();
                    listActualPrice.clear();
                    listDscntprcnt.clear();
                    listItmDesc.clear();
                    listShpng_icon.clear();
                    listUrl.clear();
                    listOtherImages.clear();
                    listtracking_link.clear();


                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);
                        String brand = object.optString("brand");
                        listBrand.add(brand);

                        String discounted_percentage = object.optString("discounted_percentage");
                        listDscntprcnt.add(discounted_percentage);

                        String product_name = object.optString("product_name");
                        listItmDesc.add(product_name);

                        String picture_url = object.optString("picture_url");
                        listUrl.add(picture_url);

                        String tracking_link = object.getString("tracking_link");
                        listtracking_link.add(tracking_link);

                        String image_url_2 = object.optString("image_url_2");
                        if (image_url_2.equals("null") || image_url_2.equals("")) {
                            image_url_2 = " ";

                            listOtherImages.add(image_url_2);
                        }
                        String image_url_3 = object.optString("image_url_3");
                        if (image_url_3.equals("null") || image_url_3.equals("")) {
                            image_url_3 = " ";
                            listOtherImages.add(image_url_3);
                        }
                        String image_url_4 = object.optString("image_url_4");
                        if (image_url_4.equals("null") || image_url_4.equals("")) {
                            image_url_4 = " ";

                            listOtherImages.add(image_url_4);
                        }

                        String image_url_5 = object.optString("image_url_5");
                        if (image_url_5.equals("null") || image_url_5.equals("")) {
                            image_url_5 = " ";
                            listOtherImages.add(image_url_5);
                        }

                        String currency = object.optString("currency");

                        String discounted_price = object.optString("discounted_price");
                        String sale_price = object.optString("sale_price");

                        if (currency.equals("null") || currency.equals("")) {
                            currency = " ";
                        }

                        discounted_price = discounted_price + currency;
                        listPrice.add(discounted_price);

                        sale_price = sale_price + currency;
                        listActualPrice.add(sale_price);


                    }


                    list_items_grid.setExpanded(true);
                    Show_ShopResults adapter = new Show_ShopResults(activity, listBrand, listPrice, listActualPrice, listDscntprcnt, listItmDesc, listShpng_icon, listUrl, listOtherImages, listtracking_link);
                    adapter.notifyDataSetChanged();
                    list_items_grid.setAdapter(adapter);


                    lnrSort.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            sortDialog(activity);

                        }
                    });

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



    public static void showResponseSort(String s) {

        if (s != null) {


            if(s.contains("Success")){
                try {

                    JSONObject obj = new JSONObject(s);

                    JSONArray array =  obj.getJSONArray("data");

                    if (array.length() != 0) {


                        listBrand.clear();
                        listPrice.clear();
                        listActualPrice.clear();
                        listDscntprcnt.clear();
                        listItmDesc.clear();
                        listShpng_icon.clear();
                        listUrl.clear();
                        listOtherImages.clear();
                        listtracking_link.clear();


                        for (int i = 0; i < array.length(); i++) {

                            JSONObject object = array.getJSONObject(i);
                            String brand = object.optString("brand");
                            listBrand.add(brand);

                            String discounted_percentage = object.optString("discounted_percentage");
                            listDscntprcnt.add(discounted_percentage);

                            String product_name = object.optString("product_name");
                            listItmDesc.add(product_name);

                            String picture_url = object.optString("picture_url");
                            listUrl.add(picture_url);

                            String tracking_link = object.getString("tracking_link");
                            listtracking_link.add(tracking_link);

                            String image_url_2 = object.optString("image_url_2");
                            if (image_url_2.equals("null") || image_url_2.equals("")) {
                                image_url_2 = " ";

                                listOtherImages.add(image_url_2);
                            }
                            String image_url_3 = object.optString("image_url_3");
                            if (image_url_3.equals("null") || image_url_3.equals("")) {
                                image_url_3 = " ";
                                listOtherImages.add(image_url_3);
                            }
                            String image_url_4 = object.optString("image_url_4");
                            if (image_url_4.equals("null") || image_url_4.equals("")) {
                                image_url_4 = " ";

                                listOtherImages.add(image_url_4);
                            }

                            String image_url_5 = object.optString("image_url_5");
                            if (image_url_5.equals("null") || image_url_5.equals("")) {
                                image_url_5 = " ";
                                listOtherImages.add(image_url_5);
                            }

                            String currency = object.optString("currency");

                            String discounted_price = object.optString("discounted_price");
                            String sale_price = object.optString("sale_price");

                            if (currency.equals("null") || currency.equals("")) {
                                currency = " ";
                            }

                            discounted_price = discounted_price + currency;
                            listPrice.add(discounted_price);

                            sale_price = sale_price + currency;
                            listActualPrice.add(sale_price);


                        }


                        list_items_grid.setExpanded(true);
                        Show_ShopResults adapter = new Show_ShopResults(activity, listBrand, listPrice, listActualPrice, listDscntprcnt, listItmDesc, listShpng_icon, listUrl, listOtherImages, listtracking_link);
                        adapter.notifyDataSetChanged();
                        list_items_grid.setAdapter(adapter);


                        lnrSort.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                sortDialog(activity);

                            }
                        });

                    } else {
                        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            else {

            }




        } else {
            Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
        }

    }





    public static class SortItems extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            String url = "http://35.162.179.154/neibobackend/public/api/getfeeds";



            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("orderby", strSortBy));

            String response = Common_methods.getResponseShop(url, list);

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

            showResponseSort(s);


        }
    }


    public static void callSortApi(Activity activity) {


        ConnectionDetector cd = new ConnectionDetector(activity);
        try {
            Boolean isInternetPresent = cd.isConnectingToInternet(); // true or false

            if (isInternetPresent) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    sortItems = new SortItems();
                    sortItems.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                } else {
                    sortItems = new SortItems();
                    sortItems.execute();
                }


            } else {
                Common_methods.Alert(activity);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

package android.app.krap.neibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 11/7/2016.
 */
public class Location_find extends Activity implements AdapterView.OnItemClickListener {
    //http://maps.googleapis.com/maps/api/geocode/json?address=activecraft%20mohali&sensor=true
    private static final String LOG_TAG = "ExampleApp";

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/geocode";
    private static final String TYPE_AUTOCOMPLETE = "/json?";
    static String lat;

    static String lng;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    TextView txtPlace;
    List<String> listDeal = new ArrayList<String>();
    List<String> listDesc = new ArrayList<String>();
    List<String> listshop = new ArrayList<String>();
    private static final String API_KEY = "AIzaSyAU9ShujnIg3IDQxtPr7Q1qOvFVdwNmWc4";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_find);
      //  getActionBar().hide();
     //   this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sp = getSharedPreferences("Session data", MODE_MULTI_PROCESS);
        edt = sp.edit();
        String strPlace = sp.getString("placeName", "");
        AutoCompleteTextView autoCompView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        autoCompView.setText(strPlace);
        autoCompView.setSelectAllOnFocus(true);
    //    int id= R.drawable.ic_launcher;


        // Setting the adapter to the listView

        autoCompView.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.list_item));
        autoCompView.setOnItemClickListener(this);
    }


    public void onBackPressed() {
        Intent it = new Intent(Location_find.this,Me_edit.class);
        startActivity(it);
        finish();
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String str = (String) adapterView.getItemAtPosition(position);
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        Log.e(lat, lng);
        Intent it = new Intent(Location_find.this,Me_edit.class);

        it.putExtra("Address", str);
        it.putExtra("LAT", lat);
        it.putExtra("LNG", lng);
        startActivity(it);
        finish();

    }

    public static ArrayList<String> autocomplete(String input) {
        ArrayList<String> resultList = null;
        ArrayList<String> str = null;
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE);
            //	sb.append("?key=" + API_KEY);

            sb.append("address=" + URLEncoder.encode(input, "utf8"));
            sb.append("&sensor=true");


            URL url = new URL(sb.toString());

            Log.e("urlllllllllll", "" +url);
            System.out.println("URL: "+url);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {

            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("results");
            Log.e("jsooonnnnnn", "" +predsJsonArray);
            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predsJsonArray.length());
            str = new ArrayList<String>(predsJsonArray.length());

            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("address_components"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("formatted_address"));
                JSONObject location = predsJsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location");;
                lat  = location.optString("lat");
                lng  = location.optString("lng");
                Log.e("stdtsdef", "" +lat);
                Log.e("loggg", "" +lng);
                //	predsJsonArray.getJSONObject(i).getString("lng");
                //	str.add(predsJsonArray.getJSONObject(i).getString("lng"));
            }



        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }

    class GooglePlacesAutocompleteAdapter extends ArrayAdapter<String> implements Filterable {
        private ArrayList<String> resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;

                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    }
                    else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }
}

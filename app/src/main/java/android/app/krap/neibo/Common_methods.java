package android.app.krap.neibo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

/**
 * Created by Android on 11/3/2016.
 */
public class Common_methods {

 static String response;

    public  static Dialog showDialog;
   static SharedPreferences sp;
  static   SharedPreferences.Editor edt;
  static   Document document;
    static  String imgurl;
    static  ProgressBar progressBar;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    public static String getResponse(String url, List<NameValuePair> list){


        DefaultHttpClient client = new DefaultHttpClient();
        ResponseHandler<String> handler = new BasicResponseHandler();
        HttpPost post = new HttpPost(url);
        try {
            post.setEntity(new UrlEncodedFormEntity(list));


            HttpResponse httpresponse = client.execute(post);


            HttpEntity resEntity = httpresponse.getEntity();


            String res = httpresponse.getStatusLine().toString();
            response = EntityUtils.toString(resEntity);


            Log.e("responseAlldeals", "" + response);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return response;

    }


    public static String getResponseGet(String url){


        DefaultHttpClient client = new DefaultHttpClient();
        ResponseHandler<String> handler = new BasicResponseHandler();
        HttpGet post = new HttpGet(url);
        try {



            HttpResponse httpresponse = client.execute(post);


            HttpEntity resEntity = httpresponse.getEntity();


            String res = httpresponse.getStatusLine().toString();
            response = EntityUtils.toString(resEntity);


       //     Log.e("responseAlldeals", "" + response);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return response;

    }



    public static String getResponseBRest(Activity activity,String url, List<NameValuePair> list){

        sp = activity.getSharedPreferences("shared_value", Context.MODE_MULTI_PROCESS);
        edt = sp.edit();

        DefaultHttpClient client = new DefaultHttpClient();
        ResponseHandler<String> handler = new BasicResponseHandler();
        HttpPost post = new HttpPost(url);
        try {
            post.setEntity(new UrlEncodedFormEntity(list));

            post.setHeader("Authorization", "Bearer "+sp.getString("access_token",""));
            post.setHeader("Accept","application/json");

            HttpResponse httpresponse = client.execute(post);


            HttpEntity resEntity = httpresponse.getEntity();


            String res = httpresponse.getStatusLine().toString();
            response = EntityUtils.toString(resEntity);


            Log.e("responseAlldeals", "" + response);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return response;

    }



    public static void showProgressDialog(Activity ct){
        showDialog = new Dialog(ct);
        LayoutInflater inflater = ct.getLayoutInflater();
        View v = inflater.inflate(R.layout.custom_loading_icon, null);

//        ImageView imgGif =(ImageView) v.findViewById(R.id.animlogo);
//        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imgGif);
//        Glide.with(ct).load(R.raw.new_logo_gifs).into(imageViewTarget);

        showDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        showDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        showDialog.setContentView(v);
        showDialog.setCancelable(false);

    }


    static  void Alert(final Activity activity) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("You are not connected to internet.")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                        activity.finish();

                    }
                });

        final AlertDialog alert = builder.create();
        alert.show();
    }



    public static String GetUrls(String url){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                String UserAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36";

                document = Jsoup.connect(url).userAgent(UserAgent).get();
            } catch (Exception e) {
                e.printStackTrace();

            }

            if (document != null) {

                //  viewHolder.title.setText(document.title().replace("&amp;", "&"));

                Elements description = document.select("meta[name=description]");
                //  viewHolder.sub.setText(description.attr("content"));

                Elements metaOgImage = document.select("meta[property=og:image]");
                if (metaOgImage != null && metaOgImage.size() > 0) {
                    imgurl = metaOgImage.first().attr("content");
                    //  viewHolder.photo.setVisibility(View.VISIBLE);




                } else {
                    imgurl="";
                }

            }

        }

        return   imgurl;
    }


    public static void showProgressBar(Activity activity, LinearLayout layout){


        progressBar = new ProgressBar(activity,null,android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);

        RelativeLayout rl = new RelativeLayout(activity);
        rl.setGravity(Gravity.CENTER);
        rl.addView(progressBar);

        layout.addView(rl,params);

    }



  public   static class BackTask extends AsyncTask<Void, Void, Void> {

        // SuggestionAdapterr.CompanyViewHolder viewHolder;
        String link;
        Activity act;
        ImageView imageUrl;

        public BackTask(Activity activity,ImageView imageView,String link) {

            this.link = link;
            this.imageUrl =imageView;
            act=    activity;
        }

        org.jsoup.nodes.Document document;

        @Override
        protected Void doInBackground(Void... params) {

            try {
                String UserAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36";

                document = Jsoup.connect(link).userAgent(UserAgent).get();
            } catch (Exception e) {
                e.printStackTrace();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (document != null) {

                Elements metaOgImage = document.select("meta[property=og:image]");
                if (metaOgImage != null && metaOgImage.size() > 0) {
                    String imgurl = metaOgImage.first().attr("content");

                    UrlImageViewHelper.setUrlDrawable(imageUrl, imgurl, R.drawable.neibo_logo);



                    //  viewHolder.photo.setVisibility(View.VISIBLE);
//                    Picasso.with(getActivity()).load(imgurl).into(viewHolder.photo);
                } else {
                    //  viewHolder.photo.setVisibility(View.GONE);
                }

            }
        }
    }


    public static void settingsRequest(final Activity ct) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(ct).addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.e("log", "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.e("log", "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(ct, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.e("log", "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.e("log", "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }


}

package android.app.krap.neibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 10/21/2016.
 */
public class Login extends Activity {

    RelativeLayout register_bck;
    TextView forgot_password;
    String response;

    EditText edtEmailId, edtEmailPasswordR;
    String strEmail, strPassword;

    Button btncontinue;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    String url = "http://35.162.179.154/neibobackend/public/profilepics/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Common_methods.showProgressDialog(Login.this);

        sp = getSharedPreferences("shared_value", Context.MODE_MULTI_PROCESS);
        edt = sp.edit();


        register_bck = (RelativeLayout) findViewById(R.id.register_bck);
        forgot_password = (TextView) findViewById(R.id.forgot_password);

        edtEmailId = (EditText) findViewById(R.id.edtEmailId);
        edtEmailPasswordR = (EditText) findViewById(R.id.edtEmailPasswordR);

        btncontinue = (Button) findViewById(R.id.btncontinue);

        register_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strEmail = edtEmailId.getText().toString();

                strPassword = edtEmailPasswordR.getText().toString();

                if (strEmail.equals("")) {
                    Toast.makeText(Login.this, "Please provide email", Toast.LENGTH_SHORT).show();
                } else if (strPassword.equals("")) {
                    Toast.makeText(Login.this, "Please provide password", Toast.LENGTH_SHORT).show();
                } else {
                    ConnectionDetector cd = new ConnectionDetector(Login.this);
                    try {
                        Boolean isInternetPresent = cd.isConnectingToInternet(); // true or false

                        if (isInternetPresent) {


                            LoginUser user = new LoginUser();
                            user.execute();

                        } else {
                            Common_methods.Alert(Login.this);


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Login.this, Forgot_password.class);
                startActivity(it);
            }
        });

    }


    public class LoginUser extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {


            String url = "http://35.162.179.154/neibobackend/public/api/login";
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("email", strEmail));
            list.add(new BasicNameValuePair("password", strPassword));

            response = Common_methods.getResponse(url, list);


            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Common_methods.showDialog.dismiss();

            if (s != null) {

                if (s.contains("access_token")) {


                    try {
                        JSONObject obj = new JSONObject(s);

                        JSONObject user = obj.getJSONObject("user");

                        String access_token =obj.getString("access_token");
                        String phone_number = user.getString("phone_number");
                        String name = user.getString("name");
                        String username = user.getString("username");
                        String email = user.getString("email");
                        String profile_image = user.getString("profile_image");
                        String profileImg_upload =user.getString("profile_image");

                        String interest = user.getString("interest");
                        String latitude = user.getString("latitude");
                        String longitude = user.getString("longitude");
                        String location = user.getString("location").toString();
                        String created_at = user.getString("created_at");
                        String updated_at = user.getString("updated_at");
                        String deck_count = user.getString("deck_count");
                        String decks = user.getString("decks").toString();
                        String about_me =user.getString("about_me");

                        profile_image=url +profile_image;
                        if (profile_image.contains(" ")) {
                            profile_image = profile_image.replaceAll(" ", "");
                        }


                        edt.putString("logged_in", "logged_in");
                        edt.putString("access_token", access_token);
                        edt.putString("phone_number", phone_number);
                        edt.putString("name", name);
                        edt.putString("username", username);
                        edt.putString("email", email);
                        edt.putString("profile_image", profile_image);
                        edt.putString("profileImg_upload",profileImg_upload);
                        edt.putString("about_me",about_me);

                        edt.putString("interest", interest);
                        edt.putString("latitude", latitude);
                        edt.putString("longitude", longitude);
                        edt.putString("location", location);
                        edt.putString("created_at", created_at);
                        edt.putString("updated_at", updated_at);
                        edt.putString("deck_count", deck_count);
                        edt.putString("decks", decks);

                        edt.commit();


                        Intent it = new Intent(Login.this, Homepage.class);
                        startActivity(it);
                        finish();
                        finishAffinity();




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(Login.this, s, Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(Login.this, s, Toast.LENGTH_SHORT).show();
            }


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Common_methods.showDialog.show();

        }
    }

}

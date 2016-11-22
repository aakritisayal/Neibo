package android.app.krap.neibo;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 10/21/2016.
 */
public class Forgot_password extends Activity {

    Button btncontinue;
    RelativeLayout register_bck;
    String response,strEmail;
    EditText edtEmailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        Common_methods.showProgressDialog(Forgot_password.this);

        btncontinue =(Button) findViewById(R.id.btncontinue);
        edtEmailId =(EditText) findViewById(R.id.edtEmailId);

        register_bck =(RelativeLayout) findViewById(R.id.register_bck);

        register_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                strEmail =edtEmailId.getText().toString();
                
                if(edtEmailId.equals("")){
                    Toast.makeText(Forgot_password.this, "Please provide email", Toast.LENGTH_SHORT).show();
                }

                else {
                    ConnectionDetector cd = new ConnectionDetector(Forgot_password.this);
                    try {
                        Boolean isInternetPresent = cd.isConnectingToInternet(); // true or false

                        if (isInternetPresent) {


                            ReterievePassword Password = new ReterievePassword();
                            Password.execute();

                        } else {
                            Common_methods.Alert(Forgot_password.this);


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }


    public class ReterievePassword extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {


            String url = "http://35.162.179.154/neibobackend/public/api/forget_password";
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("email", strEmail));


            response =Common_methods.getResponse(url,list);


            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Common_methods.showDialog.dismiss();

            if (s != null) {

                if(s.contains("Please check your email to reset password")){
                    Toast.makeText(Forgot_password.this, "Password sucessfully send to your mail id.", Toast.LENGTH_SHORT).show();

                    Intent it = new Intent(Forgot_password.this, Suggestion_login.class);
                    startActivity(it);
                    finish();
                    finishAffinity();
                }

                else{
                    Toast.makeText(Forgot_password.this, s, Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(Forgot_password.this, s, Toast.LENGTH_SHORT).show();
            }


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Common_methods.showDialog.show();

        }
    }

}

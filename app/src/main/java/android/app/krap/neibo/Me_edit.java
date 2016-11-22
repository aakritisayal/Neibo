package android.app.krap.neibo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 10/28/2016.
 */
public class Me_edit extends Activity {

    TextView txtCancel, txtSave, txtLoction;
    LinearLayout lnrlocation;
    EditText edtUsername, edtName, edtEmail, txtAbtUrslf;
    String strUsrname, strName, strEmail, strAbout, response, strLatitude, strLongitude, strLocation, realPath;
    public static final int REQUEST_CAMERA = 0x1;
    public static final int SELECT_FILE = 0x2;

    SharedPreferences sp;
    SharedPreferences.Editor edt;

    ImageView imgChange;
    Bitmap bitMap;
    String profile_imagenew;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_edit);


        Common_methods.showProgressDialog(Me_edit.this);

        sp = getSharedPreferences("shared_value", Context.MODE_MULTI_PROCESS);
        edt = sp.edit();

        String About = sp.getString("about_me", "");


        Intent it = getIntent();
        String Address = it.getStringExtra("Address");
        String LAT = it.getStringExtra("LAT");
        String LNG = it.getStringExtra("LNG");


        imgChange = (ImageView) findViewById(R.id.imgChange);
        txtSave = (TextView) findViewById(R.id.txtSave);
        lnrlocation = (LinearLayout) findViewById(R.id.lnrlocation);

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtName = (EditText) findViewById(R.id.edtName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);

        txtAbtUrslf = (EditText) findViewById(R.id.txtAbtUrslf);

        txtCancel = (TextView) findViewById(R.id.txtCancel);
        txtLoction = (TextView) findViewById(R.id.txtLoction);

        edtUsername.setText(sp.getString("username", ""));
        edtName.setText(sp.getString("name", ""));
        edtEmail.setText(sp.getString("email", ""));
        txtAbtUrslf.setText(sp.getString("about_me", ""));


        if(About.equals("null")){
            txtAbtUrslf.setText("");

        }


        if (sp.getString("location", "").equals("") || sp.getString("location", "").equals("null")) {
            txtLoction.setText("Location");
        } else {

            txtLoction.setText(sp.getString("location", ""));

        }

        if (Address != null) {

            strLocation = Address;
            strLatitude = LAT;
            strLongitude = LNG;

            txtLoction.setText(strLocation);


        }

        else {
            strLocation = "";
            strLatitude = "";
            strLongitude = "";
        }



        UrlImageViewHelper.setUrlDrawable(imgChange, sp.getString("profile_image", ""), R.drawable.round_grey);


        imgChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPickImage();
            }
        });

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strUsrname = edtUsername.getText().toString();
                strName = edtName.getText().toString();
                strEmail = edtEmail.getText().toString();
                strAbout = txtAbtUrslf.getText().toString();
                strLocation = txtLoction.getText().toString();


                if (strLocation.equals("Location")) {

                    strLocation = "Not specified";
                }


                EditProfile profile = new EditProfile();
                profile.execute();

                //  finish();

            }
        });

        lnrlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(Me_edit.this, Location_find.class);
                startActivity(it);
                finish();
            }
        });


        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }


    public class EditProfile extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {


            String url = "http://35.162.179.154/neibobackend/public/api/updateprofile";

            try {

                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(url);
                post.setHeader("Authorization", "Bearer "+sp.getString("access_token",""));
                post.setHeader("Accept","application/json");

                MultipartEntity entity = new MultipartEntity();

//                String pathReal = realPath;
//
                if (realPath==null) {
                    entity.addPart("profile_image", new StringBody(""));
                }

                else {
                    entity.addPart("profile_image", new FileBody(new File(realPath)));
                }

                entity.addPart("email", new StringBody(strEmail));
                entity.addPart("username", new StringBody(strUsrname));
                entity.addPart("name", new StringBody(strName));
                entity.addPart("latitude", new StringBody(strLatitude));
                entity.addPart("longitude", new StringBody(strLongitude));
                entity.addPart("location", new StringBody(strLocation));


                post.setEntity(entity);

                HttpResponse httpresponse = client.execute(post);
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpresponse.getEntity().getContent(), "UTF-8"));

                response = reader.readLine();


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Common_methods.showDialog.dismiss();

            if (s != null) {

                if (s.contains("Success")) {

                    try {
                        JSONObject obj = new JSONObject(s);





                        Toast.makeText(Me_edit.this, "Profile Edited successfully.", Toast.LENGTH_SHORT).show();

                        Intent it = new Intent(Me_edit.this, Me_edit.class);
                        edt.putString("email", strEmail);
                        edt.putString("username", strUsrname);
                        edt.putString("name", strName);


                        if(!strLatitude.equals("")){
                            edt.putString("latitude", strLatitude);
                        }
                        if(!strLongitude.equals("")){
                            edt.putString("longitude", strLongitude);
                        }

                        if(!strLocation.equals("")){
                            edt.putString("location", strLocation);
                        }


                        if(s.contains("profile_image")){
                            String profile_image   =obj.getString("profile_image");
                            profile_imagenew =profile_image;
                            edt.putString("profile_image","http://35.162.179.154/neibobackend/public/profilepics/"+profile_imagenew);

                        }



                        edt.commit();
                        startActivity(it);
                        it.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        finish();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }




                } else {
                    Toast.makeText(Me_edit.this, s, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Me_edit.this, s, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Common_methods.showDialog.show();
        }
    }

    public void onPickImage() {

        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Upload Images");
        builder.setCancelable(true);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {

                //    String path = (String) data.getExtras().get("data");

                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                Uri uri = getImageUri(getApplicationContext(), thumbnail);
                realPath = getRealPathFromURI(getApplicationContext(), uri);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;


                bitMap = thumbnail;
                imgChange.setImageBitmap(bitMap);

            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                realPath = getRealPathFromURI(getApplicationContext(), selectedImageUri);
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null, null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);

                bitMap = bm;
                imgChange.setImageBitmap(bitMap);

            }
        }
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}

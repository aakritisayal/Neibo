package android.app.krap.neibo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Android on 10/21/2016.
 */
public class Sign_up extends Activity {

    public static final int progress_id = 0;
    private ProgressDialog prgDialog;
    RelativeLayout register_bck;
    Button btncontinue;
    EditText edtName, edtEmailPasswordR, edtEmailConformpass, edtEmailId, edtMobile, edtUserName;
    ImageView imgProfilePicture;
    String strname, strpassword, strConfirmPass, strEmail, strMobile, response, realPath, strUsername;

    public static final int REQUEST_CAMERA = 0x1;
    public static final int SELECT_FILE = 0x2;

    Bitmap bitMap;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    String url = "http://35.162.179.154/neibobackend/public/profilepics/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        Common_methods.showProgressDialog(Sign_up.this);

        sp = getSharedPreferences("shared_value", Context.MODE_MULTI_PROCESS);
        edt = sp.edit();


        register_bck = (RelativeLayout) findViewById(R.id.register_bck);
        btncontinue = (Button) findViewById(R.id.createAcnt);
        imgProfilePicture = (ImageView) findViewById(R.id.imgProfilePicture);

        edtName = (EditText) findViewById(R.id.edtName);
        edtEmailPasswordR = (EditText) findViewById(R.id.edtEmailPasswordR);
        edtEmailConformpass = (EditText) findViewById(R.id.edtEmailConformpass);
        edtEmailId = (EditText) findViewById(R.id.edtEmailId);
        edtMobile = (EditText) findViewById(R.id.edtMobile);
        edtUserName = (EditText) findViewById(R.id.edtUserName);

        showCursor(edtName);
        showCursor(edtEmailPasswordR);
        showCursor(edtEmailConformpass);
        showCursor(edtEmailId);
        showCursor(edtMobile);
        showCursor(edtUserName);


        imgProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkDrawerPermision();
            }
        });

        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strname = edtName.getText().toString();
                strpassword = edtEmailPasswordR.getText().toString();
                strConfirmPass = edtEmailConformpass.getText().toString();
                strEmail = edtEmailId.getText().toString();
                strMobile = edtMobile.getText().toString();
                strUsername = edtUserName.getText().toString();

                int l = strpassword.length();

                if (strEmail.contains(" ")) {
                    strEmail = strEmail.replaceAll(" ", "");
                }

                if (strname.equals("")) {
                    Toast.makeText(Sign_up.this, "Please provide name", Toast.LENGTH_SHORT).show();
                } else if (strUsername.equals("")) {
                    Toast.makeText(Sign_up.this, "Please provide Username", Toast.LENGTH_SHORT).show();
                } else if (strpassword.equals("")) {
                    Toast.makeText(Sign_up.this, "Please provide Password", Toast.LENGTH_SHORT).show();
                } else if (strConfirmPass.equals("")) {
                    Toast.makeText(Sign_up.this, "Please confirm Password", Toast.LENGTH_SHORT).show();
                } else if (l < 6 || l > 25) {
                    Toast.makeText(Sign_up.this, "Password much me atleat 6 characters to 25 characters long", Toast.LENGTH_SHORT).show();
                } else if (strEmail.equals("")) {
                    Toast.makeText(Sign_up.this, "Please provide Email", Toast.LENGTH_SHORT).show();
                } else if (strMobile.equals("")) {
                    Toast.makeText(Sign_up.this, "Contact number is missing", Toast.LENGTH_SHORT).show();
                } else if (realPath == null) {
                    Toast.makeText(Sign_up.this, "Please set image for this account", Toast.LENGTH_SHORT).show();

                } else {
                    ConnectionDetector cd = new ConnectionDetector(Sign_up.this);
                    try {
                        Boolean isInternetPresent = cd.isConnectingToInternet(); // true or false

                        if (isInternetPresent) {


                            RegisterUser user = new RegisterUser();
                            user.execute();

                        } else {
                            Common_methods.Alert(Sign_up.this);


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });


        register_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public class RegisterUser extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                String url = "http://35.162.179.154/neibobackend/public/api/signup";

                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(url);


                MultipartEntity entity = new MultipartEntity();
                entity.addPart("profile_image", new FileBody(new File(realPath)));
                entity.addPart("name", new StringBody(strname));
                entity.addPart("email", new StringBody(strEmail));
                entity.addPart("password", new StringBody(strpassword));
                entity.addPart("password_confirmation", new StringBody(strpassword));
                entity.addPart("phone_number", new StringBody(strMobile));
                entity.addPart("username", new StringBody(strUsername));

                post.setEntity(entity);

                try {


                    HttpResponse httpresponse = client.execute(post);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(httpresponse.getEntity().getContent(), "UTF-8"));

                    response = reader.readLine();


                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

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

                        String access_token = obj.getString("access_token");
                        String phone_number = user.getString("phone_number");
                        String name = user.getString("name");
                        String username = user.getString("username");
                        String email = user.getString("email");
                        String profile_image = user.getString("profile_image");
                        String profileImg_upload = user.getString("profile_image");

                        String interest = user.getString("interest");
                        String latitude = user.getString("latitude");
                        String longitude = user.getString("longitude");
                        String location = user.getString("location").toString();
                        String created_at = user.getString("created_at");
                        String updated_at = user.getString("updated_at");
                        String deck_count = user.getString("deck_count");
                        String decks = user.getString("decks").toString();
                        String about_me = user.getString("about_me");


                        profile_image = url + profile_image;

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
                        edt.putString("profileImg_upload", profileImg_upload);
                        edt.putString("about_me", about_me);

                        edt.putString("interest", interest);
                        edt.putString("latitude", latitude);
                        edt.putString("longitude", longitude);
                        edt.putString("location", location);
                        edt.putString("created_at", created_at);
                        edt.putString("updated_at", updated_at);
                        edt.putString("deck_count", deck_count);
                        edt.putString("decks", decks);


                        edt.commit();


                        Intent it = new Intent(Sign_up.this, Homepage.class);
                        startActivity(it);
                        finish();
                        finishAffinity();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(Sign_up.this, s, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Sign_up.this, s, Toast.LENGTH_SHORT).show();
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
                imgProfilePicture.setImageBitmap(bitMap);

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
                imgProfilePicture.setImageBitmap(bitMap);

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


    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        switch (id) {
            case progress_id:
                prgDialog = new ProgressDialog(this);
                prgDialog.setMessage("Loading..!");
                prgDialog.setIndeterminate(false);
                prgDialog.setMax(100);
                prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                prgDialog.setCancelable(false);
                prgDialog.show();
                return prgDialog;


            default:
                return null;
        }
    }


    private void checkDrawerPermision() {


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(Sign_up.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(Sign_up.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                onPickImage();

            } else {


                ActivityCompat.requestPermissions(Sign_up.this, new String[]{android.Manifest.permission.CAMERA},
                        REQUEST_CAMERA);
            }
        } else {
            onPickImage();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
// Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

                    ActivityCompat.requestPermissions(Sign_up.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, 2);


                }
                break;

            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onPickImage();

                }
                break;
        }

    }

    public void showCursor(final EditText text) {

        View.OnClickListener editTextClickListener = new View.OnClickListener()

        {

            public void onClick(View v) {
                if (v.getId() == text.getId()) {
                    text.setCursorVisible(true);
                }

            }
        };


        text.setOnClickListener(editTextClickListener);


        text.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                text.setCursorVisible(false);
                if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(text.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });


    }

}

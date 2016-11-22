package android.app.krap.neibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Android on 10/28/2016.
 */
public class Interests extends Activity {

    ImageView backInt;
    Button btnDone;
    CheckBox check1, check2, check3, check4, check5, check6, check7, check8, check9, check10, check11, check12, check13, check14, check15;
    String strcheck1, strcheck2, strcheck3, strcheck4, strcheck5, strcheck6, strcheck7, strcheck8, strcheck9, strcheck10, strcheck11, strcheck12, strcheck13, strcheck14, strcheck15;
    SharedPreferences sp;
    SharedPreferences.Editor edt;

    LinkedHashMap<Integer, String> list = new LinkedHashMap<>();

    ArrayList<String> strList = new ArrayList<>();

    String response, interest, strinterest;
ImageView img1,img2,img3,img4,img5,img6,img7,img8,img9,img10,img11,img12,img13,img14,img15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interests);


        Common_methods.showProgressDialog(Interests.this);

        sp = getSharedPreferences("shared_value", Context.MODE_MULTI_PROCESS);
        edt = sp.edit();


        interest = sp.getString("interest", "");

        if(interest.contains(" ")){
            interest =interest.replaceAll(" ", "");
        }



        List<String> myList = new ArrayList<String>(Arrays.asList(interest.split(",")));

        img1 =(ImageView) findViewById(R.id.img1);
        img2 =(ImageView) findViewById(R.id.img2);
        img3 =(ImageView) findViewById(R.id.img3);
        img4 =(ImageView) findViewById(R.id.img4);
        img5 =(ImageView) findViewById(R.id.img5);
        img6 =(ImageView) findViewById(R.id.img6);
        img7 =(ImageView) findViewById(R.id.img7);
        img8 =(ImageView) findViewById(R.id.img8);
        img9 =(ImageView) findViewById(R.id.img9);
        img10 =(ImageView) findViewById(R.id.img10);
        img11 =(ImageView) findViewById(R.id.img11);
        img12 =(ImageView) findViewById(R.id.img12);
        img13 =(ImageView) findViewById(R.id.img13);
        img14 =(ImageView) findViewById(R.id.img14);
        img15 =(ImageView) findViewById(R.id.img15);

        backInt = (ImageView) findViewById(R.id.backInt);
        btnDone = (Button) findViewById(R.id.btnDone);

        check1 = (CheckBox) findViewById(R.id.check1);
        check2 = (CheckBox) findViewById(R.id.check2);
        check3 = (CheckBox) findViewById(R.id.check3);
        check4 = (CheckBox) findViewById(R.id.check4);
        check5 = (CheckBox) findViewById(R.id.check5);
        check6 = (CheckBox) findViewById(R.id.check6);
        check7 = (CheckBox) findViewById(R.id.check7);
        check8 = (CheckBox) findViewById(R.id.check8);
        check9 = (CheckBox) findViewById(R.id.check9);
        check10 = (CheckBox) findViewById(R.id.check10);
        check11 = (CheckBox) findViewById(R.id.check11);
        check12 = (CheckBox) findViewById(R.id.check12);
        check13 = (CheckBox) findViewById(R.id.check13);
        check14 = (CheckBox) findViewById(R.id.check14);
        check15 = (CheckBox) findViewById(R.id.check15);



        for (int i=0;i<myList.size();i++){

            String checkValues =myList.get(i);

            if(checkValues.equals("1")){
                check1.setChecked(true);

            }

            if(checkValues.equals("2")){
                check2.setChecked(true);
            }

            if(checkValues.equals("3")){
                check3.setChecked(true);
            }

            if(checkValues.equals("4")){
                check4.setChecked(true);
            }

            if(checkValues.equals("5")){
                check5.setChecked(true);
            }

            if(checkValues.equals("6")){
                check6.setChecked(true);
            }

            if(checkValues.equals("7")){
                check7.setChecked(true);
            }

            if(checkValues.equals("8")){
                check8.setChecked(true);
            }

            if(checkValues.equals("9")){
                check9.setChecked(true);
            }

            if(checkValues.equals("10")){
                check10.setChecked(true);
            }

            if(checkValues.equals("11")){
                check11.setChecked(true);
            }

            if(checkValues.equals("12")){
                check12.setChecked(true);
            }

            if(checkValues.equals("13")){
                check13.setChecked(true);
            }

            if(checkValues.equals("14")){
                check14.setChecked(true);
            }

            if(checkValues.equals("15")){
                check15.setChecked(true);
            }



        }


        if (check1.isChecked()) {
            list.put(1, "1");
        } else {
            list.put(1, "");
        }


        if (check2.isChecked()) {
            list.put(2, "2");
        } else {
            list.put(2, "");
        }

        if (check3.isChecked()) {
            list.put(3, "3");
        } else {
            list.put(3, "");
        }

        if (check4.isChecked()) {
            list.put(4, "4");
        } else {
            list.put(4, "");
        }

        if (check5.isChecked()) {
            list.put(5, "5");
        } else {
            list.put(5, "");
        }

        if (check6.isChecked()) {
            list.put(6, "6");
        } else {
            list.put(6, "");
        }

        if (check7.isChecked()) {
            list.put(7, "7");
        } else {
            list.put(7, "");
        }

        if (check8.isChecked()) {
            list.put(8, "8");
        } else {
            list.put(8, "");
        }

        if (check9.isChecked()) {
            list.put(9, "9");
        } else {
            list.put(9, "");
        }

        if (check10.isChecked()) {
            list.put(10, "10");
        } else {
            list.put(10, "");
        }

        if (check11.isChecked()) {
            list.put(11, "11");
        } else {
            list.put(11, "");
        }

        if (check12. isChecked()) {
            list.put(12, "12");
        } else {
            list.put(12, "");
        }

        if (check13.isChecked()) {
            list.put(13, "13");
        } else {
            list.put(13, "");
        }

        if (check14.isChecked()) {
            list.put(14, "14");
        } else {
            list.put(14, "");
        }

        if (check15.isChecked()) {
            list.put(15, "15");
        } else {
            list.put(15, "");
        }




        check1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    list.put(1, "1");
                    //   strcheck1
                } else {
                    list.put(1, "");
                }

            }
        });

        check2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    list.put(2, "2");
                    //   strcheck1
                } else {
                    list.put(2, "");
                }

            }
        });

        check3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    list.put(3, "3");
                    //   strcheck1
                } else {
                    list.put(3, "");
                }

            }
        });

        check4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    list.put(4, "4");
                    //   strcheck1
                } else {
                    list.put(4, "");
                }

            }
        });

        check5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    list.put(5, "5");
                    //   strcheck1
                } else {
                    list.put(5, "");
                }

            }
        });


        check6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    list.put(6, "6");
                    //   strcheck1
                } else {
                    list.put(6, "");
                }

            }
        });

        check7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    list.put(7, "7");
                    //   strcheck1
                } else {
                    list.put(7, "");
                }

            }
        });

        check8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    list.put(8, "8");
                    //   strcheck1
                } else {
                    list.put(8, "");
                }

            }
        });


        check9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    list.put(9, "9");
                    //   strcheck1
                } else {
                    list.put(9, "");
                }

            }
        });

        check10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    list.put(10, "10");
                    //   strcheck1
                } else {
                    list.put(10, "");
                }

            }
        });

        check11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    list.put(11, "11");
                    //   strcheck1
                } else {
                    list.put(11, "");
                }

            }
        });


        check12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    list.put(12, "12");
                    //   strcheck1
                } else {
                    list.put(12, "");
                }

            }
        });

        check13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    list.put(13, "13");
                    //   strcheck1
                } else {
                    list.put(13, "");
                }

            }
        });


        check14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    list.put(14, "14");
                    //   strcheck1
                } else {
                    list.put(14, "");
                }

            }
        });

        check15.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    list.put(15, "15");
                    //   strcheck1
                } else {
                    list.put(15, "");
                }

            }
        });


        backInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for (Integer key : list.keySet()) {

                    System.out.println("key : " + key);
                    System.out.println("value : " + list.get(key));

                    if (list.get(key).equals("")) {
                        Log.e("key not dded", "" + list.get(key));
                    } else {
                        strList.add(list.get(key));

                    }


                    // lineartransDesc.addView(v);
                }

                strinterest = String.valueOf(strList);


                strinterest = strinterest.replace("[", "").replace("]", "");

                if(strinterest.contains(" ")){
                    strinterest =strinterest.replaceAll(" ","");
                }


                EditInterest interest = new EditInterest();
                interest.execute();


//                Intent it = new Intent(Interests.this, Me_more.class);
//                startActivity(it);
            }
        });

    }

    public class EditInterest extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {


            String url = "http://35.162.179.154/neibobackend/public/api/updateinterest";
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("interest", strinterest));


            response = Common_methods.getResponseBRest(Interests.this, url, list);


            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Common_methods.showDialog.dismiss();

            if(s!=null){

                if(s.contains("Success")){
                    Toast.makeText(Interests.this, "Interest Edited successfully.", Toast.LENGTH_SHORT).show();

                    Intent it = new Intent(Interests.this,Interests.class);
                    edt.putString("interest", strinterest);
                    edt.commit();
                    startActivity(it);
                    it.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();



                }

                else{
                    Toast.makeText(Interests.this, s, Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(Interests.this, s, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Common_methods.showDialog.show();
        }
    }

}

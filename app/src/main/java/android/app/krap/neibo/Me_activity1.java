package android.app.krap.neibo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Android on 10/28/2016.
 */
public class Me_activity1 extends Fragment {


    ImageView me_fragment, imageChangeEdit;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    TextView txtUsrname, txtProfileNm;
    String access_token, phone_number, name, username, email, profile_image, decks;
    String interest;
    GridView gridview;
    TextView txtNoDeck;
    RelativeLayout rltvCreateNewDeck;
    Dialog dialogDeck;
    String type = "2";
    String strDeckTitle, strDeckDesc, response;
    LinearLayout lineardecks;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.me_activity1, null);

        sp = getActivity().getSharedPreferences("shared_value", Context.MODE_MULTI_PROCESS);
        edt = sp.edit();


        Common_methods.showProgressDialog(getActivity());

        access_token = sp.getString("access_token", "");
        phone_number = sp.getString("phone_number", "");
        name = sp.getString("name", "");
        username = sp.getString("username", "");
        email = sp.getString("email", "");
        profile_image = sp.getString("profile_image", "");
        decks = sp.getString("decks", "");


        rltvCreateNewDeck = (RelativeLayout) v.findViewById(R.id.rltvCreateNewDeck);
        me_fragment = (ImageView) v.findViewById(R.id.me_fragment);
        txtUsrname = (TextView) v.findViewById(R.id.txtUsrname);
        gridview = (GridView) v.findViewById(R.id.gridview);

        txtNoDeck = (TextView) v.findViewById(R.id.txtNoDeck);
        lineardecks = (LinearLayout) v.findViewById(R.id.lineardecks);

        imageChangeEdit = (ImageView) v.findViewById(R.id.imageChangeEdit);
        txtProfileNm = (TextView) v.findViewById(R.id.txtProfileNm);


        if (!decks.equals("[]")) {
            txtNoDeck.setVisibility(View.GONE);


            try {
                JSONArray deck_data = new JSONArray(decks);

                if (deck_data.length() != 0) {
                    for (int i = 0; i < deck_data.length(); i++) {
                        JSONObject object = deck_data.getJSONObject(i);
                        String title = object.getString("title");
                        String description = object.getString("description");

                        LayoutInflater inflate = getActivity().getLayoutInflater();
                        View view = inflate.inflate(R.layout.custom_decks, null);
                        TextView txtNameTitle = (TextView) view.findViewById(R.id.txtNameTitle);
                        TextView profilename = (TextView) view.findViewById(R.id.profilename);
                        TextView txtDescription = (TextView) view.findViewById(R.id.txtDescription);

                        txtNameTitle.setText(title);
                        profilename.setText(name);
                        txtDescription.setText(description);
                        lineardecks.addView(view);

                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {
            txtNoDeck.setVisibility(View.VISIBLE);
        }


        imageChangeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(getActivity(), Me_edit.class);
                startActivity(it);
            }
        });

        me_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(getActivity(), Me_more.class);
                startActivity(it);

            }
        });


        rltvCreateNewDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDeckDialog();

            }
        });


        return v;


    }

    @Override
    public void onResume() {
        super.onResume();

        interest = sp.getString("interest", "");

        access_token = sp.getString("access_token", "");
        phone_number = sp.getString("phone_number", "");
        name = sp.getString("name", "");
        username = sp.getString("username", "");
        email = sp.getString("email", "");
        profile_image = sp.getString("profile_image", "");
        decks = sp.getString("decks", "");

        UrlImageViewHelper.setUrlDrawable(imageChangeEdit, profile_image, R.drawable.round_grey);

        txtProfileNm.setText(name);


        if (username.equals("")) {
            txtUsrname.setText("@username");
        } else {
            txtUsrname.setText(username);
        }

        if (interest.contains(" ")) {
            interest = interest.replaceAll(" ", "");
        }

        ArrayList<String> myList = new ArrayList<String>(Arrays.asList(interest.split(",")));

        gridview.setAdapter(new Show_resultsAdapter(myList));


    }

    public class Show_resultsAdapter extends BaseAdapter {


        ArrayList<String> intrests_iconarr = new ArrayList<String>();

        private LayoutInflater layoutInflater;


        public Show_resultsAdapter(ArrayList<String> intrests_icon) {

            layoutInflater = LayoutInflater.from(getActivity());

            intrests_iconarr.clear();

            intrests_iconarr = intrests_icon;


        }

        @Override
        public int getCount() {
            return intrests_iconarr.size();
        }

        @Override
        public Object getItem(int position) {
            return intrests_iconarr.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.custom_intrests, null);
                holder = new ViewHolder();

                holder.imgIntrests = (ImageView) convertView.findViewById(R.id.imgIntrests);


                convertView.setTag(holder);
                holder.imgIntrests.setId(position);

                Log.e("images", "" + position);


            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //Set the text here!

            String img_id = intrests_iconarr.get(position);

            if (img_id.equals("1")) {
                holder.imgIntrests.setImageResource(R.drawable.edit_1);
            } else if (img_id.equals("2")) {
                holder.imgIntrests.setImageResource(R.drawable.edit_2);
            } else if (img_id.equals("3")) {
                holder.imgIntrests.setImageResource(R.drawable.edit_3);
            } else if (img_id.equals("4")) {
                holder.imgIntrests.setImageResource(R.drawable.edit_4);
            } else if (img_id.equals("5")) {
                holder.imgIntrests.setImageResource(R.drawable.edit_5);
            } else if (img_id.equals("6")) {
                holder.imgIntrests.setImageResource(R.drawable.edit_6);
            } else if (img_id.equals("7")) {
                holder.imgIntrests.setImageResource(R.drawable.edit_7);
            } else if (img_id.equals("8")) {
                holder.imgIntrests.setImageResource(R.drawable.edit_8);
            } else if (img_id.equals("9")) {
                holder.imgIntrests.setImageResource(R.drawable.edit_9);
            } else if (img_id.equals("10")) {
                holder.imgIntrests.setImageResource(R.drawable.edit_10);
            } else if (img_id.equals("11")) {
                holder.imgIntrests.setImageResource(R.drawable.edit_11);
            } else if (img_id.equals("12")) {
                holder.imgIntrests.setImageResource(R.drawable.edit_12);
            } else if (img_id.equals("13")) {
                holder.imgIntrests.setImageResource(R.drawable.edit_13);
            } else if (img_id.equals("14")) {
                holder.imgIntrests.setImageResource(R.drawable.edit_14);
            } else if (img_id.equals("15")) {
                holder.imgIntrests.setImageResource(R.drawable.edit_1);
            }


//            holder.txtshop_name.setText("Name :" + headingarr.get(position));
//            holder.txtshop_address.setText("City : " + addressarr.get(position));
//            holder.id_ofr_button.setText(offers_arr.get(position));
//
//            String imageUrlarrr = imageUrlarr.get(position);
//            String img_log = shop_iconarr.get(position);


            return convertView;
        }

        class ViewHolder {
            ImageView imgIntrests;

        }
    }


    public void showDeckDialog() {


        dialogDeck = new Dialog(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.custom_create_deck, null);
        ImageView imgcancel = (ImageView) v.findViewById(R.id.imgcancel);
        TextView txtSave = (TextView) v.findViewById(R.id.txtSave);
        final TextView edtDeckTitle = (TextView) v.findViewById(R.id.edtDeckTitle);
        final TextView edtDeckDes = (TextView) v.findViewById(R.id.edtDeckDes);

        ToggleButton toggleprivacyToggleButton = (ToggleButton) v.findViewById(R.id.toggleprivacy);

        imgcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDeck.dismiss();
            }
        });

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strDeckTitle = edtDeckTitle.getText().toString();
                strDeckDesc = edtDeckDes.getText().toString();

                if (strDeckDesc.equals("")) {
                    Toast.makeText(getActivity(), "Please provide deck title", Toast.LENGTH_SHORT).show();
                } else if (strDeckDesc.equals("")) {
                    Toast.makeText(getActivity(), "Please give deck description", Toast.LENGTH_SHORT).show();

                } else {

                    dialogDeck.dismiss();

                    EditInterest interest = new EditInterest();
                    interest.execute();
                }

            }
        });


        toggleprivacyToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    type = "1";
                } else {
                    type = "2";
                }
            }
        });

        dialogDeck.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogDeck.setContentView(v);
        dialogDeck.setCancelable(false);
        dialogDeck.show();
    }

    public class EditInterest extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {


            String url = "http://35.162.179.154/neibobackend/public/api/addeditdeck";
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("title", strDeckTitle));
            list.add(new BasicNameValuePair("description", strDeckDesc));
            list.add(new BasicNameValuePair("type", type));


            response = Common_methods.getResponseBRest(getActivity(), url, list);


            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Common_methods.showDialog.dismiss();

            if (s != null) {

                if (s.contains("Success")) {
                    Toast.makeText(getActivity(), "Deck created successfully.", Toast.LENGTH_SHORT).show();

                    try {
                        JSONObject obj = new JSONObject(s);
                        String deck_data = obj.getString("deck_data");

                        edt.putString("decks", deck_data);
                        edt.commit();

                        Intent it = new Intent(getActivity(), Homepage.class);
                        it.putExtra("meActivity", "meActivity");
                        startActivity(it);

                        getActivity().finish();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Common_methods.showDialog.show();
        }
    }


}

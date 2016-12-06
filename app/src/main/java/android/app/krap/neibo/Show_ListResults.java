package android.app.krap.neibo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Android on 11/24/2016.
 */
public class Show_ListResults extends BaseAdapter {

    Activity activity;
    ArrayList<String> listTitle = new ArrayList<String>();
    ArrayList<String> listcategory = new ArrayList<String>();
    ArrayList<String> listdistance = new ArrayList<String>();
    ArrayList<String> listrating = new ArrayList<String>();
    ArrayList<String> listtips = new ArrayList<String>();
    ArrayList<String> listurl = new ArrayList<String>();
    ArrayList<String> listcategory_id = new ArrayList<String>();

    private LayoutInflater layoutInflater;


    public Show_ListResults(Activity act, ArrayList<String> title, ArrayList<String> category, ArrayList<String> distance, ArrayList<String> rating, ArrayList<String> tips, ArrayList<String> url, ArrayList<String> category_id) {

        layoutInflater = LayoutInflater.from(act);

        this.activity = act;
        this.notifyDataSetChanged();


        listTitle.clear();
        listcategory.clear();
        listdistance.clear();
        listrating.clear();
        listtips.clear();
        listdistance.clear();
        listurl.clear();
        listcategory_id.clear();


        listTitle = title;
        listcategory = category;
        listdistance = distance;
        listrating = rating;
        listtips = tips;
        listdistance = distance;
        listurl = url;
        listcategory_id = category_id;


    }

    @Override
    public int getCount() {
        return listTitle.size();
    }

    @Override
    public Object getItem(int position) {
        return listTitle.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.custom_search_cells, null);
            holder = new ViewHolder();

            holder.txt1 = (TextView) convertView.findViewById(R.id.txt1);
            holder.txt2 = (TextView) convertView.findViewById(R.id.txt2);
            holder.txt3 = (TextView) convertView.findViewById(R.id.txt3);
            holder.txt4 = (TextView) convertView.findViewById(R.id.txt4);
            holder.txt5 = (TextView) convertView.findViewById(R.id.txt5);
            holder.logo_img = (ImageView) convertView.findViewById(R.id.logo_img);
            holder.linearCels = (LinearLayout) convertView.findViewById(R.id.linearCels);


            convertView.setTag(holder);
            holder.linearCels.setId(position);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //Set the text here!

        String img_id = listTitle.get(position);


        holder.txt1.setText(listTitle.get(position));
        holder.txt2.setText(listcategory.get(position));
        holder.txt3.setText(listdistance.get(position));
        holder.txt4.setText(listrating.get(position));
        holder.txt5.setText(listtips.get(position));

        // holder.news_DETAIL.setText(listdescription.get(position));
        //  holder.news_time.setText(listdate_time.get(position));


        new Common_methods.BackTask(activity, holder.logo_img, listurl.get(position)).execute();

//            holder.txtshop_address.setText("City : " + addressarr.get(position));
//            holder.id_ofr_button.setText(offers_arr.get(position));
//
//            String imageUrlarrr = imageUrlarr.get(position);
//            String img_log = shop_iconarr.get(position);


        return convertView;
    }

    class ViewHolder {

        TextView txt1;
        TextView txt2;
        TextView txt3;
        TextView txt4;
        TextView txt5;
        ImageView logo_img;
        LinearLayout linearCels;


    }
}

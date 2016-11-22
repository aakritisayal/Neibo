package android.app.krap.neibo;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.ArrayList;

/**
 * Created by Android on 5/19/2016.
 */
public class Show_resultsAdapter extends BaseAdapter {

    ArrayList<String> headingarr = new ArrayList<String>();
    ArrayList<String> addressarr = new ArrayList<String>();
    ArrayList<String> imageUrlarr = new ArrayList<String>();


    private LayoutInflater layoutInflater;




    public Show_resultsAdapter(Activity ct, ArrayList<String> heading, ArrayList<String> address, ArrayList<String> imageUrl) {

        layoutInflater = LayoutInflater.from(ct);
        headingarr = heading;
        addressarr = address;
        imageUrlarr = imageUrl;



    }

    @Override
    public int getCount() {
        return headingarr.size();
    }

    @Override
    public Object getItem(int position) {
        return headingarr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.custom_entrtnmt_layout, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            holder.txtbody = (TextView) convertView.findViewById(R.id.txtbody);
            holder.idimage = (ImageView) convertView.findViewById(R.id.idimage);


            convertView.setTag(holder);


            Log.e("images", "" + position);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //Set the text here!
        holder.txtTitle.setText(headingarr.get(position));
        holder.txtbody.setText(addressarr.get(position));


        String imageUrlarrr = imageUrlarr.get(position);


        UrlImageViewHelper.setUrlDrawable(holder.idimage, imageUrlarr.get(position), R.drawable.neibo_logo);




        return convertView;
    }

    class ViewHolder {
        TextView txtTitle;
        TextView txtbody;
        ImageView idimage;

    }
}

package android.app.krap.neibo;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Android on 12/1/2016.
 */
public class Show_music  extends BaseAdapter {

    Activity activity;
    ArrayList<String> listTtitle = new ArrayList<String>();
    ArrayList<String> listDescription = new ArrayList<String>();
    ArrayList<String> listDate_time = new ArrayList<String>();
    ArrayList<String> listUrl = new ArrayList<String>();

    private LayoutInflater layoutInflater;

    public Show_music(Activity act, ArrayList<String> title, ArrayList<String> description, ArrayList<String> date_time, ArrayList<String> url) {
        this.activity = act;
        layoutInflater = LayoutInflater.from(act);

        this.notifyDataSetChanged();

        listTtitle.clear();
        listDescription.clear();
        listDate_time.clear();
        listUrl.clear();

        listTtitle = title;
        listDescription = description;
        listDate_time = date_time;
        listUrl = url;
    }


    @Override
    public int getCount() {
        return listTtitle.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.custom_news, null);
            holder = new ViewHolder();

            holder.news_heading = (TextView) convertView.findViewById(R.id.news_heading);
            holder.news_time = (TextView) convertView.findViewById(R.id.news_time);
            holder.news_DETAIL = (TextView) convertView.findViewById(R.id.news_DETAIL);
            holder.news_icon = (ImageView) convertView.findViewById(R.id.news_icon);


            convertView.setTag(holder);
            holder.news_heading.setId(position);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }






            if(position < listDate_time.size()) {

                holder.news_heading.setText(listTtitle.get(position));
                // holder.news_DETAIL.setText(listdescription.get(position));
                holder.news_time.setText(listDate_time.get(position));



                Log.e("date_size","" +listDate_time.size());




                new Common_methods.BackTask(activity, holder.news_icon, listUrl.get(position)).execute();

            }






        return convertView;
    }

    class ViewHolder {

        TextView news_heading;
        TextView news_DETAIL;
        TextView news_time;

        ImageView news_icon;


    }
}

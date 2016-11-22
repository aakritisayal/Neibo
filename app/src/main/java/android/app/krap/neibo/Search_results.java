package android.app.krap.neibo;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.ArrayList;

/**
 * Created by Android on 5/19/2016.
 */


public class Search_results extends RecyclerView.Adapter<Search_results.MyViewHolder> {

    ArrayList<String> headingarr = new ArrayList<String>();
    ArrayList<String> arrSubheading = new ArrayList<String>();
    ArrayList<String> imageUrlarr = new ArrayList<String>();
    Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle, txtbody;
        public ImageView idimage;

        public MyViewHolder(View view) {
            super(view);
            txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            txtbody = (TextView) view.findViewById(R.id.txtbody);
            idimage = (ImageView) view.findViewById(R.id.idimage);
        }
    }


    public Search_results(Activity activi,ArrayList<String> heading, ArrayList<String> Subheading, ArrayList<String> imageUrl) {

        activity =activi;
        headingarr = heading;
        arrSubheading = Subheading;
        imageUrlarr = imageUrl;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_entrtnmt_layout, parent, false);

        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txtTitle.setText(headingarr.get(position));
        holder.txtbody.setText(arrSubheading.get(position));

        String imageUrlarrr = imageUrlarr.get(position);

        UrlImageViewHelper.setUrlDrawable(holder.idimage, imageUrlarr.get(position), R.drawable.neibo_pink);
    }

    @Override
    public int getItemCount() {
        Log.e("size","" +headingarr.size());

        return headingarr.size();
    }
}


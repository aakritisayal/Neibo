package android.app.krap.neibo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Android on 10/25/2016.
 */
public class News extends Fragment {

    ImageView img_asia;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.news,null);

        img_asia =(ImageView) v.findViewById(R.id.img_asia);

        img_asia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(),Asia_news.class);
                startActivity(it);
            }
        });
        return  v;
    }
}

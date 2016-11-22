package android.app.krap.neibo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Android on 11/3/2016.
 */
public class Featured_fragment extends Fragment {

    LinearLayout lnrFtrdCells;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.featured_fragment,null);
        lnrFtrdCells =(LinearLayout) v.findViewById(R.id.lnrFtrdCells);

        for(int i=0;i<2;i++){
            LayoutInflater inflator = getActivity().getLayoutInflater();
            View view = inflator.inflate(R.layout.custom_featuredcell,null);
            lnrFtrdCells.addView(view);

        }

        return v;
    }

}

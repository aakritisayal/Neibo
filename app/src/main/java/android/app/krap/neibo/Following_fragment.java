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
public class Following_fragment extends Fragment {

    LinearLayout lnrFollowCell,lnrFollowCell1;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.following_fragment,null);

        lnrFollowCell =(LinearLayout) v.findViewById(R.id.lnrFollowCell);
        lnrFollowCell1 =(LinearLayout) v.findViewById(R.id.lnrFollowCell1);

        for (int i =0;i<3;i++){
            LayoutInflater inflator = getActivity().getLayoutInflater();
            View view = inflator.inflate(R.layout.custom_followingfrnds,null);
            lnrFollowCell.addView(view);
        }


        for (int i =0;i<1;i++){
            LayoutInflater inflator = getActivity().getLayoutInflater();
            View view = inflator.inflate(R.layout.custom_followingfrnds,null);
            lnrFollowCell1.addView(view);
        }


        return v;
    }
}

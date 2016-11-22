package android.app.krap.neibo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Android on 10/25/2016.
 */
public class Me_fragment extends Fragment{

//  ImageView clickSettings;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.me_fragment,null);
    //    clickSettings =(ImageView) v.findViewById(R.id.clickSettings);


        changeFragment(new Me_activity1());


        return  v;
    }

    public void showFragment(Fragment fragment){

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        fm.beginTransaction();

    //    Fragment fragOne = new SubFragment();
        ft.show(fragment);

    }


    private void changeFragment(Fragment targetFragment) {

        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }






}

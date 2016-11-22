package android.app.krap.neibo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Android on 10/28/2016.
 */
public class Me_fragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.me_fragment1,null);
       // changeFragment(new Me_more());


        return v;
    }

    private void changeFragment(Fragment targetFragment) {

        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment1, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}

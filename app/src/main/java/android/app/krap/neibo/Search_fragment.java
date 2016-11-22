package android.app.krap.neibo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wefika.horizontalpicker.HorizontalPicker;

import java.util.ArrayList;

/**
 * Created by Android on 10/25/2016.
 */
public class Search_fragment extends Fragment {

    EditText edtSearch;
    ArrayList<String> value = new ArrayList<>();
  //  ViewPager pager_fragment;
    ArrayList<Fragment> list = new ArrayList<>();
    HorizontalPicker picker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_fragment, null);


      //  pager_fragment = (ViewPager) v.findViewById(R.id.pager_fragment);
        picker = (HorizontalPicker) v.findViewById(R.id.picker);


        //      picker.setSelectedItem(2);

        list.clear();


        changeFragment(new Today());

//        list.add(new Today());
//        list.add(new Entertainment());
//        list.add(new Movies());
//        list.add(new Travel());
//        list.add(new Events());
//        list.add(new News());
//        list.add(new Near_byplaces());
//        list.add(new Shopping());


//        pager_fragment.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//              //  Log.e("positio", "" + position);
//
//            }
//
//
//
//            @Override
//            public void onPageSelected(int position) {
//                if (position == 0) {
//                    picker.setSelectedItem(0);
//                    pager_fragment.setCurrentItem(0);
//
//                //    Today.callNewsApi(getActivity());
//
//                }
//                if (position == 1) {
//                    picker.setSelectedItem(1);
//                    pager_fragment.setCurrentItem(1);
//
//                    Entertainment.callApi();
//                }
//                if (position == 2) {
//                    picker.setSelectedItem(2);
//                    pager_fragment.setCurrentItem(2);
//                }
//                if (position == 3) {
//                    picker.setSelectedItem(3);
//                    pager_fragment.setCurrentItem(3);
//                }
//                if (position == 4) {
//                    picker.setSelectedItem(4);
//                    pager_fragment.setCurrentItem(4);
//                }
//                if (position == 5) {
//                    picker.setSelectedItem(5);
//                    pager_fragment.setCurrentItem(5);
//                }
//                if (position == 6) {
//                    picker.setSelectedItem(6);
//                    pager_fragment.setCurrentItem(6);
//
//                    Near_byplaces.callNearByApi(getActivity());
//
//                }
//                if (position == 7) {
//                    picker.setSelectedItem(7);
//                    pager_fragment.setCurrentItem(7);
//                }
//
//
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });


        picker.setOnItemSelectedListener(new HorizontalPicker.OnItemSelected() {
            @Override
            public void onItemSelected(int index) {

                Toast.makeText(getActivity(), String.valueOf(index), Toast.LENGTH_SHORT).show();

                if (index == 0) {

                    changeFragment(new Today());
                    Entertainment.cancelEntApi(getActivity());

                   // pager_fragment.setCurrentItem(0);
                }
                if (index == 1) {
                   // pager_fragment.setCurrentItem(1);

                    changeFragment(new Entertainment());
                    Today.cancelTodayApi(getActivity());

                    //  Entertainment.callApi(getActivity());

                }
                if (index == 2) {



                    changeFragment(new Movies());


                  //  pager_fragment.setCurrentItem(2);
                }
                if (index == 3) {
                   // pager_fragment.setCurrentItem(3);



                    changeFragment(new Travel());
                }
                if (index == 4) {
                 //   pager_fragment.setCurrentItem(4);



                    changeFragment(new Events());
                }
                if (index == 5) {



                    changeFragment(new News());

                  //  pager_fragment.setCurrentItem(5);
                }
                if (index == 6) {




                    changeFragment(new Near_byplaces());

                   // pager_fragment.setCurrentItem(6);
                }
                if (index == 7) {



                    changeFragment(new Shopping());
                  //  pager_fragment.setCurrentItem(7);
                }
            }
        });


//        pager_fragment.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
//            @Override
//            public Fragment getItem(int position) {
//                return list.get(position);
//            }
//
//            @Override
//            public int getCount() {
//                return list.size();
//            }
//        });

        return v;
    }


    private void changeFragment(Fragment targetFragment) {

        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


}

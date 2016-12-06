package android.app.krap.neibo;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wefika.horizontalpicker.HorizontalPicker;

import java.util.ArrayList;

/**
 * Created by Android on 10/25/2016.
 */
public class Search_fragment extends Fragment {

    TextView edtSearch;
    ArrayList<String> value = new ArrayList<>();
      ViewPager pager_fragment;
    ArrayList<Fragment> list = new ArrayList<>();
    HorizontalPicker picker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_fragment, null);


          pager_fragment = (ViewPager) v.findViewById(R.id.pager_fragment);
        picker = (HorizontalPicker) v.findViewById(R.id.picker);
        edtSearch = (TextView) v.findViewById(R.id.edtSearch);


        edtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(getActivity(), Search_activity.class);
                startActivity(it);

            }
        });

            // picker.setSelectedItem(2);



        list.clear();


      //  changeFragment(new Today());

        list.add(new Today());
        list.add(new Entertainment());
        list.add(new Book_table());
        list.add(new Movies());
        list.add(new Travel());
        list.add(new Events());
        list.add(new News());
        list.add(new Near_byplaces());
        list.add(new Shopping());

        pager_fragment.setOffscreenPageLimit(9);


        pager_fragment.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
              //  Log.e("positio", "" + position);

            }



            @Override
            public void onPageSelected(int index) {
                if (index == 0) {

                   // changeFragment(new Today());
                    Entertainment.cancelEntApi(getActivity());
                    picker.setSelectedItem(0);
                     //pager_fragment.setCurrentItem(0);
                }
                if (index == 1) {
                    // pager_fragment.setCurrentItem(1);

                  //  changeFragment(new Entertainment());
                    Today.cancelTodayApi(getActivity());
                    picker.setSelectedItem(1);
                    //  Entertainment.callApi(getActivity());

                }

                if (index == 2) {
                  //   pager_fragment.setCurrentItem(1);

                  //  changeFragment(new Book_table());
                    Entertainment.cancelEntApi(getActivity());
                    picker.setSelectedItem(2);

                    //  Entertainment.callApi(getActivity());

                }

                if (index == 3) {


                  //  changeFragment(new Movies());
                    picker.setSelectedItem(3);

                    //  pager_fragment.setCurrentItem(2);
                }
                if (index == 4) {
                   //  pager_fragment.setCurrentItem(3);

                    picker.setSelectedItem(4);
                  //  changeFragment(new Travel());
                }
                if (index == 5) {
                     //  pager_fragment.setCurrentItem(4);

                    picker.setSelectedItem(5);
                   // changeFragment(new Events());
                }
                if (index == 6) {
                    picker.setSelectedItem(6);

                   // changeFragment(new News());
                    Near_byplaces.cancelNear_api();

                    //  pager_fragment.setCurrentItem(5);
                }
                if (index == 7) {
                    picker.setSelectedItem(7);

                   // changeFragment(new Near_byplaces());
                    Shopping.cancelEntApi();

                   // pager_fragment.setCurrentItem(6);
                }
                if (index == 8) {

                    picker.setSelectedItem(8);
                   // changeFragment(new Shopping());
                    Near_byplaces.cancelNear_api();


                     // pager_fragment.setCurrentItem(7);
                }



            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        picker.setOnItemSelectedListener(new HorizontalPicker.OnItemSelected() {
            @Override
            public void onItemSelected(int index) {

                //  Toast.makeText(getActivity(), String.valueOf(index), Toast.LENGTH_SHORT).show();

                if (index == 0) {

                   // changeFragment(new Today());
                    Entertainment.cancelEntApi(getActivity());

                     pager_fragment.setCurrentItem(0);
                }
                if (index == 1) {
                    pager_fragment.setCurrentItem(1);

                  //  changeFragment(new Entertainment());
                    Today.cancelTodayApi(getActivity());

                    //  Entertainment.callApi(getActivity());

                }

                if (index == 2) {
                     pager_fragment.setCurrentItem(2);

                //    changeFragment(new Book_table());
                    Entertainment.cancelEntApi(getActivity());

                    //  Entertainment.callApi(getActivity());

                }

                if (index == 3) {


                  //  changeFragment(new Movies());


                      pager_fragment.setCurrentItem(3);
                }
                if (index == 4) {
                     pager_fragment.setCurrentItem(4);


                  //  changeFragment(new Travel());
                }
                if (index == 5) {
                       pager_fragment.setCurrentItem(5);


                  //  changeFragment(new Events());
                }
                if (index == 6) {


                  //  changeFragment(new News());
                    Near_byplaces.cancelNear_api();

                      pager_fragment.setCurrentItem(6);
                }
                if (index == 7) {


                  //  changeFragment(new Near_byplaces());
                    Shopping.cancelEntApi();

                     pager_fragment.setCurrentItem(7);
                }
                if (index == 8) {


                  //  changeFragment(new Shopping());
                    Near_byplaces.cancelNear_api();


                      pager_fragment.setCurrentItem(8);
                }
            }
        });


        pager_fragment.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        return v;
    }


//    private void changeFragment(Fragment targetFragment) {
//
//        getChildFragmentManager()
//                .beginTransaction()
//                .replace(R.id.main_fragment, targetFragment, "fragment")
//                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                .commit();
//    }


}

package android.app.krap.neibo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Android on 10/25/2016.
 */
public class Feed_fragment extends Fragment {

    LinearLayout lnrFollowing,lnrFeatured;
    TextView txtFollowing,txtFeatured;

    ViewPager pager_fragment;
    ArrayList<Fragment> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.feed_fragment,null);

        lnrFollowing = (LinearLayout) v.findViewById(R.id.lnrFollowing);
        lnrFeatured =(LinearLayout) v.findViewById(R.id.lnrFeatured);

        txtFollowing =(TextView) v.findViewById(R.id.txtFollowing);
        txtFeatured =(TextView) v.findViewById(R.id.txtFeatured);

        pager_fragment =(ViewPager) v.findViewById(R.id.pager);

        list.clear();

        list.add(new Following_fragment());
        list.add(new Featured_fragment());

        lnrFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pager_fragment.setCurrentItem(0);

                txtFollowing.setTextColor(getResources().getColor(R.color.pink_dark));
                txtFeatured.setTextColor(getResources().getColor(R.color.dark_grey));

            }
        });

        lnrFeatured.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pager_fragment.setCurrentItem(1);

                txtFollowing.setTextColor(getResources().getColor(R.color.dark_grey));
                txtFeatured.setTextColor(getResources().getColor(R.color.pink_dark));
            }
        });


        pager_fragment.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    txtFollowing.setTextColor(getResources().getColor(R.color.pink_dark));
                    txtFeatured.setTextColor(getResources().getColor(R.color.dark_grey));
                }

                if (position == 1) {
                    txtFollowing.setTextColor(getResources().getColor(R.color.dark_grey));
                    txtFeatured.setTextColor(getResources().getColor(R.color.pink_dark));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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

        return  v;
    }
}

package android.app.krap.neibo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Android on 10/21/2016.
 */
public class Homepage extends FragmentActivity {

    ViewPager pager_fragment;
    ArrayList<Fragment> fragment = new ArrayList<>();
    TextView txtSearch, txt_friends, txt_feed, txt_notifications, txt_me;
    ImageView img_search, img_friends, img_feed, img_notification, img_me;
    LinearLayout linear_frag1, linear_frag2, linear_frag3, linear_frag4, linear_frag5;

    Intent it;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);


       it = getIntent();


        pager_fragment = (ViewPager) findViewById(R.id.pager_fragment);

        linear_frag1 = (LinearLayout) findViewById(R.id.linear_frag1);
        linear_frag2 = (LinearLayout) findViewById(R.id.linear_frag2);
        linear_frag3 = (LinearLayout) findViewById(R.id.linear_frag3);
        linear_frag4 = (LinearLayout) findViewById(R.id.linear_frag4);
        linear_frag5 = (LinearLayout) findViewById(R.id.linear_frag5);

        txtSearch = (TextView) findViewById(R.id.txtSearch);
        txt_friends = (TextView) findViewById(R.id.txt_friends);
        txt_feed = (TextView) findViewById(R.id.txt_feed);
        txt_notifications = (TextView) findViewById(R.id.txt_notifications);
        txt_me = (TextView) findViewById(R.id.txt_me);

        img_search = (ImageView) findViewById(R.id.img_search);
        img_friends = (ImageView) findViewById(R.id.img_friends);
        img_feed = (ImageView) findViewById(R.id.img_feed);
        img_notification = (ImageView) findViewById(R.id.img_notification);
        img_me = (ImageView) findViewById(R.id.img_me);




        fragment.add(new Search_fragment());
        fragment.add(new Friends_fragment());
        fragment.add(new Feed_fragment());
        fragment.add(new Notification_fragment());
        fragment.add(new Me_fragment());



        pager_fragment.setCurrentItem(3);

        pager_fragment.postDelayed(new Runnable() {
            @Override
            public void run() {

              String meactivity=  it.getStringExtra("meActivity");

                if(meactivity!=null){
                    pager_fragment.setCurrentItem(4, true);
                }
                else{

                    Log.e("view","");
                   // pager_fragment.setCurrentItem(3, true);
                }


            }
        }, 100);

        linear_frag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pager_fragment.setCurrentItem(0);

                txtSearch.setTextColor(Color.parseColor("#FF2A55"));
                txt_friends.setTextColor(Color.parseColor("#808285"));
                txt_feed.setTextColor(Color.parseColor("#808285"));
                txt_notifications.setTextColor(Color.parseColor("#808285"));
                txt_me.setTextColor(Color.parseColor("#808285"));

                img_search.setImageResource(R.drawable.search_img_pink);
                img_friends.setImageResource(R.drawable.friends);
                img_feed.setImageResource(R.drawable.feed_img);
                img_notification.setImageResource(R.drawable.notification);
                img_me.setImageResource(R.drawable.me);

            }
        });


        linear_frag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pager_fragment.setCurrentItem(1);

                txtSearch.setTextColor(Color.parseColor("#808285"));
                txt_friends.setTextColor(Color.parseColor("#FF2A55"));
                txt_feed.setTextColor(Color.parseColor("#808285"));
                txt_notifications.setTextColor(Color.parseColor("#808285"));
                txt_me.setTextColor(Color.parseColor("#808285"));

                img_search.setImageResource(R.drawable.search_img);
                img_friends.setImageResource(R.drawable.friends_img_pink);
                img_feed.setImageResource(R.drawable.feed_img);
                img_notification.setImageResource(R.drawable.notification);
                img_me.setImageResource(R.drawable.me);

            }
        });


        linear_frag3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pager_fragment.setCurrentItem(2);

                txtSearch.setTextColor(Color.parseColor("#808285"));
                txt_friends.setTextColor(Color.parseColor("#808285"));
                txt_feed.setTextColor(Color.parseColor("#FF2A55"));
                txt_notifications.setTextColor(Color.parseColor("#808285"));
                txt_me.setTextColor(Color.parseColor("#808285"));

                img_search.setImageResource(R.drawable.search_img);
                img_friends.setImageResource(R.drawable.friends);
                img_feed.setImageResource(R.drawable.feed_icon_pink);
                img_notification.setImageResource(R.drawable.notification);
                img_me.setImageResource(R.drawable.me);

            }
        });


        linear_frag4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pager_fragment.setCurrentItem(3);

                txtSearch.setTextColor(Color.parseColor("#808285"));
                txt_friends.setTextColor(Color.parseColor("#808285"));
                txt_feed.setTextColor(Color.parseColor("#808285"));
                txt_notifications.setTextColor(Color.parseColor("#FF2A55"));
                txt_me.setTextColor(Color.parseColor("#808285"));

                img_search.setImageResource(R.drawable.search_img);
                img_friends.setImageResource(R.drawable.friends);
                img_feed.setImageResource(R.drawable.feed_img);
                img_notification.setImageResource(R.drawable.notifications_pink);
                img_me.setImageResource(R.drawable.me);


            }
        });


        linear_frag5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pager_fragment.setCurrentItem(4);

                txtSearch.setTextColor(Color.parseColor("#808285"));
                txt_friends.setTextColor(Color.parseColor("#808285"));
                txt_feed.setTextColor(Color.parseColor("#808285"));
                txt_notifications.setTextColor(Color.parseColor("#808285"));
                txt_me.setTextColor(Color.parseColor("#FF2A55"));

                img_search.setImageResource(R.drawable.search_img);
                img_friends.setImageResource(R.drawable.friends);
                img_feed.setImageResource(R.drawable.feed_img);
                img_notification.setImageResource(R.drawable.notification);
                img_me.setImageResource(R.drawable.me_icon_pink);

            }
        });


        pager_fragment.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {

                Log.e("position", "" + position);

                if (position == 0) {
                    txtSearch.setTextColor(Color.parseColor("#FF2A55"));
                    txt_friends.setTextColor(Color.parseColor("#808285"));
                    txt_feed.setTextColor(Color.parseColor("#808285"));
                    txt_notifications.setTextColor(Color.parseColor("#808285"));
                    txt_me.setTextColor(Color.parseColor("#808285"));

                    img_search.setImageResource(R.drawable.search_img_pink);
                    img_friends.setImageResource(R.drawable.friends);
                    img_feed.setImageResource(R.drawable.feed_img);
                    img_notification.setImageResource(R.drawable.notification);
                    img_me.setImageResource(R.drawable.me);

                }
                if (position == 1) {
                    txtSearch.setTextColor(Color.parseColor("#808285"));
                    txt_friends.setTextColor(Color.parseColor("#FF2A55"));
                    txt_feed.setTextColor(Color.parseColor("#808285"));
                    txt_notifications.setTextColor(Color.parseColor("#808285"));
                    txt_me.setTextColor(Color.parseColor("#808285"));

                    img_search.setImageResource(R.drawable.search_img);
                    img_friends.setImageResource(R.drawable.friends_img_pink);
                    img_feed.setImageResource(R.drawable.feed_img);
                    img_notification.setImageResource(R.drawable.notification);
                    img_me.setImageResource(R.drawable.me);
                }
                if (position == 2) {
                    txtSearch.setTextColor(Color.parseColor("#808285"));
                    txt_friends.setTextColor(Color.parseColor("#808285"));
                    txt_feed.setTextColor(Color.parseColor("#FF2A55"));
                    txt_notifications.setTextColor(Color.parseColor("#808285"));
                    txt_me.setTextColor(Color.parseColor("#808285"));

                    img_search.setImageResource(R.drawable.search_img);
                    img_friends.setImageResource(R.drawable.friends);
                    img_feed.setImageResource(R.drawable.feed_icon_pink);
                    img_notification.setImageResource(R.drawable.notification);
                    img_me.setImageResource(R.drawable.me);
                }
                if (position == 3) {
                    txtSearch.setTextColor(Color.parseColor("#808285"));
                    txt_friends.setTextColor(Color.parseColor("#808285"));
                    txt_feed.setTextColor(Color.parseColor("#808285"));
                    txt_notifications.setTextColor(Color.parseColor("#FF2A55"));
                    txt_me.setTextColor(Color.parseColor("#808285"));

                    img_search.setImageResource(R.drawable.search_img);
                    img_friends.setImageResource(R.drawable.friends);
                    img_feed.setImageResource(R.drawable.feed_img);
                    img_notification.setImageResource(R.drawable.notifications_pink);
                    img_me.setImageResource(R.drawable.me);
                }
                if (position == 4) {
                    txtSearch.setTextColor(Color.parseColor("#808285"));
                    txt_friends.setTextColor(Color.parseColor("#808285"));
                    txt_feed.setTextColor(Color.parseColor("#808285"));
                    txt_notifications.setTextColor(Color.parseColor("#808285"));
                    txt_me.setTextColor(Color.parseColor("#FF2A55"));

                    img_search.setImageResource(R.drawable.search_img);
                    img_friends.setImageResource(R.drawable.friends);
                    img_feed.setImageResource(R.drawable.feed_img);
                    img_notification.setImageResource(R.drawable.notification);
                    img_me.setImageResource(R.drawable.me_icon_pink);
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pager_fragment.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                return fragment.get(position);
            }

            @Override
            public int getCount() {
                return fragment.size();
            }
        });


    }
}

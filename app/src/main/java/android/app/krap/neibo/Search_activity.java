package android.app.krap.neibo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.wefika.horizontalpicker.HorizontalPicker;

import java.util.ArrayList;

/**
 * Created by Android on 10/25/2016.
 */
public class Search_activity extends FragmentActivity {

    ArrayList<String> value = new ArrayList<>();
    ViewPager pager_fragment;
    ArrayList<Fragment> fragment = new ArrayList<>();
    HorizontalPicker picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        pager_fragment = (ViewPager) findViewById(R.id.pager_fragment);
        picker = (HorizontalPicker) findViewById(R.id.picker);


        picker.setSelectedItem(2);


        picker.setOnItemSelectedListener(new HorizontalPicker.OnItemSelected() {
            @Override
            public void onItemSelected(int index) {

                Toast.makeText(Search_activity.this, String.valueOf(index), Toast.LENGTH_SHORT).show();
            }
        });


        //   edtSearch =(EditText) v.findViewById(R.id.edtSearch);

//        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
//
//                    if(edtSearch.getText().toString().equals("")){
//
//                        Toast.makeText(getActivity(), "Enter Text befor Submitt", Toast.LENGTH_SHORT).show();
//                    }
//
//                    else{
//
//                        Intent it = new Intent(getActivity(),Search_activity.class);
//                        startActivity(it);
//                    }
//
//                }
//                return false;
//            }
//        });

    }





}

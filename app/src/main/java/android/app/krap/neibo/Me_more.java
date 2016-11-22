package android.app.krap.neibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Android on 10/28/2016.
 */
public class Me_more extends Activity {
ImageView back;
    LinearLayout linearddFnd,linearSendFeed;
    TextView edtInterests,txtProfile,accountSettings,txtLogout;
    SharedPreferences sp;
    SharedPreferences.Editor edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_more);

        sp = getSharedPreferences("shared_value", Context.MODE_MULTI_PROCESS);
        edt = sp.edit();


        back=(ImageView) findViewById(R.id.back);
        linearddFnd =(LinearLayout) findViewById(R.id.linearddFnd);
        linearSendFeed =(LinearLayout) findViewById(R.id.linearSendFeed);
        edtInterests =(TextView) findViewById(R.id.edtInterests);
        txtProfile =(TextView) findViewById(R.id.txtProfile);
        accountSettings =(TextView) findViewById(R.id.accountSettings);
        txtLogout =(TextView) findViewById(R.id.txtLogout);


        accountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Me_more.this,Me_settings.class);
                startActivity(it);
            }
        });


        txtProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(Me_more.this,Me_edit.class);
                startActivity(it);

            }
        });

        linearddFnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(Me_more.this,Add_friends.class);
                startActivity(it);

            }
        });

        edtInterests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Me_more.this,Interests.class);
                startActivity(it);
            }
        });

        linearSendFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Me_more.this,Send_message.class);
                startActivity(it);
            }
        });

        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Me_more.this,MainActivity.class);

                edt.clear();
                edt.commit();
                edt.apply();

                startActivity(it);
                finish();
                finishAffinity();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             finish();
            }
        });

    }

}

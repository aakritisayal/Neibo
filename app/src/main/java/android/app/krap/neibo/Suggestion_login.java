package android.app.krap.neibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Android on 10/21/2016.
 */
public class Suggestion_login extends Activity {

    TextView already_acnt;
    Button btn_sign_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggestion_login);

        already_acnt =(TextView) findViewById(R.id.already_acnt);
        btn_sign_email =(Button) findViewById(R.id.btn_sign_email);

        already_acnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(Suggestion_login.this,Login.class);
                startActivity(it);

            }
        });

        btn_sign_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(Suggestion_login.this,Sign_up.class);
                startActivity(it);

            }
        });

    }
}

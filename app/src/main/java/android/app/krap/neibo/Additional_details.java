package android.app.krap.neibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Android on 10/28/2016.
 */
public class Additional_details extends Activity {

    TextView txtBack,txtSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.additional_details);

        txtSend =(TextView) findViewById(R.id.txtSend);
        txtBack =(TextView) findViewById(R.id.txtBack);

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(Additional_details.this,Me_more.class);

                Toast.makeText(Additional_details.this, "Feedback has been send", Toast.LENGTH_SHORT).show();

                startActivity(it);
                finish();
            }
        });
    }
}

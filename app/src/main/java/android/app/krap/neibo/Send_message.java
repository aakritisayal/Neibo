package android.app.krap.neibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Android on 10/28/2016.
 */
public class Send_message extends Activity {

    TextView txtCancel,txtNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_message);

        txtCancel =(TextView) findViewById(R.id.txtCancel);
        txtNext =(TextView) findViewById(R.id.txtNext);

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Send_message.this,Additional_details.class);
                startActivity(it);
            }
        });

    }
}

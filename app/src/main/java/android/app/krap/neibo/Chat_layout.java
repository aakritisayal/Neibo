package android.app.krap.neibo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Android on 11/3/2016.
 */
public class Chat_layout extends Activity {

    ImageView backcHAT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);

        backcHAT =(ImageView) findViewById(R.id.backcHAT);
        backcHAT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

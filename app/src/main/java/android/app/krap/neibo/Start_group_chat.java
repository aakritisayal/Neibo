package android.app.krap.neibo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Android on 11/3/2016.
 */
public class Start_group_chat extends Activity {

    ImageView backGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_group_chat);

        backGroup=(ImageView) findViewById(R.id.backGroup);

        backGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

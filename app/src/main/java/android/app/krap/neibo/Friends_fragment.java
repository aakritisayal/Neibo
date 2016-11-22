package android.app.krap.neibo;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Android on 10/25/2016.
 */
public class Friends_fragment extends Fragment{

    RelativeLayout add_friends,rltvGroupChat;
    LinearLayout lnrChat,lnrChat1,linearFriends;
    Dialog dialogFriends;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.friends_fragment,null);

        add_friends =(RelativeLayout) v.findViewById(R.id.add_friends);
        rltvGroupChat =(RelativeLayout) v.findViewById(R.id.rltvGroupChat);

        lnrChat=(LinearLayout) v.findViewById(R.id.lnrChat);
        lnrChat1=(LinearLayout) v.findViewById(R.id.lnrChat1);
        linearFriends =(LinearLayout) v.findViewById(R.id.linearFriends);

        linearFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();
            }
        });

        add_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(),Add_friends.class);
                startActivity(it);
            }
        });

        rltvGroupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(),Start_group_chat.class);
                startActivity(it);
            }
        });

        lnrChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(),Chat_layout.class);
                startActivity(it);
            }
        });

        lnrChat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(),Chat_layout.class);
                startActivity(it);
            }
        });

        return  v;
    }

    public void showDialog(){

        dialogFriends = new Dialog(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.custom_friends, null);
        ImageView imgcancel =(ImageView) v.findViewById(R.id.imgcancel);

        imgcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFriends.dismiss();
            }
        });

        dialogFriends.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFriends.setContentView(v);
        dialogFriends.setCancelable(false);
        dialogFriends.show();
    }

}

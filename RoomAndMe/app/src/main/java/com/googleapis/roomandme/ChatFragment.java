package com.googleapis.roomandme;




import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;
import com.googleapis.roomandme.ui.Main1Activity;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {


    public ChatFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v1 =  inflater.inflate(R.layout.fragment_chat, container, false);
        Button chatroom = v1.findViewById(R.id.button7);

        chatroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Main1Activity.class);
                startActivity(intent);


            }
        });
        return v1;


    }

}

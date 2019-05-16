package com.googleapis.roomandme;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static java.lang.Math.pow;

import static android.widget.Toast.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private Button submitRent;
    private EditText rentUp, rentDown;

    ArrayList<String> numberList = new ArrayList<>();
    ArrayList<String> numberList1 = new ArrayList<>();

    public double x=103.8404;
    public double y=1.2780;
    public double finalx,finaly;
    public int r1,r2;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_search, container, false);

        submitRent = view.findViewById(R.id.button5);
        rentUp = view.findViewById(R.id.editText12);
        rentDown = view.findViewById(R.id.editText11);


        submitRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_json();
            }
        });
        return view;
    }

    public void get_json()
    {
        String json;
        try{
            ArrayList<String> finalcoord = new ArrayList<>();
            InputStream is = getActivity().getAssets().open("new_json.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for(int i =0;i<jsonArray.length();i++)
            {
                JSONObject obj = jsonArray.getJSONObject(i);

                JSONArray jsonArray1 = obj.getJSONArray("rental");


                for(int j=0;j<jsonArray1.length();j++)
                {
                    JSONObject obj1 = jsonArray1.getJSONObject(j);

                    if((Integer.parseInt(obj1.getString("rent"))>Integer.parseInt(rentDown.getText().toString()))&& (Integer.parseInt(obj1.getString("rent"))<Integer.parseInt(rentUp.getText().toString())))
                    {

                        // if(!numberList.contains(obj.getString("y")))
                        numberList.add(obj.getString("lat"));


                        // if(!numberList1.contains(obj.getString("x")))
                        numberList1.add(obj.getString("lon"));
                    }


                }


            }
            int m=0;
            double min = pow(Double.parseDouble(numberList.get(m))-y,2)+pow(Double.parseDouble(numberList1.get(m))-x,2);
            for(int k=0;k<numberList.size();k++)
            {
                double d,e,f;
                d= Double.parseDouble(numberList.get(k));
                e= Double.parseDouble(numberList1.get(k));
                f= pow(d-y,2)+pow(e-x,2);
                if(f<min){
                    min=f;
                    finalx=e;
                    finaly=d;

                }


            }

            finalcoord.add(Double.toString(finaly));
            finalcoord.add(Double.toString(finalx));

            Toast toast1 = Toast.makeText(getActivity().getApplicationContext(), finalcoord.toString(), Toast.LENGTH_LONG);
            toast1.setGravity(Gravity.CENTER, 0, 0);
            toast1.show();



            //Toast.makeText(getActivity().getApplicationContext(),numberList.toString(),Toast.LENGTH_LONG).show();
            //for(int p=0;p<numberList.size();p++)
            {System.out.println(numberList1.size());}

        }catch (IOException e)
        {
            e.printStackTrace();
        }catch(JSONException e)
        {
            e.printStackTrace();
        }



    }



}

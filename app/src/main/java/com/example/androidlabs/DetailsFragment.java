package com.example.androidlabs;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    private boolean isTablet;
    private Bundle dataFromActivity;
    private AppCompatActivity parentActivity;

    public void setTablet(boolean tablet) { isTablet = tablet; }


    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        dataFromActivity = getArguments();
        String message = this.getArguments().getString("message");
        String id = this.getArguments().getString("id");
        String isSend = this.getArguments().getString("isSend");

        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.fragment_details, container, false);

        //show the message
        TextView fragmentTexMessage = (TextView)result.findViewById(R.id.fragmentTexMessage);
        fragmentTexMessage.setText(message);

        TextView fragmentTextID = (TextView)result.findViewById(R.id.fragmentTextID);
        fragmentTextID.setText("ID = " + id);

        if (isSend.equals("1")) {
            ((CheckBox) result.findViewById(R.id.fragmentCheckBox)).setChecked(true);
        }
        else {
            ((CheckBox) result.findViewById(R.id.fragmentCheckBox)).setChecked(false);
        }

        // get the delete button, and add a click listener:
        Button finishButton = (Button)result.findViewById(R.id.hideButton);
        finishButton.setOnClickListener( clk -> {

            //Tell the parent activity to remove
            parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
        });

        // Inflate the layout for this fragment
        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity)context;
    }
}

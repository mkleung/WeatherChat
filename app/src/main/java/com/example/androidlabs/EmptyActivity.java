package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class EmptyActivity extends AppCompatActivity {

    private boolean isTablet;
    private Bundle dataFromActivity;
    private long id;
    private long isSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        Intent intent = getIntent();
        String message = intent.getStringExtra("message");
        String id =  intent.getStringExtra("id");
        String isSend = intent.getStringExtra("isSend");

        //show the message
        TextView fragmentTexMessage = (TextView) findViewById(R.id.fragmentTexMessage);
        fragmentTexMessage.setText(message);

        TextView fragmentTextID = (TextView) findViewById(R.id.fragmentTextID);
        fragmentTextID.setText("ID = " + id + " isSend = " + isSend);

        if (isSend.equals("1")) {
            ((CheckBox) findViewById(R.id.fragmentCheckBox)).setChecked(true);
        }
        else {
            ((CheckBox) findViewById(R.id.fragmentCheckBox)).setChecked(false);
        }

    }
}

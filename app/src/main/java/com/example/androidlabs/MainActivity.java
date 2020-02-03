package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        setContentView(R.layout.activity_main_linear);

        // Step 4
        Button clickButton = findViewById(R.id.button2);
        clickButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                String toast_message = getString(R.string.toast_message);
                Toast.makeText(MainActivity.this, toast_message, Toast.LENGTH_LONG).show();
            }
        });

        // Step 5
        CheckBox cb = findViewById(R.id.checkBox);
        cb.setOnCheckedChangeListener( (compoundButton, b) -> {
            String snack_text = "The checkbox is ";
            if ( b == true) { snack_text += "on"; }
            else { snack_text += "off"; }
            Snackbar.make(cb, snack_text, Snackbar.LENGTH_LONG)
                    .setAction("Undo", click-> cb.setChecked( !b ))
                    .show();
        });

        Switch sw = findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener( (compoundButton, b) -> {
            String snack_text = "The switch is ";
            if ( b == true) { snack_text += "on"; }
            else { snack_text += "off"; }
            Snackbar.make(sw, snack_text, Snackbar.LENGTH_LONG)
                    .setAction("Undo", click-> cb.setChecked( !b ))
                    .show();
        });


    }
}

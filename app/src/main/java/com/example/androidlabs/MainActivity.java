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

        //setContentView(R.layout.activity_main_linear);

        setContentView(R.layout.activity_main_grid);

        // Step 4
        Button clickButton = findViewById(R.id.button2);
        clickButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                String toast_message = getString(R.string.toast_message);
                Toast.makeText(MainActivity.this, toast_message, Toast.LENGTH_LONG).show();
            }
        });

        // Step 5
        String on_message = getString(R.string.on_message);
        String off_message = getString(R.string.off_message);
        String undo_message = getString(R.string.undo_message);

        CheckBox cb = findViewById(R.id.checkBox);
        cb.setOnCheckedChangeListener( (compoundButton, b) -> {
            String checkbox_text = getString(R.string.checkbox_text);
            if ( b == true) { checkbox_text = checkbox_text + " " + on_message; }
            else { checkbox_text = checkbox_text + " " + off_message; }
            Snackbar.make(cb, checkbox_text, Snackbar.LENGTH_LONG)
                    .setAction(undo_message, click-> cb.setChecked( !b ))
                    .show();
        });

        Switch sw = findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener( (compoundButton, b) -> {
            String switch_text = getString(R.string.switch_text);
            if ( b == true) { switch_text = switch_text + " " + on_message; }
            else { switch_text = switch_text + " " + off_message; }
            Snackbar.make(sw, switch_text, Snackbar.LENGTH_LONG)
                    .setAction(undo_message, click-> cb.setChecked( !b ))
                    .show();
        });


    }
}

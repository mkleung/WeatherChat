package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    EditText emailText;
    Button loginButton;


    public static final String ACTIVITY_NAME = "MAIN_ACTIVITY";

    SharedPreferences sharedPreferences = null;

    public static final String DEFAULT="N/A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        emailText = (EditText) findViewById(R.id.emailText);

        // Load data from sharedpreferences
        sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email",DEFAULT);
        if (email.equals(DEFAULT)) {
            emailText.setText("");
        }
        else {
            emailText.setText(email);
        }

        // Save Email in SharedPreferences
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Go to profile
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME, "In function: onPause");

        // Save email in shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", emailText.getText().toString());
        editor.commit();

    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        // Week3, Page20 - The String fileName specifies the name of the file, mode is the security permissions
//        Button loginButton = findViewById(R.id.loginButton);
//        loginButton.setOnClickListener( bt -> saveSharedPrefs( emailText.getText().toString()) );
//
//    }
//    private void saveSharedPrefs(String stringToSave)
//    {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("email", stringToSave);
//        editor.commit();
//        Toast.makeText(getApplicationContext(), "Data was saved successfully ", Toast.LENGTH_LONG).show();
//    }

}

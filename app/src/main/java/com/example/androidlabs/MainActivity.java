package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    Button profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_login);

        emailText = (EditText)findViewById(R.id.emailText);


        // Go to profile activity
        profileButton = (Button) findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        // Save email
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=getSharedPreferences("MyData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("email",  emailText.getText().toString());
                editor.commit();

                Toast.makeText(getApplicationContext(), "Data was saved successfully ", Toast.LENGTH_LONG).show();

            }
        });

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        // Week3, Page20 - The String fileName specifies the name of the file, mode is the security permissions
//        prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
//        String savedString = prefs.getString("ReserveName", "Enter something");
//        EditText emailText = findViewById(R.id.emailText);
//        emailText.setText(savedString);
//        Button loginButton = findViewById(R.id.loginButton);
//        loginButton.setOnClickListener( bt -> saveSharedPrefs( emailText.getText().toString()) );
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
//
//    private void saveSharedPrefs(String stringToSave)
//    {
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString("ReserveName", stringToSave);
//        editor.commit();
//    }




}

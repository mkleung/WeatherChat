package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatRoomActivity extends AppCompatActivity {

    private ArrayList<String> elements = new ArrayList<>( Arrays.asList( "One", "Two" ) );
    private MyListAdapter myAdapter;

    private EditText messageText;
    private Button sendButton;
    private Button receivedButton;
    private String messageContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        messageText = (EditText) findViewById(R.id.messageText);

        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageContent = messageText.getText().toString();
                elements.add(messageContent);
                messageText.setText("");
            }
        });

        receivedButton = (Button) findViewById(R.id.receivedButton);
        receivedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageContent = messageText.getText().toString();
                elements.add(messageContent);
                messageText.setText("");
            }
        });



        // LIST
        ListView myList = findViewById(R.id.theListView);
        myList.setAdapter( myAdapter = new MyListAdapter());
//        myList.setOnItemClickListener( (parent, view, pos, id) -> {
//                //elements.remove(pos);
//                Toast.makeText(getApplicationContext(), "Row selected " + pos, Toast.LENGTH_LONG).show();
//                myAdapter.notifyDataSetChanged();
//            }
//        );

        myList.setOnItemLongClickListener( (p, b, pos, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?")

                    //What is the message:
                    .setMessage("The selected row is:"+pos+". The database id:"+pos)

                    //what the Yes button does:
                    .setPositiveButton("Yes", (click, arg) -> {
                        elements.remove(pos);
                        myAdapter.notifyDataSetChanged();
                    })
                    //What the No button does:
                    .setNegativeButton("No", (click, arg) -> { })


                    //You can add extra layout elements:
                    .setView(getLayoutInflater().inflate(R.layout.row_layout, null) )

                    //Show the dialog
                    .create().show();
            return true;
        });

        //Whenever you swipe down on the list, do something:
//        SwipeRefreshLayout refresher = findViewById(R.id.refresher);
//        refresher.setOnRefreshListener( () -> refresher.setRefreshing(false)  );


    }

    private class MyListAdapter extends BaseAdapter{
        public int getCount() { return elements.size();}
        public Object getItem(int position) { return "This is row " + position; }
        public long getItemId(int position) { return (long) position; }
        public View getView(int position, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();

            //make a new row:
            View newView = inflater.inflate(R.layout.row_layout, parent, false);

            //set what the text should be for this row:
            TextView tView = newView.findViewById(R.id.textGoesHere);
            tView.setText( getItem(position).toString() );

            //return it to be put in the table
            return newView;
        }
    }


}

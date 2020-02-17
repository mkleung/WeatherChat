package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    private ArrayList<Message> messages;
    private MessagesAdapter myAdapter;

    private EditText messageText;
    private Button sendButton;
    private Button receivedButton;
    private String messageContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);


        messages = new ArrayList<Message>();

        Message msg1 = new Message("Hello", false);
        Message msg2 = new Message("World", true);
        messages.add(msg1);
        messages.add(msg2);




        messageText = (EditText) findViewById(R.id.messageText);
        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(messageText.getText().toString())) {
                    messages.add( new Message(messageText.getText().toString(), true));
                    messageText.setText("");
                    myAdapter.notifyDataSetChanged();
                }
            }
        });

        receivedButton = (Button) findViewById(R.id.receivedButton);
        receivedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(messageText.getText().toString())) {
                    messages.add( new Message(messageText.getText().toString(), false));
                    messageText.setText("");
                    myAdapter.notifyDataSetChanged();
                }
            }
        });



        // ListView
        ListView myList = findViewById(R.id.theListView);
        myList.setAdapter( myAdapter = new MessagesAdapter());
        myList.setOnItemLongClickListener( (p, b, pos, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Do you want to delete this?")
                    .setMessage("The selected row is:"+pos+". The database id:"+pos)
                    .setPositiveButton("Yes", (click, arg) -> {
                        messages.remove(pos);
                        myAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("No", (click, arg) -> { })
                    .setView(getLayoutInflater().inflate(R.layout.row_layout_receive, null))
                    .create().show();
            return true;
        });

        //Whenever you swipe down on the list, do something:
//        SwipeRefreshLayout refresher = findViewById(R.id.refresher);
//        refresher.setOnRefreshListener( () -> refresher.setRefreshing(false)  );


    }


    private class MessagesAdapter extends BaseAdapter{
        public int getCount() { return messages.size();}
        public Object getItem(int position) { return "This is row " + position; }
        public long getItemId(int position) { return (long) position; }
        public View getView(int position, View convertView, ViewGroup parent) {

            Message message = messages.get(position);

            int imageResource;
            int row_layout;

            if (message.isSender){
                row_layout = R.layout.row_layout_send;
                imageResource = R.drawable.row_send;
            }
            else {
                row_layout = R.layout.row_layout_receive;
                imageResource = R.drawable.row_receive;
            }

            View newView = getLayoutInflater().inflate(row_layout, parent, false);
            TextView messageView = (TextView)newView.findViewById(R.id.messageView);
            messageView.setText(message.title);
            ImageView imageView = (ImageView)newView.findViewById(R.id.imageView);
            imageView.setImageResource(imageResource);

            return newView;
        }
    }


}

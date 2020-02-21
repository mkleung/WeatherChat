package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    private ArrayList<Message> messageList;
    private MessagesAdapter myAdapter;

    private EditText messageText;
    private Button sendButton;
    private Button receivedButton;
    private String messageContent;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        // Database
        loadDataFromDatabase(); //get any previously saved Contact objects


        messageList = new ArrayList<Message>();
        Message msg1 = new Message(1, "Hello", false);
        Message msg2 = new Message(2,"World", true);
        messageList.add(msg1);
        messageList.add(msg2);

        messageText = (EditText) findViewById(R.id.messageText);
        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(messageText.getText().toString())) {
                    messageList.add(new Message(3, messageText.getText().toString(), true));
                    messageText.setText("");
                    myAdapter.notifyDataSetChanged();
                    closeKeyboard();
                }
            }
        });

        receivedButton = (Button) findViewById(R.id.receivedButton);
        receivedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(messageText.getText().toString())) {
                    messageList.add(new Message(4, messageText.getText().toString(), false));
                    messageText.setText("");
                    myAdapter.notifyDataSetChanged();
                    closeKeyboard();
                }
            }
        });

        // ListView
        ListView myList = findViewById(R.id.theListView);
        myList.setAdapter(myAdapter = new MessagesAdapter());
        myList.setOnItemLongClickListener((p, b, pos, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);


            alertDialogBuilder.setTitle(getString(R.string.alert_title))
                    .setMessage(getString(R.string.alert_description_1) + ": " + pos + "\n" + getString(R.string.alert_description_2) + ": " + pos)
                    .setPositiveButton(R.string.alert_yes, (click, arg) -> {
                        messageList.remove(pos);
                        myAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton(R.string.alert_no, (click, arg) -> {
                    })
                    .setView(getLayoutInflater().inflate(R.layout.row_layout_receive, null))
                    .create().show();
            return true;
        });

        //Whenever you swipe down on the list, do something:
//        SwipeRefreshLayout refresher = findViewById(R.id.refresher);
//        refresher.setOnRefreshListener( () -> refresher.setRefreshing(false)  );
    }

    private class MessagesAdapter extends BaseAdapter {
        public int getCount() {
            return messageList.size();
        }

        public Object getItem(int position) {
            return "This is row " + position;
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            Message message = messageList.get(position);
            int imageResource;
            int row_layout;
            if (message.isSender) {
                row_layout = R.layout.row_layout_send;
                imageResource = R.drawable.row_send;
            } else {
                row_layout = R.layout.row_layout_receive;
                imageResource = R.drawable.row_receive;
            }
            View newView = getLayoutInflater().inflate(row_layout, parent, false);
            TextView messageView = (TextView) newView.findViewById(R.id.messageView);
            messageView.setText(message.title);
            ImageView imageView = (ImageView) newView.findViewById(R.id.imageView);
            imageView.setImageResource(imageResource);
            return newView;
        }
    }

    // Close The Virtual keyboard
    private void closeKeyboard() {
        // current edittext
        View view = this.getCurrentFocus();
        // if there is a view that has focus
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public void loadDataFromDatabase() {

        //get a database connection:
        DbOpener dbOpener = new DbOpener(this);
        db = dbOpener.getWritableDatabase();


        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        String [] columns = {DbOpener.COL_ID, DbOpener.COL_TITLE, DbOpener.COL_IS_SENDER};
        //query all the results from the database:
        Cursor results = db.query(false, DbOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        //Now the results object has rows of results that match the query.
        //find the column indices:
        int emailColumnIndex = results.getColumnIndex(DbOpener.COL_TITLE);
        int nameColIndex = results.getColumnIndex(DbOpener.COL_IS_SENDER);
        int idColIndex = results.getColumnIndex(DbOpener.COL_ID);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext())
        {
            String name = results.getString(nameColIndex);
            String email = results.getString(emailColumnIndex);
            long id = results.getLong(idColIndex);

            //add the new Contact to the array list:
            messageList.add(new Message(id, "Hello", false));
        }
    }
}
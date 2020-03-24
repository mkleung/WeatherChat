package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatRoomActivity extends AppCompatActivity {

    ArrayList<Chat> chatList = new ArrayList<>();
    private static int ACTIVITY_VIEW_CONTACT = 33;
    int positionClicked = 0;
    MyOwnAdapter myAdapter;
    SQLiteDatabase db;

    private static final String TAG = "ChatRoomActivity";

    Boolean isTablet;
    public static final String ITEM_SELECTED = "ITEM";
    public static final String ITEM_POSITION = "POSITION";
    public static final String ITEM_ID = "ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        EditText chatEdit = (EditText)findViewById(R.id.chatEdit);

        Button sendButton = (Button)findViewById(R.id.sendButton);
        Button receiveButton = (Button)findViewById(R.id.receiveButton);

        ListView theList = (ListView)findViewById(R.id.the_list);

        loadDataFromDatabase();

        myAdapter = new MyOwnAdapter();
        theList.setAdapter(myAdapter);

        // DELETE CHAT
        theList.setOnItemLongClickListener(( parent,  view,  position,  id) -> {
            Chat selectedChat = chatList.get(position);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle( getString(R.string.alert_title))
                    .setMessage(getString(R.string.alert_description_1)+": " + position+ "\n" + getString(R.string.alert_description_2)+": "+position)
                    .setPositiveButton(R.string.alert_yes, (click, arg) -> {
                        deleteContact(selectedChat); //remove the contact from database
                        chatList.remove(position); //remove the contact from contact list
                        myAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton(R.string.alert_no, (click, arg) -> { })
                    .setView(getLayoutInflater().inflate(R.layout.row_layout_send, null))
                    .create().show();
            return true;
        });

        // SEND BUTTON
        sendButton.setOnClickListener( click -> {
            String name = chatEdit.getText().toString();

            boolean test = true;

            if (TextUtils.isEmpty(name)) {
                return;
            }

            ContentValues newRowValues = new ContentValues();
            newRowValues.put(DbOpener.COL_MESSAGE, name);
            newRowValues.put(DbOpener.COL_IS_SENT, 1);

            long newId = db.insert(DbOpener.TABLE_NAME, null, newRowValues);
            Chat newContact = new Chat(name, 1, newId);
            chatList.add(newContact);
            myAdapter.notifyDataSetChanged();
            chatEdit.setText("");
            closeKeyboard();
        });

        // RECEIVE BUTTON
        receiveButton.setOnClickListener( click -> {
            String name = chatEdit.getText().toString();

            if (TextUtils.isEmpty(name)) {
                return;
            }

            ContentValues newRowValues = new ContentValues();
            newRowValues.put(DbOpener.COL_MESSAGE, name);
            newRowValues.put(DbOpener.COL_IS_SENT, 0);

            long newId = db.insert(DbOpener.TABLE_NAME, null, newRowValues);
            Chat newContact = new Chat(name, 0, newId);
            chatList.add(newContact);
            myAdapter.notifyDataSetChanged();
            chatEdit.setText("");
            closeKeyboard();
        });

        // LAB 7 - FrameLayout

        isTablet = findViewById(R.id.fragmentLocation) != null; //check if the FrameLayout is loaded
        theList.setOnItemClickListener((list, item, position, id) -> {
            Bundle dataToPass = new Bundle();
            dataToPass.putString("message", chatList.get(position).getMessage() );
            dataToPass.putString("isSend", Long.toString(chatList.get(position).getIsSent()) );
            dataToPass.putString("id", Long.toString(id) );

            if (isTablet) {
                DetailsFragment dFragment = new DetailsFragment(); //add a DetailFragment
                dFragment.setArguments( dataToPass ); //pass it a bundle for information
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentLocation, dFragment) //Add the fragment in FrameLayout
                        .commit(); //actually load the fragment.
            }
            else {
                Intent nextActivity = new Intent(ChatRoomActivity.this, EmptyActivity.class);
                nextActivity.putExtras(dataToPass); //send data to next activity
                startActivity(nextActivity); //make the transition
            }
        });
    }

    private void loadDataFromDatabase() {
        DbOpener dbOpener = new DbOpener(this);
        db = dbOpener.getWritableDatabase();

        String [] columns = {DbOpener.COL_ID, DbOpener.COL_MESSAGE, DbOpener.COL_IS_SENT};
        Cursor results = db.query(false, DbOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        int titleColumnIndex = results.getColumnIndex(DbOpener.COL_MESSAGE);
        int isSentColumnIndex = results.getColumnIndex(DbOpener.COL_IS_SENT);
        int idColIndex = results.getColumnIndex(DbOpener.COL_ID);

        while(results.moveToNext()) {
            String title = results.getString(titleColumnIndex);
            int isSent = results.getInt(isSentColumnIndex);
            long id = results.getLong(idColIndex);
            chatList.add(new Chat(title, isSent, id));
        }

        printCursor(results, db.getVersion());
    }

    protected void deleteContact(Chat c) {
        db.delete(DbOpener.TABLE_NAME, DbOpener.COL_ID + "= ?", new String[] {Long.toString(c.getId())});
    }

    protected class MyOwnAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return chatList.size();
        }

        public Chat getItem(int position){
            return chatList.get(position);
        }

        public View getView(int position, View old, ViewGroup parent) {
            Chat thisRow = getItem(position);

            int isSent = thisRow.getIsSent();

            int layout_type;
            if (isSent > 0) {
                layout_type = R.layout.row_layout_send;
            } else {
                layout_type = R.layout.row_layout_receive;
            }

            View newView = getLayoutInflater().inflate(layout_type, parent, false );

            TextView rowTitle = (TextView)newView.findViewById(R.id.chatTitle);
            rowTitle.setText(thisRow.getMessage());
            return newView;
        }
        public long getItemId(int position)
        {
            return getItem(position).getId();
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

    private void printCursor(Cursor c, int version) {
        Log.v(TAG, "The database number = " + version);
        Log.v(TAG, "The number of columns in the cursor = " + c.getColumnCount());

        String[] columnNames = c.getColumnNames();
        Log.v(TAG, "The names of columns in the cursor = " + Arrays.toString(columnNames));

        Cursor  cursor = db.rawQuery("select * from " +  DbOpener.TABLE_NAME,null);
        int titleColumnIndex = cursor.getColumnIndex(DbOpener.COL_MESSAGE);
        int isSentColumnIndex = cursor.getColumnIndex(DbOpener.COL_IS_SENT);
        int idColIndex = cursor.getColumnIndex(DbOpener.COL_ID);

        while(cursor.moveToNext()) {
            String title = cursor.getString(titleColumnIndex);
            int isSent = cursor.getInt(isSentColumnIndex);
            long id = cursor.getLong(idColIndex);
            Log.v(TAG, "_id:" + id + " - title:" + title + " - isSent:" + isSent);
        }
    }
}
package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    ArrayList<Chat> chatList = new ArrayList<>();
    private static int ACTIVITY_VIEW_CONTACT = 33;
    int positionClicked = 0;
    MyOwnAdapter myAdapter;
    SQLiteDatabase db;

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
        sendButton.setOnClickListener( click ->
        {
            String name = chatEdit.getText().toString();

            if (TextUtils.isEmpty(name)) {
                return;
            }

            ContentValues newRowValues = new ContentValues();
            newRowValues.put(DbOpener.COL_MESSAGE, name);
            newRowValues.put(DbOpener.COL_IS_SENT, "send");

            long newId = db.insert(DbOpener.TABLE_NAME, null, newRowValues);
            Chat newContact = new Chat(name, true, newId);
            chatList.add(newContact);
            myAdapter.notifyDataSetChanged();
            chatEdit.setText("");
            closeKeyboard();
        });

        // RECEIVE BUTTON
        receiveButton.setOnClickListener( click ->
        {
            String name = chatEdit.getText().toString();

            if (TextUtils.isEmpty(name)) {
                return;
            }

            ContentValues newRowValues = new ContentValues();
            newRowValues.put(DbOpener.COL_MESSAGE, name);
            newRowValues.put(DbOpener.COL_IS_SENT, true);

            long newId = db.insert(DbOpener.TABLE_NAME, null, newRowValues);
            Chat newContact = new Chat(name, false, newId);
            chatList.add(newContact);
            myAdapter.notifyDataSetChanged();
            chatEdit.setText("");
            closeKeyboard();
        });

    }


    private void loadDataFromDatabase()
    {
        DbOpener dbOpener = new DbOpener(this);
        db = dbOpener.getWritableDatabase();

        String [] columns = {DbOpener.COL_ID, DbOpener.COL_MESSAGE, DbOpener.COL_IS_SENT};
        Cursor results = db.query(false, DbOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        int titleColumnIndex = results.getColumnIndex(DbOpener.COL_MESSAGE);
        int isSentColumnIndex = results.getColumnIndex(DbOpener.COL_IS_SENT);
        int idColIndex = results.getColumnIndex(DbOpener.COL_ID);

        while(results.moveToNext())
        {
            String title = results.getString(titleColumnIndex);
            boolean isSent = results.getInt(isSentColumnIndex) > 0;
            long id = results.getLong(idColIndex);
            chatList.add(new Chat(title, isSent, id));
        }
    }

//    protected void deleteChat(int position)
//    {
//        Chat selectedMessage = chatList.get(position);
//
//        View contact_view = getLayoutInflater().inflate(R.layout.row_layout_send, null);
//        //get the TextViews
//        EditText rowName = contact_view.findViewById(R.id.row_name);
//        EditText rowEmail = contact_view.findViewById(R.id.row_email);
//        TextView rowId = contact_view.findViewById(R.id.row_id);
//
//        //set the fields for the alert dialog
//        rowName.setText(selectedContact.getName());
//        rowEmail.setText(selectedContact.getEmail());
//        rowId.setText("id:" + selectedContact.getId());
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("You clicked on item #" + position)
//                .setMessage("You can update the fields and then click update to save in the database")
//                .setView(contact_view) //add the 3 edit texts showing the contact information
//                .setPositiveButton("Update", (click, b) -> {
//                    selectedContact.update(rowName.getText().toString(), rowEmail.getText().toString());
//                    updateContact(selectedContact);
//                    myAdapter.notifyDataSetChanged(); //the email and name have changed so rebuild the list
//                })
//                .setNegativeButton("Delete", (click, b) -> {
//                    deleteContact(selectedContact); //remove the contact from database
//                    chatList.remove(position); //remove the contact from contact list
//                    myAdapter.notifyDataSetChanged(); //there is one less item so update the list
//                })
//                .setNeutralButton("dismiss", (click, b) -> { })
//                .create().show();
//    }

//    protected void updateContact(Chat c)
//    {
//        ContentValues updatedValues = new ContentValues();
//        updatedValues.put(DbOpener.COL_TITLE, c.getTitle());
//        updatedValues.put(DbOpener.COL_TYPE, c.getType());
//        db.update(DbOpener.TABLE_NAME, updatedValues, DbOpener.COL_ID + "= ?", new String[] {Long.toString(c.getId())});
//    }

    protected void deleteContact(Chat c)
    {
        db.delete(DbOpener.TABLE_NAME, DbOpener.COL_ID + "= ?", new String[] {Long.toString(c.getId())});
    }

    protected class MyOwnAdapter extends BaseAdapter
    {
        @Override
        public int getCount() {
            return chatList.size();
        }

        public Chat getItem(int position){
            return chatList.get(position);
        }

        public View getView(int position, View old, ViewGroup parent)
        {
            Chat thisRow = getItem(position);

            boolean isSent = thisRow.getIsSent();

            int layout_type;
            if (isSent) {
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
}
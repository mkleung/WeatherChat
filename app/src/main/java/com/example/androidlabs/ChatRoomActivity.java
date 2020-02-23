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

        //Get the fields from the screen:
        EditText chatEdit = (EditText)findViewById(R.id.chatEdit);

        Button insertButton = (Button)findViewById(R.id.sendButton);
        ListView theList = (ListView)findViewById(R.id.the_list);

        loadDataFromDatabase(); //get any previously saved Contact objects

        //create an adapter object and send it to the listVIew
        myAdapter = new MyOwnAdapter();
        theList.setAdapter(myAdapter);


        //This listens for items being clicked in the list view
        theList.setOnItemClickListener(( parent,  view,  position,  id) -> {
           // showContact( position );
        });


        //Listen for an insert button click event:
        insertButton.setOnClickListener( click ->
        {
            //get the email and name that were typed
            String name = chatEdit.getText().toString();

            //add to the database and get the new ID
            ContentValues newRowValues = new ContentValues();

            //Now provide a value for every database column defined in MyOpener.java:
            //put string name in the NAME column:
            newRowValues.put(DbOpener.COL_TITLE, name);

            //Now insert in the database:
            long newId = db.insert(DbOpener.TABLE_NAME, null, newRowValues);

            //now you have the newId, you can create the Contact object
            Chat newContact = new Chat(name, "sender", newId);

            //add the new contact to the list:
            chatList.add(newContact);
            //update the listView:
            myAdapter.notifyDataSetChanged();

            //clear the EditText fields:
            chatEdit.setText("");

            //Show the id of the inserted item:
            Toast.makeText(this, "Inserted item id:"+newId, Toast.LENGTH_LONG).show();
        });
    }


    private void loadDataFromDatabase()
    {
        //get a database connection:
        DbOpener dbOpener = new DbOpener(this);
        db = dbOpener.getWritableDatabase();

        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        String [] columns = {DbOpener.COL_ID, DbOpener.COL_TITLE, DbOpener.COL_TYPE};
        //query all the results from the database:
        Cursor results = db.query(false, DbOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        //Now the results object has rows of results that match the query.
        //find the column indices:
        int titleColumnIndex = results.getColumnIndex(DbOpener.COL_TITLE);
        int typeColumnIndex = results.getColumnIndex(DbOpener.COL_TYPE);
        int idColIndex = results.getColumnIndex(DbOpener.COL_ID);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext())
        {
            String title = results.getString(titleColumnIndex);
            String type = results.getString(typeColumnIndex);
            long id = results.getLong(idColIndex);

            //add the new Contact to the array list:
            chatList.add(new Chat(title, type, id));
        }

        //At this point, the contactsList array has loaded every row from the cursor.
    }


//    protected void showContact(int position)
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

    protected void updateContact(Chat c)
    {
        //Create a ContentValues object to represent a database row:
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(DbOpener.COL_TITLE, c.getTitle());
        updatedValues.put(DbOpener.COL_TYPE, c.getType());

        //now call the update function:
        db.update(DbOpener.TABLE_NAME, updatedValues, DbOpener.COL_ID + "= ?", new String[] {Long.toString(c.getId())});
    }

    protected void deleteContact(Chat c)
    {
        db.delete(DbOpener.TABLE_NAME, DbOpener.COL_ID + "= ?", new String[] {Long.toString(c.getId())});
    }

    //This class needs 4 functions to work properly:
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
            View newView = getLayoutInflater().inflate(R.layout.row_layout_send, parent, false );

            Chat thisRow = getItem(position);

            //get the TextViews
            TextView rowTitle = (TextView)newView.findViewById(R.id.chatTitle);
            TextView rowType = (TextView)newView.findViewById(R.id.chatType);
            TextView rowId = (TextView)newView.findViewById(R.id.chatId);

            //update the text fields:
            rowTitle.setText(  thisRow.getTitle());
            rowType.setText( thisRow.getType());
            rowId.setText("id:" + thisRow.getId());

            //return the row:
            return newView;
        }

        //last week we returned (long) position. Now we return the object's database id that we get from line 73
        public long getItemId(int position)
        {
            return getItem(position).getId();
        }
    }
}
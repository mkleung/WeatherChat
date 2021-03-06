package com.example.androidlabs;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class TestToolbar extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        //This gets the toolbar from the layout:
        Toolbar tBar = findViewById(R.id.toolbar);

        //This loads the toolbar, which calls onCreateOptionsMenu below:
        setSupportActionBar(tBar);


        //For NavigationDrawer:
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, tBar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);


	    /* slide 15 material:
	    MenuItem searchItem = menu.findItem(R.id.search_item);
        SearchView sView = (SearchView)searchItem.getActionView();
        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }  });
	    */

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        //Look at your menu XML file. Put a case for every id in that file:
        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.item1:
                message = "You clicked item 1";
                break;
//            case R.id.search_item:
//                message = "You clicked on the search";
//                break;
            case R.id.item2:
                message = "You clicked item 2";
                break;
            case R.id.item3:
                message = "You clicked item 3";
                break;
            case R.id.item4:
                message = "You clicked on overflow menu";
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        return true;
    }



    // Needed for the OnNavigationItemSelected interface:
    @Override
    public boolean onNavigationItemSelected( MenuItem item) {

        String message = null;

        switch(item.getItemId())
        {
            case R.id.itemChat:
//                message = "You go to chat page";
                Intent chatIntent = new Intent(getApplicationContext(), ChatRoomActivity.class);
                startActivity(chatIntent);
                break;
            case R.id.itemWeather:
//                message = "You go to weather page";
                Intent weatherIntent = new Intent(getApplicationContext(), WeatherForecast.class);
                startActivity(weatherIntent);
                break;
            case R.id.itemLogin:
//                message = "You go to login page";
                finish();
//                Intent loginIntent = new Intent(getApplicationContext(), WeatherForecast.class);
//                startActivity(loginIntent);
                break;
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

//        Toast.makeText(this, "NavigationDrawer: " + message, Toast.LENGTH_LONG).show();
        return false;
    }

}

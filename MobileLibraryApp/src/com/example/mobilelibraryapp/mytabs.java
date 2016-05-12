package com.example.mobilelibraryapp;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class mytabs extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mytabs);
        Log.d("My Menu","On Create");
 
        TabHost tabHost = getTabHost();
 
        // Tab for MyAccount
        TabSpec myaccountspec = tabHost.newTabSpec("My Account");
        // setting Title and Icon for the Tab
        myaccountspec.setIndicator("My Account", getResources().getDrawable(R.drawable.icon_myaccount_tab));
        Intent myaccountIntent = new Intent(this, myaccount.class);
        myaccountspec.setContent(myaccountIntent);
 
        // Tab for My reminders
        TabSpec myremindersspec = tabHost.newTabSpec("My Reminders");
        myremindersspec.setIndicator("My Reminders", getResources().getDrawable(R.drawable.icon_myreminders_tab));
        Intent myreminderIntent = new Intent(this, myremainders.class);
        myremindersspec.setContent(myreminderIntent);
 
        // Tab for Search
        TabSpec searchspec = tabHost.newTabSpec("Search");
        searchspec.setIndicator("Search", getResources().getDrawable(R.drawable.icon_search_tab));
        Intent searchIntent = new Intent(this, mysearchmain.class);
        searchspec.setContent(searchIntent);
 
        // Adding all TabSpec to TabHost
        tabHost.addTab(myaccountspec); // Adding photos tab
        tabHost.addTab(myremindersspec); // Adding songs tab
        tabHost.addTab(searchspec); // Adding videos tab
    }
}
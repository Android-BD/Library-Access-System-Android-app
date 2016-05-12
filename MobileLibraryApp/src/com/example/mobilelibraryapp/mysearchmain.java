package com.example.mobilelibraryapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;


public class mysearchmain extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.mysearchmain);
		
		View scanandsearchButton=findViewById(R.id.doyouhavethebookinhand_button);
		scanandsearchButton.setOnClickListener(this);
		View keywordsearchButton=findViewById(R.id.search_button);
		keywordsearchButton.setOnClickListener(this);
		

	}
	
	public void onClick(View v)
	{
	//	Log.d("Main Activity","OnClick");
	      switch (v.getId()) {
	      case R.id.doyouhavethebookinhand_button:
	    	  Intent i1=new Intent(this,ScanSearch.class);
	    	  startActivity(i1);
	    	  break;
	      
	      case R.id.search_button:
	    	  Intent i2=new Intent(this,SearchbyISBNActivity.class);
	    	  startActivity(i2);
	    	  break;
	      }
	   }
	   
	   
	   


}


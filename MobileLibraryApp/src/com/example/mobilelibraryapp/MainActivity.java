package com.example.mobilelibraryapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener{
	@Override
	   public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.mainactivity);

	      View loginButton = findViewById(R.id.btn_MA_login);
	      loginButton.setOnClickListener(this);
	      View registerButton = findViewById(R.id.btn_MA_register);
	      registerButton.setOnClickListener(this);
	      View searchbyisbnButton = findViewById(R.id.btn_MA_search);
	      searchbyisbnButton.setOnClickListener(this);
	      View ReserveButton = findViewById(R.id.btn_MA_reserve);
	      ReserveButton.setOnClickListener(this);
	      View ScanButton = findViewById(R.id.btn_MA_scan);
	      ScanButton.setOnClickListener(this);
	   }	
	
	public void onClick(View v) {
	      switch (v.getId()) {
	      case R.id.btn_MA_login:
	    	  Intent i1 = new Intent(this, LoginActivity.class);
		      startActivity(i1);
		      break;      
	      case R.id.btn_MA_register:
	    	 Intent i2 = new Intent(this, RegisterActivity.class);
		     startActivity(i2);
	         break;
	         
	      case R.id.btn_MA_search:
	    	 Intent i3 = new Intent(this, SearchbyISBNActivity.class);
		     startActivity(i3);
	         break;
	      
	      case R.id.btn_MA_reserve:
	    	  Intent i4 = new Intent(this, ReservationActivity.class);
			  startActivity(i4);
		      break;
	      case R.id.btn_MA_scan:
	    	  Intent i5 = new Intent(this, NewScanDirect.class);
			  startActivity(i5);
		      break;
	    	  
	      }
	   }
	   

}

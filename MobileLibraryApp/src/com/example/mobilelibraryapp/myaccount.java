package com.example.mobilelibraryapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class myaccount extends Activity implements OnClickListener
{
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myaccount);
		Button borrowedButton= (Button)findViewById(R.id.booksborrowed_button);
		borrowedButton.setOnClickListener(this);
		Button renew = (Button)findViewById(R.id.renewbooks_button);
		renew.setOnClickListener(this);
		Log.d("My Account","On Create");
	}
	public void onClick(View v)
	{
		Log.d("Main Activity","OnClick");
		switch (v.getId()) {
		case R.id.booksborrowed_button:
			Intent i1 = new Intent(this, BooksBorrowed.class);
			startActivity(i1);
			break;
		case R.id.renewbooks_button:
			Intent i5 = new Intent(this, NewScanDirect.class);
		  startActivity(i5);
		break;
		}
}
}
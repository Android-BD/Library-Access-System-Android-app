package com.example.mobilelibraryapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ReservationActivity extends Activity{

	Button reserve;
	EditText userid,bookid,reservedby;
	TextView reservedresponse;
	HttpPost httppost;
	StringBuffer buffer;
	HttpResponse response;
	HttpClient httpclient;
	List<NameValuePair> nameValuePairs;
	ProgressDialog dialog = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation);
        
        reserve = (Button)findViewById(R.id.reservation_button_for_reserve);  
        userid = (EditText)findViewById(R.id.reservation_edittext_for_userid);
        bookid= (EditText)findViewById(R.id.reservation_editext_for_bookid);
        reservedby=(EditText)findViewById(R.id.reservation_edittext_for_reservedby);
        reservedresponse = (TextView)findViewById(R.id.reservation_textview_for_response);
        
        reserve.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog = ProgressDialog.show(ReservationActivity.this, "", 
                        "Reserving a book...", true);
				 new Thread(new Runnable() {
					    public void run() {
					    	reserve();					      
					    }
					  }).start();				
			}
		});
    }
	
	void reserve(){
		try{			
			 
			httpclient=new DefaultHttpClient();
			httppost= new HttpPost("http://"+Constants.IPP+"//reservation_dbandroid.php"); // make sure the url is correct.
			//add your data
			nameValuePairs = new ArrayList<NameValuePair>(3);
			// Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar, 
			nameValuePairs.add(new BasicNameValuePair("userid",userid.getText().toString().trim()));  // $Edittext_value = $_POST['Edittext_value'];
			nameValuePairs.add(new BasicNameValuePair("bookid",bookid.getText().toString().trim()));
			nameValuePairs.add(new BasicNameValuePair("reservedby",reservedby.getText().toString().trim()));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			//Execute HTTP Post Request
			//response=httpclient.execute(httppost);
			// edited by James from coderzheaven.. from here....
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			final String response = httpclient.execute(httppost, responseHandler);
			System.out.println("Response : " + response); 
			runOnUiThread(new Runnable() {
			    public void run() {
			    	reservedresponse.setText("Response from PHP : " + response);
					dialog.dismiss();
			    }
			});
		
			if(response.equalsIgnoreCase("Book Reserved")){
				runOnUiThread(new Runnable() {
				    public void run() {
				    	Toast.makeText(ReservationActivity.this,"Reservation Success", Toast.LENGTH_SHORT).show();
				    }
				});
				//Toast.makeText(ReservationActivity.this,"Reservation Success", Toast.LENGTH_SHORT).show();
				//startActivity(new Intent(ReservationActivity.this, ReservationSuccess.class));
			}else{
				showAlert();				
			}
			
		}catch(Exception e){
			dialog.dismiss();
			System.out.println("Exception : " + e.getMessage());
		}
	}
	public void showAlert(){
		ReservationActivity.this.runOnUiThread(new Runnable() {
		    public void run() {
		    	AlertDialog.Builder builder = new AlertDialog.Builder(ReservationActivity.this);
		    	builder.setTitle("Reservation Error.");
		    	builder.setMessage("Book not Reserved")  
		    	       .setCancelable(false)
		    	       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		    	           public void onClick(DialogInterface dialog, int id) {
		    	           }
		    	       });		    	       
		    	AlertDialog alert = builder.create();
		    	alert.show();		    	
		    }
		});
	}
}
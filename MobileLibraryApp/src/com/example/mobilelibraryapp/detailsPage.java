package com.example.mobilelibraryapp;

import static com.example.mobilelibraryapp.Constants.active_user;
import static com.example.mobilelibraryapp.Constants.active_user_ID;
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
import com.example.mobilelibraryapp.SearchbyISBNActivity.LoadBooksISBN;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class detailsPage extends Activity {
	Button reserve;
	EditText userid,bookid,reservedby;
	TextView reservedresponse;
	HttpPost httppost;
	StringBuffer buffer;
	HttpResponse response;
	HttpClient httpclient;
	String userID;
	String bookID;
	String reservedBy;
	List<NameValuePair> nameValuePairs;
	ProgressDialog dialog = null;
	

	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailspage);
       
    	SharedPreferences settings = getSharedPreferences("Login",0);
        reservedBy = settings.getString(active_user,"None");
        userID=settings.getString(active_user_ID,"None");
        
        Bundle extras = getIntent().getExtras();
      		if (extras != null)
      		{
      			bookID = extras.getString("BookID");
      			
      		}
      		
        
	}
	
	void reserve(){
		try{			
			 
			httpclient=new DefaultHttpClient();
			httppost= new HttpPost("http://"+Constants.IPP+"//reservation_dbandroid.php"); // make sure the url is correct.
			//add your data
			nameValuePairs = new ArrayList<NameValuePair>(3);
			// Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar, 
			nameValuePairs.add(new BasicNameValuePair("userid",userID));  // $Edittext_value = $_POST['Edittext_value'];
			nameValuePairs.add(new BasicNameValuePair("bookid",bookID));
			nameValuePairs.add(new BasicNameValuePair("reservedby",reservedBy));
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
				    	Toast.makeText(detailsPage.this,"Reservation Success", Toast.LENGTH_SHORT).show();
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
		detailsPage.this.runOnUiThread(new Runnable() {
		    public void run() {
		    	AlertDialog.Builder builder = new AlertDialog.Builder(detailsPage.this);
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
public void btn_details_reserve_Click(View v)
{
	dialog = ProgressDialog.show(detailsPage.this, "", 
            "Reserving a book...", true);
	 new Thread(new Runnable() {
		    public void run() {
		    	reserve();					      
		    }
		  }).start();
}
}

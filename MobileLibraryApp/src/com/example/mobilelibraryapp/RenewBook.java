package com.example.mobilelibraryapp;

import static com.example.mobilelibraryapp.Constants.active_user;

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
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RenewBook extends Activity implements OnClickListener{

	ProgressDialog dialog = null;
	HttpPost httppost;
	StringBuffer buffer;
	HttpResponse response;
	HttpClient httpclient;
	List<NameValuePair> nameValuePairs;
	 public void onCreate(Bundle savedInstanceState) 
	 {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.renewxml);
	        Log.d("Renew","On Create");
	        Button bnt=(Button)findViewById(R.id.btn_rnw);
	        bnt.setOnClickListener(this);
	}
	 
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		   
		  	 
		      dialog = ProgressDialog.show(RenewBook.this, "", 
                      "Reserving a book...", true);
				 new Thread(new Runnable() {
					    public void run() {
					    	renew();					      
					    }
					  }).start();
		      
	}
	void renew(){
		 SharedPreferences settings = getSharedPreferences("Login",0);
	      String user = settings.getString(active_user,"None");
	      EditText txt=(EditText)findViewById(R.id.editText1);
	      String bookID =txt.getText().toString();
		try{			
			Log.d("Renew","On sending request");
			httpclient=new DefaultHttpClient();
			httppost= new HttpPost("http://"+Constants.IPP+"//renew.php"); // make sure the url is correct.
			//add your data
			nameValuePairs = new ArrayList<NameValuePair>(3);
			// Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar, 
			nameValuePairs.add(new BasicNameValuePair("userid",user));  // $Edittext_value = $_POST['Edittext_value'];
			nameValuePairs.add(new BasicNameValuePair("bookid",bookID));
			nameValuePairs.add(new BasicNameValuePair("reservedby",user));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			//Execute HTTP Post Request
			//response=httpclient.execute(httppost);
			// edited by James from coderzheaven.. from here....
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			final String response = httpclient.execute(httppost, responseHandler);
			Log.d("Renew","lets see what we got"+response);
			
			String success="";
			runOnUiThread(new Runnable() {
			    public void run() {
			    	Toast.makeText(RenewBook.this,response, Toast.LENGTH_SHORT).show();
			    }
			});
			    	Toast.makeText(this,response, Toast.LENGTH_SHORT).show();
			
			
		}catch(Exception e){
			dialog.dismiss();
			System.out.println("Exception : " + e.getMessage());
		}
	}
}

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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity{

	Button btnReg;
	EditText et,pass,email;
	HttpPost httppost;
	StringBuffer buffer;
	HttpResponse response;
	HttpClient httpclient;
	List<NameValuePair> nameValuePairs;
	ProgressDialog dialog = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        
        btnReg = (Button)findViewById(R.id.btn_REG_register);  
        et = (EditText)findViewById(R.id.txt_REG_username);
        pass= (EditText)findViewById(R.id.edtxt_REG_password);
        email=(EditText)findViewById(R.id.edtxt_REG_email);
      
        
        btnReg.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				
				String uname=et.getText().toString();
				String pwd=pass.getText().toString();
				String eaddr= email.getText().toString();
				String reg11="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
				boolean validEmail=eaddr.matches(reg11);
				AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
				if (uname.matches("")) {
					alertDialog.setTitle("Username Error");
					alertDialog.setMessage("Please enter a valid username");
					alertDialog.setIcon(R.drawable.error);
					alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                // Write your code here to execute after dialog closed
	                Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
	                }
					});
					alertDialog.show();
				}
				else if (pwd.matches("")) {
					alertDialog.setTitle("Password Error");
					alertDialog.setMessage("Please enter a valid password");
					alertDialog.setIcon(R.drawable.error);
					alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                // Write your code here to execute after dialog closed
	                Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
	                }
					});
					alertDialog.show();
				}
				
				/*if(eaddr.matches(reg11))
					Log.d("REg","Email matches");
				else
					Log.d("Reg","Does not Match");*/
				//Toast.makeText(thi//," text", Toast.LENGTH_LONG).show();
				else if(!validEmail)
				{
					alertDialog.setTitle("Email Error");
					alertDialog.setMessage("Please enter a valid email");
					alertDialog.setIcon(R.drawable.error);
	        
	 
	        // Setting OK Button
					alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                // Write your code here to execute after dialog closed
	                Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
	                }
	        });
	 
	        // Showing Alert Message
					alertDialog.show();
				}
				else
				{
				
				
				
				
				dialog = ProgressDialog.show(RegisterActivity.this, "", 
                        "Registering user...", true);
				 new Thread(new Runnable() {
					    public void run() {
					    	register();					      
					    }
					  }).start();				
			}
			}
		});
    }
	
	void register(){
		try{			
			 Log.e("REGISTER","registering sending request");
			httpclient=new DefaultHttpClient();
			httppost= new HttpPost("http://"+Constants.IPP+"//register_dbandroid.php"); // make sure the url is correct.
			//add your data
			nameValuePairs = new ArrayList<NameValuePair>(3);
			// Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar, 
			nameValuePairs.add(new BasicNameValuePair("myusername",et.getText().toString().trim()));  // $Edittext_value = $_POST['Edittext_value'];
			nameValuePairs.add(new BasicNameValuePair("mypassword",pass.getText().toString().trim()));
			nameValuePairs.add(new BasicNameValuePair("myemail",email.getText().toString().trim()));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			//Execute HTTP Post Request
			//response=httpclient.execute(httppost);
			// edited by James from coderzheaven.. from here....
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			final String response = httpclient.execute(httppost, responseHandler);
			//System.out.println("Response : " + response);
			 Log.e("REGISTER","back with response  "+response);
			runOnUiThread(new Runnable() {
			    public void run() {
			    	//tv.setText("Response from PHP : " + response);
					dialog.dismiss();
			    }
			});
		
			if(response.equalsIgnoreCase("User Registered")){
				runOnUiThread(new Runnable() {
				    public void run() {
				    	Toast.makeText(RegisterActivity.this,"Register Success", Toast.LENGTH_SHORT).show();
				    }
				});
				
				startActivity(new Intent(RegisterActivity.this, RegisterSuccess.class));
			}else{
				showAlert();				
			}
			
		}catch(Exception e){
			dialog.dismiss();
			System.out.println("Exception : " + e.getMessage());
		}
	}
	public void showAlert(){
		RegisterActivity.this.runOnUiThread(new Runnable() {
		    public void run() {
		    	AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
		    	builder.setTitle("Register Error.");
		    	builder.setMessage("User not Registered.")  
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
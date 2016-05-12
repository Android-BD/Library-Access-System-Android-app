package com.example.mobilelibraryapp;

import java.util.ArrayList;
import java.util.List;

import java.util.Date; 
import java.util.Properties;
import javax.activation.CommandMap; 
import javax.activation.DataHandler; 
import javax.activation.DataSource; 
import javax.activation.FileDataSource; 

import javax.activation.MailcapCommandMap; 
import javax.mail.BodyPart;
import javax.mail.Multipart; 
import javax.mail.PasswordAuthentication; 
import javax.mail.Session;
import javax.mail.Transport; 
import javax.mail.internet.InternetAddress; 
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage; 
import javax.mail.internet.MimeMultipart; 



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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.mobilelibraryapp.Constants.active_user;
import static com.example.mobilelibraryapp.Constants.active_user_ID;

public class LoginActivity extends Activity implements OnClickListener{
	Button btnReg;
	EditText et,pass;
	HttpPost httppost;
	StringBuffer buffer;
	HttpResponse response;
	HttpClient httpclient;
	List<NameValuePair> nameValuePairs;
	ProgressDialog dialog = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btnReg = (Button)findViewById(R.id.btn_login);  
        et = (EditText)findViewById(R.id.login_etxt_username);
        pass= (EditText)findViewById(R.id.login_etxt_password);
        TextView regNew=(TextView)findViewById(R.id.link_to_register);
        regNew.setOnClickListener(this);
     //   tv = (TextView)findViewById(R.id.tv);      
    }
	
	public void register_new_user(View v)
	{
		Log.d("LOGIN","Register New user");
		Intent register=new Intent(this, RegisterActivity.class);
		startActivity(register);		
	}
	
	@SuppressWarnings("deprecation")
	public void btn_login_click(View v)
	{
		sendEmail();
					String uname=et.getText().toString();
					String pwd=pass.getText().toString();
					AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
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
					
					
				else
				{
					dialog = ProgressDialog.show(LoginActivity.this, "", 
	                        "Validating user...", true);
					 new Thread(new Runnable() {
						    public void run() {
						    	login();					      
						    }
						  }).start();			
				}
}
		
		

	
	void login(){
		try{			
			 
			httpclient=new DefaultHttpClient();
			httppost= new HttpPost("http://"+Constants.IPP+"//check.php"); // make sure the url is correct.
			//add your data
			nameValuePairs = new ArrayList<NameValuePair>(2);
			String logged_user=et.getText().toString().trim();
			// Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar, 
			nameValuePairs.add(new BasicNameValuePair("myusername",et.getText().toString().trim()));  // $Edittext_value = $_POST['Edittext_value'];
			nameValuePairs.add(new BasicNameValuePair("mypassword",pass.getText().toString().trim())); 
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			//Execute HTTP Post Request
			response=httpclient.execute(httppost);
			// edited by James from coderzheaven.. from here....
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			final String response = httpclient.execute(httppost, responseHandler);
			System.out.println("Response : " + response); 
			runOnUiThread(new Runnable() {
			    public void run() {
			    	//tv.setText("Response from PHP : " + response);
					dialog.dismiss();
			    }
			});
			
			if(response.startsWith("User Found")){
				runOnUiThread(new Runnable() {
				    public void run() {
				    	
				    	
				    	String idd=response.substring(response.indexOf("||"));
				    	SharedPreferences settings = getSharedPreferences("Login", MODE_WORLD_WRITEABLE);
					    settings.edit().putString(active_user_ID, idd).commit();
				    	Toast.makeText(LoginActivity.this,"Login Success", Toast.LENGTH_SHORT).show();
				    	Intent i1 = new Intent(LoginActivity.this, mytabs.class);
			    		startActivity(i1);
				    }
				});
				//startActivity(new Intent(LoginActivity.this, SearchbyISBNActivity.class));
				Log.d("LoginActivity","Saving prefernces");
		    	 SharedPreferences settings = getSharedPreferences("Login", MODE_WORLD_WRITEABLE);
		    	 settings.edit().putString(active_user, logged_user).commit();
		    	 //startActivity(new Intent(LoginActivity.this, UserPage.class));
			}
			else{
				showAlert();				
			}
			
		}catch(Exception e){
			dialog.dismiss();
			System.out.println("Exception : " + e.getMessage());
		}
	}
	
	public void showAlert(){
		LoginActivity.this.runOnUiThread(new Runnable() {
		    public void run() {
		    	AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
		    	builder.setTitle("Login Error.");
		    	builder.setMessage("User not Found.")  
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.d("LOGIN","on click");
		if(v.getId() == R.id.link_to_register )
		{
		Log.d("LOGIN","Register New user");
		Intent register=new Intent(this, RegisterActivity.class);
		startActivity(register);
		}
		
	}
	
	void sendEmail()
	{
		Log.d("Login","mail mail mail");
		
		
		 Mail m = new Mail("shallysahi@gmail.com", "SahiShally2908"); 
		 String emailCombined="Hi Aishwarya,Are you getting my msg?  yes?PARTY!!";
		    String[] toArr = {"kkaur02@syr.edu", "s.its.aishu@gmail.com"};
		    m.setTo(toArr);
		    m.setFrom("shallysahi@gmail.com"); 
		    m.setSubject("Party Booked"); 
		    m.setBody(emailCombined); 

		    try { 
		      //m.addAttachment("/sdcard/filelocation"); 

		      if(m.send()) { 
		        Toast.makeText(LoginActivity.this, "Sent Email.", Toast.LENGTH_LONG).show(); 
		      } else { 
		        Toast.makeText(LoginActivity.this, "Email was not sent.", Toast.LENGTH_LONG).show(); 
		      } 
		    } catch(Exception e) { 
		      //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show(); 
		      Log.e("LoginActivity", "Could not send email.", e); 
		    } 

	}
}
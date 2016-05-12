package com.example.mobilelibraryapp;

import static com.example.mobilelibraryapp.Constants.active_user;
import static com.example.mobilelibraryapp.Constants.active_user_ID;
import static com.example.mobilelibraryapp.Constants.book_Author;
import static com.example.mobilelibraryapp.Constants.book_Isbn;
import static com.example.mobilelibraryapp.Constants.book_Name;
import static com.example.mobilelibraryapp.Constants.book_Status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class detailBorrow extends Activity
{
	String userID;
	String bookID;
	String reservedBy;
	ProgressDialog dialog = null;
	HttpPost httppost;
	JSONParser jParser = new JSONParser();
	StringBuffer buffer;
	HttpResponse response;
	JSONArray books = null; 
	HttpClient httpclient;
	List<NameValuePair> nameValuePairs;
	final String detailUrl="http://"+Constants.IPP+"//bookIssuedDetails.php";
	private static final String TAG_SUCCESS = "success"; 
	private static final String TAG_Books = "products"; 
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailsburrowed);
       
    	  SharedPreferences settings = getSharedPreferences("Login",0);
        reservedBy = settings.getString(active_user,"None");
        userID=settings.getString(active_user_ID,"None");
        
        Bundle extras = getIntent().getExtras();
      		if (extras != null)
      		{
      			Log.e("DEATIL BORROW","extras found");
      			bookID = extras.getString("BookID");
      			Log.d("DETAIL B","extras ID"+bookID);
      			
      			
      		}
      		else
      		{
      			Log.e("DEATIL BORROW","NOOOOOOOO extras found");
      		}
      		getDetails();
        
	}
	public void btn_details_reserve_Click(View v) 
	{
		// TODO Auto-generated method stub
		   
		  	 
		      dialog = ProgressDialog.show(detailBorrow.this, "", 
                      "Reserving a book...", true);
				 new Thread(new Runnable() {
					    public void run() {
					    	renew();					      
					    }
					  }).start();
		      
	}
	void getDetails()
	{
		try
		{
			Log.d("DETAIL ","On sending request for Detais");
			List<NameValuePair> params = new ArrayList<NameValuePair>(); 
		       Log.d("DETAILS B","USer"+ reservedBy +"Bbok ID  "+bookID);
			params.add(new BasicNameValuePair("User",reservedBy)); 
			params.add(new BasicNameValuePair("BookID",bookID)); 
	        // getting JSON string from URL 
	        JSONObject json = jParser.makeHttpRequest(detailUrl, "POST", params); 
	     
	        // Check your log cat for JSON reponse 
	        Log.d("Details ", json.toString()); 

	      
	            // Checking for SUCCESS TAG 
	            int success = json.getInt(TAG_SUCCESS); 
	            Log.d("Detail JSON","lets see what we got in suceess "+ success);
	            if (success == 1) 
	            { 
	            	Log.d("Detail JSON","Getting books after getting success");
	                // products found 
	                // Getting Array of Products 
	                books = json.getJSONArray(TAG_Books); 
	                Log.d("Detail Borrow JSON","lets see no of products "+ books.length());
	                // looping through All Products 
	                for (int i = 0; i < books.length(); i++)
	                { 
	                    JSONObject c = books.getJSONObject(i); 
	                    String name = c.getString(book_Name); 
	                    Log.d("In Loop","lets See");
	                    TextView ntxt=(TextView)findViewById(R.id.txt_DB_bookName);
	                    ntxt.setText(name);
	                    
	                    Log.d("DETAILS JSON","Name "+ name);
	                    String author = c.getString(book_Author);
	                    TextView Atxt=(TextView)findViewById(R.id.txt_BD_bookAuthor);
	                    Atxt.setText(author);
	                    Log.d("DETAILS JSON","Author "+author);
	                                   
	                    String issbn = c.getString(book_Isbn);
	                    TextView istxt=(TextView)findViewById(R.id.txt_DB_isbn);
	                    istxt.setText(issbn);
	                    Log.d("DETAILS JSON","ISBN "+issbn);
	                    
//	                    String desc = c.getString("Description");
//	                    TextView destxt=(TextView)findViewById(R.id.txt_DB_desc);
//	                    destxt.setText(desc);
//	                    Log.d("DETAILS JSON","ISBN "+desc);
	                    
	                    String issueDate = c.getString("Issue_date");
	                    TextView datetxt=(TextView)findViewById(R.id.txt_DB_IssueDate);
	                    datetxt.setText(issueDate);
	                    Log.d("DETAIS JSON","ISBN "+issueDate);
	                    
	                    String dueDatetxt = c.getString("Due_date");
	                    TextView duetxt=(TextView)findViewById(R.id.txt_DB_Duedate);
	                    duetxt.setText(dueDatetxt);
	                    Log.d("DETAILS JSON","ISBN "+dueDatetxt);
	                    
	                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	                    Date dueDate = df.parse(dueDatetxt);
	        		    Date nowDate=new Date();
	        		    if(dueDate.before(nowDate))
	        		    {
	        		    	View v= findViewById(R.layout.detailsburrowed);
	        		    	v.setBackgroundColor(Color.RED);
	        		    	Toast.makeText(this,"This book is past Due",Toast.LENGTH_LONG).show();
	          			 
	          			
	          		    }
			
	                }			
	            }
		}
		catch(Exception e)
		{
			 Log.d("BURROW D","exception"+e.toString());
		}
			
	       
	
	        
		}
	
	void renew(){
		 
	      
		try{			
			Log.d("Renew","On sending request");
			httpclient=new DefaultHttpClient();
			httppost= new HttpPost("http://"+Constants.IPP+"//renew.php"); // make sure the url is correct.
			//add your data
			nameValuePairs = new ArrayList<NameValuePair>(3);
			// Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar, 
			nameValuePairs.add(new BasicNameValuePair("userid",reservedBy));  // $Edittext_value = $_POST['Edittext_value'];
			nameValuePairs.add(new BasicNameValuePair("bookid",bookID));
			nameValuePairs.add(new BasicNameValuePair("reservedby",reservedBy));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			final String response = httpclient.execute(httppost, responseHandler);
			Log.d("Renew","lets see what we got"+response);
			System.out.println("Response : " + response); 
			Toast.makeText(this,response, Toast.LENGTH_LONG).show();				
			
			
		}catch(Exception e){
			dialog.dismiss();
			System.out.println("Exception : " + e.getMessage());
		}
	}
}

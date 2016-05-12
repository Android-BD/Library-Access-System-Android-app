package com.example.mobilelibraryapp;

import static android.provider.BaseColumns._ID;
import static com.example.mobilelibraryapp.Constants.active_user;
import static com.example.mobilelibraryapp.Constants.book_Author;
import static com.example.mobilelibraryapp.Constants.book_Name;
import static com.example.mobilelibraryapp.Constants.book_Status;
import static com.example.mobilelibraryapp.Constants.book_due_Date;
import static com.example.mobilelibraryapp.Constants.book_reservation_Date;
import static com.example.mobilelibraryapp.Constants.book_ID;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class BooksBorrowed extends ListActivity 
{
	int MILLISECONDS_IN_DAY= 24 * 60 * 60 *1000;

	
	  ArrayList<HashMap<String, String>> issuedbooksList; 
      // products JSONArray 
	  JSONArray books = null; 
	  static int fine=0;
	  // Progress Dialog 
	  private ProgressDialog pDialog; 
	  JSONParser jParser = new JSONParser();
	  final String url_books_issued="http://"+Constants.IPP+"//Search_Books_Issued.php";
		private static final String TAG_SUCCESS = "success"; 
		private static final String TAG_Books = "products";
	  
	 @Override
     public void onCreate(Bundle savedInstanceState)
	 {
		
		Log.e("BORROW","OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allborrowwed);
        Log.e("BORROW","set view done");
        issuedbooksList = new ArrayList<HashMap<String, String>>(); 
       //get Logged In User 
        SharedPreferences settings = getSharedPreferences("Login",0);
        String user = settings.getString(active_user,"None");
        TextView txtUName=(TextView)findViewById(R.id.lbl_UserName);
        txtUName.setText(user);

      
      Log.d("ALL Borrow","Books borrowed");
      new LoadIssuedBooks().execute(); 
   
     // Get listview 
      ListView lv = getListView(); 
      lv.setClickable(true);
      lv.setOnItemClickListener(new OnItemClickListener() { 
    	  
          @Override
          public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
          { 
        	  LinearLayout v=(LinearLayout)parent.getChildAt(position);
    		  TableLayout tb=(TableLayout)v.getChildAt(1);
    		  TableRow tr=(TableRow)tb.getChildAt(3);
    		  TextView txtV=(TextView)tr.getChildAt(0);
    		  
    		  TableRow btr=(TableRow)tb.getChildAt(1);
    		  TextView btxtV=(TextView)btr.getChildAt(0);
    		  Log.d("one selected","position"+position);
    		  Log.d("one selected","id"+id);
    		  
    		  String bookID=btxtV.getText().toString();
    		  Log.d("Putting extras ID",""+bookID);
              Intent iDetail=new Intent(BooksBorrowed.this,detailBorrow.class);
              iDetail.putExtra("BookID",bookID);
              String dateStr=txtV.getText().toString();
              Log.d("one selected","put extras ID"+bookID);
              iDetail.putExtra("DueDate",dateStr);
              startActivity(iDetail);
          }
      });      

	}


	class LoadIssuedBooks extends AsyncTask<String, String, String> 
	{

		 /** 
	     * Before starting background thread Show Progress Dialog 
	     **/
		
	    @Override
	    protected void onPreExecute() { 
	        super.onPreExecute(); 
	        pDialog = new ProgressDialog(BooksBorrowed.this); 
	        pDialog.setMessage("Loading products. Please wait..."); 
	        pDialog.setIndeterminate(false); 
	        pDialog.setCancelable(false); 
	        pDialog.show(); 
	    } 

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			Log.d("AAL B","borrowed Books do in BG");
	    	 // Building Parameters 
	        List<NameValuePair> params = new ArrayList<NameValuePair>(); 
	        issuedbooksList = new ArrayList<HashMap<String, String>>(); 
	    	  SharedPreferences settings = getSharedPreferences("Login",0);
	          String user = settings.getString(active_user,"None");
	        
			params.add(new BasicNameValuePair("User",user)); 
	        // getting JSON string from URL 
			Log.d("All B","meking request");

	        JSONObject json = jParser.makeHttpRequest(url_books_issued, "POST", params); 
	        // Check your log cat for JSON reponse 
	        Log.d("BORROW All Products: ", json.toString()); 
	        try { 
	            // Checking for SUCCESS TAG 
	            int success = json.getInt(TAG_SUCCESS); 
	            Log.d("ALL B","lets see what we got in suceess "+ success);
	            if (success == 1) { 
	            	Log.d("ALL B","Getting books after getting success");
	                // products found 
	                // Getting Array of Products 
	                books = json.getJSONArray(TAG_Books); 
	                Log.d("ALL B","lets see no of products "+ books.length());
	                // looping through All Products 
	                for (int i = 0; i < books.length(); i++)
	                { 
	                	
	                    JSONObject c = books.getJSONObject(i); 
	                    // Storing each json item in variable                 
	                    String name = c.getString(book_Name); 
	                    Log.d("ALL B","Name "+ name);
	                    String author = c.getString(book_Author);
	                    Log.d("ALL B","Author "+author);
	                    String dueDate = c.getString(book_due_Date);
	                    Log.d("ALL B","due Date "+dueDate);
	                    String bookID = c.getString(book_ID);
	                    Log.d("ALL B","bookID "+bookID);
	                    String imgSrc = c.getString("Image");
	                    Log.d("ALL B","bookID "+bookID);
	                    // creating new HashMap 
	                    HashMap<String, String> map = new HashMap<String, String>(); 
	                    // adding each child node to HashMap key => value 
	                    map.put(book_Name, name); 
	                    map.put(book_Author, author ); 
	                    map.put(book_due_Date,dueDate);
	                    map.put(book_ID,bookID);
	                    map.put(Constants.image_url,imgSrc);
	                    // adding HashList to ArrayList 
	                    issuedbooksList.add(map); 
	                    
	                   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	         		   Date dueONDate=new Date();
	         		   try 
	         		   {
	         			  dueONDate = df.parse(dueDate);
	         			  Log.e("DUE DATE","Found NOW"+dueDate.toString());
	         		   } 
	         		  catch (ParseException e) 
	         		  {
	         			// TODO Auto-generated catch block
	         			e.printStackTrace();
	         		  }
	                    Date date = new Date();
	                    if(dueONDate.before(date))
	          		  {
	                    double diff = Math.abs(date.getTime() - dueONDate.getTime());

	                    int days = (int) (diff / MILLISECONDS_IN_DAY);
	                    BooksBorrowed.fine+=days;
	          		  }
	                } 
	            } 
	            else { 
	                // no products found 
	                // Launch Add New product Activity 
//	                Intent i = new Intent(getApplicationContext(), 
//	                        NewProductActivity.class); 
//	                // Closing all previous activities 
//	                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
//	                startActivity(i); 
	            } 
	        } catch (JSONException e) { 
	            e.printStackTrace(); 
	        } 

	        
			return null;
		} 
		 
		 /** 
	     * After completing background task Dismiss the progress dialog 
	     * **/
	    protected void onPostExecute(String file_url) { 
	        // dismiss the dialog after getting all products 
	        pDialog.dismiss(); 
	        // updating UI from Background Thread 
	        runOnUiThread(new Runnable() { 
	            public void run() { 
	                            	
	            	Log.d("ALL B","settng upthe List item");
	            	Log.d("ALL B","Size   "+issuedbooksList.size());
	            	MyOwnAdapterBorrow adapter=new MyOwnAdapterBorrow(BooksBorrowed.this,issuedbooksList);
	            	setListAdapter(adapter);
	            	
                
	            } 
	        }); 
	        Log.d("ALL B","trying to find finetxtview");
        	TextView totalFineTxt=(TextView)findViewById(R.id.txt_fine);
        	Log.d("ALL B","trying to set fine");
        	totalFineTxt.setText(BooksBorrowed.fine+"");
	    } 
	}

}


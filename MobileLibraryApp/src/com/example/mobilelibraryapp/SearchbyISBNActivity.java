package com.example.mobilelibraryapp;

import java.util.ArrayList;
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
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.mobilelibraryapp.Constants.active_user;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import static android.provider.BaseColumns._ID;
import static com.example.mobilelibraryapp.Constants.My_Books_Table;

import static com.example.mobilelibraryapp.Constants.My_User_Table;
import static com.example.mobilelibraryapp.Constants.book_Author;
import static com.example.mobilelibraryapp.Constants.book_Isbn;
import static com.example.mobilelibraryapp.Constants.book_Name;
import static com.example.mobilelibraryapp.Constants.book_Status;
import static com.example.mobilelibraryapp.Constants.book_Keyword;
import static com.example.mobilelibraryapp.Constants.user_Pass;

public class SearchbyISBNActivity extends ListActivity {
	Button search;
	EditText isbn;
	TextView isbntv;
	HttpPost httppost;
	StringBuffer buffer;
	HttpResponse response;
	HttpClient httpclient;
	List<NameValuePair> nameValuePairs;
	ProgressDialog dialog = null;
	String urlStr="http://"+Constants.IPP+"//AllBooks.php";
	String url_Isbn_search="http://"+Constants.IPP+"//Search_ISBN.php";
	String url_Author="http://"+Constants.IPP+"//Search_Author.php";
	String url_Keyword="http://"+Constants.IPP+"//Search_Keyword.php";
	String url_Title="http://"+Constants.IPP+"//Search_Title.php";
	private static final String TAG_SUCCESS = "success"; 
	private static final String TAG_Books = "products"; 
	JSONParser jParser = new JSONParser(); 
	  
    ArrayList<HashMap<String, String>> booksList; 
    // products JSONArray 
    JSONArray books = null; 
    // Progress Dialog 
    private ProgressDialog pDialog; 
  	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
		Log.e("Scan or search by Isbn","OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchbyisbn);
        
        booksList = new ArrayList<HashMap<String, String>>(); 
       //get Logged In User 
      TextView user_logged = (TextView)findViewById(R.id.logged_in_user);
  	  SharedPreferences settings = getSharedPreferences("Login",0);
      String user = settings.getString(active_user,"None");
      user_logged.setText(user);

      Bundle extras = getIntent().getExtras();
  		if (extras != null)
  		{
  			boolean callX = extras.getBoolean("Click_Search_by_Isbn");
  			Log.e("SCAN","value of callX"+ callX);
  			String isbn_scanned=extras.getString("search_isbn");
  			Log.e("SCAN","ISBN Scanned"+ isbn_scanned);
  			if(callX) 
  			{
  				EditText txt_search=(EditText)findViewById(R.id.txt_search_key);
  				txt_search.setText(isbn_scanned);
  				new  LoadBooksISBN().execute();
  			}
  		}
 }
	
    public void reserve_click(View v)
    {
    	Log.d("SEARCH","item clicked ");
    	LinearLayout vwParentRow = (LinearLayout)v.getParent();
    	TableLayout child = (TableLayout)vwParentRow.getChildAt(1);
    
    	TableRow row=(TableRow)child.getChildAt(1);
    	TextView subChild=(TextView)row.getChildAt(0);
    	String selectedID=subChild.getText().toString();
    
    	TableRow rowisbn=(TableRow)child.getChildAt(2);
    	TextView isbnView=(TextView)rowisbn.getChildAt(0);
    	String selectedISBN=isbnView.getText().toString();
    	Intent iDetails=new Intent(this, detailsPage.class);
    
    	iDetails.putExtra("BookID",selectedID);
    	iDetails.putExtra("SelectedISBN",selectedISBN);
    	startActivity(iDetails);
    
    Toast.makeText(this,"ID SELECTED"+ selectedID +" and ISBN "+ selectedISBN, Toast.LENGTH_LONG).show();

    }
	
	 public void search_click(View v)
	    {
	    //	SQLiteDatabase db=data.getReadableDatabase();
	    	Log.d("MainActivity","on click default");
	    	Spinner spinner1=(Spinner) findViewById(R.id.spinner1);
	    	String category = String.valueOf(spinner1.getSelectedItem());
	    	TextView txt=(TextView)findViewById(R.id.txt_search_key);
	    	String uname=txt.getText().toString();
	    	if (uname.matches("")) {
	    		AlertDialog alertDialog = new AlertDialog.Builder(SearchbyISBNActivity.this).create();
				alertDialog.setTitle("No Search value Found");
				alertDialog.setMessage("Please enter some Value");
				alertDialog.setIcon(R.drawable.error);
				alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                }
					});
				alertDialog.show();
				return;
			}
	    	
	    	if(category.equalsIgnoreCase("Title"))
	    	{
	    		 booksList = new ArrayList<HashMap<String, String>>(); 
		    	  new LoadBooksTiTle().execute(); 
	    	}
	    	else if(category.equalsIgnoreCase("Author"))
	    	{
	    		Log.d("JSon","In author");
	    		 booksList = new ArrayList<HashMap<String, String>>(); 
	    		new LoadBooksAuthor().execute(); 
	    		
	    	}
	    	else if(category.equalsIgnoreCase("ISBN"))
	    	{
	    		Log.d("JSon","In ISBN");
	    		 booksList = new ArrayList<HashMap<String, String>>();
	    		new LoadBooksISBN().execute(); 
	    	}
	    	else if(category.equalsIgnoreCase("Keywords"))
	    	{
	    		Log.d("JSon","In Keywords");
	    		 booksList = new ArrayList<HashMap<String, String>>();
	    		new LoadBooksKeyword().execute(); 
	    	}
	    		   
	    	//search_byKeyword(v);
		
	    }



class LoadAllBooks extends AsyncTask<String, String, String> 
{ 
	  
    /** 
     * Before starting background thread Show Progress Dialog 
     * */
    @Override
    protected void onPreExecute() { 
        super.onPreExecute(); 
        pDialog = new ProgressDialog(SearchbyISBNActivity.this); 
        pDialog.setMessage("Loading products. Please wait..."); 
        pDialog.setIndeterminate(false); 
        pDialog.setCancelable(false); 
        pDialog.show(); 
    } 

    /** 
     * getting All products from url 
     * */
    protected String doInBackground(String... args) 
    { 
    	Log.d("JSON","searchAll");
    	isbn = (EditText)findViewById(R.id.txt_search_key);
        // Building Parameters 
        List<NameValuePair> params = new ArrayList<NameValuePair>(); 
       
		params.add(new BasicNameValuePair("ISBN",isbn.getText().toString().trim())); 
		
        // getting JSON string from URL 
        JSONObject json = jParser.makeHttpRequest(urlStr, "GET", params); 

        // Check your log cat for JSON reponse 
        Log.d("All Products: ", json.toString()); 

        try { 
            // Checking for SUCCESS TAG 
            int success = json.getInt(TAG_SUCCESS); 
            Log.d("JSON","lets see what we got in suceess "+ success);
            if (success == 1) { 
            	Log.d("JSON","Getting books after getting success");
                // products found 
                // Getting Array of Products 
                books = json.getJSONArray(TAG_Books); 
                Log.d("JSON","lets see no of products "+ books.length());
                // looping through All Products 
                for (int i = 0; i < books.length(); i++)
                { 
                    JSONObject c = books.getJSONObject(i); 
                    // Storing each json item in variable                 
                    String name = c.getString(book_Name); 
                    Log.d("ISBN JSON","Name "+ name);
                    String author = c.getString(book_Author);
                    Log.d("ISBN JSON","Author "+author);
                    String status = c.getString(book_Status);
                    Log.d("ISBN JSON","Author "+author);
                    String issbn = c.getString(book_Isbn);
                    Log.d("ISBN JSON","ISBN "+issbn);
                    String id = c.getString("ID");
                    Log.d("ISBN JSON","ID "+id);
                    String img = c.getString("Image");
                    Log.d("ISBN JSON","image "+img);
                    // creating new HashMap 
                    HashMap<String, String> map = new HashMap<String, String>(); 
                    // adding each child node to HashMap key => value 
                    map.put(book_Name, name); 
                    map.put(book_Author, author ); 
                    map.put(book_Status,status);
                    map.put(book_Isbn,issbn);
                    map.put("ID",id);
                    map.put(Constants.image_url,img);
                    // adding HashList to ArrayList 
                    booksList.add(map); 
                } 
            } else { 
                // no products found 
                // Launch Add New product Activity 
//                Intent i = new Intent(getApplicationContext(), 
//                        NewProductActivity.class); 
//                // Closing all previous activities 
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
//                startActivity(i); 
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
                /** 
                 * Updating parsed JSON data into ListView 
                 * */
            	
            	Log.d("JSON","settng upthe List item");
            	Log.d("JSON","Size   "+booksList.size());
            	MyOwnAdapter adapter=new MyOwnAdapter(SearchbyISBNActivity.this,booksList);
            	setListAdapter(adapter);

            } 
        }); 

    } 

} 


class LoadBooksISBN extends AsyncTask<String, String, String> 
{ 
	  
    /** 
     * Before starting background thread Show Progress Dialog 
     * */
    @Override
    protected void onPreExecute() { 
        super.onPreExecute(); 
        pDialog = new ProgressDialog(SearchbyISBNActivity.this); 
        pDialog.setMessage("Loading products. Please wait..."); 
        pDialog.setIndeterminate(false); 
        pDialog.setCancelable(false); 
        pDialog.show(); 
    } 

    /** 
     * getting All products from url 
     * */
    protected String doInBackground(String... args) 
    { 
    	Log.d("JSON","searchByISBN");
    	isbn = (EditText)findViewById(R.id.txt_search_key);
        // Building Parameters 
        List<NameValuePair> params = new ArrayList<NameValuePair>(); 
        booksList = new ArrayList<HashMap<String, String>>(); 
		params.add(new BasicNameValuePair("ISBN",isbn.getText().toString().trim())); 
        // getting JSON string from URL 
        JSONObject json = jParser.makeHttpRequest(url_Isbn_search, "POST", params); 
        // Check your log cat for JSON reponse 
        Log.d("All Products: ", json.toString()); 
        try { 
            // Checking for SUCCESS TAG 
            int success = json.getInt(TAG_SUCCESS); 
            Log.d("JSON","lets see what we got in suceess "+ success);
            if (success == 1) { 
            	Log.d("JSON","Getting books after getting success");
                // products found 
                // Getting Array of Products 
                books = json.getJSONArray(TAG_Books); 
                Log.d("JSON","lets see no of products "+ books.length());
                // looping through All Products 
                for (int i = 0; i < books.length(); i++)
                { 
                    JSONObject c = books.getJSONObject(i); 
                    // Storing each json item in variable                 
                    String name = c.getString(book_Name); 
                    Log.d("ISBN JSON","Name "+ name);
                    String author = c.getString(book_Author);
                    Log.d("ISBN JSON","Author "+author);
                    String status = c.getString(book_Status);
                    Log.d("ISBN JSON","Author "+author);
                    String issbn = c.getString(book_Isbn);
                    Log.d("ISBN JSON","ISBN "+issbn);
                    String id = c.getString("ID");
                    Log.d("ISBN JSON","ID "+id);
                    String img = c.getString("Image");
                    Log.d("ISBN JSON","image "+img);
                    // creating new HashMap 
                    HashMap<String, String> map = new HashMap<String, String>(); 
                    // adding each child node to HashMap key => value 
                    map.put(book_Name, name); 
                    map.put(book_Author, author ); 
                    map.put(book_Status,status);
                    map.put(book_Isbn,issbn);
                    map.put("ID",id);
                    map.put(Constants.image_url,img);
                    // adding HashList to ArrayList 
                    booksList.add(map); 
                } 
            } 
            else { 
                // no products found 
                // Launch Add New product Activity 
//                Intent i = new Intent(getApplicationContext(), 
//                        NewProductActivity.class); 
//                // Closing all previous activities 
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
//                startActivity(i); 
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
        runOnUiThread(new Runnable()
        { 
            public void run() 
            { 
                /** 
                 * Updating parsed JSON data into ListView 
                 * */
            	
            	Log.d("JSON","settng upthe List item");
            	Log.d("JSON","Size   "+ booksList.size());
            	MyOwnAdapter adapter=new MyOwnAdapter(SearchbyISBNActivity.this,booksList);
            	setListAdapter(adapter);
            } 
        });
}
    }


class LoadBooksAuthor extends AsyncTask<String, String, String> 
{ 
	  
    /** 
     * Before starting background thread Show Progress Dialog 
     * */
    @Override
    protected void onPreExecute() { 
        super.onPreExecute(); 
        pDialog = new ProgressDialog(SearchbyISBNActivity.this); 
        pDialog.setMessage("Loading products. Please wait..."); 
        pDialog.setIndeterminate(false); 
        pDialog.setCancelable(false); 
        pDialog.show(); 
    } 

    /** 
     * getting All products from url 
     * */
    protected String doInBackground(String... args) 
    { 
    	 Log.d("JSON","searchByAUTHOR");
    	isbn = (EditText)findViewById(R.id.txt_search_key);
        // Building Parameters 
        List<NameValuePair> params = new ArrayList<NameValuePair>(); 
        booksList = new ArrayList<HashMap<String, String>>(); 
		params.add(new BasicNameValuePair("Author",isbn.getText().toString().trim())); 
		
        // getting JSON string from URL 
        JSONObject json = jParser.makeHttpRequest(url_Author, "POST", params); 

        // Check your log cat for JSON reponse 
        Log.d("All Products: ", json.toString()); 

        try { 
            // Checking for SUCCESS TAG 
            int success = json.getInt(TAG_SUCCESS); 
            Log.d("JSON","lets see what we got in suceess "+ success);
            if (success == 1) { 
            	Log.d("JSON","Getting books after getting success");
                // products found 
                // Getting Array of Products 
                books = json.getJSONArray(TAG_Books); 
                Log.d("JSON","lets see no of products "+ books.length());
                // looping through All Products 

                for (int i = 0; i < books.length(); i++)
                { 
                    JSONObject c = books.getJSONObject(i); 
                    // Storing each json item in variable                 
                    String name = c.getString(book_Name); 
                    Log.d("ISBN JSON","Name "+ name);
                    String author = c.getString(book_Author);
                    Log.d("ISBN JSON","Author "+author);
                    String status = c.getString(book_Status);
                    Log.d("ISBN JSON","Author "+author);
                    String issbn = c.getString(book_Isbn);
                    Log.d("ISBN JSON","ISBN "+issbn);
                    String id = c.getString("ID");
                    Log.d("ISBN JSON","ID "+id);
                    String img = c.getString("Image");
                    Log.d("ISBN JSON","image "+img);
                    // creating new HashMap 
                    HashMap<String, String> map = new HashMap<String, String>(); 
                    // adding each child node to HashMap key => value 
                    map.put(book_Name, name); 
                    map.put(book_Author, author ); 
                    map.put(book_Status,status);
                    map.put(book_Isbn,issbn);
                    map.put("ID",id);
                    map.put(Constants.image_url,img);
                    // adding HashList to ArrayList 
                    booksList.add(map); 
                } 
            } 
            else { 

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
                /** 
                 * Updating parsed JSON data into ListView 
                 * */
            	
            	Log.d("JSON","settng upthe List item");
            	Log.d("JSON","Size   "+booksList.size());
            	MyOwnAdapter adapter=new MyOwnAdapter(SearchbyISBNActivity.this,booksList);
            	setListAdapter(adapter);

            } 
        }); 

    } 

} 




class LoadBooksKeyword extends AsyncTask<String, String, String> 
{ 
	  
    /** 
     * Before starting background thread Show Progress Dialog 
     * */
    @Override
    protected void onPreExecute() { 
        super.onPreExecute(); 
        pDialog = new ProgressDialog(SearchbyISBNActivity.this); 
        pDialog.setMessage("Loading products. Please wait..."); 
        pDialog.setIndeterminate(false); 
        pDialog.setCancelable(false); 
        pDialog.show(); 
    } 

    /** 
     * getting All products from url 
     * */
    protected String doInBackground(String... args) 
    { 
    	 Log.d("JSON","searchByKeyword");
    	isbn = (EditText)findViewById(R.id.txt_search_key);
        // Building Parameters 
        List<NameValuePair> params = new ArrayList<NameValuePair>(); 
        booksList = new ArrayList<HashMap<String, String>>(); 
		params.add(new BasicNameValuePair("Keyword",isbn.getText().toString().trim())); 
		
        // getting JSON string from URL 
        JSONObject json = jParser.makeHttpRequest(url_Keyword, "POST", params); 

        // Check your log cat for JSON reponse 
        Log.d("All Products: ", json.toString()); 

        try { 
            // Checking for SUCCESS TAG 
            int success = json.getInt(TAG_SUCCESS); 
            Log.d("JSON","lets see what we got in suceess "+ success);
            if (success == 1) { 
            	Log.d("JSON","Getting books after getting success");
                // products found 
                // Getting Array of Products 
                books = json.getJSONArray(TAG_Books); 
                Log.d("JSON","lets see no of products "+ books.length());
                // looping through All Products 

                for (int i = 0; i < books.length(); i++)
                { 
                    JSONObject c = books.getJSONObject(i); 
                    // Storing each json item in variable                 
                    String name = c.getString(book_Name); 
                    Log.d("ISBN JSON","Name "+ name);
                    String author = c.getString(book_Author);
                    Log.d("ISBN JSON","Author "+author);
                    String status = c.getString(book_Status);
                    Log.d("ISBN JSON","Author "+author);
                    String issbn = c.getString(book_Isbn);
                    Log.d("ISBN JSON","ISBN "+issbn);
                    String id = c.getString("ID");
                    Log.d("ISBN JSON","ID "+id);
                    String img = c.getString("Image");
                    Log.d("ISBN JSON","image "+img);
                    // creating new HashMap 
                    HashMap<String, String> map = new HashMap<String, String>(); 
                    // adding each child node to HashMap key => value 
                    map.put(book_Name, name); 
                    map.put(book_Author, author ); 
                    map.put(book_Status,status);
                    map.put(book_Isbn,issbn);
                    map.put("ID",id);
                    map.put(Constants.image_url,img);
                    // adding HashList to ArrayList 
                    booksList.add(map); 
                } 
            } 
 else { 

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
                /** 
                 * Updating parsed JSON data into ListView 
                 * */
            	
            	Log.d("JSON","settng upthe List item");
            	Log.d("JSON","Size   "+ booksList.size());
            	MyOwnAdapter adapter=new MyOwnAdapter(SearchbyISBNActivity.this,booksList);
            	setListAdapter(adapter);

            } 
        }); 

    } 

}

public void btn_reserve_selected(View v)
{
	
    LinearLayout vwParentRow = (LinearLayout)v.getParent();
    TableLayout child = (TableLayout)vwParentRow.getChildAt(1);
    View vvv=child.getChildAt(0);
    TextView subChild=(TextView)child.getChildAt(1);
    String selected = subChild.getText().toString();
    Toast.makeText(this,"ID SELECTED"+ selected , Toast.LENGTH_LONG);
}

class LoadBooksTiTle extends AsyncTask<String, String, String> 
{ 
	
    @Override
    protected void onPreExecute() { 
        super.onPreExecute(); 
        pDialog = new ProgressDialog(SearchbyISBNActivity.this); 
        pDialog.setMessage("Loading products. Please wait..."); 
        pDialog.setIndeterminate(false); 
        pDialog.setCancelable(false); 
        pDialog.show(); 
    } 

  
    protected String doInBackground(String... args) 
    { 
    	 Log.d("JSON","searchByTitle");
    	isbn = (EditText)findViewById(R.id.txt_search_key);
        // Building Parameters 
        List<NameValuePair> params = new ArrayList<NameValuePair>(); 
        booksList = new ArrayList<HashMap<String, String>>(); 
		params.add(new BasicNameValuePair("Title",isbn.getText().toString().trim())); 
		
        // getting JSON string from URL 
        JSONObject json = jParser.makeHttpRequest(url_Title, "POST", params); 

        // Check your log cat for JSON reponse 
        Log.d("All Products: ", json.toString()); 

        try { 
            // Checking for SUCCESS TAG 
            int success = json.getInt(TAG_SUCCESS); 
            Log.d("JSON","lets see what we got in suceess "+ success);
            if (success == 1) { 
            	Log.d("JSON","Getting books after getting success");
                // products found 
                // Getting Array of Products 
                books = json.getJSONArray(TAG_Books); 
                Log.d("JSON","lets see no of products "+ books.length());
                // looping through All Products 
                for (int i = 0; i < books.length(); i++)
                { 
                    JSONObject c = books.getJSONObject(i); 
                    // Storing each json item in variable                 
                    String name = c.getString(book_Name); 
                    Log.d("ISBN JSON","Name "+ name);
                    String author = c.getString(book_Author);
                    Log.d("ISBN JSON","Author "+author);
                    String status = c.getString(book_Status);
                    Log.d("ISBN JSON","Author "+author);
                    String issbn = c.getString(book_Isbn);
                    Log.d("ISBN JSON","ISBN "+issbn);
                    String id = c.getString("ID");
                    Log.d("ISBN JSON","ID "+id);
                    String img = c.getString("Image");
                    Log.d("ISBN JSON","image "+img);
                    // creating new HashMap 
                    HashMap<String, String> map = new HashMap<String, String>(); 
                    // adding each child node to HashMap key => value 
                    map.put(book_Name, name); 
                    map.put(book_Author, author ); 
                    map.put(book_Status,status);
                    map.put(book_Isbn,issbn);
                    map.put("ID",id);
                    map.put(Constants.image_url,img);
                    // adding HashList to ArrayList 
                    booksList.add(map); 
                 
                } 
            } else { 
                // no products found 
                // Launch Add New product Activity 
//                Intent i = new Intent(getApplicationContext(), 
//                        NewProductActivity.class); 
//                // Closing all previous activities 
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
//                startActivity(i); 
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
                /** 
                 * Updating parsed JSON data into ListView 
                 * */
            	
            	Log.d("JSON","settng upthe List item");
            	Log.d("JSON","Size   "+booksList.size());
            	MyOwnAdapter adapter=new MyOwnAdapter(SearchbyISBNActivity.this,booksList);
            	setListAdapter(adapter);
//                ListAdapter adapter = new SimpleAdapter( 
//                        SearchbyISBNActivity.this, booksList, 
//                        R.layout.resultitem, new String[] { book_Name,book_Author,book_Status,_ID},
//                        new int[] { R.id.bookName_result, R.id.Author_result,R.id.Status_result, R.id.bookID_result}); 
//                // updating listview 
//                setListAdapter(adapter); 
            } 
        }); 

    } 

}
}



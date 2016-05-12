package com.example.mobilelibraryapp;
import static com.example.mobilelibraryapp.Constants.active_user;
import static com.example.mobilelibraryapp.Constants.active_user_ID;

import com.google.zxing.Result;
import com.google.zxing.client.result.*;
import jim.h.common.android.zxinglib.integrator.IntentIntegrator;
import jim.h.common.android.zxinglib.integrator.IntentResult;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import android.os.Message;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
 
// used for connectivity
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

public class NewScanDirect extends Activity {
 
    private Handler  handler = new Handler();
    private TextView txtScanResult;
    private Result res;
    private Handler h;
    private String data;
    private String httpreq; 
    private String isbn;
    private String bookID;
    private ImageView imgview;
    private TextView txtbook;
    private TextView txtauthor;
    JSONParser jParser = new JSONParser();
    final String detailUrl="http://"+Constants.IPP+"//bookIssuedDetails.php";
	private static final String TAG_SUCCESS = "success"; 
	private static final String TAG_Books = "products"; 

   
//   LibraryDataSource dataSource=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanandregister);

        View btnScan = findViewById(R.id.scan_button);
      
        // Scan button
        btnScan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // set the last parameter to true to open front light if available
                IntentIntegrator.initiateScan(NewScanDirect.this, R.layout.capture,
                        R.id.viewfinder_view, R.id.preview_view, true);
            }
        });
        
        this.h = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                // process incoming messages here
                switch (msg.what) {
                    case 0:
                    //data+=((String) msg.obj);
                    //tView.append((String) msg.obj);
                    break;
                }
                super.handleMessage(msg);
            }
        };
       
            txtScanResult = (TextView) findViewById(R.id.scan_result);

             imgview=(ImageView)findViewById(R.id.imageView1);
             txtbook=(TextView)findViewById(R.id.bookName);
             txtauthor=(TextView)findViewById(R.id.bookAuthor);
            
                             
 }
    
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
        case IntentIntegrator.REQUEST_CODE:
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode,
                    resultCode, data);
            if (scanResult == null) {
                return;
            }
            final String result = scanResult.getContents();
            isbn=result;
            if(result!=null)
            {
            	txtScanResult.setText(result); 
            scanSearch_Click2();
            }
 
                break;
            default:
        }
    }
    
private void interpretHtmlwithUrl(String s)
{
String s1="http://www.isbnsearch.org/isbn/"+isbn;
try
{
Document doc = Jsoup.connect(s1).get();

String title=doc.title();
 
Elements imgtit=doc.select("div[class=thumbnail]");
Elements tit1=doc.select("div[class=bookinfo]");
   
System.out.println("Book Title"+tit1);
Log.d("BOOK TITLE",imgtit.toString());
 
data+=imgtit;
data+=tit1;
 
//image view
String imgsrc=imgtit.select("img").first().attr("src");
Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(imgsrc).getContent());
imgview.setImageBitmap(bitmap);
 
String Booktitle=imgtit.select("img").first().attr("alt");
    
 
List<String> DataList=new ArrayList<String>();
Elements elements = tit1.select("p").select("strong");
    for(int i=0; i<elements.size(); i++){
        Element para = elements.get(i);
        DataList.add(para.nextSibling().toString());
    }
 
    for(int j=0;j<DataList.size();j++)
    {
    Log.d("LIST:",DataList.get(j).toString());
    }
 
txtbook.setText(Booktitle);
txtauthor.setText(DataList.get(2));
 
}
catch (IOException e) {
e.printStackTrace();
}
}

public void scanSearch_Click2() 
{
Log.d("ScanSearch","Search Clicked for isbn  "+  isbn);
Log.d("ScanSearch","Starting new Intent  "+  isbn);
Intent iDetail=new Intent(NewScanDirect.this,detailBorrow.class);
	if(checkIfReserved(isbn)==1)
	{
		iDetail.putExtra("BookID",isbn);
		Log.d("one selected","put extras ID"+isbn);
		
		startActivity(iDetail);
	}
 
}



public int checkIfReserved(String bookID)
{
	  SharedPreferences settings = getSharedPreferences("Login",0);
      String reservedBy = settings.getString(active_user,"None");
      String userID=settings.getString(active_user_ID,"None");
      
	Log.d("DETAIL ","On sending request for Detais");
	List<NameValuePair> params = new ArrayList<NameValuePair>(); 
	Log.d("DETAILS B","USer"+ reservedBy +"Bbok ID"+bookID);
	params.add(new BasicNameValuePair("User",reservedBy)); 
	params.add(new BasicNameValuePair("BookID",bookID)); 
// getting JSON string from URL 
	JSONObject json = jParser.makeHttpRequest(detailUrl, "POST", params); 
	JSONArray books = null; 
// Check your log cat for JSON reponse 
Log.d("Details ", json.toString()); 

int success=0;
 // Checking for SUCCESS TAG 
try
{
		success = json.getInt(TAG_SUCCESS); 
//		if(success==1)
//		{
//			books = json.getJSONArray(TAG_Books);
//			JSONObject c = books.getJSONObject(0); 
//		}
}
catch(Exception e)
{
	
}
	return success;
	
}
public void scanSearch_Click(View v) {
    try
    {  
//         Cursor curr;
//         Log.d("ScanSearch","Search Clicked for isbn  "+  isbn);
//         curr =dataSource.getSearchResultByIsbn(isbn);
//         TextView txt=(TextView)findViewById(R.id.bookName_s);
//         Log.d("ScanSearch","back with results for isbn  "+  curr.getColumnCount());
//         if(curr.moveToFirst())
//         {
//         txt.setText(curr.getString(curr.getColumnIndex("Name")));
//         Log.d("ScanSearch",txt.getText().toString());
//         }
   
    }
   catch (Exception e)
   {
  
  Log.d("ScanSearch","Exception in"+e.toString());
       }
  }
  
       
}


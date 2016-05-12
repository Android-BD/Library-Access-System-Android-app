package com.example.mobilelibraryapp;
import com.google.zxing.Result;
import com.google.zxing.client.result.*;
import jim.h.common.android.zxinglib.integrator.IntentIntegrator;
import jim.h.common.android.zxinglib.integrator.IntentResult;
import android.app.Activity;
import android.content.Intent;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

public class ScanSearch extends Activity {
 
    private Handler  handler = new Handler();
    private TextView txtScanResult;
    private Result res;
    private Handler h;
    private String data;
    private String httpreq; 
    private String isbn;
    private ImageView imgview;
    private TextView txtbook;
    private TextView txtauthor;

   
//   LibraryDataSource dataSource=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanandsearch);

        View btnScan = findViewById(R.id.scan_button);
      
        // Scan button
        btnScan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // set the last parameter to true to open front light if available
                IntentIntegrator.initiateScan(ScanSearch.this, R.layout.capture,
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
        final Button button = (Button) findViewById(R.id.ButtonGo);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            try {
            //tView.setText("");
                // Perform action on click
            httpreq="http://books.google.com/books?vid=ISBN";
            //httpreq+=txtScanResult.getText().toString();
            httpreq+=isbn;
            //httpreq="http://www.google.com/";
                URL url = new URL(httpreq);
                    URLConnection conn = url.openConnection();
                    // Get the response
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = "";
                    while ((line = rd.readLine()) != null) {
                Message lmsg;
                        lmsg = new Message();
                        lmsg.obj = line;
                        lmsg.what = 0;
                        ScanSearch.this.h.sendMessage(lmsg);
                    }
                    
                    interpretHtmlwithUrl(isbn);
                    //interpretHtml(data);
            }
            catch (Exception e) {
            }
            }
        });
            txtScanResult = (TextView) findViewById(R.id.scan_result);

             imgview=(ImageView)findViewById(R.id.imageView1);
             txtbook=(TextView)findViewById(R.id.bookName);
             txtauthor=(TextView)findViewById(R.id.bookAuthor);

                final Button buttonSearch = (Button) findViewById(R.id.ButtonSearch);
               buttonSearch.setOnClickListener(new Button.OnClickListener() {
                   public void onClick(View v) {
                    try {
                   
                    scanSearch_Click2() ;
                    }
                catch (Exception e) {
                }
                   }
               });
               
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
            
            if (result != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                       txtScanResult.setText(result);                       
                       Log.d("Scan Search","Found ISBN"+ isbn);
                           isbn=result;
                        }
                    });
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
Intent i2 = new Intent(this, SearchbyISBNActivity.class);
i2.putExtra("search_isbn", isbn);
i2.putExtra("Click_Search_by_Isbn", true);
    startActivity(i2);
 
}
public void scanSearch_Click(View v) {
    try
    {  

   
    }
   catch (Exception e)
   {
  
  Log.d("ScanSearch","Exception in"+e.toString());
       }
  }
  
       
}


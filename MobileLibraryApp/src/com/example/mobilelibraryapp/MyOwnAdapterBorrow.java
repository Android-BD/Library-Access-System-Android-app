package com.example.mobilelibraryapp;

import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import static android.provider.BaseColumns._ID;
import static com.example.mobilelibraryapp.Constants.My_Books_Table;

import static com.example.mobilelibraryapp.Constants.My_User_Table;
import static com.example.mobilelibraryapp.Constants.book_Author;
import static com.example.mobilelibraryapp.Constants.book_Isbn;
import static com.example.mobilelibraryapp.Constants.book_Name;
import static com.example.mobilelibraryapp.Constants.book_Status;
import static com.example.mobilelibraryapp.Constants.book_Keyword;
import static com.example.mobilelibraryapp.Constants.user_Pass;
import static com.example.mobilelibraryapp.Constants.image_url;

public class MyOwnAdapterBorrow extends BaseAdapter { 
  
    private Activity activity; 
    private ArrayList<HashMap<String, String>> data; 
    private static LayoutInflater inflater=null; 
   // public ImageLoader imageLoader;  
  
    public MyOwnAdapterBorrow(Activity a, ArrayList<HashMap<String, String>> d) { 
        activity = a; 
        data=d; 
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
        //imageLoader=new ImageLoader(activity.getApplicationContext()); 
    } 
  
    public int getCount() { 
        return data.size(); 
    } 
  
    public Object getItem(int position) { 
        return position; 
    } 
  
    public long getItemId(int position) { 
        return position; 
    } 
  
    public View getView(int position, View convertView, ViewGroup parent) { 
    	if(position==0)
    		BooksBorrowed.fine=0;
    	
    	Log.e("MYOWNADApterBorrow ","getView position"+position);
        View vi=convertView; 
        if(convertView==null) 
            vi = inflater.inflate(R.layout.borroweditem, null); 
        Log.e("MYOWNADApter","getView getting controls");
        TextView id = (TextView)vi.findViewById(R.id.bookID_BRW_result);
        TextView author = (TextView)vi.findViewById(R.id.txt_BRW_bookAuthor); // title 
        TextView title = (TextView)vi.findViewById(R.id.txt_BRW_bookName); // artist name 
        TextView dueDate = (TextView)vi.findViewById(R.id.txt_BRW_dueDate); // duration 
        ImageView imgview=(ImageView)vi.findViewById(R.id.imageView1); // thumb image 
        TextView fineLbl=(TextView)vi.findViewById(R.id.lbl_fine_oneBook);
        TextView fineTxt=(TextView)vi.findViewById(R.id.txt_fine_oneBook);
        
        Log.e("MYOWNADApter","getView getting DATA");
        HashMap<String, String> song = new HashMap<String, String>(); 
        song = data.get(position); 
        title.setText(song.get(book_Name));
        author.setText(song.get(book_Author)); 
        id.setText(song.get(Constants.book_ID));
        String dueOn=song.get(Constants.book_due_Date);
        dueDate.setText(dueOn); 
       
       String imgsrc= song.get(image_url);
       
       
       
try
{
		Log.e("MYOWNADApterBorrow","Image"+imgsrc);
		Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(imgsrc).getContent());
		imgview.setImageBitmap(bitmap);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
		   Date dueONDate=new Date();
		  try 
		  {
			  dueONDate = df.parse(dueOn);
			  Log.e("DUE DATE","Found NOW"+dueDate.toString());
		  } 
		  catch (ParseException e) 
		  {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		  
		  Date date = new Date();
		  Log.e("DATE","Found NOW"+date.toString());
		  Log.e("DUE DATE","Found NOW"+dueDate.toString());
		  
		  if(dueONDate.before(date))
		  {
			  int MILLISECONDS_IN_DAY= 24 * 60 * 60 *1000;
			  double diff = Math.abs(date.getTime() - dueONDate.getTime());
			  
			  int days = (int) (diff / MILLISECONDS_IN_DAY);	  
			  Log.e("MYOWNADApterBorrow","IN BACKGROUND set color");
			  vi.setBackgroundColor(Color.RED);
			  Log.e("My own Adapter","diff"+days);
			  fineLbl.setVisibility(View.VISIBLE);
			  fineTxt.setVisibility(View.VISIBLE);
			  fineTxt.setText(days+"");
//			  Log.d("Detail B","Adding to fine previous "+BooksBorrowed.fine);
//			  BooksBorrowed.fine+=days;
//			  Log.d("Detail B","Adding to fine AFTER "+BooksBorrowed.fine);
		  }
		  else
		  {
			  fineLbl.setVisibility(View.INVISIBLE);
		  }
	 
   
}
catch(Exception e)
{
		Log.d("IMAGE OWN ADAPTER","ACTCH BLOCK");
}
        return vi; 
    } 
}
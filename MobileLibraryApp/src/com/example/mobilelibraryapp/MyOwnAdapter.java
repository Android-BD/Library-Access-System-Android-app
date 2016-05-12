package com.example.mobilelibraryapp;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
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
import android.widget.Button;
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

public class MyOwnAdapter extends BaseAdapter { 
  
    private Activity activity; 
    private ArrayList<HashMap<String, String>> data; 
    private static LayoutInflater inflater=null; 
   // public ImageLoader imageLoader;  
  
    public MyOwnAdapter(Activity a, ArrayList<HashMap<String, String>> d) { 
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
    	
    	Log.e("MYOWNADApter","getView");
        View vi=convertView; 
        if(convertView==null) 
            vi = inflater.inflate(R.layout.resultitem, null); 
        Log.e("MYOWNADApter","getView getting controls");
        TextView title = (TextView)vi.findViewById(R.id.Author_result); // title 
        TextView artist = (TextView)vi.findViewById(R.id.bookName_result); // artist name 
        TextView duration = (TextView)vi.findViewById(R.id.Status_result); // duration 
        ImageView imgview=(ImageView)vi.findViewById(R.id.imageView1); // thumb image 
  
        HashMap<String, String> song = new HashMap<String, String>(); 
        song = data.get(position); 
        Log.e("MYOWNADApter","getView fetching data"+song.size());
        // Setting all values in listview 
        Log.e("MYOWNADApter","getView fetching data"+song.get(book_Author)+""+song.get(book_Name));
        title.setText(song.get(book_Author));
        Log.e("MYOWNADApter","set done");
        Log.e("MYOWNADApter","getView fetching data"+song.get(book_Name));
        artist.setText(song.get(book_Name)); 
        Log.e("MYOWNADApter","getView fetching data"+song.get(Constants.book_due_Date));
        duration.setText(song.get(Constants.book_due_Date)); 
        Log.e("MYOWNADApter","getView fetching image");
        String imgsrc= song.get(image_url);
        
        String status= song.get(Constants.book_Status);
        Button btnReserve= (Button)vi.findViewById(R.id.btn_reserve);
        if(status.equalsIgnoreCase("unavailable"))
        {
        	btnReserve.setClickable(false);
        	btnReserve.setBackgroundColor(Color.GRAY);
        	btnReserve.setText("Unavailable");
        }
        else
        {
        	btnReserve.setClickable(true);
        	btnReserve.setBackgroundColor(Color.GREEN);
        	btnReserve.setText("Details");
        }
try
{
		Log.e("MYOWNADApter","Image"+imgsrc);
       Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(imgsrc).getContent());
       imgview.setImageBitmap(bitmap);
}
catch(Exception e)
{
		Log.d("IMAGE OWN ADAPTER","ACTCH BLOCK");
}
        return vi; 
    } 
}
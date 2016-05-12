package com.example.mobilelibraryapp;

import android.provider.BaseColumns;

public interface Constants extends BaseColumns{
	public final static String IPP="10.59.1.40";
	public final static String active_user="Active_User";
	public final static String active_user_ID="Active_User_ID";
	public static String My_Books_Table="Books";
	public static String My_User_Table="Users";
	public static String book_Name="Name";
	public static String book_Isbn="ISBN";
	public static String book_Author="Author";
	public static String book_Status="Status";
	public static String book_Keyword="Keywords";
	public static String user_name="Name";
	public static String user_Pass="Password";
	public static String book_ID="Book_ID";
	public static String user_Status="Status";
	public static String image_url="ImageUrl";
	public static String My_Reservation_Table="ReservedBooks";
	public static String Reserved_by ="Reserved_By";
	public static String book_due_Date="Due_date";
	public static String book_reservation_Date="Reserve_Date";
}

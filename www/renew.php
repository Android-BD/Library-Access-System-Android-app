<?php
//session_start();
//require "sql_connection.php";
$hostname_localhost ="localhost";
$database_localhost ="Library";
$username_localhost ="root";
$password_localhost ="";
$localhost = mysql_connect($hostname_localhost,$username_localhost,$password_localhost)
or
trigger_error(mysql_error(),E_USER_ERROR);
mysql_select_db($database_localhost, $localhost);
$userid=$_POST['userid'];
$bookid=$_POST['bookid'];
$reservedby=$_POST['reservedby'];

$get_reserve=" SELECT * FROM reservedbooks  WHERE Reserved_by='".$userid."' AND Book_id=$bookid";
$query_get_renew= mysql_query($get_reserve)or die(mysql_error());
if($row = mysql_fetch_array($query_get_renew))
{ 
  if($row['Available_renew']<1)
  echo "No More Renews Possible";
  else
  {
	$query_reserve ="UPDATE reservedbooks SET Due_date=DATE_ADD(Due_date, INTERVAL 7 DAY) WHERE Reserved_by='".$userid."' AND Book_id=$bookid";
	$query_exec =mysql_query($query_reserve)or die(mysql_error());
	if($query_exec)
		echo "Book Renewed";
	else
	echo "Book Not Renewed";
	}
}
else
{
	echo "Book Not found Reserved"; 
}
?>
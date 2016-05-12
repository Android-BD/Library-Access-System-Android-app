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
//$isbn=$_POST['ISBN'];
//$query_find="SELECT * FROM reservedbooks WHERE user_id='".$userid."' AND book_ISBN=$isbn ";
//$query_already_reserved=mysql_query($query_find)or die(mysql_error());
//if($row = $query_exec) { }else{echo "you already have a copy of the book issued";

$reserveddate=strtotime( date("Y-m-d") );//$phpdate;
$date=date("Y-m-d");
$duedate= strtotime(date("Y-m-d", strtotime($date)) . " +30 days");
$query_reserve ="INSERT INTO reservedbooks(user_id, Book_id, Reserved_by,Due_date,Reserved_date)VALUES('$userid', '$bookid','$reservedby',((CURRENT_DATE)+3),CURRENT_DATE)";
$query_exec =mysql_query($query_reserve)or die(mysql_error());



 if($query_exec) { 
 
  echo "Book Reserved"; 
}
else
{
echo "Book Not Reserved"; 
}
?>
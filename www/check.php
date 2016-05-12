<?php
$hostname_localhost ="localhost";
$database_localhost ="Library";
$username_localhost ="root";
$password_localhost ="";
$localhost = mysql_connect($hostname_localhost,$username_localhost,$password_localhost)
or
trigger_error(mysql_error(),E_USER_ERROR);

mysql_select_db($database_localhost, $localhost);

$myusername = $_POST['myusername'];
$mypassword = $_POST['mypassword'];

$query_search = "select * from tbl_user where username = '".$myusername."' AND password = '".$mypassword. "'";
$query_exec = mysql_query($query_search) or die(mysql_error());
$rows = mysql_num_rows($query_exec);
//echo $rows;
 if($rows == 0) { 
 echo "No Such User Found"; 
 }
 else  {
	echo "User Found ||".$rows['_id']; 
}
?>

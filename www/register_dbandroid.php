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


$myusername=$_POST['myusername'];
$mypassword=$_POST['mypassword'];
$myemail=$_POST['myemail'];





$query_insert ="INSERT INTO tbl_user(,username, password, email)VALUES('$myusername', '$mypassword','$myemail')";
$query_exec =mysql_query($query_insert)or die(mysql_error());
//$rows = mysql_num_rows($query_exec);
//echo $rows;
 if($query_exec) { 
 
 	echo "User Registered"; 
}
else
{
	echo "Username already exists.User Not Registered"; 
}

?>

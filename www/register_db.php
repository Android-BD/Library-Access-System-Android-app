<?php
session_start();
require "sql_connection.php";

$myusername=$_POST['myusername'];
$mypassword=$_POST['mypassword'];
$myemail=$_POST['myemail'];

mysql_query("INSERT INTO tbl_user(_id,username, password, email)VALUES('','$myusername', '$mypassword','$myemail')")or die(mysql_error());
echo "User Registered";

?>
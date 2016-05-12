<?php
session_start();
//if(!session_is_registered(myusername)){
//header("location:main_login.php");
//}
$host="localhost"; // Host name 
$username="root"; // Mysql username 
$password=""; // Mysql password 
$db_name="androidroot"; // Database name 
$tbl_name="users"; // Table name 

// Connect to server and select databse.
mysql_connect("$host", "$username", "$password")or die("cannot connect"); 
mysql_select_db("$db_name")or die("cannot select DB");

// username and password sent from form 
//$myusername=$_POST['myusername']; 
//$mypassword=$_POST['mypassword']; 

// To protect MySQL injection (more detail about MySQL injection)
//$myusername = stripslashes($myusername);
//$mypassword = stripslashes($mypassword);
//$myusername = mysql_real_escape_string($myusername);
//$mypassword = mysql_real_escape_string($mypassword);
$sql="SELECT * FROM $tbl_name";
// WHERE username='$myusername' and password='$mypassword'";
$result=mysql_query($sql);

?>

<html>
<body>
Login Successful
</body>
</html>
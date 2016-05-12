<?php

$hostname_localhost ="localhost";
$database_localhost ="androidroot";
$username_localhost ="root";
$password_localhost ="";
$localhost = mysql_connect($hostname_localhost,$username_localhost,$password_localhost)
or
trigger_error(mysql_error(),E_USER_ERROR);

mysql_select_db($database_localhost, $localhost);

$query_select =mysql_query("SELECT * FROM Books");
while($row = mysql_fetch_array($query_select))
  {
  echo $row['ISBN'] . " " . $row['Name']. " " . $row['Author'];
  echo "<br />";
  }


/*$con=mysqli_connect("example.com","peter","abc123","my_db");
// Check connection
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

$result = mysqli_query($con,"SELECT * FROM Persons");

while($row = mysqli_fetch_array($result))
  {
  echo $row['FirstName'] . " " . $row['LastName'];
  echo "<br />";
  }

mysqli_close($con);*/
?>
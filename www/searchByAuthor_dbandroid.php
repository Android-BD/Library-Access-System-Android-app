<?php
$hostname_localhost ="localhost";
$database_localhost ="Library";
$username_localhost ="root";
$password_localhost ="";
$localhost = mysql_connect($hostname_localhost,$username_localhost,$password_localhost)
or
trigger_error(mysql_error(),E_USER_ERROR);

mysql_select_db($database_localhost, $localhost);


$Author=$_POST['Author'];

$query_select =mysql_query("SELECT * FROM Books where Author LIKE '%".$Author."%'");
$searchresults=mysql_num_rows($query_select);
if($searchresults==0){
echo "Not in library"; 
}
else
{
echo "in library"; 
while($row = mysql_fetch_array($query_select))
  {
  echo "<Book>< Name>".$row['Name']."</Name>";
  
  echo"<Author>". $row['Author']. "</Author>";
  
  echo"<Status>". $row['Status']."</Status></Book>";
  }
}

?>
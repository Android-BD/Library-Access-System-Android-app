<?php
session_start();
require "sql_connection.php";

$ISBN=$_POST['ISBN'];
$Name=$_POST['Name'];
$Author=$_POST['Author'];
$Status=$_POST['Status'];

$query_select =mysql_query("SELECT * FROM Books where ISBN='".$ISBN."'");
$searchresults=mysql_num_rows($query_select);
if($searchresults==0){
echo "Not in library"; 
}
else
{
while($row = mysql_fetch_array($query_select))
  {
  echo $row['Name'] . " " . $row['Author']. " " . $row['Status'];
  echo "<br />";
  }
}

?>
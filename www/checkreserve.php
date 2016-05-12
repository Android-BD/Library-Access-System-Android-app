<?php
session_start();
require "sql_connection.php";

$userid=$_POST['userid'];
$bookid=$_POST['bookid'];
$reservedby=$_POST['reservedby'];
$reserveddate=$_POST['reserveddate'];
$duedate=$_POST['duedate'];

mysql_query("INSERT INTO reservedbooks(_id,user_id, Book_id, Reserved_by,Due_date,Reserved_date)VALUES('','$userid', '$bookid','$reservedby','$reserveddate','$duedate')")or die(mysql_error());
echo "Done!!!!";
?>



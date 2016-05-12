<?php
$db_host = "localhost";
$db_username = "root";
$db_pass = "";
$db_name = "androidroot";

@mysql_connect("$db_host", "$db_username", "$db_pass", "$db_name") or die("connection is fail.");
@mysql_select_db("$db_name") or die("database does not exsist.");
//echo "Successfully connection!!";
?>
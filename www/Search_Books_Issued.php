<?php 
  
/* 
 * Following code will list all the products 
 */
  
// array for JSON response 
$response = array(); 
  
// include db connect class 
require_once __DIR__ . '/db_connect.php'; 
  
// connecting to db 
$db = new DB_CONNECT(); 
$user=$_POST['User'];  
// get all products from products table 
$query = "SELECT * FROM reservedbooks as rs INNER JOIN books as b ON rs.Book_Id= b._id WHERE Reserved_By='".$user."'";
$result = mysql_query($query) or die(mysql_error()); 
  
// check for empty result 
if (mysql_num_rows($result) > 0) { 
    // looping through all results 
    // products node 
    $response["products"] = array(); 
  
    while ($row = mysql_fetch_array($result)) { 
        // temp user array 
        $product = array(); 
        $product["Book_ID"] = $row["_id"]; 
        $product["Name"] = $row["Name"]; 
        $product["Author"] = $row["Author"]; 
        $product["ISBN"] = $row["ISBN"]; 
        $product["Due_date"] = $row["Due_date"]; 
		$product["Image"] = $row["ImageSource"]; 
  
        // push single product into final response array 
        array_push($response["products"], $product); 
    } 
    // success 
    $response["success"] = 1; 
  
    // echoing JSON response 
    echo json_encode($response); 
} else { 
    // no products found 
    $response["success"] = 0; 
    $response["message"] = "No products found"; 
  
    // echo no users JSON 
    echo json_encode($response); 
} 
?>
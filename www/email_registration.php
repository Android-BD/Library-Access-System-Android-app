<?php  
 
require("./PHPMailer/class.phpmailer.php"); // path to the PHPMailer class
 
$mail = new PHPMailer();  
 $mail->SMTPSecure = 'ssl';
$mail->IsSMTP();  // telling the class to use SMTP
$mail->Mailer = "smtp";
$mail->Host = "ssl://smtp.gmail.com";
$mail->Port = 465;
$mail->SMTPAuth = true; // turn on SMTP authentication
$mail->Username = "mob.lib.cis600@gmail.com"; // SMTP username
$mail->Password = "librarytest"; // SMTP password 
 
$mail->From     = "mob.lib.cis600@gmail.com";
$mail->AddAddress("shallysahi@gmail.com");  
 
$mail->Subject  = "User Registered";
$mail->Body     = "Hi! \n\n User Registered.";
$mail->WordWrap = 50;  
 
if(!$mail->Send()) {
echo 'Message was not sent.';
echo 'Mailer error: ' . $mail->ErrorInfo;
} else {
echo 'Message has been sent.';
}
?>
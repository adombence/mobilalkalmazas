<?php
$host = "mysql.omega:3306";   //Host
$username = "cukorbeteg";    //User
$password = "Q2BfC4Udu97RMZg"; //Passsword
$database = "cukorbeteg";     // Database Name
 
//creating a new connection object using mysqli 
$conn = new mysqli($host, $username, $password, $database);
 
//if there is some error connecting to the database
//with die we will stop the further execution by displaying a message causing the error.
if ($conn->connect_error) {
    die("Database connection failed: " . $conn->connect_error);
}
?>
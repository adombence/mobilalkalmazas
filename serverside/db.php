<?php
$host = "";   //Host
$username = "";    //User
$password = ""; //Passsword
$database = "";     // Database Name

/* creating a new connection object using mysqli */
$conn = new mysqli($host, $username, $password, $database);
/* check connection */
if (mysqli_connect_errno()) {
    printf("Connect failed: %s\n", mysqli_connect_error());
    exit();
}

//printf("Initial character set: %s\n", $conn->character_set_name());

/* change character set to utf8mb4 */
if (!$conn->set_charset("utf8mb4")) {
    printf("Error loading character set utf8mb4: %s\n", $conn->error);
    exit();
} else {
    //printf("Current character set: %s\n", $conn->character_set_name());
}

//$conn->close();


?>

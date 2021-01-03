<?php
require_once 'db.php';

$id = mysqli_real_escape_string($conn, $_REQUEST['id']);

$response = array();
$sql = "SELECT * FROM `recipe`";
$result = $conn->query($sql) or die($conn->error);
while ($rows = mysqli_fetch_assoc($result)) {
    str_replace("\u00e1", "á", $rows);
    str_replace("\u00e9", "é", $rows);
    str_replace("\u00ed", "í", $rows);
    str_replace("\u00f3", "ó", $rows);
    str_replace("\u00f6", "ö", $rows);
    str_replace("\u0151", "ő", $rows);
    str_replace("\u00fa", "ú", $rows);
    str_replace("\u00fc", "ü", $rows);
    str_replace("\u0171", "ű", $rows);

    $response[] = $rows;
}
echo json_encode($response);

?>

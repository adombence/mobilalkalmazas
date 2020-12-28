<?php
require_once 'db.php';

$id = mysqli_real_escape_string($conn, $_REQUEST['id']);

$response = array();

$sql = "SELECT * FROM `datas` WHERE id=" . $id;
$result = $conn->query($sql) or die($conn->error);

while ($rows = mysqli_fetch_assoc($result)) {
    $response[] = $rows;
}

echo json_encode($response);

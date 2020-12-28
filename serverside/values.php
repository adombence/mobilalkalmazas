<?php
require_once('db.php');

$id = mysqli_real_escape_string($conn, $_REQUEST['id']);
$value = mysqli_real_escape_string($conn, $_REQUEST['value']);

$SQL = "INSERT INTO `sugar`(`id`,`value`) VALUES (" . $id . ',' . $value . ');';
echo "id= " . $id . "value= " . $value;

if (mysqli_query($conn, $SQL)) {
    echo "Records added successfully.";
} else {
    echo "ERROR: Could not able to execute $SQL. " . mysqli_error($conn);
}
?>
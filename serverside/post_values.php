<?php
require_once 'db.php';

$id = mysqli_real_escape_string($conn, $_REQUEST['id']);
$value = mysqli_real_escape_string($conn, $_REQUEST['value']);
$etkezesElott = mysqli_real_escape_string($conn, $_REQUEST['etkezeselott']);
$energy = mysqli_real_escape_string($conn, $_REQUEST['energy']);
$carbonhydrate = mysqli_real_escape_string($conn, $_REQUEST['$carbonhydrate']);


$SQL = "INSERT INTO `sugar`(`id`, `value`, `etkezesElott`, `energy`, `carbohydrate`) VALUES (" . $id . ',' . $value . ',' . $etkezesElott . ',' . $energy . ',' . $carbonhydrate . ');';

if (mysqli_query($conn, $SQL)) {
    echo "Records added successfully.";
} else {
    echo "ERROR: Could not able to execute $SQL. " . mysqli_error($conn);
}
?>
<?php
require_once 'db.php';
$response = array();

$action = mysqli_real_escape_string($conn, $_REQUEST['action']);
$id = mysqli_real_escape_string($conn, $_REQUEST['id']);
$gender = mysqli_real_escape_string($conn, $_REQUEST['gender']);
$age = mysqli_real_escape_string($conn, $_REQUEST['age']);
$weight = mysqli_real_escape_string($conn, $_REQUEST['weight']);
$height = mysqli_real_escape_string($conn, $_REQUEST['height']);
echo "action=" . $action . "id=" . $id . "gender=" . $gender . "age=" . $age . "weight=" . $weight .  "height=" . $height;
/*echo "\taction: " . $action . "\n";
echo "\tid: " . $id . "\n";
echo "\tgender: " . $gender . "\n";
echo "\tage: " . $age . "\n";
echo "\tweight: " . $weight . "\n";
echo "\theight: " . $height . "\n";*/

$SQL = "INSERT INTO `datas`(`id`, `gender`, `age`, `weight`, `height`) VALUES  (" . $id . ',' . $gender . ',' . $age . ',' . $weight . ',' . $height . ');';

if (mysqli_query($conn, $SQL)) {
    echo "Records added successfully.";
} else {
    echo "ERROR: Could not able to execute $SQL. " . mysqli_error($conn);
}
/*echo json_encode($response);*/


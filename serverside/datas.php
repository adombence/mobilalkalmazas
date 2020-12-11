<?php
require_once 'db.php';

$id = $_POST['id'];
$gender = $_POST['gender'];
$age = $_POST['age'];
$weight = $_POST['weight'];
$height = $_POST['height'];

$sql = "insert into datas (id, gender, age, weight, height) values ('$id','$gender','$age','$weight','$height')";

if (mysqli_query($conn, $sql)) {
    echo "succes";
} else {
    echo "error";
}

mysqli_close($conn);

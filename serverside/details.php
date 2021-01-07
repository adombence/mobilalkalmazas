<?php

require 'db.php';


$email = $_POST['email'];
$pass = $_POST['pass'];


$email = mysqli_real_escape_string($conn, $_REQUEST['email']);
$pass = mysqli_real_escape_string($conn, $_REQUEST['pass']);

$SQL = "SELECT id FROM users WHERE email = '" . $email . "' AND password = '" . $pass . "';";
$result = $conn->query($SQL) or die($conn->error);

$row = mysqli_fetch_array($result);

$id = $row[0];

mysqli_free_result($result);

$sugarDatas = "SELECT * FROM `sugar` WHERE id = " . $id . " ORDER BY `sugar`.`date` DESC";
$result = $conn->query($sugarDatas) or die($conn->error);
?>

<!DOCTYPE html>
<html lang="hu">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <link rel="stylesheet" href="css/style.css">
    <script src="js/linechart.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels"></script>

    <title>Cukorbeteg | Részletek</title>
    <link rel="icon" type="image/png" href="images/logo.png">
    <script>
        let tempval = [];
        let tempdat = [];
    </script>
</head>
<body>
<div class="container">
    <div class="chartBox">
        <canvas id="myChart" width="400" height="400" class="chart"></canvas>
    </div>
    <div class="user__datas">
        <table>
            <tr>
                <th>érték</th>
                <th>időpont</th>
                <th>étkezés</th>
                <th>energia</th>
                <th>szénhidrát</th>
            </tr>
            <?php
            $etketzes = "";
            while ($rows = $result->fetch_assoc()) {
                if ($rows['etkezesElott'] == 1) {
                    $etketzes = "előtt";
                } else {
                    $etketzes = "után";
                }
                ?>
                <tr>
                    <td><?php echo $rows['value'] ?></td>
                    <td><?php echo $rows['date'] ?></td>
                    <td><?php echo $etketzes ?></td>
                    <td><?php echo $rows['energy'] ?> kcal</td>
                    <td><?php echo $rows['carbohydrate'] ?> g</td>
                    <script>
                        tempval.push(<?php echo $rows['value'] ?>);
                        tempdat.push("<?php echo $rows['date'] ?>");
                        //console.log(tempval);
                    </script>
                </tr>
                <?php
            }
            ?>
        </table>
    </div>
</div>
<script>

    console.log(tempdat);
    console.log(tempval);
    let chart = BuildChart(tempdat, tempval, "vércukor értékek");
</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels"></script>

</body>
</html>
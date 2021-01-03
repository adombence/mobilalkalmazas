<?php

mysqli_set_charset($con, "utf8");
$text = "ádom";
echo $text;
echo "<br/><br/>";
echo mb_convert_encoding($text, "UTF-8");
echo "<br/><br/>";
echo iconv(mb_detect_encoding($text), "UTF-8", $text);
?>
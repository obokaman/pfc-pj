<?php
/*
session_name("PFC");
session_start();

if (!isset($_SESSION["visitas"])){
	$_SESSION["visitas"] = 0;
}else{
	$_SESSION["visitas"]++;
}

echo $_SESSION["visitas"];
*/

$message = "<form action='main.php' method='POST'>
								Nombre de la función: <input name='function' type='text'>
						</form>";
						
echo $message;

?>
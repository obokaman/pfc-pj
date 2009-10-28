<?php


include("includes.php");

session_name("PFC");
session_start();

activated('dgb', 'laskhdf')


/*
session_name("PFC");
session_start();

if (!isset($_SESSION["visitas"])){
	$_SESSION["visitas"] = 0;
}else{
	$_SESSION["visitas"]++;
}

echo $_SESSION["visitas"];


$message = "<form action='main.php' method='POST'>
								Nombre de la función: <input name='function' type='text'>
								User: <input name='user' type='text'>
								Pass: <input name='password' type='text'>
								<input type='submit'>
						</form>";
						
echo $message;
*/
?>
<?php include("includes.php");

session_name("PFC");
session_start();

if (isset($_REQUEST["user"])) print( json_encode(1)); 
else print( json_encode(0)); 

/*
if (isset($_SESSION["user"])){	
	echo 'Usuario registrado: '.$_SESSION["user"].'<br>';
	echo set_user('David', 'Garcia', 'Bautista', 'dvdgarcia.83@gmail.com', 'Blanes', 'Colegio1', 'cole@colegio1.com', 'alumno', 123, 1234);
}else{
	echo "usuario no  registrado";
}

get_user('dgb');


echo "<br><br>";

echo login('dgb', 123);

echo get_user_nick("dgb");
*/
	
?>

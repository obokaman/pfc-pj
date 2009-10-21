<?php include("includes.php");

session_name("PFC");
session_start();

fopen("/tmp/pfc.php.log", "a+");
// fwrite("Hola.");


//recoge: a) comprueba que esta en isset (sino, genera excepcion)
//b) limpia caracteres "raros" del valor (sanitize el valor; limpiarlo si no tiene buena pinta)
$f = recoge("function", "string");


//devuelve: recibe un valor de php (int, array, lo que sea) y
//hace un print( json_encode(valor) )
if ($f == "login") {
	devuelve( login( recoge("user", "string"),
	                           recoge("password", "string") ) );
}
else if ($f == "newUser") {
	devuelve(
		create_user(
			recoge("nick", "string"),
			recoge("name", "string"),
			recoge("surname1", "string"),
			recoge("surname2", "string"),
			recoge("email_user", "string"),
			recoge("city", "string"),
			recoge("school", "string"),
			recoge("email_school", "string"),
			recoge("password", "string")
		)
	);
}/*
else if ($f == "changeUser") {
	$nick = recoge("nick", "string");
	if (isset($_SESSION["user"]) && $_SESSION["user"]==$nick) {
		devuelve(
			set_user(
				$nick.
				recoge("name", "string"),
				recoge("surname1", "string"),
				recoge("surname2", "string"),
				recoge("email_user", "string"),
				recoge("city", "string"),
				recoge("school", "string"),
				recoge("email_school", "string"),
				recoge("password", "string"),
				...
			)
		);
	}
	else escribe_log("ADFadskfdashfldsjfdsali");*/
	

//else ...




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

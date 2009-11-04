<?php include("includes.php");

session_name("PFC");
session_start();
$connection = open_connection();

$f = clean("function", "string"); //--------------> Pasar el texto a minusculas ?????

if ($f == "login") {
	send( login( clean("nick", "string"),	clean("password", "string") ) );
}
else if ($f == "newUser") {
	send(
		create_user(
			clean("nick", "sql"),
			clean("name", "sql"),
			clean("surname1", "sql"),
			clean("surname2", "sql"),
			clean("email_user", "sql"),
			clean("city", "sql"),
			clean("school", "sql"),
			clean("email_school", "sql"),
			clean("password", "sql")
		)
	);
}
else if ($f == "changeUser") {	
		send(
			set_user(
				clean("name", "sql"),
				clean("surname1", "sql"),
				clean("surname2", "sql"),
				clean("email_user", "sql"),
				clean("city", "sql"),
				clean("school", "sql"),
				clean("email_school", "sql"),
				clean("type_user", "sql"),
				clean("oldpassword", "sql"),
				clean("password", "sql")
			)
		);
}
else if ($f == "getUser") {	
		send(
			get_user_nick(
				clean("nick", "string")
			)
		);
}
else if ($f == "getMyTeams") {	
		send(
			getMyTeams()
		);
}
else if ($f == "getMyChampionships") {	
		send(
			getMyChampionships()
		);
}
else if ($f == "getCircuits") {	
		send(
			get_circuits()
		);
}
else if ($f == "activated") {	
		activated(
			clean("nick", "string"),
			clean("activation_key", "string")
		);
}
	//else escribe_log("ADFadskfdashfldsjfdsali");*/
close_connection($connection);
	
	


?>
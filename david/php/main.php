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
			clean("nick", "string"),
			clean("name", "string"),
			clean("surname1", "string"),
			clean("surname2", "string"),
			clean("email_user", "string"),
			clean("city", "string"),
			clean("school", "string"),
			clean("email_school", "string"),
			clean("password", "string")
		)
	);
}
else if ($f == "changeUser") {	
		send(
			set_user(
				clean("name", "string"),
				clean("surname1", "string"),
				clean("surname2", "string"),
				clean("email_user", "string"),
				clean("city", "string"),
				clean("school", "string"),
				clean("email_school", "string"),
				clean("type_user", "string"),
				clean("oldpassword", "string"),
				clean("password", "string")
			)
		);
}
else if ($f == "getUsers") {	
		send(
			get_users()
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

<?php include("includes.php");

session_name("PFC");
session_start();

$f = clean("function", "string");

if ($f == "login") {
	send( login( clean("user", "string"),	clean("password", "string") ) );
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
	//else escribe_log("ADFadskfdashfldsjfdsali");*/
	
	
?>

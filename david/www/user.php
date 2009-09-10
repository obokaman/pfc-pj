<?php include("connection.php");?>

<?php
	
	function create_user($nick, $name, $surname1, $surname2, $email_user, $population, $school, $email_school, $type_user, $pass){
		
		$connection = open_connection();
		
	    $query = "INSERT INTO user ( nick, name, surname1, surname2, email_user, population, school, email_school, type_user, pass)
						VALUES ('$nick',' $name',' $surname1',' $surname2',' $email_user',' $population',' $school',' $email_school','$type_user',' $pass')";
						
		
		
		if (!mysql_query($query, $connection)) {
			echo "El nick introducido ya existe";
		}else{
			echo "1 record added";
		}
		
		close_connection($connection);
		
	}

	function get_users(){
		
		$conexion = open_connection();
		$query =  "SELECT * FROM user";
		$result_query = mysql_query($query, $conexion) or die(mysql_error());
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		print(json_encode($arr)); 
		close_connection($conexion);
		
	}

	function get_user_id($id){
	
		$conexion = open_connection();
		$query =  "SELECT * FROM user WHERE id_user = '$id'";
		$result_query = mysql_query($query, $conexion) or die(mysql_error());
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		print( json_encode($arr)); 
		close_connection($conexion);
	}
	
	function get_user_nick($nick){
	
		$conexion = open_connection();
		$query =  "SELECT * FROM user WHERE nick = '$nick'";
		$result_query = mysql_query($query, $conexion) or die(mysql_error());
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		print( json_encode($arr)); 
		close_connection($conexion);
	}
	
	function set_user($nick, $name, $surname1, $surname2, $email_user, $population, $school, $email_school, $type_user, $pass){
		
		$connection = open_connection();
		
	    $query = "UPDATE user SET name='$name', surname1='$surname1', surname2= '$surname2', email_user='$email_user', population='$population', school='$school', email_school='$email_school', type_user='$type_user', pass='$pass' WHERE nick = '$nick'";

		if (!mysql_query($query, $connection)) {
			echo "No se ha podido modificar alguno de los parametros";
		}else{
			echo "OK";
		}
		
		close_connection($connection);
		
	}
	
	function delete_user_id($id){
		
		$connection = open_connection();
		
	    $query = "DELETE FROM user WHERE id_user = '$id'";

		if (!mysql_query($query, $connection)) {
			echo "No se ha podido borrar al usuario";
		}else{
			echo "OK";
		}
		
		close_connection($connection);
		
	}
	
	function delete_user_nick($nick){
		
		$connection = open_connection();
		
	    $query = "DELETE FROM user WHERE nick = '$nick'";

		if (!mysql_query($query, $connection)) {
			echo "No se ha podido borrar al usuario";
		}else{
			echo "OK";
		}
		
		close_connection($connection);
		
	}
?>
<?php include("connection.php");?>
<?php include("error.php");?>

<?php

	/*La función crea un usuario en la BBDD con los parametros que le pasamos de entrada*/
	function create_user($nick, $name, $surname1, $surname2, $email_user, $population, $school, $email_school, $type_user, $pass){
	/*Pre: - */	
		$connection = open_connection();
		
	    $query = "INSERT INTO user ( nick, name, surname1, surname2, email_user, population, school, email_school, type_user, pass)
						VALUES ('$nick',' $name',' $surname1',' $surname2',' $email_user',' $population',' $school',' $email_school','$type_user',' $pass')";
						
		if (!mysql_query($query, $connection)) {
			my_error(mysql_errno($connection) . ": " . mysql_error($connection), 1);
			close_connection($connection);	
			return false;
		}else{	
			close_connection($connection);	
			return true;
		}
	}
	/*Post: La función nos retorna cierto en caso de que haya tenido exito la creacion del nuevo usuario, en caso contrario devuelve falso*/
	
	
	/*La función nos devuelve la lista de todos los usuarios que estan almacenados en la BBDD, en formato JSON*/
	function get_users(){
	/*Pre: - */	
		$connection = open_connection();
		$query =  "SELECT * FROM user";
		$result_query = mysql_query($query, $connection) or my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		print(json_encode($arr)); 
		close_connection($connection);		
	}
	/*Post: La función nos devuelve la lista de todos los usuarios que estan almacenados en la base de datos, en formato JSON*/

	
	/*La función nos devuelve la información del usuario en formato JSON a partir del identificador que tiene en la BBDD*/
	function get_user_id($id){
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT * FROM user WHERE id_user = '$id'";
		$result_query = mysql_query($query, $connection) or my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		print( json_encode($arr)); 
		close_connection($connection);
	}
	/*Post: Retorna la información del usuario en formato JSON*/
	
	
	/*La función nos devuelve la información de usuario en formato JSON a partir del nick del usuario*/
	function get_user_nick($nick){
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT * FROM user WHERE nick = '$nick'";
		$result_query = mysql_query($query, $connection) or my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		print( json_encode($arr)); 
		close_connection($connection);
	}
	/*Post: Retorna la información del usuario en formato JSON*/
	
	
	/*Esta función modifica los campos almacenados de un usuario en la BBDD*/
	function set_user($id, $nick, $name, $surname1, $surname2, $email_user, $population, $school, $email_school, $type_user, $pass){
	/*Pre: El identificador del usuario existe y los campos no son nulos a exception del segundo apellido o del email de la escuela */
		$connection = open_connection();
		
	    $query = "UPDATE user SET nick = '$nick' name='$name', surname1='$surname1', surname2= '$surname2', email_user='$email_user', population='$population', school='$school', email_school='$email_school', type_user='$type_user', pass='$pass' WHERE id_user='$id'";

		if (!mysql_query($query, $connection)) {
			my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
			close_connection($connection);	
			return false;
		}else{
			close_connection($connection);	
			return true;
		}
	}
	/*Post: La función nos retorna cierto si sea modificado correctamente en usuario de la entrada, en caso contrario retorna falso*/
	
	
	/*Esta función borra el usuario de la BBDD identificado por el mismo identificador que tienen en la BBDD*/
	function delete_user_id($id){
	/*Pre: - */
		$connection = open_connection();
		
	    $query = "DELETE FROM user WHERE id_user = '$id'";

		$result_query = mysql_query($query, $connection) or my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
		
		close_connection($connection);		
	}
	/*Post: La función borra el usuario de la BBDD*/
	
	
	/*La función comprueba si el identificador del usuario existe*/
	function exist_user($id){	
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT * FROM user WHERE id_user = '$id'";
		$result_query = mysql_query($query, $connection) or my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
	
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		if (count($arr)==0)	return false;
		else return true;
	}
	/*Post: Devuelve cierto en caso de que el identificador del usuario existe, en caso contrario devuelve falso*/
?>
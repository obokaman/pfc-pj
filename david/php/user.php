<?php

	/*La función crea un usuario en la BBDD con los parametros que le pasamos de entrada*/
	function create_user($nick, $name, $surname1, $surname2, $email_user, $population, $school, $email_school, $type_user, $pass){
	/*Pre: - */	
		$connection = open_connection();
		
	    $query = "INSERT INTO user ( nick, name, surname1, surname2, email_user, population, school, email_school, type_user, pass)
						VALUES ('$nick',' $name',' $surname1',' $surname2',' $email_user',' $population',' $school',' $email_school','$type_user',' $pass')";
						
		if (!mysql_query($query, $connection)) {
			if (exist_user_nick($nick)) return 1;
			else{			
						my_error('CREATE_USER-> '.mysql_errno($connection) . ": " . mysql_error($connection), 1);
						close_connection($connection);	
						return 2;
					}
		}else{	
			close_connection($connection);	
			return 0;
		}

	}
	/*Post: La función devolverá según el comportamiento de la inserción del nuevo usuario:
				- Un 0, si el usuario se ha insertado correctamente
				- Un 1, en caso de que el nick del nuevo usuario ya exista
				- Un 2 si se ha producido algún error en el momento de insertar en nuevo usuario */
	
	
	/*La función nos devuelve la lista de todos los usuarios que estan almacenados en la BBDD, en formato JSON*/
	function get_users(){
	/*Pre: - */	
		$connection = open_connection();
		$query =  "SELECT * FROM user";
		$result_query = mysql_query($query, $connection) or my_error('GET_USERS-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		close_connection($connection);		
		return($arr);	
	}
	/*Post: La función nos devuelve la lista de todos los usuarios que estan almacenados en la base de datos, en formato JSON*/

	
	/*La función nos devuelve la información del usuario en formato JSON a partir del identificador que tiene en la BBDD*/
	function get_user_id($id){
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT * FROM user WHERE id_user = '$id'";
		$result_query = mysql_query($query, $connection) or my_error('GET_USER_ID-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		close_connection($connection);		
		return($arr);
	}
	/*Post: Retorna la información del usuario en formato JSON*/
	
	
	/*La función nos devuelve la información de usuario en formato JSON a partir del nick del usuario*/
	function get_user_nick($nick){
	/*Pre: - */	
		if (isset($_SESSION["user"])){
			
			$connection = open_connection();
			
			if ($nick == $_SESSION["user"]){
				$nick_session = $_SESSION["user"];
				$query =  "SELECT nick, name, surname1, surname2, email_user, population, school, email_school, type_user FROM user WHERE nick = '$nick_session '";
			}else{
				$query =  "SELECT nick, name, population, school FROM user WHERE nick = '$nick'";
			}
			
			$result_query = mysql_query($query, $connection) or my_error('GET_USER_NICK-> '.mysql_errno($connection).": ".mysql_error($connection), 1);			
			$arr = array();
			
			while($obj = mysql_fetch_object($result_query)) {
				$arr[] = $obj;
			}
			
			close_connection($connection);		
		     return($arr);
			
			}
		
		}
	/*Post: La función devolvera información distinta en JSON en diferentes casos:
				- Devuelve toda la información del usuario, si el nick de la entrada es el mismo que el nick del usuario logueado de la session.
				- Devuelve el nick, en nombre, población y colegio si el nick de la entrada es distinto que el nick del usuario logueado de la session.
				- No devuelve nada en caso de que el usuario de la session no este logueado. */
	
	
	/*Esta función modifica los campos almacenados de un usuario en la BBDD*/
	function set_user($name, $surname1, $surname2, $email_user, $population, $school, $email_school, $type_user, $old_pass, $pass){
	/*Pre: El identificador del usuario existe y los campos no son nulos a exception del segundo apellido o del email de la escuela */
		$connection = open_connection();
		
		if(!isset($_SESSION["user"])){
				return 1; /*No estamos en modo login*/
		}else{		
				$nick_session = $_SESSION["user"];
				$query = "SELECT pass FROM user WHERE nick='$nick_session'";
				$result_query = mysql_query($query, $connection);
				while ($row = mysql_fetch_row($result_query)){
						$pass_user_session = $row[0];
				}		
		
				if ($pass_user_session != $old_pass){
					return 2;	  /*La 'old_pass' no coincide con la contraseña del usuario de la sesion*/
				}else{				
						$query = "UPDATE user SET name='$name', surname1='$surname1', surname2= '$surname2', email_user='$email_user', population='$population', school='$school', email_school='$email_school', type_user='$type_user', pass='$pass' WHERE nick='$nick_session' and pass='$old_pass'";
						
						$result_query = mysql_query($query, $connection) or my_error('SET_USER-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
						close_connection($connection);	
						return 1; /*Se ha realizado el cambio correctamente*/
				}
		}
	}
	/*Post: La función retorna enteros distintos dependiendo del comportamiento que siga:
				- 0 si el usuario de la sesion no esta logueado
				- 1 se realiza el cambio correctamente ya que el 'old_pass' es correcto para el usuario logueado
				- 2 el 'old_pass' no corresponde al usuario logueado*/
	
	
	/*Esta función borra el usuario de la BBDD identificado por el mismo identificador que tienen en la BBDD*/
	function delete_user_id($id){
	/*Pre: - */
		$connection = open_connection();
		
	    $query = "DELETE FROM user WHERE id_user = '$id'";

		$result_query = mysql_query($query, $connection) or my_error('DELETE_USER_ID-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		close_connection($connection);		
	}
	/*Post: La función borra el usuario de la BBDD*/
	
	
	/*La función comprueba si el identificador del usuario existe*/
	function exist_user_id($id){	
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT * FROM user WHERE id_user = '$id'";
		$result_query = mysql_query($query, $connection) or my_error('EXIST_USER_ID-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
	
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		if (count($arr)==0)	return false;
		else return true;
	}
	/*Post: Devuelve cierto en caso de que el identificador del usuario existe, en caso contrario devuelve falso*/
	
	/*La función comprueba si el nick del usuario existe*/
	function exist_user_nick($nick){	
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT * FROM user WHERE nick = '$nick'";
		$result_query = mysql_query($query, $connection) or my_error('EXIST_USER_NICK-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
	
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		if (count($arr)==0)	return false;
		else return true;
	}
	/*Post: Devuelve cierto en caso de que el nick del usuario existe, en caso contrario devuelve falso*/
	
	
	/* Función que gestiona el logueo de los usuarios*/
	function login( $nick, $pass){
	/*Pre: - */
		$connection = open_connection();
		
	    $query = "SELECT activated FROM user WHERE nick = '$nick' AND pass = '$pass'";
		
		$result_query = mysql_query($query, $connection) or my_error('LOGIN-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
	
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;		 
		 }
		
		close_connection($connection);	
		
		if (count($arr)==0)	return 1; /*El nick no existe o la contraseña es incorrecta*/
		else{
			if ( $arr[0] ){   /*La cuenta del usuario esta activada*/
				$_SESSION["user"] = $nick;
				return 0;
			}
			else 	return 2;			/*La cuenta del usuario no esta activada*/
		 };		
	 }
	/*Post: */	
	
	
	
?>
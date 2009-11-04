<?php

	/*La función crea un usuario en la BBDD con los parametros que le pasamos de entrada*/
	function create_user($nick, $name, $surname1, $surname2, $email_user, $city, $school, $email_school, $pass){
	/*Pre: - */	
		global $connection;
		
		$key_act = make_activationkey();
	    $query = "INSERT INTO user ( nick, name, surname1, surname2, email_user, city, school, email_school, type_user, pass, activation_key)
						VALUES ('$nick',' $name',' $surname1',' $surname2',' $email_user',' $city',' $school',' $email_school','alumno','$pass', '$key_ac')";
						
		if (!mysql_query($query, $connection)) {
			if (exist_user_nick($nick)) return 1;
			else{			
						my_error('CREATE_USER-> '.mysql_errno($connection) . ": " . mysql_error($connection), 1);
							
						return 2;
					}
		}else	return 0;
		
	}
	/*Post: La función devolverá según el comportamiento de la inserción del nuevo usuario:
				- Un 0, si el usuario se ha insertado correctamente
				- Un 1, en caso de que el nick del nuevo usuario ya exista
				- Un 2 si se ha producido algún error en el momento de insertar en nuevo usuario */
	
	
	/*La función nos devuelve la lista de todos los usuarios que estan almacenados en la BBDD*/
	function get_users(){
	/*Pre: - */	
		global $connection;
		
		$query =  "SELECT * FROM user";
		$result_query = mysql_query($query, $connection) or my_error('GET_USERS-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

		return(extract_rows($result_query));	
	}
	/*Post: La función nos devuelve una array de objetos de todos los usuarios que estan almacenados en la base de datos*/

	
	/*La función nos devuelve la información del usuario a partir del identificador que tiene en la BBDD*/
	function get_user_id($id){
	/*Pre: - */
		global $connection;
		
		$query =  "SELECT * FROM user WHERE id_user = '$id'";
		$result_query = mysql_query($query, $connection) or my_error('GET_USER_ID-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		return(extract_row($result_query));
	}
	/*Post: Retorna una array con el objeto usuario que contiene la información del usuario*/
	
	
	/*La función nos devuelve la información de usuario a partir del nick del usuario*/
	function get_user_nick($nick){
	/*Pre: - */	
		if (isset($_SESSION["user"])){
			
			global $connection;
			
			if ($nick == $_SESSION["user"]){
				$nick_session = $_SESSION["user"];
				$query =  "SELECT nick, name, surname1, surname2, email_user, city, school, email_school, type_user FROM user WHERE nick = '$nick_session '";
			}else{
				$query =  "SELECT nick, name, city, school FROM user WHERE nick = '$nick'";
			}
			
			$result_query = mysql_query($query, $connection) or my_error('GET_USER_NICK-> '.mysql_errno($connection).": ".mysql_error($connection), 1);			
			
		    return(extract_row($result_query));
			
			}		
		}
	/*Post: La función devolvera información distinta en diferentes casos:
				- Devuelve toda la información del usuario, si el nick de la entrada es el mismo que el nick del usuario logueado de la session.
				- Devuelve el nick, en nombre, población y colegio si el nick de la entrada es distinto que el nick del usuario logueado de la session.
				- No devuelve nada en caso de que el usuario de la session no este logueado. */
	
	
	/*Esta función modifica los campos almacenados de un usuario en la BBDD*/
	function set_user($name, $surname1, $surname2, $email_user, $city, $school, $email_school, $type_user, $old_pass, $pass){
	/*Pre: El identificador del usuario existe y los campos no son nulos a exception del segundo apellido o del email de la escuela */
		
		global $connection;
		
		if(!isset($_SESSION["user"])){
				return 1; /*No estamos en modo login*/
		}else{		
				$nick_session = $_SESSION["user"];
				$query = "SELECT pass FROM user WHERE nick='$nick_session'";
				$result_query = mysql_query($query, $connection);
				$pass_user_session = extract_row($result_query);
				
				if ($pass_user_session -> pass != $old_pass){
					return 2;	  /*La 'old_pass' no coincide con la contraseña del usuario de la sesion*/
				}else{	
						if($pass != null){
								$query = "UPDATE user SET name='$name', surname1='$surname1', surname2= '$surname2', email_user='$email_user', city='$city', school='$school', email_school='$email_school', type_user='$type_user', pass='$pass' WHERE nick='$nick_session' and pass='$old_pass'";
						}else{
								$query = "UPDATE user SET name='$name', surname1='$surname1', surname2= '$surname2', email_user='$email_user', city='$city', school='$school', email_school='$email_school', type_user='$type_user' WHERE nick='$nick_session' and pass='$old_pass'";
						}

						$result_query = mysql_query($query, $connection) or my_error('SET_USER-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
							
						return 0; /*Se ha realizado el cambio correctamente*/
				}
		}
	}
	/*Post: La función retorna enteros distintos dependiendo del comportamiento que siga:
				- 1 si el usuario de la sesion no esta logueado
				- 0 se realiza el cambio correctamente ya que el 'old_pass' es correcto para el usuario logueado
				- 2 el 'old_pass' no corresponde al usuario logueado*/
	
	
	/*Esta función borra el usuario de la BBDD identificado por el mismo identificador que tienen en la BBDD*/
	function delete_user_id($id){
	/*Pre: - */
	
		global $connection;
		
	    $query = "DELETE FROM user WHERE id_user = '$id'";
		$result_query = mysql_query($query, $connection) or my_error('DELETE_USER_ID-> '.mysql_errno($connection).": ".mysql_error($connection), 1);		
	
	}
	/*Post: La función borra el usuario de la BBDD*/
	
	
	/*La función comprueba si el identificador del usuario existe*/
	function exist_user_id($id){	
	/*Pre: - */
	
		global $connection;
		
		$query =  "SELECT * FROM user WHERE id_user = '$id'";
		$result_query = mysql_query($query, $connection) or my_error('EXIST_USER_ID-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		if (count(extract_row($result_query))==0)	return false;
		else return true;
	}
	/*Post: Devuelve cierto en caso de que el identificador del usuario existe, en caso contrario devuelve falso*/
	
	/*La función comprueba si el nick del usuario existe*/
	function exist_user_nick($nick){	
	/*Pre: - */
		global $connection;
		$query =  "SELECT * FROM user WHERE nick = '$nick'";
		$result_query = mysql_query($query, $connection) or my_error('EXIST_USER_NICK-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		if (count(extract_row($result_query))==0)	return false;
		else return true;
	}
	/*Post: Devuelve cierto en caso de que el nick del usuario existe, en caso contrario devuelve falso*/
	
	
	/* Función que gestiona el logueo de los usuarios*/
	function login( $nick, $pass){
	/*Pre: - */
	
		global $connection;
		
	    $query = "SELECT activated,pass FROM user WHERE nick = '$nick'";
		
		$result_query = mysql_query($query, $connection) or my_error('LOGIN-> '.mysql_errno($connection).": ".mysql_error($connection), 1);	
		$arr = extract_row($result_query);
		
		if ((!$arr) or ($arr->pass != $pass)) return 1; /*El nick no existe o la contraseña es incorrecta*/
		else{
			if ($arr->activated){   /*La cuenta del usuario esta activada*/
				$_SESSION["user"] = $nick;				
				return 0;
			}
			else 	return 2;			/*La cuenta del usuario no esta activada*/
		 };		
	 }
	/*Post: La función puede devolver varios valores:
				- 0 si nick y passwrod son correctos
				- 1 si nick no existe, o password es incorrecto
				- 2 si nick y password son correctos, pero no esta activado*/
	
	
	function activated($nick, $activation_key){
	/*{Pre: -}*/	
	
		$message = " <html> 
								<head> 
								<meta http-equiv='Refresh' content='5;url=http://www.cristalab.com'> 
								</head>								 
								<body> ";
	
		global $connection;
		
		$query = "SELECT activated, activation_key FROM user WHERE nick = '$nick'";
		
		$result_query = mysql_query($query, $connection) or my_error('ACTIVATED-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
	
		$arr = extract_row($result_query);
		
		if (count($arr)==0){//No existe el nick
			$message = $message.'ERROR: No existe el nick que se quiere activar';
		}
		else if ($arr[0]->activated == 1){//El usuario ya esta activado
			$message = $message.'ERROR: El usuario ya esta activado';
		}
		else if ($activation_key <>$arr[0]->activation_key){//La clave de activacion es incorrecta
			$message = $message.'ERROR: La clave de activación es incorrecta';			
		}else{
			$query = "UPDATE user SET activated= 1";		
			$result_query = mysql_query($query, $connection) or my_error('ACTIVATED-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
			$message = $message. "Has sido activado. Serás dirigido automáticamente en cinco segundos. En caso contrario, puedes acceder haciendo click <a href='http://www.cristalab.com'>aquí</a>";
		}
		
		$message = $message."</body></html>";
		
		return $message;
		
	}
	
	
?>
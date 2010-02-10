<?php

	/*La función crea un usuario en la BBDD
			-nick: Nick del usuario
			- name: Nombre del usuario
			- surname1: Primer apellido del usuario
			- surname2: Segundo apellido del usuario
			- email_user: Dirección de correo del usuario
			- city: Ciudad del usuario
			- school: Colegio del usuario
			- email_school: Direccion de correo del colegio
			- pass: Password del usuario
	*/
	function create_user($nick, $name, $surname1, $surname2, $email_user, $city, $school, $email_school, $pass){
	/*Pre: Ninguno de los parametros de la entrada es nulo a excepcion del surname2, el email_school y el pass */	
		global $connection;
		
		/*Creamos la clave de activación del usuario*/
		$key_act = make_activationkey();
	    $query = "INSERT INTO user ( nick, name, surname1, surname2, email_user, city, school, email_school, type_user, pass, activation_key, date_insertion)
						VALUES ('$nick',' $name',' $surname1',' $surname2',' $email_user',' $city',' $school',' $email_school','alumno','$pass', '$key_act', now())";//<--- ACABAR DE RETOCAR LA QUERY CON EL TIPO DE ALUMNO
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
	


	/*La funcion nos retorna el identificador del usuario a partir del nick
			- nick: Nick del usuario
	*/
	function get_id_user($nick){
	/*Pre: - */
		global $connection;
		
		$query = "SELECT u.id_user FROM user u WHERE u.nick = '$nick'";	
		$result_query = mysql_query($query, $connection) or my_error('GET_ID_USER-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		return extract_row($result_query)->id_user;		
	}
	/*Post: Retorna un entero que representa el identificador del usuario*/
	
	
	/*La funcion nos retorna la clave de activación del usuario con el mismo nick que el de la entrada
			- nick: Nick del usuario
	*/
	function get_activation_key($nick){
	/*Pre: - */
		global $connection;
		
		$query = "SELECT u.activation_key FROM user u WHERE u.nick = '$nick'";	
		$result_query = mysql_query($query, $connection) or my_error('GET_ACTIVATION_KEY-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		return extract_row($result_query)->activation_key;		
	}
	/*Post: Retorna la cadena de caracteres que representa la clave de activación del usuario con el mismo nick que el de la entrada*/
	
	
	/*La función nos devuelve la lista de todos los nicks de los usuarios que estan almacenados en la BBDD*/
	function get_users(){
	/*Pre: - */	
		global $connection;
		
		$query =  "SELECT u.nick FROM user u WHERE u.activated = 1";
		$result_query = mysql_query($query, $connection) or my_error('GET_USERS-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

		$arr = extract_rows($result_query);
		$res = array();
		foreach ($arr as $row) {
			$res[] = $row->nick;
		}
		
		return $res;
	}
	/*Post: La función nos devuelve una array de strings de todos los nicks de los  usuarios que estan almacenados en la base de datos*/
	
	
	/*La función nos devuelve la información de un usuario a partir del nick del usuario, si este usuario con el nick de la entrada es el mismo que el que esta logueado devuelve toda la informacion del usuario, en caso contrario, solo devuelve parte de la informacion del usuario  con el mismo nick que el de la entrada
			-nick: Nick del usuario
	*/
	function get_user_nick($nick){
	/*Pre: Debe de haber un usuario logueado */	
		if (isset($_SESSION["user"])){
			
			global $connection;
			
			/*Comprobamos si usuario que esta logueado tiene el mismo nick que el de la entrada de la funcion*/
			if ($nick == $_SESSION["user"]){
				$nick_session = $_SESSION["user"];
				$query =  "SELECT nick, name, surname1, surname2, email_user, city, school, email_school, type_user FROM user WHERE nick = '$nick_session '";
			}
			/*En el caso de que el nick sea distinto que el nick del usuario que esta logueado*/
			else 	$query =  "SELECT nick, name, city, school FROM user WHERE nick = '$nick'";			
			$result_query = mysql_query($query, $connection) or my_error('GET_USER_NICK-> '.mysql_errno($connection).": ".mysql_error($connection), 1);			
			
		    return(extract_row($result_query));			
		}		
	}
	/*Post: La función devolvera información distinta en diferentes casos:
				- Devuelve toda la información del usuario, si el nick de la entrada es el mismo que el nick del usuario logueado de la session.
				- Devuelve el nick, en nombre, población y colegio si el nick de la entrada es distinto que el nick del usuario logueado de la session.
				- No devuelve nada en caso de que el usuario de la session no este logueado. */
	
	
	/*Esta función modifica los campos almacenados de un usuario en la BBDD
			- name: Nombre del usuario
			- surname1: Primer apellido del usuario
			- surname2: Segundo apellido del usuario
			- email_user: Dirección de correo del usuario
			- city: Ciudad del usuario
			- school: Colegio del usuario
			- email_school: Direccion de correo del colegio
			- type_user: tipo de usuario
			- old_pass: Antiguo password del usuario
			- pass: Nuevo password del usuario
    */
	function set_user($name, $surname1, $surname2, $email_user, $city, $school, $email_school, $type_user, $old_pass, $pass){
	/*Pre: El identificador del usuario existe y los campos no son nulos a exception del segundo apellido o del email de la escuela */
		
		global $connection;
		
		/*Si el usuario no esta logueado*/
		if(!isset($_SESSION["user"]))	return 1; /*No estamos en modo login*/
		
		/*Si el usuario si que esta logueado*/
		$nick_session = $_SESSION["user"];
		$query = "SELECT pass FROM user WHERE nick='$nick_session'";
		$result_query = mysql_query($query, $connection);
		$pass_user_session = extract_row($result_query);
		
		
		
		/*En caso de que el password nuevo no sea nulo, querra decir que se desea cambiar*/
		if($pass != null){
				/*Si el password es distinto al que tienen el usuario antes de ralizar ningún cambio*/
				if (($old_pass == null) or ($pass_user_session -> pass != $old_pass))	return 2;	  /*La 'old_pass' no coincide con la contraseña del usuario de la sesion*/			
				$query = "UPDATE user SET name='$name', surname1='$surname1', surname2= '$surname2', email_user='$email_user', city='$city', school='$school', email_school='$email_school', type_user='$type_user', pass='$pass' WHERE nick='$nick_session'";
		}else{
				$query = "UPDATE user SET name='$name', surname1='$surname1', surname2= '$surname2', email_user='$email_user', city='$city', school='$school', email_school='$email_school', type_user='$type_user' WHERE nick='$nick_session'";
		}

		$result_query = mysql_query($query, $connection) or my_error('SET_USER-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
			
		return 0; /*Se ha realizado el cambio correctamente*/
	}
	/*Post: La función retorna enteros distintos dependiendo del comportamiento que siga:
				- 1 si el usuario de la sesion no esta logueado
				- 0 se realiza el cambio correctamente ya que el 'old_pass' es correcto para el usuario logueado
				- 2 el 'old_pass' no corresponde al usuario logueado*/
	
	
	/*Esta función borra el usuario de la BBDD identificado por el mismo identificador de la entrada*/
	function delete_user_id($id){
	/*Pre: - */	
		global $connection;
		
	    $query = "DELETE FROM user WHERE id_user = '$id'";
		$result_query = mysql_query($query, $connection) or my_error('DELETE_USER_ID-> '.mysql_errno($connection).": ".mysql_error($connection), 1);			
	}
	/*Post: La función borra el usuario de la BBDD*/
	

	/*La función comprueba si el nick del usuario existe
			-nick: Nick del usuario
	*/
	function exist_user($nick){	
	/*Pre: - */	
		global $connection;
		
		$query =  "SELECT * FROM user WHERE nick = '$nick'";
		$result_query = mysql_query($query, $connection) or my_error('EXIST_USER-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		if (extract_num_rows($result_query) == 0)	return false;
		else return true;
	}
	/*Post: Devuelve cierto en caso de que el nick del usuario existe, en caso contrario devuelve falso*/
	
	
	/* Función que gestiona el logueo de los usuarios, guardando el nick del usuario que se acaba de loguear en una variable SESSION PHP que mantendra informacion del usuario mientras dure la session creada del navegador
			- nick: Nick del usuario
			- pass: Password del usuario
	*/
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
				- 0 si nick y password son correctos, ademas creada una variable session con el nick del usuario logueado
				- 1 si nick no existe, o password es incorrecto
				- 2 si nick y password son correctos, pero no esta activado*/
	
	
	
	/*Esta función comprueba si la clave de activacion de una cuenta es correcta o no y nos devuelve codigo HTML con un mensaje con los resultados de la operacion. En caso de que la clave sea correcta el usuario queda activado para poder realizar operaciones con su nick, en caso contrario se muestra un mensaje de error
			- nick: Nick del usuario
			- activation_key: Clave de activacion
	*/
	function activated($nick, $activation_key){
	/*Pre: -*/			
		global $connection;
		
		$message = " <html> 
								<head> 
								<meta http-equiv='Refresh' content='5;url=http://www.cristalab.com'> 
								</head>								 
								<body> ";
		
		$query = "SELECT activated, activation_key FROM user WHERE nick = '$nick'";		
		$result_query = mysql_query($query, $connection) or my_error('ACTIVATED-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
	
		$arr = extract_row($result_query);
	
		if (!exist_user($nick)){//No existe el nick
			$message = $message.'ERROR: No existe el nick que se quiere activar';
		}
		else if ($arr->activated == 1){//El usuario ya esta activado
			$message = $message.'ERROR: El usuario ya esta activado';
		}
		else if ($activation_key <>$arr->activation_key){//La clave de activacion es incorrecta
			$message = $message.'ERROR: La clave de activación es incorrecta';			
		}else{
			$query = "UPDATE user SET activated= 1";		
			$result_query = mysql_query($query, $connection) or my_error('ACTIVATED-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
			$message = $message. "Has sido activado. Serás dirigido automáticamente en cinco segundos. En caso contrario, puedes acceder haciendo click <a href='http://www.cristalab.com'>aquí</a>";
		}		
		$message = $message."</body></html>";		
		return $message;		
	}
	/*Post: Devuelve codigo HTML con el mensaje de la operacion si se ha realizado con exito o no*/
	
?>
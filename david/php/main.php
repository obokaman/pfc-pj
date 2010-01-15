<?php include("includes.php");
/*Todas las funciones realizadas en este archivo que devuelvan valores, los devuelven en formato JSON*/

/*Iniciamos la sesion con el nombre 'PFC'*/
session_name("PFC");
session_start();

/*Abrimos una conneccion hacia la base de datos para poder realizar las siguientes operaciones */
$connection = open_connection();

/*Comprobamos la funcion que nos pide el cliente*/
$f = clean("function", "string");

/*Según la función que nos haya pedido el cliente, seleccionamos el codigo que hace referencia a dicha función. En este archivo podremos ver las principales funciones que puede realizar el cliente. Cada función o seccion de codigo para una operacion tiene un filtrado de parametros, para mantener la cohesion del codigo y evitar inyeccion de codigo SQL que podria provocar el mal funcionamiento de la base de datos. 
Si queremos saber mas sobre las funciones que son llamadas, podemos ver una explicación mas detallada sobre cada una de ellas en los archivos que aparecen listados en el include.php*/

/*Funcion que realiza el logueo de los usuarios*/
if ($f == "login") {
	send( login( clean("nick", "string"),	clean("password", "string") ) );
}
else if ($f=="logout"){
	session_unset();
}
/*Función que nos inserta los nuevos usuarios en la base de datos*/
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



/*Función para cambiar los parametros de los usuarios registrados*/
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



/*La función nos devuelve información de los usuarios*/
else if ($f == "getUser") {	
		send(
			get_user_nick(
				clean("nick", "string")
			)
		);
}



/*Función que devuelve los equipos a los que esta inscrito el usuario loguado*/
else if ($f == "getMyTeams") {	
		send(
			get_my_teams(
				clean("circuit", "sql"),
				clean("championship", "sql")			
			)
		);
}



/*Función que devuelve los campeonatos a los que esta inscritos el usuario logueado*/
else if ($f == "getMyChampionships") {	
		send(
			getMyChampionships(
				clean("circuit", "sql")			
			)
		);
}



/*Función que devuelve los circuitos que hay en la base de datos*/
else if ($f == "getCircuits") {	
		send(
			get_circuits()
		);
}



/*Función que comprueba la clave de activacion de una cuenta de usuario, y devuelve un codigo HTML con un mensaje en caso de exito o de fallida. Esta funcion no devuelve el resultado en formato JSON*/
else if ($f == "activated") {	
		activated(
			clean("nick", "string"),
			clean("activation_key", "string")
		);
}



/*Esta función devuelve un fragmento del ranking realizado a partir de los parametros circuit, team y championship (nombre del circuito, nombre del equipo y nombre del campeonato, respectivamente). Este fragmento, lo llamaremos pagina, y es lo que devolveremos.*/
else if ($f == "getRankings") {	
		send(
			getRankings(
				clean("circuit", "string"),
				clean("team", "string"),
				clean("championship", "string"),
				clean("page", "string"),
				clean("sizepage", "string")
			)
		);
}



/*Función que nos crea un nuevo campeonato en la base de datos, y nos incluye los circuitos en los que se realiza el campenato*/
else if ($f == "newChampionship") {	
		$name = clean("name", "string");
		$date_limit = clean("date_limit", "date");
		$circuits = Array();
		$circuits = clean("circuits", "array");	
		$b = true;
		
		if(exist_championship($name)){	//Comprobamos que el nombre del campeonato no exista
			$result = 1;
		}else{		
			if(create_championship($name, $date_limit, get_id_user( $_SESSION['user']) ) ){	//Creamos el campeonato
				if ( create_inscription( get_id_user ( $_SESSION['user'] ), get_id_championship($name), 1 ) ){
					foreach ($circuits as $circuit){
						//Comprobamos que todos los circutos se puede insertan correctamente, ya que todos existen y no estan repetidos.
						$b = $b and  exist_circuit($circuit) and (!(exist_circuit_champ( get_id_circuit( $circuit), get_id_championship($name) ) ) ) ;
					}				
					
					if ( $b ){		//Si todo es correcto los insertamos
						foreach($circuits as $circuit) add_circuit_champ( get_id_circuit( $circuit), get_id_championship($name) ); 
						$result = 0;
					}else{ // En caso de que un circuito no se pueda insertar cancelamos la operacion de insercion de un campeonato.
						delete_championship_id( get_id_championship( $name ) );
						$result = 2;
					}
				}else $result = 2;
			}else $result = 2;
		}		
		send($result);
}
/*La funcion según el resultado de la operacion retorna:
	- 0 si el campeonato es creado con exito junto con los circuitos que iban asociados a este
	- 1 si el nombre del campeonato ya existe
	- 2 otros errores */



/*La funcion crea un nuevo equipo en la base de datos*/
else if ($f == "newTeam") {
		$name = clean("name", "string");
		if (exist_team($name)){			//comprovamos si existe un equipo con el mismo nombre que la entrada
			$result =  1;
		}else{
			if(isset($_SESSION['user'])){		//comprovamos que el usuario esta logueado
				$nick_session = $_SESSION['user'];
				if(create_team($name, get_id_user($nick_session) ))	{ // comprovamos que se haya creado correctamente el equipo					
					if(add_user_team( get_id_user($nick_session), get_id_team($name), 1 ) )	$result = 0;
					else $result = 2;
				}
				else $result = 2;				
			}													    
			else $result = 2;	// el usuario no esta logueado
		}; 
		send($result);
}
/*La función retorna un entero dependiendo la ejecución de la operacion:
	- 0 el equipo nuevo ha sido creado con exito
	- 1 el nombre del equipo ya existe
	- 2 otros errores*/



/*La función nos dice los campeonatos a los que pertence el usuario logueado*/
else if ($f == "getMyOwnChampionships") {	
		send(
			get_championships_by_founded()
		);
}



/*La función nos dice los equipos a los que pertence el usuario logueado*/
else if ($f == "getMyOwnTeams") {	
		send(
			get_teams_by_founded()
		);
}



/*La función retorna todos los nicks de los usuarios registrados en la base de datos*/
else if ($f == "getAllNicks") {	
		send(
			get_users()
		);
}



/*La funcion crea una invitación pendiente de confirmar del usuario con el nick y el nombre del campeonato de entrada*/
else if ($f == "addPlayerToChampionship") {
		$name_champ = clean("name", "string");
		$nick_user = clean("nick", "string");
		
		if ( ( exist_championship($name_champ) ) and ( exist_user($nick_user) ) ){ // Existe el nombre del campeonato y del usuario
			if ( !exist_inscription( get_id_user ($nick_user), get_id_championship($name_champ) ) ) { //No existe invitacion  				
				if (create_inscription( get_id_user ($nick_user), get_id_championship($name_champ), 0 ) ) $res = 0;
				else $res = 3;
			}else $res = 1;
		}else $res = 2;
		send($res);
}
/*Según el estado de la operación retorna un entero con un valor dependiendo como haya ido la operacion:
	- 0 creado con exito
	- 1 Ya existe la invitacion del usuario con el nick de la entrada para el campeonato con el mismo nombre que el de la entrada
	- 2 No existe el nick introducido
	- 3 Otros errores */



/*La función crea una invitacion pendiente de confirmar del usuario con el nick de la entrada en el equipo con el mismo nombre de la entrada*/
else if ($f == "addPlayerToTeam") {
		$name_team = clean("name", "string");
		$nick_user = clean("nick", "string");
		
		if ( ( exist_team($name_team) ) and ( exist_user($nick_user) ) ){ // Existe el nombre del equipo y del usuario
			if ( !exist_user_team( get_id_user ($nick_user), get_id_team($name_team) ) ) { //No existe invitacion  				
				if (add_user_team( get_id_user ($nick_user), get_id_team($name_team), 0 ) ) $res = 0;
				else $res = 3;
			}else $res = 1;
		}else $res = 2;
		send($res);
}
/*Según el estado de la operación retorna un entero con un valor dependiendo como haya ido la operacion:
	- 0 creado con exito
	- 1 Ya existe la invitacion del usuario con el nick de la entrada para el equipo con el mismo nombre que el de la entrada
	- 2 No existe el nick introducido
	- 3 Otros errores */



/*La función retorna el número de peticiones pendientes de los campeonatos y el número de peticiones pendientes de los equipos*/
else if ($f == "getNInvitations") {		
		class obj{
				public $nChamps;
				public $nTeams;					
		}			
		$result = new obj;		
		$obj->nChamps = get_num_inscriptions_pendents( get_id_user( $_SESSION[ 'user' ] ) );
		$obj->nTeams = get_num_userteam_pendents( get_id_user( $_SESSION[ 'user' ] ) );
		
		send($obj);
}
/*La funcion retorna los valores:
	- nChamps: Número de peticiones pendientes para entrar en algún campeonato
	- nTeams: Número de peticiones pendientes para entrar en algún equipo */



/*La función devuelve información sobre todas las invitaciones a campeonatos hechas al usuario logueado que estan pendientes de confirmar/rechazar */
else if ($f == "getChampionshipsInvited") {
			send( get_championships_invited() );		
}



/*La función devuelve información sobre todas las invitaciones a equipos hechas al usuario logueado que estan pendientes de confirmar/rechazar */
else if ($f == "getTeamsInvited") {
			send( get_teams_invited() );		
}



/*La función modifica el estado de la inscripcion entre el usuario logueado y el campeonato identificado por el nombre en la entrada. Si 'answer' es igual a 1, la petición correspondiente queda aceptada, en el caso que 'answer' es igual a 0, la petición queda rechazada*/
else if ($f == "setChampionshipAnswer") {	
		$name_champ = clean("name", "string");
		$answer = clean("answer", "int");		
		//Comprovamos que el usuario este logueado y exista una instancia de inscripcion con el nombre del campeonato y el usuario
		if( ( isset( $_SESSION[ 'user' ] ) ) and ( exist_inscription( get_id_user( $_SESSION[ 'user' ] ), get_id_championship ( $name_champ ) ) ) ){			
			//Peticion aceptada
			if ( $answer == 1 ) set_inscription_status( get_id_user( $_SESSION[ 'user' ] ), get_id_championship ( $name_champ ), 1);
			//Peticion rechazada
			if ( ( $answer == 0 ) and ( get_status_inscription( get_id_user( $_SESSION[ 'user' ] ), get_id_championship ( $name_champ ) ) == 0 ) )  delete_inscription( get_id_user( $_SESSION[ 'user' ] ), get_id_championship ( $name_champ ) );			
		}
}



/*La función modifica el estado de la peticion de ingresar en un equipo identificado por el nombre de la entrada y el usuario logueado. Si 'answer' es igual a 1, la petición correspondiente queda aceptada, en el caso que 'answer' es igual a 0, la petición queda rechazada*/
else if ($f == "setTeamAnswer") {
		$name_team = clean("name", "string");
		$answer = clean("answer", "int");		
		//Comprovamos que el usuario este logueado y exista una instancia de inscripcion con el nombre del campeonato y el usuario
		if( ( isset( $_SESSION[ 'user' ] ) ) and ( exist_user_team( get_id_user( $_SESSION[ 'user' ] ), get_id_team ( $name_team ) ) ) ){			
			//Peticion aceptada
			if ( $answer == 1 ) set_user_team_status( get_id_user( $_SESSION[ 'user' ] ), get_id_team ( $name_team ), 1);
			//Peticion rechazada
			if ( ( $answer == 0 ) and ( get_status( get_id_user( $_SESSION[ 'user' ] ), get_id_team ( $name_team ) ) == 0 ) )  delete_user_team( get_id_user( $_SESSION[ 'user' ] ), get_id_team ( $name_team ) );			
		}
}



/*La función devuelve información sobre un circuito*/
else if ($f == "getCircuitInfo") {	
		send(
			get_circuit( clean("name", "string") )
		);
}



/*La función devuelve información sobre un campeonato*/
else if ($f == "getChampionshipCircuits") {	

		send(
			get_championship_circuits( clean("name", "string") )
		);
}


/*La función inserta en la base de datos el codigo nuevo con el nombre de fichero indicado en la entrada*/
else if( $f == "saveCode" ) {	
	$code = clean ( "code", "json" );
	$file_name = clean ( "name", "string" );
	$date = date("l,M d, Y g:i:s");
	
	if ( ! isset($_SESSION['user']) ) $result = 3;
	else {
		$id_user = get_id_user( $_SESSION[ 'user' ] );
		
		if ( exist_file_name( $file_name, $id_user ) ) $result = 1;
		else if ( new_code( $file_name, $code, $date, $id_user) ) $result = 0;
		else $result = 2;
	}
	send($result);
}
/*Según el resultado de la operación devuelve un entero con el valor:
	- 0 se ha guardado correctamente
	- 1 ya existe el nombre del fichero
	- 2 otros errores
	- 3 si el usuario no esta logueado*/



/*La función retorna el codigo guardado por el usuario logueado con el mismo nombre que nombre de fichero de la entrada*/
else if( $f == "loadCode" ) {	
	if ( isset($_SESSION['user']) ){
		send(
			get_code ( 
				clean( "name", "string" ),
				get_id_user( $_SESSION['user'] )
			)
		);
	}
}



/*La función devuelve el nombre de todas las partidas guardadas por el usuario logueado*/
else if( $f == "getSavedCodes" ) {	
	if ( isset($_SESSION['user']) ){
		send(
			get_saved_codes ( 	get_id_user( $_SESSION['user'] ) )
		);
	}
}



/*La función retorna un fragmento de codigo de una partida*/
else if( $f == "getTraceFragment" ) {	
	send(
		get_trace_fragment( 			
			clean("id_game", "int"),
			clean("start_byte", "int"),
			clean("length", "int")
		)
	);	
}



/*La función retorna el contenido entero del codigo de una partida*/
else if( $f == "getFullTrace" ) {	
	send(
		get_trace_fragment( 			
			clean("id_game", "int"),
			0,
			-1
		)
	);	
}



/*La función envia un codigo de partida para que lo compile y lo ejecute en el simulador, indicandole ademas el circuito donde se realizara la partida*/
else if( $f == "run" ) {	
	send(
		run( 			
			clean("code", "json"),
			clean("circuit", "string"),
			clean("championship", "string")
		)
	);	
}

close_connection($connection);
	
?>

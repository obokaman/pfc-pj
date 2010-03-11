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
/*Pre: - */
	session_unset();
	send( login( clean("nick", "string"),	clean("password", "string") ) );
}
/*Post: La función puede devolver varios valores:
				- 0 si nick y password son correctos, ademas creada una variable session con el nick del usuario logueado
				- 1 si nick no existe, o password es incorrecto
				- 2 si nick y password son correctos, pero no esta activado*/


else if ($f=="logout"){
/*Pre: - */
	session_unset();
}
/*Post: Cierra la variables de la sesión activadas por el usuario*/


/*Función que nos inserta los nuevos usuarios en la base de datos*/
else if ($f == "newUser") {
/*Pre: Ningún parámetro de la entrada puede ser nulo a excepción de 'surname2' y  'email_school' */
	$email_user = clean("email_user", "string");
	$nick = clean("nick", "string");
	$result = create_user(
			$nick,
			clean("name", "string"),
			clean("surname1", "string"),
			clean("surname2", "string"),
			$email_user,
			clean("city", "string"),
			clean("school", "string"),
			clean("email_school", "string"),
			clean("password", "string")
		);
	
	if ($result == 0){
		send_mail( $nick, $email_user);		
	}
	
	send($result);
}
/* Post: La función devolverá según el comportamiento de la inserción del nuevo usuario:
				- Un 0, si el usuario se ha insertado correctamente y envia un correo a la dirección del usuario
				- Un 1, en caso de que el nick del nuevo usuario ya exista
				- Un 2 si se ha producido algún error en el momento de insertar en nuevo usuario */


/*Función para cambiar los parametros de los usuarios registrados*/
else if ($f == "changeUser") {	
/*Pre: Ningún parámetro de la entrada puede ser nulo a excepción de 'surname2' y  'email_school' */
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
/*Post: La función retorna enteros distintos dependiendo del comportamiento que siga:
				- 1 si el usuario de la sesion no esta logueado
				- 0 se realiza el cambio correctamente ya que el 'old_pass' es correcto para el usuario logueado
				- 2 el 'old_pass' no corresponde al usuario logueado*/


/*La función nos devuelve información de los usuarios*/
else if ($f == "getUser") {	
/*Pre: - */
		send(
			get_user_nick(
				clean("nick", "string")
			)
		);
}
/*Post: La función devolvera información distinta en diferentes casos:
				- Devuelve toda la información del usuario, si el nick de la entrada es el mismo que el nick del usuario logueado de la session.
				- Devuelve el nick, en nombre, población y colegio si el nick de la entrada es distinto que el nick del usuario logueado de la session.
				- No devuelve nada en caso de que el usuario de la session no este logueado. */


/*Función que devuelve los equipos a los que esta inscrito el usuario logueado*/
else if ($f == "getMyTeams") {	
/*Pre: Los nombres han de existir y no ser nulos */
		send(
			get_my_teams(
				clean("circuit", "string"),
				clean("championship", "string")			
			)
		);
}
/*Post:  Devuelve una array de nombres de equipos a los que pertenece el usuario logueado*/


/*Función que devuelve los campeonatos a los que esta inscritos el usuario logueado*/
else if ($f == "getMyChampionships") {	
/*Pre: Los nombres han de existir y no ser nulos */
		send(
			get_my_championships(
				clean("circuit", "string")			
			)
		);
}
/*Post:  Devuelve una array de strings con los nombres de los campeonatos a los que esta inscrito el usuario logueado y que ademas contienen el circuito de la entrada. En caso de que el usuario no este logueado o no exista el circuito de la entrada dentro del campeonato, retorna una array vacia*/


/*Función que devuelve los circuitos que hay en la base de datos*/
else if ($f == "getCircuits") {	
/*Pre: - */
		send(
			get_circuits()
		);
}
/*Post: La función nos devuelve una array de strings con los nombres de todos los circuitos que estan almacenados en la base de datos*/


/*Función que comprueba la clave de activacion de una cuenta de usuario, y devuelve un codigo HTML con un mensaje en caso de exito o de fallida. Esta funcion no devuelve el resultado en formato JSON*/
else if ($f == "activated") {	
/*Pre: - */
		activated(
			clean("nick", "string"),
			clean("activation_key", "string")
		);
}
/*Post: Devuelve código HTML con el mensaje de la operación si se ha realizado con exito o no*/


/*Esta función devuelve un fragmento del ranking realizado a partir de los parametros circuit, team y championship (nombre del circuito, nombre del equipo y nombre del campeonato, respectivamente). Este fragmento, lo llamaremos pagina, y es lo que devolveremos.*/
else if ($f == "getRankings") {	
/*Pre: El circuito no puede ser nulo */
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
/*Post: La funcion nos devolvera 3 valores:
			- page: Un entero que corresponde a el numero de la pagina del ranking
			- numpages: Numero total de paginas que hay en el ranking realizado
			- data: Un array con los pares nick de usuario y tiempo realizado en una partida. Esta array como maximo puede contener sizepage elementos*/


/*Función que nos crea un nuevo campeonato en la base de datos, y nos incluye los circuitos en los que se realiza el campenato*/
else if ($f == "newChampionship") {	
/*Pre: Los parámetros que se reciben no puede ser nulos */
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
/* Post: La funcion según el resultado de la operacion retorna:
	- 0 si el campeonato es creado con exito junto con los circuitos que iban asociados a este
	- 1 si el nombre del campeonato ya existe
	- 2 otros errores */



/*La funcion crea un nuevo equipo en la base de datos*/
else if ($f == "newTeam") {
/*Pre: - */
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
/*Post: La función retorna un entero dependiendo la ejecución de la operacion:
	- 0 el equipo nuevo ha sido creado con exito
	- 1 el nombre del equipo ya existe
	- 2 otros errores*/



/*La función nos dice los campeonatos a los que pertence el usuario logueado*/
else if ($f == "getMyOwnChampionships") {
/*Pre: - */
		send(
			get_championships_by_founded()
		);
}
/*Post: Devuelve una array de string con los nombres de los campeonatos que ha fundado el usuario que esta logueado*/


/*La función nos dice los equipos a los que pertence el usuario logueado*/
else if ($f == "getMyOwnTeams") {
/*Pre: - */
		send(
			get_teams_by_founded()
		);
}
/*Post: Retorna una array de cadenas de carácteres con los nombres de los equipos que ha fundado el usuario que esta logueado*/


/*La función retorna todos los nicks de los usuarios registrados en la base de datos*/
else if ($f == "getAllNicks") {	
/*Pre: - */
		send(
			get_users()
		);
}
/*Post: La función nos devuelve una array de strings de todos los nicks de los  usuarios que estan almacenados en la base de datos*/


/*La funcion crea una invitación pendiente de confirmar del usuario con el nick y el nombre del campeonato de entrada*/
else if ($f == "addPlayerToChampionship") {
/*Pre: - */
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
/*Post: Según el estado de la operación retorna un entero con un valor dependiendo como haya ido la operacion:
	- 0 creado con exito
	- 1 Ya existe la invitacion del usuario con el nick de la entrada para el campeonato con el mismo nombre que el de la entrada
	- 2 No existe el nick introducido
	- 3 Otros errores */



/*La función crea una invitacion pendiente de confirmar del usuario con el nick de la entrada en el equipo con el mismo nombre de la entrada*/
else if ($f == "addPlayerToTeam") {
/*Pre: - */
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
/*Post: Según el estado de la operación retorna un entero con un valor dependiendo como haya ido la operacion:
	- 0 creado con exito
	- 1 Ya existe la invitacion del usuario con el nick de la entrada para el equipo con el mismo nombre que el de la entrada
	- 2 No existe el nick introducido
	- 3 Otros errores */



/*La función retorna el número de peticiones pendientes de los campeonatos y el número de peticiones pendientes de los equipos*/
else if ($f == "getNInvitations") {		
/*Pre: Debe haber un usuario logueado */
		class obj{
				public $nChamps;
				public $nTeams;					
		}			
		$result = new obj;		
		$obj->nChamps = get_num_inscriptions_pendents( get_id_user( $_SESSION[ 'user' ] ) );
		$obj->nTeams = get_num_userteam_pendents( get_id_user( $_SESSION[ 'user' ] ) );
		
		send($obj);
}
/*Post: La funcion retorna los valores:
	- nChamps: Número de peticiones pendientes para entrar en algún campeonato
	- nTeams: Número de peticiones pendientes para entrar en algún equipo */



/*La función devuelve información sobre todas las invitaciones a campeonatos hechas al usuario logueado que estan pendientes de confirmar/rechazar */
else if ($f == "getChampionshipsInvited") {
/*Pre: - */
			send( get_championships_invited() );		
}
/*Post: Devuelve una array de objetos con el par nick y name donde el primero es el nick del fundador del campeonto al que el usuario logueado esta pendiente y el nombre de dicho campeonato*/


/*La función devuelve información sobre todas las invitaciones a equipos hechas al usuario logueado que estan pendientes de confirmar/rechazar */
else if ($f == "getTeamsInvited") {
/*Pre: - */
			send( get_teams_invited() );		
}
/*Post: Devuelve una array de objetos con el par nick y name donde el primero es el nick del fundador del equipo al que el usuario logueado esta pendiente y el nombre de dicho equipo*/


/*La función modifica el estado de la inscripcion entre el usuario logueado y el campeonato identificado por el nombre en la entrada. Si 'answer' es igual a 1, la petición correspondiente queda aceptada, en el caso que 'answer' es igual a 0, la petición queda rechazada*/
else if ($f == "setChampionshipAnswer") {	
/*Pre: - */
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
/*Post: Actualiza el estado de la invitación pendiente del campeonato con el mismo nombre que el de la entrada del usuario que esta logueado, en caso de que la rechace se elimina de la base de datos de la invitación. En caso de que el usuario no este logueado no se realiza ninguna actualización*/


/*La función modifica el estado de la peticion de ingresar en un equipo identificado por el nombre de la entrada y el usuario logueado. Si 'answer' es igual a 1, la petición correspondiente queda aceptada, en el caso que 'answer' es igual a 0, la petición queda rechazada*/
else if ($f == "setTeamAnswer") {
/*Pre: - */
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
/*Post: Actualiza el estado de la invitación pendiente de equipo con el mismo nombre que el de la entrada del usuario que esta logueado, en caso de que la rechace se elimina de la base de datos la invitación. Si no hay ningún usuario logueado no se realiza ninugna actualización.*/


/*La función devuelve información sobre un circuito*/
else if ($f == "getCircuitInfo") {	
/*Pre: - */
		send(
			get_circuit( clean("name", "string") )
		);
}
/*Post: La función nos devuelve una array de strings donde los elementos estan indexados con 'url', 'width', 'height', 'level' y 'n_laps'*/


/*La función devuelve los circuitos que pertenecen a un campeonato*/
else if ($f == "getChampionshipCircuits") {	
/*Pre: - */
		send(
			get_championship_circuits( clean("name", "string") )
		);
}
/*Post:Devuelve una array de string con los nombres de los circuitos que pertenecen al campeonato con el mismo nombre que la entrada*/


/*La función inserta en la base de datos el codigo nuevo con el nombre de fichero indicado en la entrada*/
else if( $f == "saveCode" ) {	
/*Pre: - */
	$code = clean ( "code", "json" );
	$file_name = clean ( "name", "string" );
	$date = date("y-m-d g:i:s");
	
	if ( ! isset($_SESSION['user']) ) $result = 3;
	else {
		$id_user = get_id_user( $_SESSION[ 'user' ] );
		
		if ( exist_file_name( $file_name, $id_user ) ) $result = 1;
		else if ( new_code( $file_name, $code, $date, $id_user) ) $result = 0;
		else $result = 2;
	}
	send($result);
}
/*Post: Según el resultado de la operación devuelve un entero con el valor:
	- 0 se ha guardado correctamente
	- 1 ya existe el nombre del fichero
	- 2 otros errores
	- 3 si el usuario no esta logueado*/

else if( $f == "saveCode2" ) {	
/*Pre: - */
	$code = clean ( "code", "string" );
	$file_name = clean ( "name", "string" );
	$date = date("y-m-d g:i:s");
	
	if ( ! isset($_SESSION['user']) ) $result = 3;
	else {
		$id_user = get_id_user( $_SESSION[ 'user' ] );
		
		if ( exist_file_name( $file_name, $id_user ) ) $result = 1;
		else if ( new_code( $file_name, $code, $date, $id_user) ) $result = 0;
		else $result = 2;
	}
	send($result);
}


/*La función retorna el codigo guardado por el usuario logueado con el mismo nombre que nombre de fichero de la entrada*/
else if( $f == "loadCode" ) {	
/*Pre: - */
	if ( isset($_SESSION['user']) ){
		send(
			get_code ( 
				clean( "name", "string" ),
				get_id_user( $_SESSION['user'] )
			)
		);
	}
}
/*Post: Devuelve el codigo asociado al nombre de fichera de la entrada y el identificador del usuario*/


/*La función devuelve el nombre de todas las partidas guardadas por el usuario logueado*/
else if( $f == "getSavedCodes" ) {	
/*Pre: - */
	if ( isset($_SESSION['user']) ){
		send(
			get_saved_codes ( 	get_id_user( $_SESSION['user'] ) )
		);
	}
}
/*Post:Devuelve lista con los pares, el nombre de la partida  y la fecha del guardado del código, del usuario de la entrada */


/*La función retorna un fragmento de codigo de una partida*/
else if( $f == "getTraceFragment" ) {	
/*Pre: El identificador de la partida existe */
	send(
		get_trace_fragment( 			
			clean("id_game", "int"),
			clean("start_byte", "int"),
			clean("length", "int")
		)
	);	
}
/*Post: Retorna dos valores, el tamaño del código leído y el contenido del código que se ha leído*/


/*La función retorna el contenido entero del codigo de una partida*/
else if( $f == "getFullTrace" ) {	
/*Pre: El identificador de la partida existe */
	send(
		get_trace_fragment( 			
			clean("id_game", "int"),
			0,
			-1
		)
	);	
}
/*Post:Retorna dos valores, el tamaño de todo el código leído y el contenido del código*/


/*La función envia un codigo de partida para que lo compile y lo ejecute en el simulador, indicandole ademas el circuito donde se realizara la partida*/
else if( $f == "run" ) {	
/*Pre: El circuito no puede ser nulo */
	send(
		run( 			
			clean("code", "code"),
			clean("circuit", "string"),
			clean("championship", "string")
		)
	);	
}
/*Post: Retorna un mensaje con el código de error, un entero, con el cual identifica el tiepo de error. Y en caso de éxito de la función también retorna el valor del identificador de la partida en la base de datos*/


close_connection($connection);
	
?>

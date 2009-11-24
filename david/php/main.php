<?php include("includes.php");

session_name("PFC");
session_start();
$connection = open_connection();

$f = clean("function", "string");

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
else if ($f == "getUser") {	
		send(
			get_user_nick(
				clean("nick", "string")
			)
		);
}
else if ($f == "getMyTeams") {	
		send(
			getMyTeams(
				clean("circuit", "sql"),
				clean("championship", "sql")			
			)
		);
}
else if ($f == "getMyChampionships") {	
		send(
			getMyChampionships(
				clean("circuit", "sql")			
			)
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
				foreach ($circuits as $circuit){
					//Comprobamos que todos los circutos se puede insertan correctamente, ya que todos existen y no estan repetidos.
					$b = $b and  exist_circuit($circuit) and (!(exist_circuit_champ( get_id_circuit( $circuit), get_id_championship($name) ) ) ) ;
				}				
				
				if ( $b ){		//Si todo es correcto los insertamos
					foreach($circuits as $circuit) add_circuit_champ( get_id_circuit( $circuit), get_id_championship($name) ); 
					$result = 0;
				}else $result = 2;					
				
			}else $result = 2;
		}		
		send($result);
}
else if ($f == "newTeam") {
		$name = clean("name", "string");
		if (exist_team($name)){			//comprovamos si existe un equipo con el mismo nombre que la entrada
			$result =  1;
		}else{
			if(isset($_SESSION['user'])){		//comprovamos que el usuario esta logueado
				$nick_session = $_SESSION['user'];
				if(create_team($name, get_id_user($nick_session) ))	{ // comprovamos que se haya creado correctamente el equipo					
					if(add_user_team( get_id_user($nick_session), get_id_team($name), 0 )	)	$result = 0;
					else $result = 2;
				}
				else $result = 2;				
			}													    
			else $result = 2;	// el usuario no esta logueado
		}; 
		send($result);
}
else if ($f == "getMyOwnChampionships") {	
		send(
			get_championships_by_founded()
		);
}
else if ($f == "getMyOwnTeams") {	
		send(
			get_teams_by_founded()
		);
}
else if ($f == "getAllNicks") {	
		send(
			get_users()
		);
}
else if ($f == "addPlayerToChampionship") {
		$name_champ = clean("name", "string");
		$nick_user = clean("nick", "string");
		
		if ( ( exist_championship($name_champ) ) and ( exist_user($nick_user) ) ){ // Existe el nombre del campeonato y del usuario
			if ( !exist_inscription( get_id_user ($nick_user), get_id_championship($name_champ) ) ) { //No existe invitacion  				
				if (create_inscription( get_id_user ($nick_user), get_id_championship($name_champ) ) ) $res = 0;
				else $res = 3;
				
			}else $res = 1;
			
		}else $res = 2;
		
		send($res);
}
else if ($f == "addPlayerToTeam") {
		$name_team = clean("name", "string");
		$nick_user = clean("nick", "string");
		
		if ( ( exist_team($name_team) ) and ( exist_user($nick_user) ) ){ // Existe el nombre del equipo y del usuario
			if ( !exist_user_team( get_id_user ($nick_user), get_id_team($name_team) ) ) { //No existe invitacion  				
				if (add_user_team( get_id_user ($nick_user), get_id_team($name_team) ) ) $res = 0;
				else $res = 3;
				
			}else $res = 1;
			
		}else $res = 2;
		
		send($res);
}


//else escribe_log("ADFadskfdashfldsjfdsali");*/
close_connection($connection);
	
?>

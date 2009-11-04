<?php

	/*La función crea un equipo en la BBDD con los parametros que le pasamos de entrada*/
	function create_team($name, $id_founded){
	/*Pre: 'id_founded' es un identificador de usuario que existe */	
		global $connection;
		
	    $query = "INSERT INTO team (name, id_founded) VALUES ('$name', '$id_founded')";
						
		if (!mysql_query($query, $connection)) {
			my_error('CREATE_TEAM-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
				
			return false;
		}else{	
				
			return true;
		}
	}
	/*Post: La función nos retorna cierto en caso de que haya tenido exito la creacion del nuevo equipo, en caso contrario devuelve falso*/
	
	
	/*La función nos devuelve la lista de todos los equipos que estan almacenados en la BBDD*/
	function get_teams(){
	/*Pre: - */	
		global $connection;
		$query =  "SELECT * FROM team";
		$result_query = mysql_query($query, $connection) or my_error('GET_TEAMS-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
				
		return(extract_row($result_query));	
	}
	/*Post: La función nos devuelve una array de objetos de todos los equipos que estan almacenados en la base de datos*/

	
	/*La función nos devuelve la información del equipo a partir del identificador que tiene en la BBDD*/
	function get_team_id($id){
	/*Pre: - */
		global $connection;
		$query =  "SELECT * FROM team WHERE id_team = '$id'";
		$result_query = mysql_query($query, $connection) or my_error('GET_TEAM_ID-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

				
		return(extract_row($result_query));
	}
	/*Post: Retorna una array con el objeto equipo que contienen la información del equipo*/
	
	
	/*La función nos devuelve la información del equipo a partir del nombre del campeonato*/
	function get_team_name($name){
	/*Pre: - */
		global $connection;
		$query =  "SELECT * FROM team WHERE name = '$name'";
		$result_query = mysql_query($query, $connection) or my_error('GET_TEAM_NAME-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

				
		return(extract_row($result_query));
	}
	/*Post: Retorna la información del equipo*/
	
	
	/*La función devuelve una lista de nombres de equipos a los que pertenece el usuario que esta logueado*/
	function getMyTeams($name_circuit, $name_champ){
	/*Pre: - */			
		if(isset($_SESSION["user"])){
			
			$nick_session = $_SESSION["user"]; 
			global $connection;
			
			if ($name_champ == null){
					$query =  	"select t.name
										from 	user u,
													user_team ut,
													team t
										where u.nick = '$nick_session'
										and u.id_user = ut.id_user
										and ut.id_team = t.id_team";
			}else{
					$query =  	"select t.name
										from 	user u,
													inscription i,
													championship c,
													circuit ci,
													circuit_championship cc,
													user_team ut,
													team t
										where u.nick = '$nick_session'
										and  u.id_user = i.id_user
										and  i.pendent <> 0
										and  i.id_champ = c.id_champ
										and c.name = '$name_champ'
										and c.id_champ = cc.id_champ
										and cc.id_circuit = ci.id_circuit
										and ci.name = '$name_circuit'
										and u.id_user = ut.id_user
										and ut.id_team = t.id_team ";
			}
			
			$result_query = mysql_query($query, $connection) or my_error('GET_MYTEAM-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
			
			$arr = extract_rows($result_query);
			$res = array();
			foreach ($arr as $row) {
				$res[] = $row->name;
			}
			return $res;			
		}
		
	}
	/*Post:  Devuelve una array de nombres de equipos a loos que pertenece el usuario logueado*/
	
	
	/*Esta función modifica los campos almacenados de un equipo en la BBDD*/
	function set_team($id, $name, $id_founded){
	/*Pre: EL identificador del equipo y el 'id_founded' existen, y ademas el resto de los parametros no son nulos */
		global $connection;
		
	    $query = "UPDATE team SET name='$name'  id_founded='$id_founded' WHERE id_team = '$id'";

		if (!mysql_query($query, $connection)) {
			my_error('SET_TEAM-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
				
			return false;
		}else{
				
			return true;
		}
	}
	/*Post: La función nos retorna cierto si sea modificado correctamente el equipo de la entrada, en caso contrario retorna falso*/
	
	
	/*Esta función borra el equipo de la BBDD identificado por el mismo identificador que tienen en la BBDD*/
	function delete_team_id($id){
	/*Pre: - */
		global $connection;
		
	    $query = "DELETE FROM team WHERE id_team = '$id'";

		$result_query = mysql_query($query, $connection) or my_error('DELETE_TEAM_ID-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
				
	}
	/*Post: La función borra el circuito de la BBDD*/
	
	
	
	/*La función comprueba si el identificador del equipo existe*/
	function exist_team($id){	
	/*Pre: - */
		global $connection;
		$query =  "SELECT * FROM team WHERE id_team = '$id'";
	
		$result_query = mysql_query($query, $connection) or my_error('EXIST_TEAM-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

		if (count(extract_row($result_query))==0)	return false;
		else return true;
	}
	/*Post: Devuelve cierto en caso de que el identificador del equipo existe, en caso contrario devuelve falso*/

?>
<?php

	/*La función crea un equipo en la BD 
			- name: Nombre del equipo
			- id_founded: Identificador del usuario fundador
	*/
	function create_team($name, $id_founded){
	/*Pre: 'id_founded' es un identificador de usuario que existe y name no es un valor nulo*/	
		global $connection;
		
	    $query = "INSERT INTO team (name, id_founded) VALUES ('$name', '$id_founded')";
		if (!mysql_query($query, $connection)) {
			my_error('CREATE_TEAM-> '.mysql_errno($connection).": ".mysql_error($connection), 1);				
			return false;
		}else	return true;
	}
	/*Post: La función nos retorna cierto en caso de que haya tenido exito la creacion del nuevo equipo, en caso contrario devuelve falso*/
	
	
	/*La funcion nos retorna el identificador del equipo a partir del nombre
			- name: Nombre del equipo
	*/
	function get_id_team($name){
	/*Pre: En nombre del equipo existe */
		global $connection;
		
		$query = "SELECT t.id_team FROM team t WHERE t.name = '$name'";	
		$result_query = mysql_query($query, $connection) or my_error('GET_ID_TEAM-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		return extract_row($result_query)->id_team;		
	}
	/*Post: Retorna un entero que representa el identificador del equipo*/

	
	/*La función retorna todos los nombres de los equipos que ha fundado el usuario que esta logueado*/
	function get_teams_by_founded(){
	/*Pre: - */
		if ( !isset($_SESSION['user']) ) return null;	// Si no esta logueado un usuario
		
		global $connection;
		
		$id_user_founded = get_id_user($_SESSION['user']);		
		$query =  "SELECT t.name FROM team t WHERE t.id_founded = '$id_user_founded'";		
		$result_query = mysql_query($query, $connection) or my_error('GET_TEAMS_BY_FOUNDED-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

		$arr = extract_rows($result_query);
		$res = array();
		foreach ($arr as $row) {
			$res[] = $row->name;
		}
		
		return $res;
	}
	/*Post: Devuelve una array de string con los nombres de los equipos a los que pertencen el usuario con el identificador igual al de la entrada, en caso de que el usuario que hace la peticion no este loguedo retorna un valor nulo*/	
	
	
	/*La función devuelve una lista de nombres de equipos a los que pertenece el usuario que esta logueado
			- name_circuit: Nombre del circuito
			- name_champ: Nombre del campeonato
	*/
	function get_my_teams($name_circuit, $name_champ){
	/*Pre: El parametros de entrada no puede ser nulos*/			
		if(isset($_SESSION["user"])){						
			global $connection;
			
			$nick_session = $_SESSION["user"]; 

			$query =  	"select t.name
								from 	user u,
											user_team ut,
											team t
								where u.nick = '$nick_session'
								and u.id_user = ut.id_user
								and ut.active <> 0
								and ut.id_team = t.id_team";

			$result_query = mysql_query($query, $connection) or my_error('GET_MYTEAMS-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
			
			$arr = extract_rows($result_query);
			$res = array();
			foreach ($arr as $row) {
				$res[] = $row->name;
			}
			return $res;			
		}
	}
	/*Post:  Devuelve una array de nombres de equipos a los que pertenece el usuario logueado*/
	
	
	/*Esta función borra el equipo de la BD identificado con el parametro de la entrada de la funcion
			- id: Identificador del equipo
	*/
	function delete_team_id($id){
	/*Pre: - */
		global $connection;
		
	    $query = "DELETE FROM team WHERE id_team = '$id'";
		$result_query = mysql_query($query, $connection) or my_error('DELETE_TEAM_ID-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
	}
	/*Post: La función borra el circuito de la BBDD*
	
	
	/*La función comprueba si el nombre del equipo existe en la BD.
			name: Nombre del equipo
	*/
	function exist_team($name){	
	/*Pre: - */
		global $connection;
		
		$query =  "SELECT * FROM team WHERE name = '$name'";
		$result_query = mysql_query($query, $connection) or my_error('EXIST_TEAM-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

		if (extract_num_rows($result_query) == 0)	return false;
		else return true;
	}
	/*Post: Devuelve cierto en caso de que el nombre del equipo existe, en caso contrario devuelve falso*/

?>
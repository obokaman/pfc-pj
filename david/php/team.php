<?php

	/*La función crea un equipo en la BBDD con los parametros que le pasamos de entrada*/
	function create_team($name, $id_founded){
	/*Pre: 'id_founded' es un identificador de usuario que existe */	
		$connection = open_connection();
		
	    $query = "INSERT INTO team (name, id_founded) VALUES ('$name', '$id_founded')";
						
		if (!mysql_query($query, $connection)) {
			my_error('CREATE_TEAM-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
			close_connection($connection);	
			return false;
		}else{	
			close_connection($connection);	
			return true;
		}
	}
	/*Post: La función nos retorna cierto en caso de que haya tenido exito la creacion del nuevo equipo, en caso contrario devuelve falso*/
	
	
	/*La función nos devuelve la lista de todos los equipos que estan almacenados en la BBDD, en formato JSON*/
	function get_teams(){
	/*Pre: - */	
		$connection = open_connection();
		$query =  "SELECT * FROM team";
		$result_query = mysql_query($query, $connection) or my_error('GET_TEAMS-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		close_connection($connection);		
		return($arr);	
	}
	/*Post: La función nos devuelve la lista de todos los equipos que estan almacenados en la base de datos, en formato JSON*/

	
	/*La función nos devuelve la información del equipo en formato JSON a partir del identificador que tiene en la BBDD*/
	function get_team_id($id){
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT * FROM team WHERE id_team = '$id'";
		$result_query = mysql_query($query, $connection) or my_error('GET_TEAM_ID-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		close_connection($connection);		
		return($arr);
	}
	/*Post: Retorna la información del equipo en formato JSON*/
	
	
	/*La función nos devuelve la información del equipo en formato JSON a partir del nombre del campeonato*/
	function get_team_name($name){
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT * FROM team WHERE name = '$name'";
		$result_query = mysql_query($query, $connection) or my_error('GET_TEAM_NAME-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		close_connection($connection);		
		return($arr);
	}
	/*Post: Retorna la información del equipo en formato JSON*/
	
	
	/*Esta función modifica los campos almacenados de un equipo en la BBDD*/
	function set_team($id, $name, $id_founded){
	/*Pre: EL identificador del equipo y el 'id_founded' existen, y ademas el resto de los parametros no son nulos */
		$connection = open_connection();
		
	    $query = "UPDATE team SET name='$name'  id_founded='$id_founded' WHERE id_team = '$id'";

		if (!mysql_query($query, $connection)) {
			my_error('SET_TEAM-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
			close_connection($connection);	
			return false;
		}else{
			close_connection($connection);	
			return true;
		}
	}
	/*Post: La función nos retorna cierto si sea modificado correctamente el equipo de la entrada, en caso contrario retorna falso*/
	
	
	/*Esta función borra el equipo de la BBDD identificado por el mismo identificador que tienen en la BBDD*/
	function delete_team_id($id){
	/*Pre: - */
		$connection = open_connection();
		
	    $query = "DELETE FROM team WHERE id_team = '$id'";

		$result_query = mysql_query($query, $connection) or my_error('DELETE_TEAM_ID-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		close_connection($connection);		
	}
	/*Post: La función borra el circuito de la BBDD*/
	
	
	
	/*La función comprueba si el identificador del equipo existe*/
	function exist_team($id){	
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT * FROM team WHERE id_team = '$id'";
	
		$result_query = mysql_query($query, $connection) or my_error('EXIST_TEAM-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
	
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		if (count($arr)==0)	return false;
		else return true;
	}
	/*Post: Devuelve cierto en caso de que el identificador del equipo existe, en caso contrario devuelve falso*/

?>
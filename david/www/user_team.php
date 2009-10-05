<?php

	/*La función crea nua instancia que relaciona un usuario con un equipo mediante sus identificadores*/
	function add_user_team($id_user, $id_team, $pendent){
	/*Pre: - */	
		$connection = open_connection();
		
	    $query = "INSERT INTO user_team ( id_user, id_team, pendent)
						VALUES ('$id_user',' $id_team', '$pendent')";
						
		if (!mysql_query($query, $connection)) {
			my_error(mysql_errno($connection) . ": " . mysql_error($connection), 1);
			close_connection($connection);	
			return false;
		}else{	
			close_connection($connection);	
			return true;
		}
	}
	/*Post: La función nos retorna cierto en caso de que haya tenido exito la creacion de la nueva instancia entre el usuario y el equipo, en caso contrario devuelve falso*/
	
	
	/*La función nos devuelve la lista de todos los usuarios y equipos que estan relacionados que estan almacenados en la BBDD, en formato JSON*/
	function get_users_teams(){
	/*Pre: - */	
		$connection = open_connection();
		$query =  "SELECT * FROM user_team";
		$result_query = mysql_query($query, $connection) or my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		print(json_encode($arr)); 
		close_connection($connection);		
	}
	/*Post: La función nos devuelve la lista de todos los usuarios junto con los equipos a los que pertenecen y el estado que estan almacenados en la base de datos, en formato JSON*/

	
	/*La función nos devuelve el identificador de los equipos a los que pertenece el identificador del usuario de la entrada en formato JSON */
	function get_teams_of_user($id){
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT id_team FROM user_team WHERE id_user = '$id'";
		$result_query = mysql_query($query, $connection) or die(mysql_error());
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		print( json_encode($arr)); 
		close_connection($connection);
	}
	/*Post: Retorna una lista de los identificadores de equipo a los que pertence el usuario de la entrada en formato JSON*/
	
	
	/*La función nos devuelve la lista de usuarios que pertenecen a un equipo en concreto, la información la retorna en formato JSON*/
	function get_users_of_team($id){
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT id_user FROM user_team WHERE id_team = '$id'";
		$result_query = mysql_query($query, $connection) or die(mysql_error());
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		print( json_encode($arr)); 
		close_connection($connection);
	}
	/*Post: Retorna la lista de identificadores de usuarios que pertenecen al identificador de equipo de la entrada en formato JSON*/
	
	
	/*Devuelve el estado de un usuario en un equipo*/
	function get_status($id_user, $id_team){
	/*Pre: - */
		$connection = open_connection();
		
	    $query = "SELECT pendent FROM user_team WHERE id_user='$id_user' AND id_team='$id_team'";

		if (!mysql_query($query, $connection)) {
			my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
			close_connection($connection);	
			return null;
		}else{
			close_connection($connection);	
			return $query;
		}
	}
	/*Post: La función nos retorna el valor del estado del usuario en el equipo, en caso de que no exista relación entre el usuario y el equipo se retorna valor nulo*/
	
	
	/*Esta función nos modifica el estado de una instancia entre un usuario y un equipo*/
	function set_user_team_status($id_user, $id_team, $pendent){
	/*Pre: - */
		$connection = open_connection();
		
		$query = "UPDATE user_team SET pendent='$pendent' WHERE id_user='$id_user' AND id_team='$id_team'";
		
		if (!mysql_query($query, $connection)) {
			my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
			close_connection($connection);	
			return false;
		}else{
			close_connection($connection);	
			return true;
		}	
	}
	/*Post: Devuelve cierto si ha realizado la modificacion correctamente, en caso contrario devuelve falso*/
	
	
	/*Esta función elimina la instancia en la que esta relacionado un usuario y un equipo */
	function delete_user_team($id_user, $id_team){
	/*Pre: - */
		$connection = open_connection();
		
	    $query = "DELETE FROM user_team WHERE id_user = '$id_user' and id_team='$id_team'";

		$result_query = mysql_query($query, $connection) or my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
		
		close_connection($connection);		
	}
	/*Post: La función elimina la instancia en la que aparece relacionado el usuario y el equipo de la entrada mediante sus identificadores*/
	
	
	/*La función comprueba si hay una instancia que relacione el usuario con el equipo de la entrada*/
	function exist_user_team($id_user, $id_team){	
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT * FROM user_team WHERE id_user = '$id_user' AND id_team='$id_team'";
	
		$result_query = mysql_query($query, $connection) or my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
	
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		if (count($arr)==0)	return false;
		else return true;
	}
	/*Post: Devuelve cierto en caso de que el identificador del usuario y del equipo existe, en caso contrario devuelve falso*/
?>
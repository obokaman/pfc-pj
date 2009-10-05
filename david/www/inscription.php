<?php

	/*La función crea una instancia que relaciona un usuario con un campeonato mediante sus identificadores*/
	function create_inscription($id_user, $id_champ, $pendent){
	/*Pre: - */	
		$connection = open_connection();
		
	    $query = "INSERT INTO inscription ( id_user, id_champ, pendent)
						VALUES ('$id_user',' $id_champ', '$pendent')";
						
		if (!mysql_query($query, $connection)) {
			my_error(mysql_errno($connection) . ": " . mysql_error($connection), 1);
			close_connection($connection);	
			return false;
		}else{	
			close_connection($connection);	
			return true;
		}
	}
	/*Post: La función nos retorna cierto en caso de que haya tenido exito la creacion de la nueva instancia entre el usuario y el campeonato, en caso contrario devuelve falso*/
	
	
	/*La función nos devuelve la lista de todos los usuarios y campeonatos que estan relacionados que estan almacenados en la BBDD, en formato JSON*/
	function get_inscriptions(){
	/*Pre: - */	
		$connection = open_connection();
		$query =  "SELECT * FROM inscription";
		$result_query = mysql_query($query, $connection) or my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		print(json_encode($arr)); 
		close_connection($connection);		
	}
	/*Post: La función nos devuelve la lista de todos los usuarios junto con los campeonatos a los que estan inscritos y el estado que estan almacenados en la base de datos, en formato JSON*/

	
	/*La función nos devuelve el identificador de los campeonatos a los que esta inscrito el identificador del usuario de la entrada en formato JSON */
	function get_champs_of_user($id){
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT id_champ FROM inscription WHERE id_user = '$id'";
		$result_query = mysql_query($query, $connection) or die(mysql_error());
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		print( json_encode($arr)); 
		close_connection($connection);
	}
	/*Post: Retorna una lista de los identificadores de campeonatos a los que esta inscrito el usuario de la entrada en formato JSON*/
	
	
	/*La función nos devuelve la lista de usuarios que estan inscritos a un campeonato en concreto, la información la retorna en formato JSON*/
	function get_users_of_champ($id){
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT id_user FROM inscription WHERE id_champ = '$id'";
		$result_query = mysql_query($query, $connection) or die(mysql_error());
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		print( json_encode($arr)); 
		close_connection($connection);
	}
	/*Post: Retorna la lista de identificadores de usuarios que estan inscritos al identificador de campeonato de la entrada en formato JSON*/
	
	
	/*Devuelve el estado de un usuario en un campeonato*/
	function get_status_inscription($id_user, $id_champ){
	/*Pre: - */
		$connection = open_connection();
		
	    $query = "SELECT pendent FROM inscription WHERE id_user='$id_user' AND id_champ='$id_champ'";

		if (!mysql_query($query, $connection)) {
			my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
			close_connection($connection);	
			return null;
		}else{
			close_connection($connection);	
			return $query;
		}
	}
	/*Post: La función nos retorna el valor del estado del usuario en el campeonato, en caso de que no exista relación entre el usuario y el campeonato se retorna valor nulo*/
	
	
	/*Esta función nos modifica el estado de una instancia entre un usuario y un campeonato al que esta inscrito*/
	function set_inscription_status($id_user, $id_champ, $pendent){
	/*Pre: - */
		$connection = open_connection();
		
		$query = "UPDATE inscription SET pendent='$pendent' WHERE id_user='$id_user' AND id_champ='$id_champ'";
		
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
	
	
	/*Esta función elimina la instancia en la que esta relacionado un usuario y un campeonato */
	function delete_inscription($id_user, $id_champ){
	/*Pre: - */
		$connection = open_connection();
		
	    $query = "DELETE FROM inscription WHERE id_user = '$id_user' and id_champ='$id_champ'";

		$result_query = mysql_query($query, $connection) or my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
		
		close_connection($connection);		
	}
	/*Post: La función elimina la instancia en la que aparece relacionado el usuario y el campeonato de la entrada mediante sus identificadores*/
	
	
	/*La función comprueba si hay una instancia que relacione el usuario con el campeonato de la entrada*/
	function exist_inscription($id_user, $id_champ){	
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT * FROM inscription WHERE id_user = '$id_user' AND id_champ='$id_champ'";
	
		$result_query = mysql_query($query, $connection) or my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
	
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		if (count($arr)==0)	return false;
		else return true;
	}
	/*Post: Devuelve cierto en caso de que el identificador del usuario y del campeonato existe, en caso contrario devuelve falso*/
?>
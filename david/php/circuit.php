<?php

	/*La función crea un circuito en la BBDD con los parametros que le pasamos de entrada*/
	function create_circuit($name, $short_name, $level, $n_laps, $time){
	/*Pre: - */	
		$connection = open_connection();
		
	    $query = "INSERT INTO circuit (name, short_name, level, n_laps, time) VALUES ('$name', '$short_name', '$level', '$n_laps', '$time')";
						
		if (!mysql_query($query, $connection)) {
			 my_error('CREATE_CIRCUIT-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
			close_connection($connection);	
			return false;
		}else{	
			close_connection($connection);	
			return true;
		}
	}
	/*Post: La función nos retorna cierto en caso de que haya tenido exito la creacion del nuevo circuito, en caso contrario devuelve falso*/
	
	
	/*La función nos devuelve la lista de todos los circuitos que estan almacenados en la BBDD*/
	function get_circuits(){
	/*Pre: - */	
		$connection = open_connection();
		$query =  "SELECT name FROM circuit";
		$result_query = mysql_query($query, $connection) or  my_error('GET_CIRCUITS-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

		$arr = extract_rows($result_query);
		$res = array();
		foreach ($arr as $row) {
			$res[] = $row->name;
		}
		return $res;
	}
	/*Post: La función nos devuelve una array con todos los objetos circuito que estan almacenados en la base de datos*/

	
	/*La función nos devuelve la información del circuito a partir del identificador que tiene en la BBDD*/
	function get_circuit_id($id){
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT * FROM circuit WHERE id_circuit = '$id'";
		$result_query = mysql_query($query, $connection) or  my_error('GET_CIRCUIT_ID-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		close_connection($connection);		
		return(extract_row($result_query));
	}
	/*Post: La función nos devuelve una array el objeto circuito seleccionado a partir de su identificador de circuito */
	
	
	/*La función nos devuelve la información del circuito a partir del nick del usuario*/
	function get_circuit_name($name){
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT * FROM circuit WHERE name = '$name'";
		$result_query = mysql_query($query, $connection) or  my_error('GET_CIRCUIT_NAME-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

		close_connection($connection);		
		return(extract_row($result_query));
	}
	/*Post: La función nos devuelve una array el objeto circuito seleccionado a partir de su nombre */
	
	
	/*La función nos devuelve la información del circuito a partir del nombre corto del circuito*/
	function get_circuit_short_name($short_name){
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT * FROM circuit WHERE short_name = '$short_name'";
		$result_query = mysql_query($query, $connection) or  my_error('GET_CIRCUIT_SHORT_NAME-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

		close_connection($connection);		
		return(extract_row($result_query));
	}
	/*Post: La función nos devuelve una array el objeto circuito seleccionado a partir de su nombre corto */
	
	
	/*Esta función modifica los campos almacenados de un circuito en la BBDD*/
	function set_circuit($id, $name, $short_name, $level, $n_laps, $time){
	/*Pre: El identificador del circuito existe, ademas el nombre y  el nombre corto del circuito no son valores nulos*/
		$connection = open_connection();
		
	    $query = "UPDATE circuit SET name='$name' short_name='$short_name' level= '$level', n_laps='$n_laps', time='$time' WHERE id_circuit = '$id'";

		if (!mysql_query($query, $connection)) {
			 my_error('SET_CIRCUIT-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
			close_connection($connection);	
			return false;
		}else{
			close_connection($connection);	
			return true;
		}
	}
	/*Post: La función nos retorna cierto si sea modificado correctamente el circuito de la entrada, en caso contrario retorna falso*/
	
	
	/*Esta función borra el circuito de la BBDD identificado por el mismo identificador que tienen en la BBDD*/
	function delete_circuit_id($id){
	/*Pre: - */
		$connection = open_connection();
		
	    $query = "DELETE FROM circuit WHERE id_circuit = '$id'";

		$result_query = mysql_query($query, $connection) or  my_error('DELETE_CIRCUIT_ID-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		close_connection($connection);		
	}
	/*Post: La función borra el circuito de la BBDD*/
	
	
	
	/*La función comprueba si el identificador del circuito existe*/
	function exist_circuit($id){	
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT * FROM circuit WHERE id_circuit = '$id'";
	
		$result_query = mysql_query($query, $connection) or my_error('EXIST_CIRCUIT-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

		if (count(extract_row($result_query))==0)	return false;
		else return true;
	}
	/*Post: Devuelve cierto en caso de que el identificador del circuito existe, en caso contrario devuelve falso*/
?>
<?php

	/*La función crea una instancia que relaciona un circuito con un campeonato mediante sus identificadores
			- id_circuit: Identificador del circuito
			- id_champ: Identificador del campeonato
	*/
	function add_circuit_champ($id_circuit, $id_champ){
	/*Pre: Los dos identificadores existen */	
		global $connection;
		
	    $query = "INSERT INTO circuit_championship ( id_circuit, id_champ)	VALUES ('$id_circuit',' $id_champ')";
		if (!mysql_query($query, $connection)) {
			my_error('ADD_CIRCUIT_CHAMP-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
			return false;
		}else			return true;
	}
	/*Post: La función nos retorna cierto en caso de que haya tenido exito la creacion de la nueva instancia entre el circuito y el campeonato, en caso contrario devuelve falso*/

	
	/*Esta función elimina la instancia en la que esta relacionado un circuito y un campeonato
			- id_circuit: Identificador del circuito
			- id_champ: Identificador del campeonato
	*/
	function delete_circuit_champ($id_circuit, $id_champ){
	/*Pre: - */
		global $connection;
		
	    $query = "DELETE FROM circuit_championship WHERE id_circuit = '$id_circuit' and id_champ='$id_champ'";
		$result_query = mysql_query($query, $connection) or my_error('DELETE_CIRCUIT_CHAMP-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
	}
	/*Post: La función elimina la instancia en la que aparece relacionado el circuito y el campeonato de la entrada mediante sus identificadores*/
	
	
	/*La función comprueba si hay una instancia que relacione el circuito con el campeonato de la entrada
			- id_circuit: Identificador del circuito
			- id_champ: Identificador del campeonato
	*/
	function exist_circuit_champ($id_circuit, $id_champ){	
	/*Pre: - */
		global $connection;
		
		$query =  "SELECT * FROM circuit_championship WHERE id_circuit = '$id_circuit' AND id_champ='$id_champ'";	
		$result_query = mysql_query($query, $connection) or my_error('EXIST_CIRCUIT_CHAMP-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		if (extract_num_rows($result_query)==0)	return false;
		else return true;
	}
	/*Post: Devuelve cierto en caso de que el identificador del circuito y del campeonato existe, en caso contrario devuelve falso*/
?>
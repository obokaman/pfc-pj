<?php

	/*La función crea una instancia que relaciona un circuito con un campeonato mediante sus identificadores*/
	function add_circuit_champ($id_circuit, $id_champ){
	/*Pre: - */	
		global $connection;
		
	    $query = "INSERT INTO circuit_championship ( id_circuit, id_champ)
						VALUES ('$id_circuit',' $id_champ')";
						
		if (!mysql_query($query, $connection)) {
			my_error('ADD_CIRCUIT_CHAMP-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
				
			return false;
		}else{	
				
			return true;
		}
	}
	/*Post: La función nos retorna cierto en caso de que haya tenido exito la creacion de la nueva instancia entre el circuito y el campeonato, en caso contrario devuelve falso*/
	
	
	/*La función nos devuelve la lista de todos los circuitos y campeonatos que estan relacionados que estan almacenados en la BBDD*/
	function get_circuit_champ(){
	/*Pre: - */	
		global $connection;
		$query =  "SELECT * FROM circuit_championship";
		$result_query = mysql_query($query, $connection) or my_error('GET_CIRCUIT_CHAMP-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
				
		return(extract_row($result_query));		
	}
	/*Post: La función nos devuelve una array de todos los circuitos junto con los campeonatos a los que pertenecen y el estado que estan almacenados en la base de datos*/

	
	/*La función nos devuelve el identificador de los campeonatos a los que pertenece el identificador del circuito de la entrada */
	function get_champs_of_circuit($id){
	/*Pre: - */
		global $connection;
		$query =  "SELECT id_champ FROM circuit_championship WHERE id_circuit = '$id'";
		$result_query = mysql_query($query, $connection) or my_error('GET_CHAMPS_OF_CIRCUIT-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

				
		return(extract_row($result_query));
	}
	/*Post: Retorna una lista de los identificadores de campeonatos a los que pertenece el circuito de la entrada en formato JSON*/
	
	
	/*La función nos devuelve la lista de circuitos que pertenecen a un campeonato en concreto, la información la retorna en formato JSON*/
	function get_circuits_of_champ($id){
	/*Pre: - */
		global $connection;
		$query =  "SELECT id_circuit FROM circuit_championship WHERE id_champ = '$id'";
		$result_query = mysql_query($query, $connection) or my_error('GET_CIRCUITS_OF_CHAMP-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

				
		return(extract_row($result_query));
	}
	/*Post: Retorna una array con los de identificadores de circuitos que pertenecen al identificador de campeonato de la entrada*/
	
	
	/*Esta función elimina la instancia en la que esta relacionado un circuito y un campeonato */
	function delete_circuit_champ($id_circuit, $id_champ){
	/*Pre: - */
		global $connection;
		
	    $query = "DELETE FROM circuit_championship WHERE id_circuit = '$id_circuit' and id_champ='$id_champ'";

		$result_query = mysql_query($query, $connection) or my_error('DELETE_CIRCUIT_CHAMP-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
				
	}
	/*Post: La función elimina la instancia en la que aparece relacionado el circuito y el campeonato de la entrada mediante sus identificadores*/
	
	
	/*La función comprueba si hay una instancia que relacione el circuito con el campeonato de la entrada*/
	function exist_circuit_champ($id_circuit, $id_champ){	
	/*Pre: - */
		global $connection;
		$query =  "SELECT * FROM circuit_championship WHERE id_circuit = '$id_circuit' AND id_champ='$id_champ'";
	
		$result_query = mysql_query($query, $connection) or my_error('EXIST_CIRCUIT_CHAMP-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		if (count(extract_row($result_query))==0)	return false;
		else return true;
	}
	/*Post: Devuelve cierto en caso de que el identificador del circuito y del campeonato existe, en caso contrario devuelve falso*/
?>
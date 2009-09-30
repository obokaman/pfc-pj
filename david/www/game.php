<?php

	/*La función crea una partida que nos relaciona, mediante identificadores, el usuario que la realiza, el ciruito y el campeonato al que     
   participa, finalmente se nos indica el tiempo que el usuario ha estado*/
	function create_game($id_user, $id_circuit, $id_champ, $time){
	/*Pre: Los identificadores de la entrada de la función existen, y el circuito pertenece al campeonato que se nos indica
    en la entrada */	
		$connection = open_connection();
		
		if(($id_champ!=null) and (!exist_circuit_champ($id_circuit, $id_champ))){
				return false; 
		}else{		
				$query = "INSERT INTO game ( id_user, id_circuit, id_champ, time_result)
								VALUES ('$id_user',' $id_circuit', '$id_champ', '$time')";
								
				if (!mysql_query($query, $connection)) {
					my_error(mysql_errno($connection) . ": " . mysql_error($connection), 1);
					close_connection($connection);	
					return false;
				}else{	
					close_connection($connection);	
					return true;
				}
		}
	}
	/*Post: La función nos retorna cierto en caso de que haya tenido exito la creacion de la nueva partida, en caso contrario devuelve falso*/
	
	
	/*La función nos devuelve la lista de todos las partidas realizadas que estan almacenados en la BBDD, en formato JSON*/
	function get_game(){
	/*Pre: - */	
		$connection = open_connection();
		$query =  "SELECT * FROM game";
		$result_query = mysql_query($query, $connection) or my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		print(json_encode($arr)); 
		close_connection($connection);		
	}
	/*Post: La función nos devuelve la lista de todas las partidas realizadas que estan almacenados en la base de datos, en formato JSON*/
	
	
	/*La función devuelve la información de una partida a partir del identificador de esta en formato JSON */
	function get_game_id($id){
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT * FROM game WHERE id_game = '$id'";
		$result_query = mysql_query($query, $connection) or die(mysql_error());
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		print( json_encode($arr)); 
		close_connection($connection);
	}
	/*Post: Retorna la información de una partida a partir del identificador de esta en formato JSON */
	
	
	/*Esta función nos modifica el tiempo que tiene la partida */
	function set_time($id_game, $time){
	/*Pre: - */
		$connection = open_connection();
		
		$query = "UPDATE game SET time_result='$time' WHERE id_game='$id_game'";
		
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
	
	
	/*Esta función elimina la partida*/
	function delete_game($id_game){
	/*Pre: - */
		$connection = open_connection();
		
	    $query = "DELETE FROM game WHERE id_game = '$id_game'";

		$result_query = mysql_query($query, $connection) or my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
		
		close_connection($connection);		
	}
	/*Post: La función elimina la partida*/
	
	
	/*La función comprueba si hay una partida con el identificador de la entrada*/
	function exist_game($id_game){	
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT * FROM game WHERE id_game = '$id_game'";
	
		$result_query = mysql_query($query, $connection) or my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
	
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		if (count($arr)==0)	return false;
		else return true;
	}
	/*Post: Devuelve cierto en caso de que el identificador de la partida exista, en caso contrario devuelve falso*/
?>
<?php

	/*La función crea un circuito en la BBDD con los parametros que le pasamos de entrada
			- name: Nombre del circuito
			- short_name: Nombre de la imagen del circuito
			- level: Nivel del circuito
			- n_laps: Numero de vueltas
			- time: -
	*/
	function create_circuit($name, $short_name, $level, $n_laps, $time){
	/*Pre: - */		
		global $connection;
			
	    $query = "INSERT INTO circuit (name, short_name, level, n_laps, time) VALUES ('$name', '$short_name', '$level', '$n_laps', '$time')";			
		if (!mysql_query($query, $connection)) {
			my_error('CREATE_CIRCUIT-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
			return false;
		}else			return true;
	}
	/*Post: La función nos retorna cierto en caso de que haya tenido exito la creacion del nuevo circuito, en caso contrario devuelve falso*/
	
	
	/*La funcion nos retorna el identificador del circuito a partir del nombre de la entrada
			- name: Nombre del circuito
	*/
	function get_id_circuit($name){
	/*Pre: El nombre del circuito debe existir */
		global $connection;
		
		$query = "SELECT c.id_circuit FROM circuit c WHERE c.name = '$name'";	
		$result_query = mysql_query($query, $connection) or my_error('GET_ID_CIRCUIT-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		return extract_row($result_query)->id_circuit;		
	}
	/*Post: Retorna un entero que representa el identificador del circuito con el mismo nombre que name*/
	
	
	/*La funcion nos da el short_name de un circuito a partir del identificador del circuito
			- id: Identificador del circuito
	*/
	function get_short_name_circuit($id){
	/*Pre: - */	
		global $connection;
		
		$query = "SELECT c.short_name FROM  circuit c WHERE c.id_circuit = '$id'";
		$result_query = mysql_query($query, $connection) or my_error('GET_SHORT_NAME_CIRCUIT-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		return extract_row($result_query)->short_name;			
	}
	/*Post: Devuelve el short_name correspondiente al identificador de la entrada*/
	
	
	/*La función nos devuelve la lista de todos los nombres de los circuitos que estan almacenados en la BBDD*/
	function get_circuits(){
	/*Pre: - */		
		global $connection;
		
		$query =  "SELECT name FROM circuit";
		$result_query = mysql_query($query, $connection) or  my_error('GET_CIRCUITS-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

		$arr = extract_rows($result_query);
		$res = array();
		foreach ($arr as $row) {
			$res[] = $row->name;
		}
		return $res;
	}
	/*Post: La función nos devuelve una array de strings con los nombres de todos los circuitos que estan almacenados en la base de datos*/

	
	/*La función nos devuelve la información del circuito a partir del nombre 
			- name: Nombre del circuito
	*/
	function get_circuit($name){
	/*Pre: - */		
		global $connection, $path;
		
		$query =  "SELECT *  FROM circuit WHERE name = '$name'";
		$result_query = mysql_query($query, $connection) or  my_error('GET_CIRCUIT-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		$obj = extract_row($result_query);
		
		$arr = Array ( 'url' => $path['url-images'].$obj->short_name.".png",
								'width' => $obj->width,
								'height' => $obj->height,
								'level' => $obj->level,
								'n_laps' => $obj->n_laps);
		
		return $arr;
	}
	/*Post: La función nos devuelve una array de strings donde los elementos estan indexados con 'url', 'width', 'height', 'level' y 'n_laps'*/
	
	
	/*Esta función borra el circuito de la BBDD identificado por el id de la entrada
			- id: Identificador del circuito
	*/
	function delete_circuit_id($id){
	/*Pre: - */		
		global $connection;
		
	    $query = "DELETE FROM circuit WHERE id_circuit = '$id'";
		$result_query = mysql_query($query, $connection) or  my_error('DELETE_CIRCUIT_ID-> '.mysql_errno($connection).": ".mysql_error($connection), 1);		
	}
	/*Post: La función borra el circuito de la BBDD*/
	
	
	/*La función comprueba si el nombre del circuito existe
			- name: Nombre del circuito
	*/
	function exist_circuit($name){	
	/*Pre: - */		
		global $connection;
		
		$query =  "SELECT * FROM circuit WHERE name = '$name'";	
		$result_query = mysql_query($query, $connection) or my_error('EXIST_CIRCUIT-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

		if (extract_num_rows($result_query) == 0)	return false;
		else return true;
	}
	/*Post: Devuelve cierto en caso de que el nombre del circuito existe, en caso contrario devuelve falso*/
?>
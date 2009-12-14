<?php
	
	/*La función añade una nueva fila en la base de datos con el codigo de una partida realizada por un usuario*/
	function new_code ( $file_name, $code, $date, $id_user ){
	/*Pre: - */
		global $connection;
		
		$query = "INSERT INTO code (file_name, file_date, code, id_user) VALUES ('$file_name', '$date', '$code', '$id_user')";
		
		if (!mysql_query($query, $connection)) {
			my_error('NEW_CODE -> '.mysql_errno($connection).": ".mysql_error($connection), 1);				
			return false;
		}else{					
			return true;
		}		
	}
	/*Post: La función devuelve cierto si ha insertado correctamente la fila junto con todos sus parametros, en caso con contrario devuelve falso y no inserta la nueva fila*/


	/*La funcion retorna el codigo segun el nombre del fichero que lo contiene y el usuario que lo creo*/
	function get_code( $file_name, $id_user) {
	/*Pre: - */	
		global $connection;
		
		$query = "SELECT c.code FROM code c WHERE c.file_name= '$file_name' AND c.id_user = '$id_user'";
	
		$result_query = mysql_query($query, $connection) or my_error('GET_CODE-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		return extract_row($result_query)->code;
	}
	/*Post:  Devuelve el codigo asociado al nombre de fichera de la entrada y el identificador del usuario*/


	/*La funcion retorna una lista de los nombres de ficheros y sus fechas a partir de identificador de un usuario que las ha realizado*/
	function get_saved_codes( $id_user) {
	/*Pre: - */	
		global $connection;
		
		$query = "SELECT c.file_name as name, c.file_date as date FROM code c WHERE c.id_user = '$id_user'";
	
		$result_query = mysql_query($query, $connection) or my_error('GET_CODE-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		return extract_rows($result_query);
	}
	/*Post:  Devuelve el codigo asociado al nombre de fichera de la entrada y el identificador del usuario*/


	/*La funcion comprueba que el nombre del fichero no este repetido en la base de datos*/
	function exist_file_name( $name, $id_user ){
	/*Pre: - */
			global $connection;
			
			$query =  "SELECT * FROM code WHERE file_name = '$name' AND id_user = '$id_user'";
		$result_query = mysql_query($query, $connection) or my_error('EXIST_FILE_NAME-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		if (extract_num_rows($result_query) == 0)	return false;
		else return true;
	}
	/*Post: Retorna cierto si ha encontrado una fila con el mismo nombre de fichero que el de la entrada y caso contrario retorna falso*/

?>
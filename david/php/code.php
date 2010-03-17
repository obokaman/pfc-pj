<?php
	
	/*La función añade una nueva fila en la base de datos con el codigo de una partida realizada por un usuario
			- file_name: Nombre del fichero
			- code: Codigo
			- date: Fecha de insercion
			- id_user: Identificador de usuario
	*/
	function new_code ( $file_name, $code, $date, $id_user ){
	/*Pre: En nombre del fichero y el identifcador de usuario no pueden ser nulos*/
		global $connection;
		
		$query = "INSERT INTO code (file_name, file_date, code, id_user) VALUES ('$file_name', '$date', '$code', '$id_user')";		
		if (!mysql_query($query, $connection)) {
			my_error('NEW_CODE -> '.mysql_errno($connection).": ".mysql_error($connection), 1);				
			return false;
		}else			return true;
	}
	/*Post: La función devuelve cierto si ha insertado correctamente la fila junto con todos sus parametros, en caso con contrario devuelve falso y no inserta la nueva fila*/


	/*La funcion retorna el codigo segun el nombre del fichero que lo contiene y el usuario que lo creo
			- file_name: Nombre del fichero
			- id_user: Identificador de usuario
	*/
	function get_code( $file_name, $id_user) {
	/*Pre: Los parametros de entrada han de existir en la base de datos*/	
		global $connection;
		
		$query = "SELECT c.code FROM code c WHERE c.file_name= '$file_name' AND c.id_user = '$id_user'";	
		$result_query = mysql_query($query, $connection) or my_error('GET_CODE-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		return extract_row($result_query)->code;
	}
	/*Post:  Devuelve el codigo asociado al nombre de fichera de la entrada y el identificador del usuario*/



	/*La funcion retorna una lista de los nombres de ficheros y sus fechas a partir de identificador de un usuario que las ha realizado
			- id_user: Identificador de usuario
	*/
	function get_saved_codes( $id_user) {
	/*Pre: El identificador debe existir en la base de datos */	
		global $connection;
		
		$query = "SELECT c.file_name as name, c.file_date as date FROM code c WHERE c.id_user = '$id_user'";	
		$result_query = mysql_query($query, $connection) or my_error('GET_CODE-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		return extract_rows($result_query);
	}
	/*Post:  Devuelve lista con los pares, el nombre de la partida  y la fecha del guardado del código, del usuario de la entrada */


	/*La función actualiza una partida guardada en la base de datos con los nuevos parametros de entrada
			- file_name: Nombre del fichero
			- code: Codigo
			- date: Fecha de insercion
			- id_user: Identificador de usuario
	*/
	function set_code ( $file_name, $code, $date, $id_user ){
	/*Pre: En nombre del fichero y el identifcador de usuario no pueden ser nulos*/
		global $connection;		
		
		$query = "UPDATE code SET code='$code', file_date='$date' WHERE file_name='$file_name' AND id_user='$id_user' ";
		
		if (!mysql_query($query, $connection)) {
			my_error('SET_CODE -> '.mysql_errno($connection).": ".mysql_error($connection), 1);				
			return false;
		}else			return true;
	}
	/*Post: La función devuelve cierto si ha modificado correctamente la fila junto con todos sus parametros, en caso con contrario devuelve falso y no inserta la nueva fila*/


	/*La funcion comprueba que existe el codigo con el mismo nombre de fichero y de identificador de usuario que la entrada
			- name: Nombre del fichero
			- id_user: Identificador de usuario
	*/
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
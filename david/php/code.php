<?php
	
	/*La función añade el código de una partida junto con el identificador del usuario que lo ha creado, fecha y nombre de la partida
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
	/*Post: La función devuelve cierto si ha insertado correctamente el codigo de la partida junto con el resto de los parametros, en caso con contrario devuelve falso*/


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



	/*La funcion retorna una lista donde cada elemento contiene: el nombre y fecha de los codigos de partidas que ha guardado el usuario
	con el mismo identificador que la variable de entrada de la funcion
			- id_user: Identificador de usuario
	*/
	function get_saved_codes( $id_user) {
	/*Pre: - */	
		global $connection;
		
		$query = "SELECT c.file_name as name, c.file_date as date FROM code c WHERE c.id_user = '$id_user'";	
		$result_query = mysql_query($query, $connection) or my_error('GET_CODE-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		return extract_rows($result_query);
	}
	/*Post:  Devuelve lista con los pares: el nombre y la fecha de los codigos de partida que ha guardado el usuario con el mismo identificador que la variable de la entrada*/


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
	/*Post: La función devuelve cierto si ha modificado correctamente la fila junto con todos sus parametros, en caso con contrario devuelve falso*/

	/*La función que elimina una partida guardada en la base de datos
			- name: Nombre de la partida
			- id_user: Identificador del usuario propietario de la partida
	*/
	function delete_code ($name, $id_user){
	/*Pre: - */	
		global $connection;
		
	    $query = "DELETE FROM code WHERE  file_name = '$name' AND id_user = '$id_user' ";
		$result_query = mysql_query($query, $connection) or my_error('DELETE_CODE-> '.mysql_errno($connection).": ".mysql_error($connection), 1);		
		
	}
	/*Post: Elimina el código almacenado de una partida en la base de datos identificandola a partir del nombre  de la partida y usuario propietario*/


	/*La funcion comprueba que existe el codigo con el mismo nombre de partida que la variable 'name' y mismo identificador de usuario que la variable 'id_user'
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
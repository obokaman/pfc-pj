<?php include("connection.php");?>
<?php include("error.php");?>

<?php

	/*La función crea un campeonato en la BBDD con los parametros que le pasamos de entrada*/
	function create_championship($name, $data_limit, $id_founded){
	/*Pre: 'id_founded' es un identificador de usuario que existe en la BBDD */	
		$connection = open_connection();
		
	    $query = "INSERT INTO champioship (name, data_limit, id_founded) VALUES ('$name', '$data_limit', '$id_founded')";
						
		if (!mysql_query($query, $connection)) {
			my_error(mysql_errno($connection) . ": " . mysql_error($connection), 1);
			close_connection($connection);	
			return false;
		}else{	
			close_connection($connection);	
			return true;
		}
	}
	/*Post: La función nos retorna cierto en caso de que haya tenido exito la creacion del nuevo campeonato, en caso contrario devuelve falso*/
	
	
	/*La función nos devuelve la lista de todos los campeonatos que estan almacenados en la BBDD, en formato JSON*/
	function get_championships(){
	/*Pre: - */	
		$conexion = open_connection();
		$query =  "SELECT * FROM championship";
		$result_query = mysql_query($query, $conexion) or my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		print(json_encode($arr)); 
		close_connection($conexion);		
	}
	/*Post: La función nos devuelve la lista de todos los campeonatos que estan almacenados en la base de datos, en formato JSON*/

	
	/*La función nos devuelve la información del campeonato en formato JSON a partir del identificador que tiene en la BBDD*/
	function get_championship_id($id){
	/*Pre: - */
		$conexion = open_connection();
		$query =  "SELECT * FROM championship WHERE id_champ = '$id'";
		$result_query = mysql_query($query, $conexion) or die(mysql_error());
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		print( json_encode($arr)); 
		close_connection($conexion);
	}
	/*Post: Retorna la información del campeonato en formato JSON*/
	
	
	/*La función nos devuelve la información del campeonato en formato JSON a partir del nombre del campeonato*/
	function get_championship_name($name){
	/*Pre: - */
		$conexion = open_connection();
		$query =  "SELECT * FROM championship WHERE name = '$name'";
		$result_query = mysql_query($query, $conexion) or die(mysql_error());
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		print( json_encode($arr)); 
		close_connection($conexion);
	}
	/*Post: Retorna la información del campeonato en formato JSON*/
	
	
	/*Esta función modifica los campos almacenados de un campeonato en la BBDD*/
	function set_championship($id, $name, $data_limit, $id_founded){
	/*Pre: EL identificador del campeonato y el 'id_founded' existen, y ademas el resto de los parametros no son nulos */
		$connection = open_connection();
		
	    $query = "UPDATE championship SET name='$name' data_limit= '$data_limit', id_founded='$id_founded' WHERE id_champ = '$id'";

		if (!mysql_query($query, $connection)) {
			my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
			close_connection($connection);	
			return false;
		}else{
			close_connection($connection);	
			return true;
		}
	}
	/*Post: La función nos retorna cierto si sea modificado correctamente el campeonato de la entrada, en caso contrario retorna falso*/
	
	
	/*Esta función borra el campeonato de la BBDD identificado por el mismo identificador que tienen en la BBDD*/
	function delete_championship_id($id){
	/*Pre: - */
		$connection = open_connection();
		
	    $query = "DELETE FROM championship WHERE id_champ = '$id'";

		$result_query = mysql_query($query, $conexion) or my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
		
		close_connection($connection);		
	}
	/*Post: La función borra el circuito de la BBDD*/
	
	
	
	/*La función comprueba si el identificador del campeonato existe*/
	function exist_championship($id){	
	/*Pre: - */
		$conexion = open_connection();
		$query =  "SELECT * FROM championship WHERE id_champ = '$id'";
	
		if (!mysql_query($query, $connection)) {			
			close_connection($connection);	
			return false;
		}else{
			close_connection($connection);	
			return true;
		}		
	}
	/*Post: Devuelve cierto en caso de que el identificador del campeonato existe, en caso contrario devuelve falso*/

?>
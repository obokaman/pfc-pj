<?php include("connection.php");?>
<?php include("error.php");?>


<?php

	/*La función crea una instancia que relaciona un circuito con un campeonato mediante sus identificadores*/
	function add_circuit_champ($id_circuit, $id_champ){
	/*Pre: - */	
		$connection = open_connection();
		
	    $query = "INSERT INTO circuit_championship ( id_circuit, id_champ)
						VALUES ('$id_circuit',' $id_champ')";
						
		if (!mysql_query($query, $connection)) {
			my_error(mysql_errno($connection) . ": " . mysql_error($connection), 1);
			close_connection($connection);	
			return false;
		}else{	
			close_connection($connection);	
			return true;
		}
	}
	/*Post: La función nos retorna cierto en caso de que haya tenido exito la creacion de la nueva instancia entre el circuito y el campeonato, en caso contrario devuelve falso*/
	
	
	/*La función nos devuelve la lista de todos los circuitos y campeonatos que estan relacionados que estan almacenados en la BBDD, en formato JSON*/
	function get_circuit_champ(){
	/*Pre: - */	
		$connection = open_connection();
		$query =  "SELECT * FROM circuit_championship";
		$result_query = mysql_query($query, $connection) or my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		print(json_encode($arr)); 
		close_connection($connection);		
	}
	/*Post: La función nos devuelve la lista de todos los circuitos junto con los campeonatos a los que pertenecen y el estado que estan almacenados en la base de datos, en formato JSON*/

	
	/*La función nos devuelve el identificador de los campeonatos a los que pertenece el identificador del circuito de la entrada en formato JSON */
	function get_champs_of_circuit($id){
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT id_champ FROM circuit_championship WHERE id_circuit = '$id'";
		$result_query = mysql_query($query, $connection) or die(mysql_error());
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		print( json_encode($arr)); 
		close_connection($connection);
	}
	/*Post: Retorna una lista de los identificadores de campeonatos a los que pertenece el circuito de la entrada en formato JSON*/
	
	
	/*La función nos devuelve la lista de circuitos que pertenecen a un campeonato en concreto, la información la retorna en formato JSON*/
	function get_circuits_of_champ($id){
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT id_circuit FROM circuit_championship WHERE id_champ = '$id'";
		$result_query = mysql_query($query, $connection) or die(mysql_error());
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		print( json_encode($arr)); 
		close_connection($connection);
	}
	/*Post: Retorna la lista de identificadores de circuitos que pertenecen al identificador de campeonato de la entrada en formato JSON*/
	
	
	/*Esta función elimina la instancia en la que esta relacionado un circuito y un campeonato */
	function delete_circuit_champ($id_circuit, $id_champ){
	/*Pre: - */
		$connection = open_connection();
		
	    $query = "DELETE FROM circuit_championship WHERE id_circuit = '$id_circuit' and id_champ='$id_champ'";

		$result_query = mysql_query($query, $connection) or my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
		
		close_connection($connection);		
	}
	/*Post: La función elimina la instancia en la que aparece relacionado el circuito y el campeonato de la entrada mediante sus identificadores*/
	
	
	/*La función comprueba si hay una instancia que relacione el circuito con el campeonato de la entrada*/
	function exist_circuit_champ($id_circuit, $id_champ){	
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT * FROM circuit_championship WHERE id_circuit = '$id_circuit' AND id_champ='$id_champ'";
	
		$result_query = mysql_query($query, $connection) or my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
	
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		if (count($arr)==0)	return false;
		else return true;
	}
	/*Post: Devuelve cierto en caso de que el identificador del circuito y del campeonato existe, en caso contrario devuelve falso*/
?>
<?php

	/*La función crea un campeonato en la BBDD con los parametros que le pasamos de entrada*/
	function create_championship($name, $data_limit, $id_founded){
	/*Pre: 'id_founded' es un identificador de usuario que existe en la BBDD */	
		$connection = open_connection();
		
	    $query = "INSERT INTO championship (name, data_limit, id_founded) VALUES ('$name', '$data_limit', '$id_founded')";
						
		if (!mysql_query($query, $connection)) {
			my_error('CREATE_CHAMPIONSHIP-> '.mysql_errno($connection) . ": " . mysql_error($connection), 1);
			close_connection($connection);	
			return false;
		}else{	
			close_connection($connection);	
			return true;
		}
	}
	/*Post: La función nos retorna cierto en caso de que haya tenido exito la creacion del nuevo campeonato, en caso contrario devuelve falso*/
	
	
	/*La función nos devuelve la lista de todos los campeonatos que estan almacenados en la BBDD*/
	function get_championships(){
	/*Pre: - */	
		$connection = open_connection();
		$query =  "SELECT * FROM championship";
		$result_query = mysql_query($query, $connection) or my_error('GET_CHAMPIONSHIPS-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

		close_connection($connection);		
		return(extract_row($result_query));		
	}
	/*Post: La función nos devuelve la array con todos los objetos campeonato que estan almacenados en la base de datos*/

	
	/*La función nos devuelve la información del campeonato a partir del identificador que tiene en la BBDD*/
	function get_championship_id($id){
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT * FROM championship WHERE id_champ = '$id'";
		$result_query = mysql_query($query, $connection) or die(mysql_error());
		
		close_connection($connection);		
		return(extract_row($result_query));
	}
	/*Post: La función devuelve un array con el objeto campeonato seleccionado por su identificador*/
	
	
	/*La función nos devuelve la información del campeonato en formato JSON a partir del nombre del campeonato*/
	function get_championship_name($name){
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT * FROM championship WHERE name = '$name'";
		$result_query = mysql_query($query, $connection) or my_error('GET_CHAMPIONSHIP_NAME-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

		close_connection($connection);		
		return(extract_row($result_query));
	}
	/*Post: La función devuelve un array con el objeto campeonato seleccionado por su nombre*/
	
	
	    /*La función devuelve una lista de nombres de campeonatos a los que pertenece el usuario que esta logueado*/
	function getMyChampionships(){
	/*Pre: - */	
		
		if(isset($_SESSION["user"])){
			
			$nick_session = $_SESSION["user"]; 
			$connection = open_connection();
			$query =  	"select c.name
								from 	user u,
											inscription i,
											championship c
								where u.nick = '$nick_session'
								and  u.id_user = i.id_user
								and  i.pendent <> 0
								and  i.id_champ = c.id_champ";
			$result_query = mysql_query($query, $connection) or my_error('GET_MYTEAM-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
			
			close_connection($connection);	
			return(extract_row($result_query)); 		
		}
		
	}
	/*Post: Devuelve una array de strings con los nombres de los campeonatos que tiene relacion con el usuario que esta logueado*/
	
	
	/*Esta función modifica los campos almacenados de un campeonato en la BBDD*/
	function set_championship($id, $name, $data_limit, $id_founded){
	/*Pre: EL identificador del campeonato y el 'id_founded' existen, y ademas el resto de los parametros no son nulos */
		$connection = open_connection();
		
	    $query = "UPDATE championship SET name='$name' data_limit= '$data_limit', id_founded='$id_founded' WHERE id_champ = '$id'";

		if (!mysql_query($query, $connection)) {
			my_error('SET_CHAMPIONSHIP-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
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

		$result_query = mysql_query($query, $connection) or my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
		
		close_connection($connection);		
	}
	/*Post: La función borra el circuito de la BBDD*/
	
	
	
	/*La función comprueba si el identificador del campeonato existe*/
	function exist_championship($id){	
	/*Pre: - */
		$connection = open_connection();
		$query =  "SELECT * FROM championship WHERE id_champ = '$id'";
	
		$result_query = mysql_query($query, $connection) or my_error('EXIST_CHAMPIONSHIP-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		if (count(extract_row($result_query))==0)	return false;
		else return true;
	}
	/*Post: Devuelve cierto en caso de que el identificador del campeonato existe, en caso contrario devuelve falso*/

?>
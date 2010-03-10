<?php

	/*La función crea un campeonato en la BBDD con los parametros que le pasamos de entrada
			- name: Nombre del campeonato
			- date_limit: Fecha limite para poderse inscribir en el campeonato
			- id_founded: Identificador del usuario fundador del campeonat
	*/
	function create_championship($name, $date_limit, $id_founded){
	/*Pre: 'id_founded' es un identificador de usuario que existe en la BBDD y ningún valor puede ser nulo */	
		global $connection;
		
	    $query = "INSERT INTO championship (name, data_limit, id_founded) VALUES ('$name', '$date_limit', '$id_founded')";
		if (!mysql_query($query, $connection)) {
			my_error('CREATE_CHAMPIONSHIP-> '.mysql_errno($connection) . ": " . mysql_error($connection), 1);
			return false;
		}else			return true;
	}
	/*Post: La función nos retorna cierto en caso de que haya tenido exito la creacion del nuevo campeonato, en caso contrario devuelve falso*/
	
	
	/*La funcion nos retorna el identificador del campeonato a partir del nombre
			- name: Nombre del campeonato
	*/	
	function get_id_championship($name){
	/*Pre: El nombre del campeonato de la entrada existe*/
		global $connection;
		
		$query = "SELECT c.id_champ FROM championship c WHERE c.name = '$name'";	
		$result_query = mysql_query($query, $connection) or my_error('GET_ID_CHAMPIONSHIP-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		return extract_row($result_query)->id_champ;
	}
	/*Post: Retorna un entero que representa el identificador del campeonato*/
	
	
	/*La función retorna todos los nombres de los campeonatos que ha fundado el usuario que esta logueado*/
	function get_championships_by_founded(){
	/*Pre: - */
		if ( isset($_SESSION['user']) ) {
			
			global $connection;
			
			$id_user_founded = get_id_user($_SESSION['user']);
			
			$query =  "SELECT c.name FROM championship c WHERE c.id_founded = '$id_user_founded' AND c.data_limit >= now()";		
			
			$result_query = mysql_query($query, $connection) or my_error('GET_CHAMPIONSHIPS_BY_FOUNDED-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

			$arr = extract_rows($result_query);
			$res = array();
			foreach ($arr as $row) {
				$res[] = $row->name;
			}
			
			return $res;	
			
		}else return null;			
	}
	/*Post: Devuelve una array de string con los nombres de los campeonatos que ha fundado el usuario que esta logueado*/	
	
	
	
	/*La función devuelve una lista de nombres de campeonatos a los que pertenece el usuario que esta logueado, y el circuito
			- name_circuit: Nombre del circuito
	*/
	function get_my_championships($name_circuit){
	/*Pre: - */			
		if(isset($_SESSION["user"])){			
			$nick_session = $_SESSION["user"]; 
			
			global $connection;
			
			$query =  	"select c.name
								from 	user u,
											inscription i,
											championship c";

			if($name_circuit!=null) $query =$query.", circuit ci,	circuit_championship cc";

			$query = $query." where u.nick = '$nick_session'
								and  u.id_user = i.id_user
								and  i.active <> 0
								and  i.id_champ = c.id_champ";
			
			if($name_circuit!=null) $query =$query." and c.id_champ = cc.id_champ and cc.id_circuit = ci.id_circuit and ci.name = '$name_circuit'";
			
			$result_query = mysql_query($query, $connection) or my_error('GET_MYTEAM-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
			$arr = extract_rows($result_query);
			$res = array();
			foreach ($arr as $row) {
				$res[] = $row->name;
			}
			return $res;		
		}		
	}
	/*Post: Devuelve una array de strings con los nombres de los campeonatos a los que esta inscrito el usuario logueado y que ademas contienen el circuito de la entrada. En caso de que el usuario no este logueado o no exista el circuito de la entrada dentro del campeonato, retorna una array vacia*/
	
	
	
	
	/*Esta función borra el campeonato de la BBDD con el mismo identificador que le pasamos en la entrada
			- id: Identificador del campeonato
	*/
	function delete_championship_id($id){
	/*Pre: - */
		global $connection;
		
	    $query = "DELETE FROM championship WHERE id_champ = '$id'";
		$result_query = mysql_query($query, $connection) or my_error(mysql_errno($connection).": ".mysql_error($connection), 1);
	}
	/*Post: La función borra el circuito de la BBDD*/
	
	
	
	/*La función comprueba si el nombre del campeonato de la entrada existe en la BBDD
			- name: Nombre del campeonato
	*/
	function exist_championship($name){	
	/*Pre: - */
		global $connection;
		
		$query =  "SELECT * FROM championship WHERE name = '$name'";	
		$result_query = mysql_query($query, $connection) or my_error('EXIST_CHAMPIONSHIP-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		if (extract_num_rows($result_query)==0)	return false;
		else return true;
	}
	/*Post: Devuelve cierto en caso de que el nombre del campeonato exista, en caso contrario devuelve falso*/

?>
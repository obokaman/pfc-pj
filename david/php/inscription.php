<?php

	/*La función crea una instancia que relaciona un usuario con un campeonato mediante sus identificadores*/
	function create_inscription($id_user, $id_champ, $active){
	/*Pre: - */	
		global $connection;
		
	    $query = "INSERT INTO inscription ( id_user, id_champ, active)
						VALUES ('$id_user',' $id_champ', '$active' )";
						
		if (!mysql_query($query, $connection)) {
			my_error('CREATE_INSCRIPTION-> '.mysql_errno($connection).": ".mysql_error($connection), 1);				
			return false;
		}else	return true;		
	}
	/*Post: La función nos retorna cierto en caso de que haya tenido exito la creacion de la nueva instancia entre el usuario y el campeonato, en caso contrario devuelve falso*/
	
	
	/*La función nos devuelve la lista de todos los usuarios y campeonatos que estan relacionados que estan almacenados en la BBDD*/
	function get_inscriptions(){
	/*Pre: - */	
		global $connection;
		$query =  "SELECT * FROM inscription";
		$result_query = mysql_query($query, $connection) or my_error('GET_INSCRIPTIONS-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
				
		return(extract_row($result_query));	
	}
	/*Post: La función nos devuelve una array de objetos de todos los usuarios junto con los campeonatos a los que estan inscritos y el estado que estan almacenados en la base de datos*/

	
	/*La función nos devuelve el identificador de los campeonatos a los que esta inscrito el identificador del usuario de la entrada */
	function get_champs_of_user($id){
	/*Pre: - */
		global $connection;
		$query =  "SELECT id_champ FROM inscription WHERE id_user = '$id'";
		$result_query = mysql_query($query, $connection) or my_error('GET_CHAMPS_OF_USER-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

				
		return(extract_row($result_query));
	}
	/*Post: Retorna una array de los identificadores de campeonatos a los que esta inscrito el usuario de la entrada*/
	
	
	/*La función nos devuelve la lista de usuarios que estan inscritos a un campeonato en concreto, la información la retorna*/
	function get_users_of_champ($id){
	/*Pre: - */
		global $connection;
		$query =  "SELECT id_user FROM inscription WHERE id_champ = '$id'";
		$result_query = mysql_query($query, $connection) or my_error('GET_USERS_OF_CHAMP-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

		return(extract_row($result_query));
	}
	/*Post: Retorna una array de identificadores de usuarios que estan inscritos al identificador de campeonato de la entrada*/
	
	
	/*Devuelve el estado de un usuario en un campeonato*/
	function get_status_inscription($id_user, $id_champ){
	/*Pre: - */
		global $connection;
		
	    $query = "SELECT active FROM inscription WHERE id_user='$id_user' AND id_champ='$id_champ'";
		$result_query = mysql_query($query, $connection) or my_error('GET_STATUS_INSCRIPTION-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

		return( extract_row($result_query)->active );
		
	}
	/*Post: La función nos retorna el valor del estado del usuario en el campeonato, en caso de que no exista relación entre el usuario y el campeonato se retorna valor nulo*/
	
	/*La función devuelve el numero de inscripciones pendientes que tiene el usuario de la entrada*/
	function get_num_inscriptions_pendents($id_user){
	/*Pre: - */
		global $connection;
		
		$query = " SELECT COUNT(*) AS num FROM inscription i WHERE i.id_user = '$id_user' AND i.active = 0";
		$result_query = mysql_query($query, $connection) or my_error('GET_NUM_INSCRIPTIONS_PENDENTS-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		return( extract_row($result_query)->num );
		
	}
	/*Post: Devuelve el numero de inscripciones pendientes que tiene el usuario de la entrada, en caso de que el usuario no exista devuelve 0
	inscripciones*/
	
	
	/*La funcion devuelve una lista de las inscripciones con el nick del fundador y el nombre del campeontao que tiene pendientes el usuario logueado */
	function get_championships_invited(){
	/*Pre: El usuario esta logueado*/
		if ( isset( $_SESSION[ 'user' ] ) ){
				global $connection;
				
				$id_user_session = get_id_user( $_SESSION[ 'user' ] );			
				
				$query = "SELECT ( SELECT u.nick FROM user u WHERE u.id_user = a. id_founded ) as nick, a.name as name
								FROM ( SELECT c.id_founded, c.name
											FROM 	inscription i,
														championship c
											WHERE i.id_user = '$id_user_session'
											AND i.active = 0
											AND c.id_champ = i.id_champ) a";
					
				$result_query = mysql_query($query, $connection) or my_error('GET_CHAMPIONSHIPS_INVITED-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
				
				return extract_rows( $result_query );
				
				return $id_user_session;
		} else return null;
	}
	/*Post: Devuelve una array de objetos con el par nick y name donde el primero es el nick del fundador del campeonto al que el usuario logueado esta pendiente y el nombre de dicho campeonato*/
	
	
	/*Esta función nos modifica el estado de una instancia entre un usuario y un campeonato al que esta inscrito*/
	function set_inscription_status($id_user, $id_champ, $active){
	/*Pre: - */
		global $connection;
		
		$query = "UPDATE inscription SET active='$active' WHERE id_user='$id_user' AND id_champ='$id_champ'";
		
		if (!mysql_query($query, $connection)) {
			my_error('SET_INSCRIPTION_STATUS-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
			return false;
		}else return true;	
		
	}
	/*Post: Devuelve cierto si ha realizado la modificacion correctamente, en caso contrario devuelve falso*/
	
	
	/*Esta función elimina la instancia en la que esta relacionado un usuario y un campeonato */
	function delete_inscription($id_user, $id_champ){
	/*Pre: - */
		global $connection;
		
	    $query = "DELETE FROM inscription WHERE id_user = '$id_user' and id_champ='$id_champ'";

		$result_query = mysql_query($query, $connection) or my_error('DELETE_INSCRIPTION-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

	}
	/*Post: La función elimina la instancia en la que aparece relacionado el usuario y el campeonato de la entrada mediante sus identificadores*/
	
	
	/*La función comprueba si hay una instancia que relacione el usuario con el campeonato de la entrada*/
	function exist_inscription($id_user, $id_champ){	
	/*Pre: - */
		global $connection;
		$query =  "SELECT * FROM inscription WHERE id_user = '$id_user' AND id_champ='$id_champ'";
	
		$result_query = mysql_query($query, $connection) or my_error('EXIST_INSCRIPTION-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		if (extract_num_rows($result_query)==0)	return false;
		else return true;
	}
	/*Post: Devuelve cierto en caso de que el identificador del usuario y del campeonato existe, en caso contrario devuelve falso*/
?>
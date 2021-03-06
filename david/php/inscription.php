<?php

	/*La función crea una instancia que relaciona un usuario con un campeonato mediante sus identificadores
			- id_user: Identificador del usuario
			- id_champ: Identificador del campeonato
			- active: Entero que indica el estado de la inscripcion
	*/
	function create_inscription($id_user, $id_champ, $active){
	/*Pre: Los identificadores deben existir y active puede tomar unicamente los valores 0 o 1 */	
		global $connection;
		
	    $query = "INSERT INTO inscription ( id_user, id_champ, active)
						VALUES ('$id_user',' $id_champ', '$active' )";
		if (!mysql_query($query, $connection)) {
			my_error('CREATE_INSCRIPTION-> '.mysql_errno($connection).": ".mysql_error($connection), 1);				
			return false;
		}else	return true;		
	}
	/*Post: La función nos retorna cierto en caso de que haya tenido exito la creacion de la nueva instancia entre el usuario y el campeonato, en caso contrario devuelve falso*/

	
	/*La función nos devuelve el identificador de los campeonatos a los que esta inscrito el identificador del usuario de la entrada 
			- id: Identificador del usuario
	*/	
	function get_champs_of_user($id){
	/*Pre: El identificador del usuario no es nulo */
		global $connection;
		
		$query =  "SELECT id_champ FROM inscription WHERE id_user = '$id' AND active=1";
		$result_query = mysql_query($query, $connection) or my_error('GET_CHAMPS_OF_USER-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

		return(extract_row($result_query));
	}
	/*Post: Retorna una array de enteros con los identificadores de campeonatos a los que esta inscrito el usuario de la entrada*/
	
	
	/*La función nos devuelve la lista de usuarios de los que estan inscritos a un campeonato identificado por el parametro de la entrada
			- id: Identificador del campeonato
	*/
	function get_users_of_champ($id){
	/*Pre: El identificador del campeonato debe existir*/
		global $connection;
		
		$query =  "SELECT id_user FROM inscription WHERE id_champ = '$id' AND active=1";
		$result_query = mysql_query($query, $connection) or my_error('GET_USERS_OF_CHAMP-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

		return(extract_row($result_query));
	}
	/*Post: Retorna una array de enteros con los identificadores de usuarios que estan inscritos al identificador de campeonato de la entrada*/
	
	
	/*Devuelve el estado de un usuario en un campeonato
			- id_user: Identificador del usuario
			- id_champ: Identificador el campeonato
	*/
	function get_status_inscription($id_user, $id_champ){
	/*Pre: Los parametros de entrada no  son nulos */
		global $connection;
		
	    $query = "SELECT active FROM inscription WHERE id_user='$id_user' AND id_champ='$id_champ'";
		$result_query = mysql_query($query, $connection) or my_error('GET_STATUS_INSCRIPTION-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

		return( extract_row($result_query)->active );
	}
	/*Post: La función nos retorna el valor del estado del usuario en el campeonato, en caso de que no exista relación entre el usuario y el campeonato se retorna valor nulo*/
	
	
	
	/*La función devuelve el numero de inscripciones pendientes que tiene el usuario de la entrada
			- id_user: Identificador del usuario
	*/
	function get_num_inscriptions_pendents($id_user){
	/*Pre: El identificador del usuario no es nulo */
		global $connection;
		
		$query = " SELECT COUNT(*) AS num FROM inscription i, championship c WHERE i.id_user = '$id_user' AND i.active = 0 AND c.id_champ = i.id_champ AND c.data_limit >= now()";
		$result_query = mysql_query($query, $connection) or my_error('GET_NUM_INSCRIPTIONS_PENDENTS-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		return( extract_row($result_query)->num );
	}
	/*Post: Devuelve un entero que representa el numero de inscripciones pendientes que tiene el usuario de la entrada, en caso de que el usuario no exista devuelve 0	inscripciones*/
	
	
	
	/*La funcion devuelve una lista de las inscripciones con el nick del fundador y el nombre del campeonato que tiene pendientes el usuario logueado */
	function get_championships_invited(){
	/*Pre: - */
		if ( isset( $_SESSION[ 'user' ] ) ){
				global $connection;
				
				$id_user_session = get_id_user( $_SESSION[ 'user' ] );							
				$query = "SELECT ( SELECT u.nick FROM user u WHERE u.id_user = a. id_founded ) as nick, a.name as name
								FROM ( SELECT c.id_founded, c.name
											FROM 	inscription i,
														championship c
											WHERE i.id_user = '$id_user_session'
											AND i.active = 0
											AND c.id_champ = i.id_champ
											AND c.data_limit >= now()) a";					
				$result_query = mysql_query($query, $connection) or my_error('GET_CHAMPIONSHIPS_INVITED-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
				
				return extract_rows( $result_query );
		} else return null;
	}
	/*Post: Devuelve una array de objetos con el par nick y name donde el primero es el nick del fundador del campeonto al que el usuario logueado esta pendiente y el nombre de dicho campeonato*/
	
	
	/*Esta función nos modifica el estado de una instancia de las inscripciones entre un usuario y un campeonato al que esta inscrito
			- id_user: Identificador de usuario
			- id_champ: Identificador del campeonato
			- active: Entero con el nuevo valor del estado de la inscripcion
	*/
	function set_inscription_status($id_user, $id_champ, $active){
	/*Pre: Los identificadores deben existir y la invitación no ha pasado la fecha limite para inscribirse del campeonato*/
		global $connection;
		
		$query = "UPDATE inscription SET active='$active' WHERE id_user='$id_user' AND id_champ='$id_champ'";		
		if (!mysql_query($query, $connection)) {
			my_error('SET_INSCRIPTION_STATUS-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
			return false;
		}else return true;			
	}
	/*Post: Devuelve cierto si ha realizado la modificacion correctamente, en caso contrario devuelve falso*/
	
	
	
	/*Esta función elimina la instancia en la que esta relacionado un usuario y un campeonato 
			- id_user: Identificador de usuario
			- id_champ: Identificador del campeonato	
	*/
	function delete_inscription($id_user, $id_champ){
	/*Pre: - */
		global $connection;
		
	    $query = "DELETE FROM inscription WHERE id_user = '$id_user' and id_champ='$id_champ'";
		$result_query = mysql_query($query, $connection) or my_error('DELETE_INSCRIPTION-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
	}
	/*Post: La función elimina la instancia en la que aparece relacionado el usuario y el campeonato de la entrada mediante sus identificadores*/
	
	
	/*La función comprueba si hay una instancia que relacione el usuario con el campeonato de la entrada
			- id_user: Identificador de usuario
			- id_champ: Identificador del campeonato
	*/
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
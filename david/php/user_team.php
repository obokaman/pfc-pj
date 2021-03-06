<?php

	/*La función crea una instancia que relaciona un usuario con un equipo mediante sus identificadores
			- id_user: Identificador de usuario
			- id_team: Identificador del campeonato	
			- active: Entero que representa el valor del estado del usuario respecto al equipo
	*/
	function add_user_team($id_user, $id_team, $active){
	/*Pre: Los parametros de entrada no  son nulos  y el  parametro 'active' contiene el valor 0 o 1*/
		global $connection;
		
	    $query = "INSERT INTO user_team ( id_user, id_team, active) VALUES ('$id_user',' $id_team', '$active')";
		if (!mysql_query($query, $connection)) {
			my_error('ADD_USER_TEAM->  '.mysql_errno($connection) . ": " . mysql_error($connection), 1);
			return false;
		}else return true;
	}
	/*Post: La función nos retorna cierto en caso de que haya tenido exito la creacion de la nueva instancia entre el usuario y el equipo, en caso contrario devuelve falso*/

	
	
	/*La función nos devuelve el identificador de los equipos a los que pertenece el identificador del usuario de la entrada
			- id: Identificador del usuario
	*/
	function get_teams_of_user($id){
	/*Pre: El identificador del equipo no es nulo */
		global $connection;
		
		$query =  "SELECT id_team FROM user_team WHERE id_user = '$id' AND active = 1";
		$result_query = mysql_query($query, $connection) or  my_error('GET_TEAMS_OF_USER-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

		return(extract_rows($result_query)); 
	}
	/*Post: Retorna una array de enteros con los identificadores de equipo a los que pertence el usuario de la entrada*/
	
	
	/*La función nos devuelve la lista de identificadores de usuarios que pertenecen al equipo con el mismo identificador que el recibido en la entrada de la función
			- id: Identificador del equipo
	*/
	function get_users_of_team($id){
	/*Pre: El identificador del equipo no es nulo */
		global $connection;
		
		$query =  "SELECT id_user FROM user_team WHERE id_team = '$id' AND active = 1";
		$result_query = mysql_query($query, $connection) or  my_error('GET_USERS_OF_TEAM-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		return(extract_rows($result_query)); 		
	}
	/*Post: Retorna una array de enteros con los identificadores de usuarios que pertenecen al identificador de equipo de la entrada*/
	
	
	/*La función devuelve el numero de peticiones pendientes que tiene el usuario de la entrada
			- id_user: Identificador del usuario
	*/
	function get_num_userteam_pendents($id_user){
	/*Pre: - */
		global $connection;
		
		$query = " SELECT COUNT(*) AS num FROM user_team ut WHERE ut.id_user = '$id_user' AND ut.active = 0";
		$result_query = mysql_query($query, $connection) or my_error('GET_NUM_USERTEAM_PENDENTS-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		return( extract_row($result_query)->num );		
	}
	/*Post: Devuelve el numero de peticiones pendientes que tiene el usuario de la entrada, en caso de que el usuario no exista devuelve 0*/
	
	
	
	/*Devuelve el estado de un usuario en un equipo
			- id_user: Identificador del usuario	
			- id_team: Identificador del equipo
	*/
	function get_status($id_user, $id_team){
	/*Pre: Los parametros de la entrada no son nulos */
		global $connection;
		
	    $query = "SELECT active FROM user_team WHERE id_user='$id_user' AND id_team='$id_team'";
$result_query = mysql_query($query, $connection) or my_error('GET_STATUS-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		return( extract_row($result_query)->active );
	}
	/*Post: La función nos retorna el valor del estado del usuario en el equipo, en caso de que no exista relación entre el usuario y el equipo se retorna valor nulo*/
	
	
	/*La funcion devuelve una lista donde cada elemento contiene: el nick del fundador y el nombre de los equipos que tiene pendientes el usuario logueado */
	function get_teams_invited(){
	/*Pre: - */
		if ( isset( $_SESSION[ 'user' ] ) ){
				global $connection;
				
				$id_user_session = get_id_user( $_SESSION[ 'user' ] );							
				$query = "SELECT ( SELECT u.nick FROM user u WHERE u.id_user = a. id_founded ) as nick, a.name as name
								FROM ( SELECT t.id_founded, t.name
											FROM 	user_team ut,
														team t
											WHERE ut.id_user = '$id_user_session'
											AND ut.active = 0
											AND t.id_team = ut.id_team) a";					
				$result_query = mysql_query($query, $connection) or my_error('GET_CHAMPIONSHIPS_INVITED-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
				
				return extract_rows( $result_query );
		} else return null;
	}
	/*Post: Devuelve una array de objetos con el par nick y name donde el primero es el nick del fundador del equipo al que el usuario logueado esta pendiente y el nombre de dicho equipo*/
	
	
	
	/*Esta función nos modifica el estado de una instancia entre un usuario y un equipo
			- id_user: Identificador de usuario
			- id_team: Identificador del campeonato	
			- active: Entero que representa el valor del estado del usuario respecto al equipo
	*/
	function set_user_team_status($id_user, $id_team, $active){
	/*Pre: Los identificadores no son nulos y active contiene los valores 0 o 1*/
		global $connection;
		
		$query = "UPDATE user_team SET active='$active' WHERE id_user='$id_user' AND id_team='$id_team'";		
		if (!mysql_query($query, $connection)) {
			my_error('SET_USER_TEAM_STATUS-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
			return false;
		}else	return true;
	}
	/*Post: Devuelve cierto si ha realizado la modificacion correctamente, en caso contrario devuelve falso*/
	
	
	/*Esta función elimina la instancia en la que esta relacionado un usuario y un equipo 
			- id_user: Identificador de usuario
			- id_team: Identificador del campeonato	
	*/
	function delete_user_team($id_user, $id_team){
	/*Pre: - */
		global $connection;
		
	    $query = "DELETE FROM user_team WHERE id_user = '$id_user' and id_team='$id_team'";
		$result_query = mysql_query($query, $connection) or  my_error('DELETE_USER_TEAM-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
	}
	/*Post: La función elimina la instancia en la que aparece relacionado el usuario y el equipo de la entrada mediante sus identificadores*/
	
	
	/*La función comprueba si hay una instancia que relacione el usuario con el equipo de la entrada
			- id_user: Identificador de usuario
			- id_team: Identificador del campeonato	
	*/
	function exist_user_team($id_user, $id_team){	
	/*Pre: Los parametros de entrada no son nulos*/
		global $connection;
		
		$query =  "SELECT * FROM user_team WHERE id_user = '$id_user' AND id_team='$id_team'";	
		$result_query = mysql_query($query, $connection) or  my_error('EXIST_USER_TEAM-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		if (extract_num_rows($result_query)==0)	return false;
		else return true;
	}
	/*Post: Devuelve cierto en caso de que el identificador del usuario y del equipo existe, en caso contrario devuelve falso*/
?>
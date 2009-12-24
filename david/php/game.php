<?php

	/*Esta función añade a la BBDD una nueva partida formada por el identificador del usuario que la realiza, el identificador del circuito donde se realiza, el campeonato al que pertence dicha partida, el tiempo realizado en la partida y la fecha y hora de la insercion de esta partida
			- id_user: Identificador del usuario
			- id_circuit: Identificador del circuito
			- id_champ: Identificador del campeonato
			- time: Tiempo conseguido en la partida en el circuito indicado por el usuario
			- time_insertion: Fecha y hora de la partida cuando ha sido realizada
*/
	function create_game_with_champ($id_user, $id_circuit, $id_champ, $time, $time_insertion){
	/*Pre: Los identificadores de la entrada de la función existen, y el circuito pertenece al campeonato que se nos indica
    en la entrada */	
		global $connection;

		$query = "INSERT INTO game ( id_user, id_circuit, id_champ, time_result, time_insertion) VALUES ('$id_user',' $id_circuit', '$id_champ', '$time', '$time_insertion')";
		$result_query = mysql_query($query, $connection);
		
		if (!$result_query) {
			my_error('CREATE_GAME_WITH_CHAMP-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
			return false;
		}else	return true;
	}
	/*Post: La función nos retorna cierto en caso de que haya tenido exito la creacion de la nueva partida, en caso contrario devuelve falso*/
	

	/*Esta función añade a la BBDD una nueva partida formada por el identificador del usuario que la realiza, el identificador del circuito donde se realiza, el tiempo realizado en la partida y la fecha y hora de la insercion de esta partida
			- id_user: Identificador del usuario
			- id_circuit: Identificador del circuito
			- time: Tiempo conseguido en la partida en el circuito indicado por el usuario
			- time_insertion: Fecha y hora de la partida cuando ha sido realizada
	*/
	function create_game($id_user, $id_circuit, $time, $time_insertion){
	/*Pre: Ninguno de los valores de la entrada son nulos y los identificadores de usuari y de circuito deben existir*/	
		global $connection;

		$query = "INSERT INTO game ( id_user, id_circuit, time_result, time_insertion) VALUES ('$id_user',' $id_circuit', '$time', '$time_insertion')";	
		$result_query = mysql_query($query, $connection);
		
		if (!$result_query) {
			my_error('CREATE_GAME-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
			return false;
		}else	return true;			
	}
	/*Post: La función nos retorna cierto en caso de que haya tenido exito la creacion de la nueva partida, en caso contrario devuelve falso*/
	
	
	/*La función nos devuelve el identificador a partir del identificador de un usuario y la fecha de insercion de la partida
			- id_user: Identificador de la partida
			- time_insertion: Fecha y hora de la insercion de la partida en la BBDD
	*/
	function get_id_by_date_user($id_user, $time_insertion){
	/*Pre: Ninguno de los dos valores puede ser nulo y el identificador de usuario debe de existir*/
		global $connection;
		
		$query =  "SELECT g.id_game AS id FROM game g WHERE g.id_user = '$id_user' AND g.time_insertion = '$time_insertion'";
		$result_query = mysql_query($query, $connection) or my_error('GET_ID_BY_DATE_USER-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		return(extract_row($result_query)->id );	
	}
	/*Post: Retorna un entero que es el identificador de la partida a partir de los parametros de la entrada de la funcion*/
	
	
	/*Esta función elimina la partida a partir del identificador de la partida de la entrada
			-  id_game: Identificador de la partida
	*/
	function delete_game($id_game){
	/*Pre: - */
		global $connection;
		
	    $query = "DELETE FROM game WHERE id_game = '$id_game'";
		$result_query = mysql_query($query, $connection) or my_error('DELETE_GAME-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
	}
	/*Post: La función elimina la partida*/
	
	
	/*La función comprueba si hay una partida con el identificador de la entrada
			- id_game: Identificador de  la partida
	*/
	function exist_game($id_game){	
	/*Pre: - */
		global $connection;
		
		$query =  "SELECT * FROM game WHERE id_game = '$id_game'";	
		$result_query = mysql_query($query, $connection) or my_error('EXIST_GAME-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		if (count(extract_row($result_query))==0)	return false;
		else return true;
	}
	/*Post: Devuelve cierto en caso de que el identificador de la partida exista, en caso contrario devuelve falso*/

	
	
	
	function getRankings($circuit, $team, $championship, $page,  $sizepage){
		
			//Inicializamos los valores
			global $connection;			
			
			class obj{
				public $page = 0;
				public $numpages = 0;
				public $data = array();
			}
			$max = 50;	
			
			$result = new obj;
			
			if ($sizepage!=0)	$max=$sizepage;
			
			//Creamos la query, filtrando los parametros de la entrada
			$query = "select u.nick, g.time_result from 	circuit c, game g,";			
			if ($championship!=null) 	$query = $query. "championship ch,";			
			if ($team!=null) 	$query = $query." team t,	user_team ut,";			
			$query = $query."user u  where u.id_user = g.id_user	and 	c.name = '$circuit' and 	c.id_circuit = g.id_circuit";			
			if ($team!=null) 	$query = $query." and  t.name = '$team'	and 	t.id_team = ut.id_team and 	ut.id_user = u.id_user";			
			if ($championship!=null) 	$query = $query. " and 	ch.name = '$championship' and 	ch.id_champ = g.id_champ";			
			$query = $query." order by g.time_result, g.id_user" ;		
			
			$result_query = mysql_query($query, $connection) or my_error('GET_RANKINGS-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
			
			 //numero total de partidas(filas en la bae de datos)
			$numgames = extract_num_rows($result_query);
			if ($numgames == 0) $result->numpages = 0;
			else $result->numpages = intval(  ($numgames-1)/$max  ) +1;	

			//Comprobamos que el resultado de la consulta no sea nulo y si la pagina que se pide no es mas grande que el total maximo de paginas que hay
			if((extract_num_rows($result_query) != 0)&&($page <= $result->numpages)){							
				
					if ($page == 0){
							//El usuario esta logueado
							if (isset($_SESSION['user'])){	
									$games = extract_rows($result_query);		
									
									$pos = 0;
									$b = true;
									while (($pos < count($games))&&($b)){						
										if ($games[$pos]->nick  == $_SESSION['user'])	$b = false;
										else $pos++;
									}						
									$result->page =intval($pos / $max)+1;
									$result->data =  extract_interval_rows($result_query, (($result->page-1)*$max), $max);
								
							//El usuario no esta logueado, devolvemos la primera pagina
							}else{
									$result->data =  extract_interval_rows($result_query, 0, $max);
									$result->page = 1;						
							}
						
					}else{//Devolvemos la pagina en el caso de que page sea diferente de 0					
							//El segundo parametros debe estar en un intervalo entre 0 y n ya que es el rango que sigue el numero de las filas en la bd
							$games = extract_interval_rows($result_query, (($page-1)*$max), $max);		
							$result->data =  $games;
							$result->page = intval($page);
					}
					
			}else{
					$result->page = intval($page);
					$result->data = Array();					
			}
			
			return $result;		
	}
	
	
	
	function get_trace_fragment( $id_game, $start_byte, $length ){
		
		global $path;
		
		$game = $path['games'].$id_game.'/trace.out';
		//$game = "/etc/passwd";
		
		class result{
			public $read_bytes = 0;
			public $data = "";
		}
		
		$result = new result;
		
		$size_file = filesize($game);
		
		if ($length == -1) $length = $size_file;
		
		if ($size_file >=  $start_byte ) {		
			$handle = fopen( $game, "r" );		
			fseek( $handle, $start_byte);
			
			$result->data = fread( $handle, $length );
			
			if ( filesize($game) >= ($start_byte + $length) ) $result->read_bytes = $length;
			else $result->read_bytes = filesize($game) - $start_byte;
		}
		return $result;
	}
?>
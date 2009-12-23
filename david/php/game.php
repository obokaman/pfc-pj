<?php

	/*La función crea una partida que nos relaciona, mediante identificadores, el usuario que la realiza, el circuito y el campeonato al que     
   participa, finalmente se nos indica el tiempo que el usuario ha estado*/
	function create_game_with_champ($id_user, $id_circuit, $id_champ, $time){
	/*Pre: Los identificadores de la entrada de la función existen, y el circuito pertenece al campeonato que se nos indica
    en la entrada */	
		global $connection;

		$query = "INSERT INTO game ( id_user, id_circuit, id_champ, time_result, time_insertion) VALUES ('$id_user',' $id_circuit', '$id_champ', '$time', NOW())";
	
		if (!mysql_query($query, $connection)) {
			my_error('CREATE_GAME_WITH_CHAMP-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
			return false;
		}else{	
			return true;
		}
	}
	/*Post: La función nos retorna cierto en caso de que haya tenido exito la creacion de la nueva partida, en caso contrario devuelve falso*/
	
	/*La función crea una partida que nos relaciona, mediante identificadores, el usuario que la realizay  el circuito sin tener en cuenta el campeonato al que participa, finalmente se nos indica el tiempo que el usuario ha estado*/
	
	
	function create_game($id_user, $id_circuit, $time, $time_insertion){
	/*Pre: Los identificadores de la entrada de la función existen */	
		global $connection;

		$query = "INSERT INTO game ( id_user, id_circuit, time_result, time_insertion) VALUES ('$id_user',' $id_circuit', '$time', '$time_insertion')";
		
		$result_query = mysql_query($query, $connection);
		
		if (!$result_query) {
			my_error('CREATE_GAME-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
			return false;
		}else{	
			return true;			
		}
	}
	/*Post: La función nos retorna cierto en caso de que haya tenido exito la creacion de la nueva partida, en caso contrario devuelve falso*/
	
	
	/*La función nos devuelve la lista de todos las partidas realizadas que estan almacenados en la BBDD*/
	function get_games(){
	/*Pre: - */	
		global $connection;
		$query =  "SELECT * FROM game";
		$result_query = mysql_query($query, $connection) or my_error('GET_GAMES-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		return(extract_rows($result_query));		
	}
	/*Post: La función nos devuelve una array de todos los objetos partida realizadas que estan almacenados en la base de datos*/
	
	
	/*La función devuelve la información de una partida a partir del identificador de esta*/
	function get_game_id($id){
	/*Pre: - */
		global $connection;
		$query =  "SELECT * FROM game WHERE id_game = '$id'";
		$result_query = mysql_query($query, $connection) or my_error('GET_GAME_ID-> '.mysql_errno($connection).": ".mysql_error($connection), 1);

		return(extract_row($result_query));
	}
	/*Post: Retorna la información de una partida a partir del identificador de esta */
	
	
	function get_id_by_date_user($id_user, $time_insertion){
			
		global $connection;
		$query =  "SELECT g.id_game AS id FROM game g WHERE g.id_user = '$id_user' AND g.time_insertion = '$time_insertion'";
		$result_query = mysql_query($query, $connection) or my_error('GET_ID_BY_DATE_USER-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		return(extract_row($result_query)->id );	
	}
	
	
	
	/*Esta función nos modifica el tiempo que tiene la partida */
	function set_time($id_game, $time){
	/*Pre: - */
		global $connection;
		
		$query = "UPDATE game SET time_result='$time' WHERE id_game='$id_game'";
		
		if (!mysql_query($query, $connection)) {
			my_error('SET_TIME-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
			return false;
		}else{
			return true;
		}	
	}
	/*Post: Devuelve cierto si ha realizado la modificacion correctamente, en caso contrario devuelve falso*/
	
	
	/*Esta función elimina la partida*/
	function delete_game($id_game){
	/*Pre: - */
		global $connection;
		
	    $query = "DELETE FROM game WHERE id_game = '$id_game'";

		$result_query = mysql_query($query, $connection) or my_error('DELETE_GAME-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
	}
	/*Post: La función elimina la partida*/
	
	
	/*La función comprueba si hay una partida con el identificador de la entrada*/
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
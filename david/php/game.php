<?php

	/*Esta función añade a la BBDD una nueva partida formada por el identificador del usuario que la realiza, el identificador del circuito donde se realiza, el tiempo realizado en la partida y la fecha y hora de la insercion de esta partida
			- id_user: Identificador del usuario
			- id_circuit: Identificador del circuito
			- time: Tiempo conseguido en la partida en el circuito indicado por el usuario
			- time_insertion: Fecha y hora de la partida cuando ha sido realizada
	*/
	function create_game($id_user, $id_circuit, $id_champ, $time, $time_insertion){
	/*Pre: Ninguno de los valores de la entrada son nulos y los identificadores de usuari y de circuito deben existir*/	
		global $connection;
		
		if (!$id_champ)		$query = "INSERT INTO game ( id_user, id_circuit, time_result, time_insertion) VALUES ('$id_user',' $id_circuit', '$time', '$time_insertion')";	
		else		$query = "INSERT INTO game ( id_user, id_circuit, id_champ, time_result, time_insertion) VALUES ('$id_user',' $id_circuit', '$id_champ', '$time', '$time_insertion')";
		
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
	
	
	/*Función que retorna el tiempo realizado en una partida concreta
			- id_game: identificador de la partida
    */	
	function get_time_result($id_game){
	/*Pre: - */
		global $connection;
		
		$query =  "SELECT g.time_result AS time FROM game g WHERE g.id_game = '$id_game'";
		$result_query = mysql_query($query, $connection) or my_error('GET_ID_BY_DATE_USER-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
		
		return( (int)extract_row($result_query)->time );		
	}
	/*Post: Retorna un entero que representa los milisegundos que ha tardado la partida en ser realizada*/
	
	
	
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

	
	
	/*Esta funcion realiza la consulta obteniendo los resultados de las partidas realizadas para crear un ranking, este ranking estara divido en paginas. Este ranking tendra un filtraje que depende de varios valores como el circuito donde se realiza las partidas, el equipo que realiza las partidas o el campeonato. Las paginas que obtengamos del ranking es lo que nos devolvera la funcion. Para indicar la pagina que queramos  unicamente devolvemos un listado de elementos donde cada uno contiene el usuario y el tiempo realizado de la partida, la pagina que le corresponde y el numero total de paginas del ranking.
Si el parametro de entrada page es igual a 0 devolvemos la pagina a la que pertenece el nick logueado, en caso de que no haya un usuario logueado devolvemos la primera pagina. Si page es diferente de 0 se devuelve la pagina que se pide, y si la pagina es superior al numero total de paginas, devolvemos la ultima del ranking.

Parametros de entrada:

	- circuit: Nombre del circuito
	- team: Nombre del equipo 
	- championship: Nombre del campeonato
	- page: pagina que queremos del ranking
	- sizepage: Tamaño maximo de elementos que puede aparecer en una pagina del ranking
 */
	function getRankings($circuit, $team, $championship, $page,  $sizepage){
	/*Pre: El nombre del circuito no puede ser nulo*/
			//Inicializamos los valores
			global $connection;			
			
			//Clase que contendra la informacion que queremos devolver
			class obj{
				public $page = 0;
				public $numpages = 0;
				public $data = array();
			}
			$result = new obj;
			
			//El maximo de elementos que puede haber en una pagina es de 50 por defecto, en el caso que nos especifiquen una cantidad en concreto de elementos pasara a ser el nuevo maximo
			$max = 50;	
			if ($sizepage!=0)	$max=$sizepage;
			
			//Creamos la query, filtrando los parametros de la entrada
			$query = "select u.nick, g.time_result from 	circuit c, game g,";			
			if ($championship!=null) 	$query = $query. "championship ch,";			
			if ($team!=null) 	$query = $query." team t,	user_team ut,";			
			$query = $query."user u  where u.id_user = g.id_user	and g.id_user <> 1   and 	c.name = '$circuit' and 	c.id_circuit = g.id_circuit";			
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
			//En caso de pedir un pagina superior al numero maximo de paginas, retornamos la ultima pagina del ranking
			}else if ($page > $result->numpages){
					$result->page = $result->numpages;
					$result->data =  extract_interval_rows($result_query, (($result->page-1)*$max), $max);				
			}else{
					$result->page = intval($page);
					$result->data = Array();					
			}
			return $result;		
	}
	/*Post: La funcion nos devolvera 3 valores:
			- page: Un entero que corresponde a el numero de la pagina del ranking
			- numpages: Numero total de paginas que hay en el ranking realizado
			- data: Un array con los pares nick de usuario y tiempo realizado en una partida. Esta array como maximo puede contener sizepage elementos*/
	
	
	/*Funcion que nos devuelve fragmentos de un archivo de texto, en concreto, el trace.out (archivo de salida de realizar una partida), para ello tenemos que indicar el identificador de la partida, el byte de inicio de la lectura y la longitud que queremos leer
			- id_game: Identificador de la partida
			- start_byte: Byte de inicio de la lectura
			-length: Longitud de lectura
	*/
	function get_trace_fragment( $id_game, $start_byte, $length ){
	/*Pre: El identificador de la partida ha de existir*/	
		global $path;
		
		//Localizamos el fichero de lectura
		$game = $path['games'].$id_game.'/trace.out';
		
		//Creamos una clase que es la que devolveremos junto con la informacion de la lectura
		class result{
			public $read_bytes = 0;
			public $data = "";
		}		
		$result = new result;		
		//Tamaño del archivo a leer
		$size_file = filesize($game);		
		//Si length es igual a -1, la longitud de lectura sera de todo el fichero a leer
		if ($length == -1) $length = $size_file;		
		//Comprobamos que que el byte de inicio no sobresalga del tamaño del fichero de lectura
		if ($size_file >=  $start_byte ) {		
			$handle = fopen( $game, "r" );		
			//Situamos el puntero de lectura en el byte de inicio que hemos indicado
			fseek( $handle, $start_byte);			
			//Guardamos el contenido
			$result->data = fread( $handle, $length );
			//Guardamos el numero de bytes leidos
			if ( filesize($game) >= ($start_byte + $length) ) $result->read_bytes = $length;
			else $result->read_bytes = filesize($game) - $start_byte;
		}
		return $result;
	}
	/*Post: Devolvemos dos valores, read_bytes y data, donde el primero contiene el numero de bytes leidos por la función y el segundo contiene todo el contenido del fichero de lectura*/
?>
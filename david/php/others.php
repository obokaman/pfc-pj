<?php
    
	/*La funcion crea la clave de activacion para los usuarios*/
	function make_activationkey(){			
	/*Pre: -*/
		for($i = 0; $i < 20; $i++){			
			$s = $s.strval(mt_rand(0,9));			
		}		
		return $s;		
	}
	/*Post: La funcion devuelve un numero de 20 digitos creados aleatoriamente como clave de activacion*/
	
	
	/*La funcion mira en la base de datos el numero de filas de la consulta de la entrada*/
	function extract_num_rows($result_query){
	/*Pre: La query no es nula*/
		return mysql_num_rows($result_query);
	}
	/*Post: Devuelve un entero que representa el numero de filas de la consulta*/
	
	
	/*La  función saca todas las filas del resultado de la consulta SQL y los guarda en una array de objetos*/
	function extract_rows($result_query){
	/*Pre: La query no es nula*/		
		$arr = array();		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		return $arr;		
	}
	/*Post: Devuelve una array de objetos donde cada objeto representa una fila del resultado de la consulta SQL de la entrada*/
	
	
	/*La funcion devuelve una array de elementos, donde cada elemento representa un campo de la fila consultada*/
	function extract_array($result_query) {
	/*Pre: La query no es nula*/
		return mysql_fetch_array($result_query);
	}	
	/*Post: Devuelve una array de elementos que representa la fila del resultado de la consulta. Cada elemento de esta array representa un campo de la fila, y viene indexado en la array con el nombre del campo*/
	
	
	/*La funcion devuelve un objeto que representa una fila del resultado de la consulta*/
	function extract_row($result_query) {
	/*Pre: La query no es nula*/
		return mysql_fetch_object($result_query);
	}	
	/*Post: Devuelve un objeto que representa la fila del resultado de la consulta*/
	
	
	/*La función saca un intervalo de filas del resultado de la consulta*/
	function extract_interval_rows($result_query, $pos_ini, $length){
	/*Pre: La query no es nula y pos_ini y length son enteros positivos*/
		if (!mysql_data_seek($result_query, $pos_ini)) { //Posicionamos el puntero de la consulta en MySQL en la fila que queremos
			my_error('EXTRACT_INTERVAL_ROWS-> ERROR: Cannot seek to row $pos: ' . mysql_error() , 1); 
		}		
		$arr = array();		
		$i=1;
		while(($obj = mysql_fetch_object($result_query))&&($i <= ($pos+$length))) {
			$arr[] = $obj;
			$i++;
		}
		
		return $arr;	
	}
	/*Post: Devuelve una array de objetos que representan un intervalo de filas desde la posicion pos_ini hasta pos_ini+length*/
	
	
	/*Esta funciona retorna una array de string que hemos recibido todos juntos separados cada uno por espacios*/
	/*function extractArray($arr){
		if ($arr != "" ){	
				$var = Array();
				$last_pos = 0;
				$start_pos = 0;
				$b = true; 
				$i = 0;
				
				while ($b){		
						$start_pos = strpos($arr, " ", $last_pos);
						if ( $start_pos != false ) {
							$var[$i] = substr($arr, $last_pos, ($start_pos - $last_pos)  );
							$i++;
							
						}else{
							$var[$i] = substr($arr, $last_pos, (strlen($arr) - $last_pos) );
							$b = false;
						}
						$last_pos = $start_pos + 1;
				}
				
				return $var;
		}else{
				return null;
		}
	}*/
	/*Post: Retorna una array de string, donde cada posicion guarda la palabra separada por espacios*/
	
	
	/*Esta función se encarga de realizar la simulacion de la carrera en el circuito que se indica en la entrada a partir del codigo que le pasamos. Primero se crea un archivo temporal para realizar la simulacion en el servidor. En este directorio temporal ponemos todos los archivos necesarios para probar la simulacion. Tambien incluimos el codigo de la entrada en un fichero. Finalmente realizamos la llamada del simulador. Según el resultado que nos devuelva retornaremos un mensaje de error junto el codigo correspondiente del error. En caso de exito, añadimos la partida realiza a la base de datos junto con el tiempo realizado en la carrera simulada, seguidamente copiamos todos los archivos que habian en el directorio temporal en un directorio donde se mantendra guardado en el servidor. La función retornará un mensaje de que se ha realizado correctamente la simulacion, el codigo que retornara sera igual a 0 y el identificador de la partida
			- code: Codigo de la partida
			- circuit: Nombre del circuito			
			- champ: Nombre del campeonato
	*/
	function run( $code, $circuit, $champ ){
	/*Pre: El circuito existe en la base de datos*/
		global $path;
		
		//Creamoas el directorio temporal
		$dir_tmp = `mktemp -d`;			
		$dir_tmp = trim($dir_tmp);
		
		//En caso de que haya habido algun error en la creacion del directorio temporal
		if ( !$dir_tmp ) my_error( "RUN: No se ha podido crear el directorio temporal", 1 );
		
		//Obtenemos el short_name del circuito para conocer el nombre del fichero txt con la información sobre el circuito
		$short_name = get_short_name_circuit( get_id_circuit( $circuit) );
		
		//Copiamos el txt del circuito en el directorio temporal
		`cp ${path['circuits']}$short_name.txt $dir_tmp/circuit.txt`; 
		
		//Creamos un fichero 'studentcode.h' e insertamos el codigo de la partida que nos han pasado por la entrada
		$file = fopen($dir_tmp."/studentcode.h", "w");
		fwrite($file, $code);
		fclose($file); 
		
		//Copiamos todo el contenido del directorio pack (directorio que tenemos los archivos para poder ejecutar y simular el codigo de la entrada) en el directorio temporal
		`cp ${path['pack']}/* $dir_tmp`;	/**/

		chdir($dir_tmp);
		
		//Realizamos la llamada al archivo que realiza la ejecución y simulacion de la partida
		$res =`python run.py`;
		
		//Descodificamos el resultado en JSON y lo guardamos
		$obj_result = json_decode ($res, true);
		
		//En caso de que el resultado tenga un codigo negativo, entonces el la ejecución tiene errores y devolvemos el mensaje de error junto el codigo del error
		if($obj_result['code']<0) return $obj_result;
		
		//Leemos el fichero con el resultado de la ejecucion para obtener el tiempo tardado por la simulacion
		$file = fopen("trace.out", "r");
		$time = fscanf($file, "%d");
		fclose($file);
		
		if (isset($_SESSION['user'])) $id_user = get_id_user( $_SESSION['user'] );
		else $id_user = 1;
		
		//Obtenemos el dia y la fecha de la insercion de la partida (valor necesario para poder insertar el resultado de la partida en la BBDD)
		$time_insertion = date("y-m-d h:i:s");
		
		//Insertamos la partida en la base de datos, en caso de error nos mostrara un mensaje de error
		if (!create_game( $id_user, get_id_circuit($circuit), get_id_championship($champ), $time[0], $time_insertion)) my_error( "RUN: No se ha insertar la partida correctamente", 1 );
		
		//Obtenemos el identificar de la partida a partir del usuario que ha realizado la partida (usuario logueado) y el momento en que ha sido insertada la partida
		$id_game = get_id_by_date_user( $id_user, $time_insertion);
		
		//Creamos un nuevo directorio con el identificador de la partida
		mkdir ($path['games'].$id_game);
		
		//Copiamos todos los archivos del temporal al nuevo directorio anterior, estos archivos seran los necesarios para realizar la simulacion y los archivos del resultado de haber realizado la simulacion
		`cp $dir_tmp/* ${path['games']}/$id_game`;   /**/		
		
		$obj_result['id_game'] = (int)$id_game;
		$obj_result['time'] = get_time_result( $obj_result['id_game'] );
		
		return $obj_result;
	}
/*Post: Si la simulación se realiza con exito creamos un directorio donde se guarda todos los archivos necesarios de la simulacion junto con los resultados de esta, añadimos la partida realizada a la base de datos y retornamos el identificador de la partida, el codigo del error que es igual a 0, el mensaje de exito de la partida y el tiempo realizado por la partida. En caso de fallida, retornamos el codigo del error y un mensaje descriptivo del error, el resto de parametros en caso de éxito son nulos*/
	
	
	/*La función envia un correo a la dirección de correo del usuario con el enlace de activacion de la cuenta del usuario*/
	function send_mail($nick, $to){
	/*Pre: Los valores de la entrada no pueden ser nulos*/	
	
		$link = "http://gabarro.org/racing/JocProg?function=activated&nick=".$nick."&activation_key=". get_activation_key($nick) ;
		$subject = "Enlace de activación de la cuenta de usuario";
		$message = "Para activar su cuenta de usuario haga click en el siguiente enlace:\n
							<a href=".$link.">Activar aquí</a>";
	
		/*Enviamos el correo y retornamos el resultado del envío*/
		return(mail($to, $subject, $message) );		
	}
	/*Post: Envia un correo a la dirección de correo indicada en la entrada con la clave de activación para activar la cuenta del usuario*/
	
	
	
?>
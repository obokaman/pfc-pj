<?php
    
	/*La funcion crea la clave de activacion para los usuarios*/
	function make_activationkey(){			
	/*Pre: -*/
		for($i = 0; $i < 20; $i++){			
			$s = $s.strval(mt_rand(0,9));			
		}		
		return $s;		
	}
	/*Post: La funcion devuelve un numero de 20 digitos creados aleatoriamente*/
	
	
	/*La funcion mira en la base de datos el numero de filas de la consulta de la entrada*/
	function extract_num_rows($result_query){
	/*Pre: La query no es nula*/
		return mysql_num_rows($result_query);
	}
	/*Post: Devuelve un entero que representa el numero de filas de la consulta*/
	
	
	/*La  función saca todas las filas del resultado de la consulta SQL y los guarda en una array de objetos*/
	function extract_rows($result_query){
	/*{Pre:/*Pre: La query no es nula*/
		
		$arr = array();		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		return $arr;
		
	}
	/*Devuelve una array de objetos donde cada objeto representa una fila del resultado de la consulta SQL de la entrada*/
	
	
	/*La funcion devuelve un objeto que representa una fila del resultado de la consulta*/
	function extract_row($result_query) {
	/*Pre: La query no es nula y tiene una fila en el resultado*/
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
	function extractArray($arr){
	/*Pre: - */
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
	}
	/*Post: Retorna una array de string, donde cada posicion guarda la palabra separada por espacios*/
	
	/*La función crea la imagen según el tamaño que se le haya pasado por la entrada*/
	function get_circuit_image($name_circuit, $width, $height){
	/*Pre: El short_name del circuito ha de existir, junto con el ancho y la altura*/	
			global $path;
			
			$short_name = get_short_name_circuit( get_id_circuit( $name_circuit) );
			$name_image = $short_name."-".$width."x".$height.".png";
			if ( file_exists($path['images']."/".$name_image) )				return $name_image;
			$command = "convert ".$path['circuits'].$short_name.".png -background rgb\(128,255,128\) -resize ".$width."x".$height." -gravity center -extent ".$width."x".$height." ".$path['images']."/".$name_image;
			//my_error($command, 1);
			system($command);
			
			return $name_image;
			
	}
	/*Post: */
	
	
	function get_car_image($width, $height){
	/*Pre: */	
			
			$arr = Array ( 20, 24, 28, 32, 36, 40, 50 , 60 );
			
			//$name_image = $short_name."-".$width."x".$height.".png";
			$size = ( ( min( $width, $height))/1000 ) * 40;
			
			$aux = 99999;
			
			foreach ( $arr as $v ){
				if (abs($size - $v) < $aux ) {$aux = abs($size - $v) ; $res = $v;}				
			} 
			
			return "car-".$res.".gif";
	}
	
	
	function run( $code, $circuit ){
		
		global $path;
		
		$dir_tmp = `mktemp -d`;	
		
		echo trim($dir_tmp);
		
		$dir_tmp = trim($dir_tmp);
		
		if ( !$dir_tmp ) my_error( "RUN: No se ha podido crear el directorio temporal", 1 );
		
		$short_name = get_short_name_circuit( get_id_circuit( $circuit) );
		
		`cp ${path['circuits']}$short_name.txt $dir_tmp/circuit.txt`; 
		
		/*$file = fopen($dir_tmp."studentcode.h", "a+");
		fwrite($file, $code);
		fclose($file); */

		`cp ${path['pack']}/* $dir_tmp`;	/**/

		`cp /home/dvd/Escritorio/pfc-pj/omer/racing/src/studentcode.h $dir_tmp`;

		chdir($dir_tmp);
		$res =`python run.py`;
		
		$obj_result = json_decode ($res, true);
		
		if($obj_result['code']<0) return $obj_result;
		
		$file = fopen("trace.out", "r");
		$time = fscanf($file, "%d");
		//echo " ".$time[0]."//";
		fclose($file);
		
		$id_user = get_id_user( $_SESSION['user'] );
		
		$id_game = create_game( $id_user, get_id_circuit($circuit), $time);
		/*DEVOLVER EL IDENTIFICADOR DE LA PARTIDA QUE ACABAS DE INSERTAR,
		, TENIENDO EN CUENTA QUE NO INTERFIERA OTRO USUARIO QUE ACABA DE REALIZAR
		UNA INSERCION*/
		echo " -> ".$id_game;		
		
		
	}
?>
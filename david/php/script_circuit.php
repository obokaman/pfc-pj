<?php include("/var/www/conf/conf.php");?>
<?php include("/home/dvd/Escritorio/pfc-pj/david/php/includes.php");?>
<?php	
	/*Este script sirve para insertar los circuitos en la base de datos. La informacion de los circuitos viene dada por unos txt, dentro de estos archivos se extrae la informacion para porteriormente insertarla.*/

	//Fichero txt del circuito el cual queremos insertar
	$file_circuit = $argv[1] ;
	
	//Comprobamos que la extension sea un txt
	if (pathinfo($file_circuit, PATHINFO_EXTENSION) != "txt") {
		echo "La extension del  fichero es incorrecto\n";
		return 0;
	}
	
	global $path;
	$connection = open_connection();
	
	//Leemos la informacion del txt para poder insertar en circuito nuevo
	$handle = fopen($file_circuit, "r");
	$name_circuit = fscanf($handle, "#%s");	
	$dimension = fscanf($handle, "%d %d");
	fclose($handle);
	
	$short_name = basename($file_circuit, ".txt");
	
	echo $name_circuit[0]." ". $short_name." ".$dimension[0]." ".$dimension[1]."\n";
	
	$name = trim($name_circuit[0]);
	
	if( !exist_circuit($name) ){
		//Hacemos la llamada a la función para insertar el circuito en la base de datos y comprobamos el resultado
		if( !create_circuit( $name_circuit[0], $short_name, 1, 3, null, $dimension[0], $dimension[1]) )  echo "ERROR: No se ha podido insertar el circuto\n";
		else echo "Se ha insertado correctamente el circuito\n";
	}else echo "ERROR: El circuito insertado ya existe";
	
	close_connection($connection);
?>
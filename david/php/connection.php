<?php 
	
	function open_connection(){
/*			$dbhost="localhost";   		// Host del MySQL 
			$dbusuario="root"; 			// Usuario para acceder
			$dbpassword="proyecto"; 	// Password de acceso para el usuario
			$db="pfc";        					// Base de datos con la cual trabajar
			$connection = mysql_connect($dbhost, $dbusuario, $dbpassword);
			
			if (!$connection){
					die(my_error( 'OPEN_CONNECTION -> Could not connect: ' . mysql_error(), 1));
			}
			
			$db_selected = mysql_select_db($db, $connection);
			
			if (!$db_selected)
			{
					die (my_error('OPEN_CONNECTION -> Can\'t use test_db : ' . mysql_error(), 1));
			}
			return $connection;
*/
		global $connection;
		return $connection;
	
	} 
	
	function open_connection2(){
			$dbhost="localhost";   		// Host del MySQL 
			$dbusuario="root"; 			// Usuario para acceder
			$dbpassword="proyecto"; 	// Password de acceso para el usuario
			$db="pfc";        					// Base de datos con la cual trabajar
			$connection = mysql_connect($dbhost, $dbusuario, $dbpassword);
			
			if (!$connection){
					die(my_error( 'OPEN_CONNECTION -> Could not connect: ' . mysql_error(), 1));
			}
			
			$db_selected = mysql_select_db($db, $connection);
			
			if (!$db_selected)
			{
					die (my_error('OPEN_CONNECTION -> Can\'t use test_db : ' . mysql_error(), 1));
			}
			return $connection;
	
	} 
	
	//ARREGLAR ESTE APAÑO
	function close_connection($connection){
	
	}
	
	
	function close_connection2($connection){
			mysql_close($connection);
	}

?>
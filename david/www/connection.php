<?php 
	
	function open_connection(){
			$dbhost="localhost";   		// Host del MySQL 
			$dbusuario="root"; 			// Usuario para acceder
			$dbpassword="proyecto"; 	// Password de acceso para el usuario
			$db="pfc";        					// Base de datos con la cual trabajar
			$connection = mysql_connect($dbhost, $dbusuario, $dbpassword);
			
			if (!$connection){
					die('Could not connect: ' . mysql_error());
			}
			
			$db_selected = mysql_select_db($db, $connection);
			
			if (!$db_selected)
			{
					die ("Can\'t use test_db : " . mysql_error());
			}
			return $connection;
	} 
	
	function close_connection($connection){
			mysql_close($connection);
	}

?>
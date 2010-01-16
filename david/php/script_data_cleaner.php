<?php include("/var/www/conf/conf.php");?>
<?php include("/home/dvd/Escritorio/pfc-pj/david/php/includes.php");?>
<?php	
	/*Este script se encarga de limpiar los datos que sean innecesarios para la base de datos o algún directorio. Los dato que se limpiarán serán: 
			- Los usuarios no activados desde un mes de su inserción
			- Eliminar las invitaciones pendientes de los campeonatos despues de que pase la fecha limite de inscripcion del campeonato al que se invita
	*/
	
	global $path;
	$connection = open_connection();
	
	/*Limpieza de usuarios no activos desde un mes de su inserción*/
	
	$query = "SELECT id_user
					FROM user
					WHERE DATEDIFF(now(), date_insertion) > 30
					AND activated = 0";
	$result_query = mysql_query($query, $connection) or my_error('SCRIPT_DATA_CLEANER-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
	
	$arr = extract_rows($result_query);	
	
	for ($i = 0; $i < count($arr); $i++)		delete_user_id($arr[$i]->id_user);
	
	echo "Realizado el proceso de eliminación de los usuarios no activados desde hace un mes\n";
	
	/*Eliminación de las invitaciones de los campeonatos despues de que pase la fecha limite del campeonato al que estan inscritos los usuarios*/
	
	$query = "SELECT i.id_user, i.id_champ
					FROM inscription i,
							  championship c
					WHERE i.id_champ = c.id_champ
					AND i.active = 0
					AND c.data_limit < now()";
	$result_query = mysql_query($query, $connection) or my_error('SCRIPT_DATA_CLEANER-> '.mysql_errno($connection).": ".mysql_error($connection), 1);
	
	$arr = extract_rows($result_query);
	
	for ($i = 0; $i < count($arr); $i++)		delete_inscription($arr[$i]->id_user, $arr[$i]->id_champ);
	
	echo "Realizado el proceso de eliminación de las invitaciones pendientes de los campeonatos caducadas\n";
	
	
	close_connection($connection);
?>
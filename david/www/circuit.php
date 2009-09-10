<?php include("connection.php");?>

<?php
	
	function create_circuit($name, $short_name, $level, $n_laps, $time){
		
		$connection = open_connection();
		
	    $query = "INSERT INTO circuit (name, short_name, level, n_laps, time)
						VALUES ('$name', '$short_name', '$level', '$n_laps', '$time')";

		if (!mysql_query($query, $connection)) {
			echo "Ya existe un circuito con ese nombre";
		}else{
			echo "1 circuit added";
		}
		
		close_connection($connection);
		
	}

	function get_circuits(){
		
		$conexion = open_connection();
		$query =  "SELECT * FROM circuit";
		$result_query = mysql_query($query, $conexion) or die(mysql_error());
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		print(json_encode($arr)); 
		close_connection($conexion);
		
	}

	function get_circuit_id($id){
	
		$conexion = open_connection();
		$query =  "SELECT * FROM circuit WHERE id_circuit = '$id'";
		$result_query = mysql_query($query, $conexion) or die(mysql_error());
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		print( json_encode($arr)); 
		close_connection($conexion);
	}
	
	function get_circuit_name($name){
	
		$conexion = open_connection();
		$query =  "SELECT * FROM circuit WHERE name = '$name'";
		$result_query = mysql_query($query, $conexion) or die(mysql_error());
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		print( json_encode($arr)); 
		close_connection($conexion);
	}
	
	function get_circuit_short_name($short_name){
	
		$conexion = open_connection();
		$query =  "SELECT * FROM circuit WHERE short_name = '$short_name'";
		$result_query = mysql_query($query, $conexion) or die(mysql_error());
		
		$arr = array();
		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		
		print( json_encode($arr)); 
		close_connection($conexion);
	}
	
	function set_circuit($name, $level, $n_laps, $time){
		
		$connection = open_connection();
		
	    $query = "UPDATE circuit SET level= '$level', n_laps='$n_laps', time='$time' WHERE name = '$name'";

		if (!mysql_query($query, $connection)) {
			echo "No se ha podido modificar alguno de los parametros";
		}else{
			echo "OK";
		}
		
		close_connection($connection);
		
	}
	
	function delete_circuit_id($id){
		
		$connection = open_connection();
		
	    $query = "DELETE FROM circuit WHERE id = '$id'";

		if (!mysql_query($query, $connection)) {
			echo "No se ha podido borrar al usuario";
		}else{
			echo "OK";
		}
		
		close_connection($connection);
		
	}
	
	function delete_circuit_name($name){
		
		$connection = open_connection();
		
	    $query = "DELETE FROM circuit WHERE name = '$name";

		if (!mysql_query($query, $connection)) {
			echo "No se ha podido borrar al usuario";
		}else{
			echo "OK";
		}
		
		close_connection($connection);
		
	}
?>
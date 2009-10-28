<?php
    
	/*La  función recibe los resultados de las consultas SQL y los guarda en una array*/
	function extract_row($result_query){
	//{Pre: $result_query es el resultado de una consulta}	
		
		$arr = array();		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		return $arr;
		
	}
	/*Devuelve una array con los resultados de la consulta SQL de la entrada*/
?>
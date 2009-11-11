<?php
    
	/*La  función recibe los resultados de las consultas SQL y los guarda en una array*/
	function extract_rows($result_query){
	//{Pre: $result_query es el resultado de una consulta}	
		
		$arr = array();		
		while($obj = mysql_fetch_object($result_query)) {
			$arr[] = $obj;
		}
		return $arr;
		
	}
	/*Devuelve una array con los resultados de la consulta SQL de la entrada*/
	
	function extract_row($result_query) {
		return mysql_fetch_object($result_query);
	}
	
	function make_activationkey(){			
		
		for($i = 0; $i < 20; $i++){			
			$s = $s.strval(mt_rand(0,9));			
		}
		
		return $s;		
	}
	
	
	//CONVERSOR DE MICROSEGUNDOS A STRING EN FORMATO MINUTOS:SEGUNDO.MILISEGUNDOS
	
	
?>
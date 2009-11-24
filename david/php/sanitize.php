<?php

	/*La función limpia los parametros de entrada para que posteriormente funcionen correctamente*/
	function clean($var, $type){
		if(isset($_REQUEST[$var])){
			 $var = $_REQUEST[$var];
			 switch ( $type ) {
					case 'int':
						$var = (int) $var;
					break;
					case 'string':
						$var = trim ( $var );	
					break;
					case 'array':
						$var = explode(" ",$var);
					break;
					case 'date':
						$arr = Array();
						$arr = explode("/",$var);
						$var = date( "Y/m/d", mktime(0, 0, 0, $arr[1],$arr[0],$arr[2]) ); 
					break;
					case 'sql': 
						$var = trim ( $var );
						$var = mysql_real_escape_string($var); /*limpia el parametros evitando la inyección de codigo SQL
	ademas de colocar barras en caracteres especiales*/
					break;
					case 'email':
						$var = trim ( $var );
						$var = mysql_real_escape_string($var); /*limpia el parametros evitando la inyección de codigo SQL
	ademas de colocar barras en caracteres especiales*/
					break;
			}
			return $var;	
		}else{
			my_error('ERROR: A variable is not activated to clean', 1);
			return null;
		}
	}
	
	/*Envia la variable de la entrada en formato JSON*/
	function send($var){
		print(json_encode($var)); 
	}


	




?>
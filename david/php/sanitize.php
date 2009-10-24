<?php

	/*La función limpia los parametros de entrada para que posteriormente funcionen correctamente*/
	function clean($var, $type){
		if(isset($_REQUEST[$var])){
			 $var = $_REQUEST[$var];
			 switch ( $type ) {
					case 'int': // integer
						$var = (int) $var;
					break;
					case 'string': // trim string
						$var = trim ( $var );
						$var = mysql_real_escape_string($var); /*limpia el parametros evitando la inyección de codigo SQL
	ademas de colocar barras en caracteres especiales*/
					break;
			}
			return $var;	
		}else{
			my_error('ERROR: A variable is not activated to clean', 1);
		}
	}
	
	/*Envia la variable de la entrada en formato JSON*/
	function send($var){
		print(json_encode($var)); 
	}


	




?>
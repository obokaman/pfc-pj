<?php

	/*La función limpia los parametros de entrada para que posteriormente funcionen correctamente
			- var: Nombre de la variable REQUEST
			- type: Tipo de variable
	*/
	function clean($var, $type){
	/*Pre: - */
		//Comprobamos que el nombre de la variable aparezca en el REQUEST
		if(isset($_REQUEST[$var])){
			 $var = $_REQUEST[$var];
			//Filtramos el tipo de variable que representa, para hacerle el tratamiento correcto al valor de la variable 
			 switch ( $type ) {
					case 'int':
						$var = (int) $var;
					break;					
					case 'string':
						$var = trim ( $var );	//Quitamos los espacios de l principio y el final del contenido de la variable
						$var = mysql_real_escape_string( stripslashes($var));  /*limpia el parametros evitando la inyección de codigo SQL
	ademas de colocar barras en caracteres especiales*/
					break;
					case 'code':
						$var = trim ( $var );	
					break;
					case 'array':
						//my_error($var, 1);
						$var = mysql_real_escape_string( stripslashes($var)); /**/
						$var = explode("+",$var);
					break;
					case 'date':
						$arr = Array();
						$var = mysql_real_escape_string( stripslashes($var)); /**/
						$arr = explode("/",$var);
						$var = date( "Y/m/d", mktime(0, 0, 0, $arr[1],$arr[0],$arr[2]) ); 
					break;
					case 'json':
						$var = json_decode($var);						
					break;
			}
			return $var;	
		}else{
			my_error('ERROR: A variable is not activated to clean '.$var, 1);
			return null;
		}
	}
	/*Post. Retorna el valor de la variable mencionada a la entrada despues de haber sido tratada según el tipo que le pertence, en caso de que el nombre no forme parte de la array REQUEST retornamos un mensaje de error y el valor null*/
	
	/*Envia la variable de la entrada en formato JSON
			- var: Contenido con informacion de cualquier tipo
	*/
	function send($var){
	/*Pre: - */
		print(json_encode($var)); 
	}
	/*Post: Retorna el contenido de informacion de la entrada en formato JSON*/

	




?>
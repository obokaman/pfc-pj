<?php
	
	/*La función guarda información de los errores: el texto del parametro message y el parametro option que indica el modo de guardado de la información. También se guarda la fecha y hora en la que se ha producido el error.
			- message: Texto del error
			- option: Entero para el modo de impresion del error
	*/
	function my_error( $message, $option){	
			if ($option == 1){
					$x = fopen("/tmp/pfc.php.log", "a+");
					fwrite($x,  date("l,M d, Y g:i:s")." - ".$message."\n");	
					fclose($x);
			}
			if ($option == 2) {
				   $x = fopen("/tmp/pfc.php.log", "a+");
				   fwrite($x, date("l,M d, Y g:i:s").": \n");
				   foreach ($message as $k=>$v) {
						   fwrite($x, $k." => ".$v."\n");
				   }
                    fclose($x);
			}
	}

?>
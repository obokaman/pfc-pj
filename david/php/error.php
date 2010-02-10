<?php
	
	/*La función guarda los errores con el texto del parametro message, según el parametro option podemos seleccionar distintos modos de guardado de la información. Al principio de cada mensaje de error tb se muestra la hora de este, para diferenciar un error entre los demas
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
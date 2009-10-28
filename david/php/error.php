<?php
	
	/*La función guarda los errores con el texto del parametro message, según el parametro option podemos seleccionar distintos modos de guardado de la información*/
	function my_error( $message, $option){	
			if ($option == 1){
					$x = fopen("/tmp/pfc.php.log", "a+");
					fwrite($x,  $message."\n");	
					fclose($x);
				}
	}

?>
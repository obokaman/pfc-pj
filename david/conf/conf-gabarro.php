<?php
/*Esta array hace referencia a algunas rutas a directorios del servidor importantes para la ejecucion de algunas de la peticiones de la aplicacion cliente.
	- circuits: Directorio donde almacenan los txt de que contienen la informacion necesaria de los circuitos
	- images: Directorio con las imagenes de los circuitos
	- url-images: Este directorio hace referencia a una ruta con posicionamiento relativo, hacia el directorio donde se almacenan las imagenes de los circutos
	- bin: Directorio que contiene las aplicaciones necesarias para ejecutar una partida
	- games: Directorio donde se almacenan todas las partidas realizadas con la toda la informacion necesaria de estas
	- pack: Directorio con contenido necesario para ejecutar una partida 
*/

$path = Array( 'circuits' => '/var/local/share/racing/circuits/',
		'images' =>  '/var/www/racing/img/',
	        'url-images' => '/img/',
		'bin' => '/var/local/share/racing/bin/',
		'games' => '/var/local/share/racing/games/',
		'pack' => '/var/local/share/racing/pack/'  );
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title></title>
</head>
<body>
<?php include("includes.php");?>

<?php


	
	get_games();
	
	echo '<br><br>Por ID: ';
	
	get_game_id(1);
	
		
	$bool = exist_game(3, 1);
    
	if ($bool) echo "Funciona";
	else echo "ERROR";
	
	delete_game(9);
	
	$bool = exist_game(3, 1);
    
	if ($bool) echo "Funciona";
	else echo "ERROR";
	
?>
</body>
</html>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title></title>
</head>
<body>
<?php include("includes.php");?>

<?php

	$bool = create_game(3,1,null,null);
	
	if ($bool) echo "Insertado correctamente";
	else echo "ERROR";
	
?>
</body>
</html>
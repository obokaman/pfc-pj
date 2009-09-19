<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title></title>
</head>
<body>
<?php include("user.php");?>		


<?php

	if (exist_user(1)) echo "encontrado";
	else echo "no enconrtado";
	
?>
</body>
</html>
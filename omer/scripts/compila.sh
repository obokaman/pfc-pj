#omer
echo COMPILA
cd omer/racing
cmake .
make
cd pack
make
cd ..

#imagenes coche
cd img
sh rotate.sh

cd ../circuitos
../bin/racing-view basic.txt basic.png
../bin/racing-view basic2.txt basic2.png
../bin/racing-view basic3.txt basic3.png
../bin/racing-view chato.txt chato.png
../bin/racing-view newbasic.txt newbasic.png


GITDIR=/home/obokaman/Projects/pfc-pj
WWWDIR=/var/www/racing
SHAREDIR=/var/local/share/racing

cd $GITDIR
#sudo rm -r $WWWDIR
#sudo mkdir $WWWDIR

sudo cp -r david/php $WWWDIR


sudo cp marta/JocProg/war/JocProg.css $WWWDIR
sudo cp marta/JocProg/war/JocProg.html $WWWDIR
sudo cp -r marta/JocProg/war/WEB-INF $WWWDIR
sudo cp -r marta/JocProg/war/jocprog $WWWDIR

sudo mkdir $WWWDIR/img
sudo cp david/conf/conf-gabarro.php $WWWDIR/php/conf.php

sudo mkdir /var/local/share
sudo rm -r $SHAREDIR
sudo mkdir $SHAREDIR $SHAREDIR/circuits $SHAREDIR/bin $SHAREDIR/games $SHAREDIR/pack
sudo chown -R www-data $SHAREDIR/games
sudo chgrp -R www-data $SHAREDIR/games

#######
sh omer/scripts/compila.sh
#######

cd $GITDIR

sudo cp omer/racing/circuitos/*.txt $SHAREDIR/circuits
sudo cp omer/racing/circuitos/*.png $WWWDIR/img
sudo cp marta/img/*.png $WWWDIR/img
sudo cp omer/racing/img/*.gif $WWWDIR/img

sudo chown -R www-data $WWWDIR
sudo chgrp -R www-data $WWWDIR

sudo cp omer/racing/bin/monosim $SHAREDIR/bin
sudo cp omer/racing/bin/racing-view $SHAREDIR/bin

sudo cp -r omer/racing/pack/* $SHAREDIR/pack
cd $SHAREDIR/pack
sudo rm a.out Makefile circuit.txt studentcode.h proxycar.o

cd $GITDIR
mysql --user=root --password=proyecto < david/bbdd/bd_pfc.sql

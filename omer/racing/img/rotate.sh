
command=""
for i in `seq 0 10 359`; do
    if [ ! -f ferrari-$i.png ]; then
	convert ferrari.png -background Transparent -rotate $i -gravity center -extent 400x400 -resize 100x100 ferrari-$i.png 
    fi
    file="car-$i.gif"
    echo $file
    if [ ! -f $file ]; then 
	convert ferrari-$i.png -resize 60x60 $file
    fi
done

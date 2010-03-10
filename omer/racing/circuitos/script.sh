for f in `ls *.txt`; do ../bin/racing-view $f `basename $f .txt`.png 1500x1500 ; done


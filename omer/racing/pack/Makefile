
all: prepare compile
	

prepare:
	cp ../src/run.py .
	g++ -I../src/ ../src/mainsim.cc -c
	cp ../src/proxycar.h .
	g++ -I../src ../src/monocar.cc -c
	cp ../src/corbes.h .
	g++ -I../src ../src/corbes.cc -c
	cp ../src/monocar.h .
	cp ../src/common.h .
	cp ../src/proxycar.cc .
	cp ../circuitos/newbasic2.txt circuit.txt   ##oju
	cp ../src/studentcode.h .		    ##oju


compile:
	g++ proxycar.cc -c
	g++ mainsim.o corbes.o monocar.o proxycar.o 

clean:
	rm *.o
	rm a.out
	rm studentcode.h


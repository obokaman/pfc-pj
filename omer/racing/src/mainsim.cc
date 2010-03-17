// -*- mode: c++; -*-
#include <iostream>
#include <fstream>
#include "common.h"
#include "corbes.h"
#include "monocar.h"
#include "proxycar.h"

using namespace std;

int main(int argc, char **argv) {
  if (argc!=2) {
    cerr << "Usage: " << argv[0] << " <circuit.txt>" << endl;
    return 1;
  }

  ifstream ifs(argv[1]);
  if (not ifs) {
    cerr << "Circuit file '" << argv[1] << "' can not be opened." << endl;
    return 1;
  }
  
  Circuit c;
  if (not (ifs >> c)) {
    cerr << "Circuit file '" << argv[1] << "' does not make sense." << endl;
    return 1;
  }

  ostringstream oss;
  MonoCar car(&c, 3, &oss);
  ProxyCar *proxycar = ProxyCar::get(&car);
  try{
    //  double getCrono() {       ///<tiempo actual (en segundos)
    while (1) { //maximo: 5 minutos de tiempo
      proxycar->corre();
      car.avanza();
    }
  }
  catch (string s) {
    cerr << endl << "[END] " << s << endl;
  }

  if (car.finished()) {
    cout << car.total_time() << " "      //temps en ms
	 << c.width << " " << c.height << " " //mida del canvas
	 << car.PRINTSTEP << " "   //ms entre cada parell de dades
	 << car.ticks << endl     //nombre de dades
	 << oss.str();   // les dades en si    
  }
}

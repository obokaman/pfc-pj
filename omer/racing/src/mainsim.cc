// -*- mode: c++; -*-
#include <iostream>
#include <fstream>
#include "common.h"
#include "corbes.h"
#include "monocar.h"

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

  
  MonoCar car(&c, 3, &cout);
  try{
    while (1) {
      car.corre();
      car.avanza();
    }
  }
  catch (string s) {
    cerr << endl << "[END] " << s << endl;
  }
}

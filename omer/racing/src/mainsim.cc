// -*- mode: c++; -*-
#include <iostream>
#include "bezier.h"

using namespace std;

int main(int argc, char **argv) {
  if (argc!=2) {
    cerr << "Usage: " << argv[0] << " <circuit.txt>" << endl;
    return 1;
  }

  ifstream ifs(argv[1]);
  if (!ifs) {
    cerr << "Circuit file '" << argv[1] << "' can not be opened." << endl;
    return 1;
  }
  
  Circuito c;
  if (not (ifs >> c)) {
    cerr << "Circuit file '" << argv[1] << "' does not make sense." << endl;
    return 1;
  }

  Car car;
  Sim s(c);
  s.addCar(&car);
  s.simulate("output.txt");
}

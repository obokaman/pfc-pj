// -*- mode: c++; -*-

#include "sim.h"


void Sim::simulate(string output) {
  ofs.open(output.c_str());
  exelog.open((output+".log").c_str());
  if (!ofs) {
    cerr << "Output file '" << output
	 << "' can not be opened." << endl;
      return;
  }
  
  For(i, n) initcar(c, i);
  
  For(i, n) execute("init", i);
  
  while (not raceFinished()) {
    For(i, n) outputCar(i);
    For(i, n) execute("step", i);
    For(i, n) simCar(i);
  }
}

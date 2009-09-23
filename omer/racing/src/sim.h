// -*- mode: c++; -*-
#ifndef SIM_H
#define SIM_H

#include <vector>
#include <iostream>
#include "car.h"

struct Sim {
  vector<Car*> vc;
  Circuito c;
  int n;
  ofstream ofs, exelog;

  Sim(Circuito cc) {
    c = cc;
    n = 0;
  }

  void addCar(Car *c) {
    vc.push_back(c);
    n = vc.size();
  }

  void simulate(string output);

};


#endif 

#include "trace.h"
#include <iostream>

using namespace std;

istream &operator>>(istream &is, Trace &t) {
  is >> t.timestep;  //10 ms

  double x, y, alpha;
  while (is >> x >> y >> alpha) {
    t.x.push_back(x);
    t.y.push_back(y);
    t.alpha.push_back(alpha);
  }
  return is;
}

/*
ostream &operator<<(ostream &os, const Trace &t) {
*/

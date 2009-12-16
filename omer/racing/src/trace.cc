#include "trace.h"
#include <iostream>

using namespace std;

istream &operator>>(istream &is, Trace &t) {
  
  int kk;
  is >> kk;  //totaltime
  is >> kk;  //width
  is >> kk;  //height
  is >> t.timestep;  //100 ms
  is >> kk;  //numlineas

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

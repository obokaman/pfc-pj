#include <iostream>
#include <vector>

using namespace std;

struct Trace {
  int timestep;
  vector<double> x, y, alpha;
};
istream &operator>>(istream &is, Trace &t);
//ostream &operator<<(ostream &os, const Trace &t);

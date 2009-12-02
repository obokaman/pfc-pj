#include "monocar.h"
#include <algorithm>

using namespace std;

void MonoCar::corre() {
  while(1) {
    double maxvel = getMaxVel(100);
    double maxvel2 = getMaxVel(0);
    setVel(min(maxvel, maxvel2)/2);
  }
}

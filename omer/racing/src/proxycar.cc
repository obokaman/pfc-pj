#include "proxycar.h"
#include "monocar.h"

double ProxyCar::getLongCircuito() {
  _realcar->avanza();
  return _realcar->getLongCircuito();
}

int ProxyCar::getNumVueltas() {     
  _realcar->avanza();
  return _realcar->getNumVueltas();
}

int ProxyCar::getVuelta() {         
  _realcar->avanza();
  return _realcar->getVuelta();
}

double ProxyCar::getCronoVuelta(int vuelta) {
  _realcar->avanza();
  return _realcar->getCronoVuelta(vuelta);
}

double ProxyCar::getCrono() {      
  _realcar->avanza();
  return _realcar->getCrono();
}

double ProxyCar::getPos() {        
  _realcar->avanza();
  return _realcar->getPos();
}

double ProxyCar::getVel() {        
  _realcar->avanza();
  return _realcar->getVel();
}

double ProxyCar::getVelObjetivo() {
  _realcar->avanza();
  return _realcar->getVelObjetivo();
}

double ProxyCar::getMaxVel(double x) {
  _realcar->avanza();
  return _realcar->getMaxVel(x);
}

void ProxyCar::setVel(double v) {
  _realcar->setVel(v);
  _realcar->avanza();
}

void ProxyCar::espera(double s) {
  _realcar->espera(s);
}

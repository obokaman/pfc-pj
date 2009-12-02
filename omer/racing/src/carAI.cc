#include "carAI.h"

void carAI::preguntaVoid(const string &s, double par1) {
  mutex.lock();
  signalPregunta(s, par1);
  qwc.wait(mutex);
  mutex.unlock();
}

int carAI::preguntaInt(const string &s, double par1) {
  mutex.lock();
  signalPregunta(s, par1);
  qwc.wait(mutex);
  mutex.unlock();

  return rInt;
}

double carAI::preguntaDouble(const string &s, double par1) {
  mutex.lock();
  signalPregunta(s, par1);
  qwc.wait(mutex);
  mutex.unlock();

  return rDouble;
}

void carAI::respInt(int i) {
  rInt = i;
  qwc.wakeAll();
}
void carAI::respDouble(double d) {
  rDouble = d;
  qwc.wakeAll();
}
void carAI::respVoid() {
  qwc.wakeAll();
}

void carAI::correSlot() {
  corre();
  signalCorreEnded();
}

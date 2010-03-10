// -*- mode: c++; -*-
#include <iostream>
#include <algorithm>
#include <iomanip>
#include <sstream>
#include <cassert>
#include "common.h"
#include "corbes.h"

using namespace std;



class MonoCar {

public: 
  friend class ProxyCar;
  static const int TIMESTEP = 10; //10 ms
  static const int PRINTSTEP = 10*TIMESTEP; //must be TIMESTEP multiple
  static const int EXTRARACETIME = 5000; //must be TIMESTEP multiple

private:
  static double curv2maxvel(double curv) {
    double radi = 1/(curv+0.00001); // en metres
    return sqrt(1.8*10*radi);  //sqrt(mu * G * r); mu=1.8
    //(mu=1.8 es forca irrealista; 1.1 es mes realista)
  }

  //velocidad actual: vel (m/s)
  //velocidad objetivo: vo (m/s)
  //tiempo: time (s)
  //retorna: la nueva velocidad
  //por ahora, a lo cutre:
  // * frenar: 1.2 G
  // * acelerar: 1 G maximo, pero a velocidades altas (>30 m/s)
  // se resta un termino ~(vel-30)^2. Velmaxima: 110 m/s
  static double acelera(double vel, double vo, double time) {
    const double G = 9.8;
    const double frenoG = 1.2; //G
    const double aceleG = 1; //G
    const double espvel = 30; //m/s
    const double topvel = 110; //m/s
    double newvel;
    if (vo<vel) {
      newvel = vel - time*G*frenoG;
      if (newvel<vo) return vo;
      return newvel;
    }
    else {
      if (vel < 30) newvel = vel + time*G*aceleG;
      else newvel = vel + time*G*max(0.0,
				     aceleG *
				     (1-(vel-espvel)*(vel-espvel)/
				      (topvel-espvel)*(topvel-espvel)));
      if (newvel>vo) return vo;
      return newvel;
    }
  }

  ostream *os;
  Circuit *c;
  int numvueltas;
  double alpha;
  double pos;
  double vel, velObj;  //m/s
  vector<int> vueTimes;  //ms (tantas como vueltas completadas)
  int time;
  int timeSal;  //tiempo donde el coche se esta saliendo.

public:
  int ticks;    //ticks de rellotge de la simulacio

public:
  MonoCar(Circuit *_c, int nv, ostream *os_) {
    c = _c;
    numvueltas = nv;
    pos = 0;
    vel = velObj = 0;
    time = 0;
    timeSal = -1;
    os = os_;
    alpha = 0;
  }


protected:
  double getLongCircuito() { ///<longitud circuito (en metros)
    return c->getDist();
  }
  int getNumVueltas() {       ///<num vueltas
    return numvueltas;
  }

  int getVuelta() {         ///<vuelta actual (1-numvueltas)
    int res = int(pos/c->getDist())+1;
    return res;
  }

  ///crono vuelta (1..n). Si la vuelta no ha acabado,
  ///el crono actual de la vuelta; si la vuelta no ha empezado,
  ///devuelve 0.
  double getCronoVuelta(int vuelta) {
    double res;
    if (vuelta<1 or vuelta-1>=int(vueTimes.size())) res = 0;
    else res = vueTimes[vuelta-1]*0.001;
    return res;
  }

  double getCrono() {       ///<tiempo actual (en segundos)
    double res = time*0.001;
    return res;
  }
  double getPos() {         ///<posicion actual (en km)
    double res = pos*0.001;
    return res;
  }
  double getVel() {         ///<velocidad actual (en km/h)
    double res = vel*3.6;
    return res;
  }
  double getVelObjetivo() {  ///<velocidad objetivo (en km/h)
    double res = velObj*3.6;
    return res;
  }

  ///velocidad maxima en un punto x metros por delante (0<=x<=200), en km/h
  double getMaxVel(double x = 0) {
    double res;
    if (x<0 or x>200) res = -1;
    else res = curv2maxvel(c->getCurv(pos+x))*3.6;
    return res;
  }

  void setVel(double v) {   ///<velocidad objetivo (en km/h)
    velObj = v/3.6;
  }
  void espera(double s) {   ///<espera s segundos
    int steps = int((s*1000)/TIMESTEP+0.00001);
    For(i, steps) {
      avanza();
    }
  }

public:
  //hace avanzar la simulacion 1 paso de TIMESTEP ms.
  void avanza() {
    double vo = velObj;
    double dist = c->getDist();
    double p = pos;
    p -= dist*int(pos/dist);
    assert(p>=0 and p<=dist);


    if (timeSal>time) {  //estamos dando vueltas de campana ("salida")
      vo = 0;  
      alpha += 2*M_PI * (TIMESTEP/1000.0);
    }
    else {
      //miramos si hay accidente
      
      //calculamos la G de los neumaticos
      double curv = c->getCurv(p);
      alpha = c->getDir(p);
      
      if (vel > curv2maxvel(curv)) {  //malaSuerte(G)
	//durante un tiempo, el coche no avanza
	//5 seg. de penalizacion
	timeSal = time + 5000; 
	vo = 0;
      }
      else {
	vo = velObj;
	if (finished()) vo = 20;  //vuelta de honor
      }
    }
    
    double newvel = acelera(vel, vo, TIMESTEP/1000.0);  //(m/s)
    double v = (vel+newvel)/2;  //velocidad actual (m/s)
    double advDist = v*TIMESTEP/1000;
    
    if (p+advDist>dist) { //cambio de vuelta
      //primero: calculo el tiempo de entrada en meta
      double ratio = (dist-p)/advDist;
      vueTimes.push_back(time+int(ratio*TIMESTEP));
    }
    if (finished() and total_time()+EXTRARACETIME<time) {
      ostringstream oss;
      oss << "Race ended: ";
      int t = total_time();
      oss << setfill('0') << setw(2) << (t/60000) << ":";
      t %= 60000;
      oss << setfill('0') << setw(2) << (t/1000) << ":";
      t %= 1000;
      oss << setfill('0') << setw(3) << t;
      oss << endl;
      throw oss.str();
    }

    pos += advDist;
    
    Punt punt = c->getPos(pos);
    if (time % PRINTSTEP == 0) {
      (*os) << punt.x << " " << punt.y << " " << alpha << endl;
      ++ticks;
    }
 
    //finalmente: aceleracion, y cambio de tiempo
    vel = newvel;
    time += TIMESTEP;
  }

  bool finished() {
    return numvueltas==int(vueTimes.size());
  }
  int total_time() {
    return vueTimes[numvueltas-1];
  }
};

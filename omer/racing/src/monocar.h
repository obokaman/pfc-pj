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
 
  static const int TIMESTEP = 10; //10 ms
  static const int PRINTSTEP = 10*TIMESTEP; //must be TIMESTEP multiple

  static double curv2maxvel(double curv) {
    double radi = 1/(curv+0.00001); // en metres
    return sqrt(1.1*10*radi);  //sqrt(mu * G * r); mu=1.1
  }

  //velocidad actual: vel (m/s)
  //velocidad objetivo: vo (m/s)
  //tiempo: time (s)
  //retorna: la nueva velocidad
  //por ahora, a lo cutre:
  // * frenar: 1.2 G
  // * acelerar: 0.5 G maximo, pero a velocidades altas (>20 m/s)
  // se resta un termino ~(vel-20)^2. Velmaxima: 60 m/s
  static double acelera(double vel, double vo, double time) {
    double newvel;
    if (vo<vel) {
      newvel = vel - time*10*1.2;
      if (newvel<vo) return vo;
      return newvel;
    }
    else {
      if (vel < 20) newvel = vel + time*10*0.5;
      else newvel = vel + time*10*max(0.0, 0.5 - 0.5*(vel-20)*(vel-20)/1600.0);
      if (newvel>vo) return vo;
      return newvel;
    }
  }


  ostream *os;

private:
  Circuit *c;
  int numvueltas;
  double alpha;
  double pos;
  double vel, velObj;  //m/s
  vector<int> vueTimes;  //ms (tantas como vueltas completadas)
  int time;
  int timeSal;  //tiempo donde el coche se esta saliendo.

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
    (*os) << PRINTSTEP << endl;
  }


protected:
  double getLongCircuito() { ///<longitud circuito (en metros)
    avanza();
    return c->getDist();
  }
  int geNumVueltas() {       ///<num vueltas
    avanza();
    return numvueltas;
  }

  int getVuelta() {         ///<vuelta actual (1-numvueltas)
    int res = int(pos/c->getDist())+1;
    avanza();
    return res;
  }

  ///crono vuelta (1..n). Si la vuelta no ha acabado,
  ///el crono actual de la vuelta; si la vuelta no ha empezado,
  ///devuelve 0.
  double getCronoVuelta(int vuelta) {
    double res;
    if (vuelta<1 or vuelta-1>=vueTimes.size()) res = 0;
    else res = vueTimes[vuelta-1]*0.001;
    avanza();
    return res;
  }

  double getCrono() {       ///<tiempo actual (en segundos)
    double res = time*0.001;
    avanza();
    return res;
  }
  double getPos() {         ///<posicion actual (en km)
    double res = pos*0.001;
    avanza();
    return res;
  }
  double getVel() {         ///<velocidad actual (en km/h)
    double res = vel*3.6;
    avanza();
    return res;
  }
  double getVelObjetivo() {  ///<velocidad objetivo (en km/h)
    double res = velObj*3.6;
    avanza();
    return res;
  }

  ///velocidad maxima en un punto x metros por delante (0<=x<=200), en km/h
  double getMaxVel(double x = 0) {
    double res;
    if (x<0 or x>200) res = -1;
    else res = curv2maxvel(c->getCurv(pos+x))*3.6;
    avanza();
    return res;
  }

  void setVel(double v) {   ///<velocidad objetivo (en km/h)
    velObj = v/3.6;
    avanza();
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
    
    if (timeSal>time) {
      vo = 0;  //estamos dando vueltas de campana ("salida")
      alpha += 2*M_PI * (TIMESTEP/1000.0);
    }
    else {
      //miramos si hay accidente
      
      //calculamos la G de los neumaticos
      double curv = c->getCurv(p);
      //      double G = vel*vel/radi;
      alpha = c->getDir(p);
      
      if (vel > curv2maxvel(curv)) {  //malaSuerte(G)
	//durante un tiempo, el coche no avanza
	//2 seg. de penalizacion (min)
	timeSal = time + max(2000, int(1000*vel/20.0)); 
	vo = 0;
      }
      else vo = velObj;
    }
    
    double newvel = acelera(vel, vo, TIMESTEP/1000.0);  //(m/s)
    double v = (vel+newvel)/2;  //velocidad actual (m/s)
    double advDist = v*TIMESTEP/1000;
    
    if (p+advDist>dist) { //cambio de vuelta
      //primero: calculo el tiempo de entrada en meta
      double ratio = (dist-p)/advDist;
      vueTimes.push_back(time+int(ratio*TIMESTEP));

      if (vueTimes.size()==numvueltas) {
	ostringstream oss;
	oss << "Race ended: ";
	for (int i=0;i<numvueltas;++i) {
	  cerr << vueTimes[i] << endl;
	}
	int t = vueTimes[numvueltas-1];
	oss << setfill('0') << setw(2) << (t/60000) << ":";
	t %= 60000;
	oss << setfill('0') << setw(2) << (t/1000) << ":";
	t %= 1000;
	oss << setfill('0') << setw(3) << t;
	oss << endl;
	throw oss.str();
      }
    }
    pos += advDist;
    
    Punt punt = c->getPos(pos);
    if (time % PRINTSTEP == 0) {
      (*os) << punt.x << " " << punt.y << " " << alpha << endl;
    }
 
    //finalmente: aceleracion, y cambio de tiempo
    vel = newvel;
    time += TIMESTEP;
  }

  void corre();
};

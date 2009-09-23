// -*- mode: c++; -*-
#ifndef CORBES_H
#define CORBES_H

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

typedef pair<int, double> PID;

//punt (o vector de R^2)
struct Punt {
  double x, y;
  Punt(double x_, double y_): x(x_), y(y_) {}
  Punt() {}
  Punto unitari() const;
  double mida() const;
  Punt operator+(const Punt &p) const;
  Punt operator-(const Punt &p) const;
  Punt operator*(double e) const;
  double operator*(const Punt &p) const;
};
Punt operator*(double e, const Punto &p);
istream &operator>>(istream &is, Punt &p);
ostream &operator<<(ostream &os, const Punt &p);


//punt + direccio corba + "ganes" de la corba d'acompanyar el punt (2)
struct PuntDir {
  Punt p;
  double alpha; //0-2Pi
  double fpre, fpost; //>=0: "factor" d'acompanyament (defecte: 0.25)
};
istream &operator>>(istream &is, PuntDir &pd);
ostream &operator<<(ostream &os, const PuntDir &pd);


//Circuit
// Informacio basica circuit + pre-calcul
struct Circuit {

  Circuit(): n(0) {}

  const double MAXDISTSUBTRAM = 10;
  const int MINSEGSCORBA = 5;

  int n;               //num trams
  vector<PuntDir> vpd; //punts definint els trams (n)

  //Informacio que es precalcula:
  double totaldist;    //distancia del circuit
  vector<Punt> vp;     //(3n+1): 3n punts que intevenen en el
                       //càlcul de les corbes, +1 punt addicional repetit

  // * per a cadascun dels n trams,
  // * es trenca en tantes parts com comvingui (en distancies acumulades)
  // * i per a cadascuna es guarda la 't'
  // s'ha de seguir sempre el mateix métode de trencatge (altrament,
  // canviarien les distancies!)
  // * [posem una entrada extra, amb tram=n i t=0.0, amb la distancia total]
  vector<double> vd;
  vector<PID> vtr;  //el tram (0..n-1) i la 't' corresponent

  void precalcula(); //obte tot a partir de 'vpd'

  //Medotes de consulta
  //baix nivell:
  int getTram(double d) const;
  int recGetTram(double d, int a, int b) const;
  PID getTramt(double d) const;
  PID recGetTram(double d, int a, int b) const;

  //requereix haver pre-calculat els punts
  Punt getPos(int tram, double t) const;
  double getDir(int tram, double t) const; //0-2Pi
  double getCurv(int tram, double t) const;

  //alt nivell:
  Punt getPos(double d) const;
  double getDir(double d) const; //0-2Pi
  double getCurv(double d) const;
};
istream &operator>>(istream &is, Circuit &c);
ostream &operator<<(ostream &os, const Circuit &c);
  


  Punto getPunto(double t) const;
  Punto getPuntoBezier(const Recta &r, double t) const;

  double getDist(double t) const;
  double getDistBezier(const Recta &r, double t) const;

  double getInvDist(double dist) const;
  double getInvDistBezier(const Recta &r, double dist,
			  double fromt = 0) const;

  Punto getDir(double t) const;
  Punto getDirBezier(const Recta &r, double t) const;
  
  double getCurvBezier(const Recta &r, double t) const;
};
istream &operator>>(istream &is, Recta &r);
ostream &operator<<(ostream &os, const Recta &r);


struct Polyline {
  int n;              //num fragmentos
  vector<Punto> vp;   //los (n+1) puntos formando la curva poligonal
  vector<double> vt;  //las (n+1) evaluacions del parametro t
  vector<double> vd;  //las (n+1) distancias acumuladas

  Polyline(): n(0) {}
  Polyline(const Recta &r1, const Recta &r2);

  double t2d(double t) const;
  double d2t(double d) const;
  double dist() const;
  double getDist(double tfrom, double tto) const;
};

struct Circuito {
  int n;
  vector<Recta> vr;    //las (n) rectas del circuito
  vector<Polyline> vpl; //las (n-1) curvas de Bezier del circuito
  vector<double> vd;   //las (2*n) distancias acumuladas del circuito


  Circuito();
  
  int numSects() const;
  double d2t(double d) const;
  Punto coord(double t) const;
  Punto circdir(double t) const;
  double dist() const ;
  double dist(int i) const;
  double dist(double t1, double t2) const;
  double dist(double t) const;
  double curv(double t) const;
  //  double add(double t, double dt, double dist) const;
};
istream &operator>>(istream &is, Circuito &c);
ostream &operator<<(ostream &os, const Circuito &c);



#endif

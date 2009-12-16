// -*- mode: c++; -*-
#ifndef CORBES_H
#define CORBES_H

#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>

using namespace std;

typedef pair<int, double> PID;

//punt (o vector de R^2)
struct Punt {
  double x, y;
  Punt(double x_, double y_): x(x_), y(y_) {}
  Punt(double alpha_) {
    x = cos(alpha_);
    y = sin(alpha_);
  }
  Punt() {}
  Punt unitari() const;
  double mida() const;
  Punt operator+(const Punt &p) const;
  Punt operator-(const Punt &p) const;
  Punt operator*(double e) const;
  double operator*(const Punt &p) const;
};
Punt operator*(double e, const Punt &p);
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

  Circuit(): n(0), width(1000), height(1000) {}

  static const double MAXDISTSUBTRAM = 20;
  static const int MINSEGSCORBA = 5;

  int n;               //num trams
  int width, height;   //mida (en metres) del canvas del circuit

  vector<PuntDir> vpd; //punts definint els trams (n)

  //Informacio que es precalcula:
  double totaldist;    //distancia del circuit
  vector<Punt> vp;     //(3n+1): 3n punts que intevenen en el
                       //càlcul de les corbes, +1 punt addicional repetit

  // * per a cadascun dels n trams,
  // * es trenca en tantes parts com convingui (en distancies acumulades)
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
  PID recGetTramt(double d, int a, int b) const;

  //requereix haver pre-calculat els punts
  Punt getPos(int tram, double t) const;
  double getDir(int tram, double t) const; //0-2Pi
  double getCurv(int tram, double t) const;

  //alt nivell:
  Punt getPos(double d) const;
  double getDir(double d) const; //0-2Pi
  double getCurv(double d) const;
  double getDist() const;
  
  bool empty() const {return n==0;}
};
istream &operator>>(istream &is, Circuit &c);
ostream &operator<<(ostream &os, const Circuit &c);
  
#endif

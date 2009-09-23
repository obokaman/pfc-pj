// -*- mode: c++; -*-
#ifndef BEZIER_H
#define BEZIER_H

#include <iostream>
#include <vector>

using namespace std;

struct Punto {
  double x, y;
  Punto(double x_, double y_): x(x_), y(y_) {}
  Punto() {}
  Punto unitario() const;
  double length() const;
  Punto operator+(const Punto &p) const;
  Punto operator-(const Punto &p) const;
  Punto operator*(double e) const;
  double operator*(const Punto &p) const;
};
Punto operator*(double e, const Punto &p);
istream &operator>>(istream &is, Punto &p);
ostream &operator<<(ostream &os, const Punto &p);



struct Recta {
  Punto p1, p2;
  double d1, d2;

  Punto unitario() const;

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

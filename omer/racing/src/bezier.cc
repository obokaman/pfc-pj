// -*- mode: c++; -*-
//
#include "bezier.h"
#include "common.h"
#include <cmath>
#include <cassert>

Punto Punto::unitario() const {
  return (1/length())*(*this);
}

double Punto::length() const {
  return sqrt(x*x+y*y);
}

Punto Punto::operator+(const Punto &p) const {
  return Punto(x+p.x, y+p.y);
}

Punto Punto::operator-(const Punto &p) const {
  return Punto(x-p.x, y-p.y);
}

Punto Punto::operator*(double e) const {
  return Punto(x*e, y*e);
}

Punto operator*(double e, const Punto &p) {
  return p.operator*(e);
}

double Punto::operator*(const Punto &p) const {
  return x*p.x+y*p.y;
}

istream &operator>>(istream &is, Punto &p) {
  return is >> p.x >> p.y;
}
ostream &operator<<(ostream &os, const Punto &p) {
  return os << p.x << " " << p.y << " ";
}



Punto Recta::unitario() const {
  return (p2-p1).unitario();
}

Punto Recta::getPunto(double t) const {
  return (1-t)*p1+t*p2;
}

Punto Recta::getDir(double) const {
  return unitario();
}

Punto calcBezier(const Punto &P0, const Punto &P1,
		 const Punto &P2, const Punto &P3, double t) {
  double t2 = 1-t;
  return (t2*t2*t2)*P0 +(3*t2*t2*t)*P1 +(3*t2*t*t)*P2 + t*t*t*P3;
}

Punto Recta::getPuntoBezier(const Recta &r, double t) const {
  Punto P0 = p2;
  Punto P1 = p2 + d2*unitario();
  Punto P2 = r.p1 - r.d1*r.unitario();
  Punto P3 = r.p1;
  return calcBezier(P0, P1, P2, P3, t);
}

Punto Recta::getDirBezier(const Recta &r, double t) const {
  Punto P0 = p2;
  Punto P1 = p2 + d2*unitario();
  Punto P2 = r.p1 - r.d1*r.unitario();
  Punto P3 = r.p1;
  double t2 = 1-t;
  Punto gammaP = -3*t2*t2*P0 + (-6*t2*t+3*t2*t2)*P1
    +(-3*t*t+6*t2*t)*P2 + 3*t*t*P3;
  return gammaP.unitario();
}

#define STEP 0.01

double Recta::getDist(double t) const {
  assert(t>=0 and t<=1);
  return t*(p2-p1).length();
}

double Recta::getInvDist(double dist) const {
  double len = (p2-p1).length();
  assert(dist>=0 and dist<=len);
  return dist/len;
}

double Recta::getDistBezier(const Recta &r, double t) const {
  Punto P0 = p2;
  Punto P1 = p2 + d2*unitario();
  Punto P2 = r.p1 - r.d1*r.unitario();
  Punto P3 = r.p1;

  double t0 = 0;
  double len = 0;

  Punto A1 = P0, A2 = calcBezier(P0, P1, P2, P3, t0+STEP);
  while (t0<t) {
    len += (A2-A1).length();
    t0 += STEP;
    if (t0<t) {
      A1 = A2;
      A2 = calcBezier(P0, P1, P2, P3, t0+STEP);
    }
  }
  len -= (A2-A1).length()*(t0-t)/STEP;
  return len;
}

double Recta::getInvDistBezier(const Recta &r, double dist,
			       double fromt) const {
  Punto P0 = p2;
  Punto P1 = p2 + d2*unitario();
  Punto P2 = r.p1 - r.d1*r.unitario();
  Punto P3 = r.p1;

  double t0 = fromt;
  double len = 0;

  Punto A1 = calcBezier(P0, P1, P2, P3, t0),
    A2 = calcBezier(P0, P1, P2, P3, t0+STEP);
  while (len<dist) {
    len += (A2-A1).length();
    t0 += STEP;
    if (len<dist) {
      A1 = A2;
      A2 = calcBezier(P0, P1, P2, P3, t0+STEP);
    }
  }
  t0 -= STEP*(len-dist)/(A2-A1).length();
  return t0;
}

double Recta::getCurvBezier(const Recta &r, double t) const {
  Punto P0 = p2;
  Punto P1 = p2 + d2*unitario();
  Punto P2 = r.p1 - r.d1*r.unitario();
  Punto P3 = r.p1;

  double t2 = (1-t);
  Punto gammaP = -3*t2*t2*P0 + (-6*t2*t+3*t2*t2)*P1
    +(-3*t*t+6*t2*t)*P2 + 3*t*t*P3;
  Punto gammaPP = -6*t2*P0 + (6*t-12*t2)*P1
    +(-12*t+6*t2)*P2 + 6*t*P3;

  double lengP = gammaP.length();
  double lengPP = gammaPP.length();
  
  double gPgPP = gammaP*gammaPP;
  double sign = gammaP.x*gammaPP.y - gammaP.y*gammaPP.x < 0 ? -1:1;

  return sign*sqrt(lengP*lengP*lengPP*lengPP-gPgPP*gPgPP)/(lengP*lengP*lengP);
}

istream &operator>>(istream &is, Recta &p) {
  return is >> p.d1 >> p.p1 >> p.p2 >> p.d2;
}
ostream &operator<<(ostream &os, const Recta &p) {
  return os << p.d1 << " " << p.p1 << " " << p.p2 << " " << p.d2 << " ";
}



#define ADD(d, t, p) vp.push_back(p); vt.push_back(t); vd.push_back(d);
Polyline::Polyline(const Recta &r1, const Recta &r2) {
  Punto P0 = r1.p2;
  Punto P1 = r1.p2 + r1.d2*r1.unitario();
  Punto P2 = r2.p1 - r2.d1*r2.unitario();
  Punto P3 = r2.p1;

  double t = 0;
  double d = 0;
  Punto p = P0;
  ADD(d, t, P0);
  const double step = 0.05;

  while(t<1-3*step/2) {
    t += step;
    Punto p2 = calcBezier(P0, P1, P2, P3, t);
    d += (p2-p).length();
    p = p2;
    ADD(d, t, p);
  }

  d += (P3-p).length();
  t = 1;
  ADD(d, t, P3);
  n = vp.size()-1;
}
#undef ADD

double Polyline::t2d(double t) const {
  assert(t>=-EPSILON and t<=EPSILON);
  assert(n>0);

  if (t<=0) return 0;
  else if (t>=1) return vd[n];

  int i = 1;
  while (vt[i]<t) ++i;

  return vd[i-1] + (t-vt[i-1])/(vt[i]-vt[i-1])*(vd[i]-vd[i-1]);
}

double Polyline::d2t(double d) const {
  assert(n>0);
  assert(d>=-EPSILON and d<=vd[n]+EPSILON);
  if (d<=0) return 0;
  else if (d>=vd[n]) return 1;

  int i = 1;
  while (vd[i]<d) ++i;
  return vt[i-1] + (d-vd[i-1])/(vd[i]-vd[i-1])*(vt[i]-vt[i-1]);
}

double Polyline::dist() const {
  assert(n>0);
  return vd[n];
}

double Polyline::getDist(double tfrom, double tto) const {
  return d2t(tto) - d2t(tfrom);
}



Circuito::Circuito(): n(0) {}

istream &operator>>(istream &is, Circuito &c) {
  is >> c.n;
  int n = c.n;

  c.vr.resize(n);
  For(i, n) {
    is >> c.vr[i];
  }

  c.vpl.resize(n-1);
  For(i, n-1) {
    c.vpl[i] = Polyline(c.vr[i], c.vr[i+1]);
  }

  c.vd.resize(2*n);
  c.vd[0] = 0;

  For(i, 2*n-1) {
    c.vd[i+1] = c.vd[i];
    if (i%2==0) c.vd[i+1] += c.vr[i/2].getDist(1);
    else c.vd[i+1] += c.vpl[i/2].dist();
  }

  return is;
}

ostream &operator<<(ostream &os, Circuito &c) {
  os << c.n << endl;
  For(i, c.n) os << c.vr[i] << " ";
  os << endl;
  return os;
}
  
int Circuito::numSects() const {
  return 2*n-1;
}

Punto Circuito::coord(double t) const {
  int t0 = int(t);
  assert(t0>=0 and t0<2*n-1);

  if (t0%2==0) return vr[t0/2].getPunto(t-t0);
  else return vr[t0/2].getPuntoBezier(vr[t0/2+1], t-t0);
}

Punto Circuito::circdir(double t) const {
  int t0 = int(t);
  assert(t0>=0 and t0<2*n-1);

  if (t0%2==0) return vr[t0/2].getDir(t-t0);
  else return vr[t0/2].getDirBezier(vr[t0/2+1], t-t0);
}

double Circuito::dist() const {
  assert(n>0);
  return vd[2*n-1]; //2*n-1 fragments, 2*n distancies
}

double Circuito::dist(int i) const {
  assert(i>=0 and i<2*n-1);
  double res = vd[i];
  if (i>0) res -= vd[i-1];
  return res;
}

double Circuito::dist(double t) const {
  if (t>=2*n-1 or t<0) {
    int t0 = int(t)%(2*n-1);
    if (t0<0) t0+=2*n-1;
    t = t0 + (t-int(t));
  }

  int t0 = int(t);
  double res = vd[t0];
  if (t0%2==0) res += vr[t0/2].getDist(t-t0);
  else res += vpl[t0/2].getDist(0, t-t0);
  return res;
}
 
double Circuito::dist(double t1, double t2) const {
  return dist(t2)-dist(t1);
}

double Circuito::curv(double t) const {
  int t0 = int(t);
  if (t0%2==0) return 0;
  return vr[t0/2].getCurvBezier(vr[t0/2+1], t-t0);
}

double Circuito::d2t(double d) const {
  int i = 0;
  while (vd[i]<=d) ++i;
  --i;
  if (i%2==0) return i+(d-vd[i])/vr[i/2].getDist(1);
  else return i+vpl[i/2].d2t(d-vd[i]);
}

/*
double Circuito::add(double t, double dt, double dist) const {
  int t0 = int(t);
  assert(dt<=vd[t0]);

  while (dt+dist>vd[t0]) {
    dist -= vd[t0]-dt;
    dt = vd[t0];
    t = ++t0;
    if (t0>2*n-1) {
      dist -= vd[t0] - dt;
      t = t0 = 0;
      dt = 0;      
    }
  }

  double last = 0;
  if (t0>0) last = vd[t0-1];
  if (t0%2==0) return t + vr[t0/2].getInvDist(dist);
  else return t0 + vr[t0/2].getInvDistBezier(vr[t0/2+1], dist, t-t0);
}
*/

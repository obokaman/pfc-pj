// -*- mode: c++; -*-

#include "corbes.h"
#include "common.h"
#include <cmath>
#include <cassert>

Punt Punt::unitari() const {
  return (1/mida())*(*this);
}

double Punt::mida() const {
  return sqrt(x*x+y*y);
}

Punt Punt::operator+(const Punt &p) const {
  return Punt(x+p.x, y+p.y);
}

Punt Punt::operator-(const Punt &p) const {
  return Punt(x-p.x, y-p.y);
}

Punt Punt::operator*(double e) const {
  return Punt(x*e, y*e);
}

Punt operator*(double e, const Punt &p) {
  return p.operator*(e);
}

double Punt::operator*(const Punt &p) const {
  return x*p.x+y*p.y;
}

istream &operator>>(istream &is, Punt &p) {
  return is >> p.x >> p.y;
}
ostream &operator<<(ostream &os, const Punt &p) {
  return os << p.x << " " << p.y << " ";
}

//--------------------------------------

istream &operator>>(istream &is, PuntDir &pd) {
  is >> pd.p >> pd.alpha >> pd.fpre >> pd.fpost;
  pd.alpha *= 2*M_PI;
  return is;
}
ostream &operator<<(ostream &os, const PuntDir &pd) {
  return os << pd.p << " " << pd.alpha/(2*M_PI) << " "
	    << pd.fpre <<  " " << pd.fpost << endl;
}


//--------------------------------------

void Circuit::precalcula() {
  assert(n == vpd.size());
  vp.resize(3*n+1);

  //calculem els punts de les corbes de Bezier
  For(i, n) {
    PuntDir &pd1 = vpd[i], &pd2 = vpd[(i+1)%n];

    vp[3*i] = pd1.p;
    vp[3*i+1] = pd1.p
      + Punt(cos(pd1.alpha), sin(pd1.alpha))*pd1.fpost*(pd2.p-pd1.p).mida();
    vp[3*i+2] = pd2.p
      - Punt(cos(pd2.alpha), sin(pd2.alpha))*pd2.fpre*(pd2.p-pd1.p).mida();
  }
  vp[3*n] = vp[0];

  //per a cada corba, anem omplint els trams

  //punt inicial
  vd.resize(1); vtr.resize(1);
  int tram = 0;
  double dist = 0.0, t = 0.0;
  vd[0] = dist; vtr[0] = PID(tram, t);
  Punt on = vp[0];

  //i ara, trenquem els trams en subtrams, sempre que no siguin
  //ni massa llargs (<MAXDISTSUBTRAMS) ni hi hagi massa pocs (>=MINSEGSCORBA) 
  while (tram<n) {
    double dd, dt = min(1.0/MINSEGSCORBA, 1-t);
    Punt next_on;
    while(1) {
      next_on = getPos(tram, t+dt);
      dd = (next_on-on).mida();
      if (dd < MAXDISTSUBTRAM) break;
      dt*=0.75;
    }

    vd.push_back(dist+dd);
    dist += dd;
    if (1.0 - t+dt < EPSILON) {
      t = 0.0;
      ++tram;
      vtr.push_back(PID(tram, t));
    }
    else {
      t += dt;
      vtr.push_back(PID(tram, t));
    }

    on = next_on;
  }
  totaldist = vd[vd.size()-1];
}

double Circuit::getDist() const {
  return totaldist;
}


int Circuit::getTram(double d) const {
  if (d>=totaldist) {
    d -= int(d/totaldist)*totaldist;
  }
  assert(d>=0 and d<totaldist);

  return recGetTram(d, 0, vtr.size()-1);
}

int Circuit::recGetTram(double d, int a, int b) const {
  assert(a<b);
  if (a==b-1 or
      vtr[a].first==vtr[b].first or
      (vtr[a].first+1==vtr[b].first and vtr[b].second==0.0)) 
    return vtr[a].first;

  int m = (b+a)/2;
  if (d<vd[m]) return recGetTram(d, a, m);
  else return recGetTram(d, m, b);
}

PID Circuit::getTramt(double d) const {
  if (d>=totaldist) {
    d -= int(d/totaldist)*totaldist;
  }
  assert(d>=0 and d<totaldist);

  return recGetTramt(d, 0, vtr.size()-1);
}

PID Circuit::recGetTramt(double d, int a, int b) const {
  assert(a<b);
  if (a==b-1) {
    const double &t1 = vtr[a].second;
    double t2 = vtr[b].second;
    if (t2 == 0.0) t2 = 1.0;
    const double &d1 = vd[a], &d2 = vd[b];

    return PID(vtr[a].first, t1+(t2-t1)/(d2-d1)*(d-d1));
  }

  int m = (b+a)/2;
  if (d<vd[m]) return recGetTramt(d, a, m);
  else return recGetTramt(d, m, b);
}

Punt Circuit::getPos(int k, double t) const {
  double t2 = 1-t;
  return (t2*t2*t2)*vp[3*k]
    +(3*t2*t2*t)*vp[3*k+1]
    +(3*t2*t*t)*vp[3*k+2]
    +t*t*t*vp[3*k+3]; //correcte: vp.size()==3n+1
}

double Circuit::getDir(int k, double t) const {
  double t2 = 1-t;
  Punt gammaP = -3*t2*t2*vp[3*k] + (-6*t2*t+3*t2*t2)*vp[3*k+1]
    +(-3*t*t+6*t2*t)*vp[3*k+2] + 3*t*t*vp[3*k+3];
  return atan2(gammaP.y, gammaP.x);
}

double Circuit::getCurv(int k, double t) const {
  double t2 = 1-t;
  Punt gammaP = -3*t2*t2*vp[3*k] + (-6*t2*t+3*t2*t2)*vp[3*k+1]
    +(-3*t*t+6*t2*t)*vp[3*k+2] + 3*t*t*vp[3*k+3];
  Punt gammaPP = -6*t2*vp[3*k] + (6*t-12*t2)*vp[3*k+1]
    +(-12*t+6*t2)*vp[3*k+2] + 6*t*vp[3*k+3];

  double lengP = gammaP.mida();
  double lengPP = gammaPP.mida();
  
  double gPgPP = gammaP*gammaPP;
  double sign = gammaP.x*gammaPP.y - gammaP.y*gammaPP.x < 0 ? -1:1;

  return sign*sqrt(lengP*lengP*lengPP*lengPP-gPgPP*gPgPP)/
                  (lengP*lengP*lengP);
}

Punt Circuit::getPos(double d) const {
  PID pid = getTramt(d);
  return getPos(pid.first, pid.second);
}
double Circuit::getDir(double d) const {
  PID pid = getTramt(d);
  return getDir(pid.first, pid.second);
}
double Circuit::getCurv(double d) const {
  PID pid = getTramt(d);
  return getCurv(pid.first, pid.second);
}

istream &operator>>(istream &is, Circuit &c) {
  if (not (is >> c.n)) return is;

  c.vpd.resize(c.n);
  For(i,c.n) {
    if (not (is >> c.vpd[i])) return is;
  }
  c.precalcula();
  return is;
}

ostream &operator<<(ostream &os, const Circuit &c) {
  os << c.n << endl;
  For(i, c.n) os << c.vpd[i] << endl;
  return os;
}

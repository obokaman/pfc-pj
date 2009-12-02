// -*- mode: c++; -*-
#ifndef SIM_H
#define SIM_H

#include <vector>
#include <iostream>
#include "corbes.h"
#include "carAI.h"

//classe auxiliar per a permetre que "Sim" pugui emetre
//molts signals distints, pels distints cotxes
class QSignalCorre : public QObject {
  Q_OBJECT

signals:
  void correSignal();
};

struct query {
  string s;
  double par1;
};

struct Car {
  double pos;
  double vel;
  double velObj;
  double velSal;
  double riesgo;
  int timeSal;
  int vueAct;
  vector<int> vueTimes;
  Car() {
    pos = vel = velObj = velSal = 0.0;
    timeSal = -1; //no estamos en salida
    vueAct = 0;
  }
};

class Sim : public QObject {
  Q_OBJECT

  vector<carAI*> vc;
  vector<QSignalCorre*> vqs;
  Circuit circuit;
  ostream *os;

  vector<Car> vcars;

  int currentTime;            //en milesimas
  vector<int> vt;             //temps dels jugadors
  vector<query> vq;           //ordres que esperen

public:
  Sim(Circuit _c, ostream *_os): circuit(_c), os(_os) {}

  //afegeix un nou cotxe a la simulacio
  void addCar(carAI *cai) {
    vc.push_back(c);
    vqs.push_back(new QSignalCorre(this));
    vt.push_back(0);
    vq.push_back(query());
    vcars.push_back(Car());

    connect(vqs[vqs.size()-1], SIGNAL(correSignal()),
            vc[vc.size()-1], SLOT(correSlot()));

    connect(vc[vc.size()-1], SIGNAL(signalPregunta(string, double)),
	    this, SLOT(slotPregunta(string, double)));
    connect(vc[vc.size()-1], SIGNAL(signalCorreEnded()),
	    this, SLOT(slotCorreEnded());
  }

  
  void simulate() {
    (*os) << "#Begin simulation." << endl;
    (*os) << "#Registered cars:" << endl;
    For(i, vc.size()) {
      (*os) << "#  Car " << i << ": " << vc[i]->name()
	    << " (" << vc[i] << ")" << endl; 
    }
    (*os) << "#" << endl;
    For(i, vc.size()) {
      (*os) << "#Turn on car " << i << endl;
      vqs[i]->correSignal();
    }

    
    //TODO:
    // <think about>
  }

protected slots:
  void slotPregunta(string s, double par1);
  void slotCorreEnded();
};


#endif 

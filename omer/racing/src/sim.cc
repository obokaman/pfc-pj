// -*- mode: c++; -*-

#include "sim.h"

const int INCTIME = 10;  //1 centesima

void Sim::responde(int i, string s, double par1) {
  if (s=="LongCircuito") vqs[i]->respDouble(circuit.getDist());
  else if (s=="NumVueltas") vqs[i]->respInt(3);
  else if (s=="Vuelta") vqs[i]->respInt(0);
  else if (s=="CronoVuelta") vqs[i]->respDouble(0);
  else if (s=="Crono") vqs[i]->respDouble(currentTime/1000.0);
  else if (s=="Pos") vqs[i]->respDouble(0);
  else if (s=="Vel") vqs[i]->respDouble(0);
  else if (s=="VelObjetivo") vqs[i]->respDouble(0);
  else if (s=="MaxVel") vqs[i]->respDouble(0);
  else if (s=="SetVel") vqs[i]->respVoid();
  else if (s=="Espera") vqs[i]->respVoid();
}

//si todo el mundo está más adelantado que la simulacion,
//avanzamos hasta atrapar a alguien.
// Retorna el numero de ms. de tiempo que se ha avanzado

bool Sim::check_times() {
  int time_elapsed = 0;
  while(1) {
    For(i, vt.size()) {
      assert(vt[i]>=currentTime);
      if (vt[i]==currentTime) {
	//miramos si hay query; de otro modo, estamos bloqueados
	if (vq[i].s!="") {
	  string s = vq[i].s;
	  vq[i].s = ""; 
	  responde(i, s, vq[i].par1);
	  //'responde' hace avanzar tambien vt
	  assert(vt[i]>currentTime);
	}
	else return time_elapsed;  //no podemos avanzar
      }
    }

    //ya podemos avanzar la simulacion
    simula(INCTIME);
    time_elapsed += INCTIME;
  }
}

void Sim::slotPregunta(string s, double par1) {
  carAI *c = sender();
  int i = 0;
  while (i<vc.size() and vc[i]!=c) ++i;
  assert(i<vc.size());

  if (vt[i]==currentTime) {  //no llega a entrar en vq
    responde(i, s, par1);
    check_times(); //intenta avanzar la simulacion
  }
  else {
    assert(vt[i]>currentTime);
    vq[i].s = s;
    vq[i].par1 = par1;
  }
}


//hace avanzar la simulacion un paso de 'step' ms
void Sim::simula(int step) {
  For(i, vcars.size()) {
    Car &c = vcars[i];
    double vo = c.velObj;
    double dist = circuit.getDist();
    double pos = c.pos;
    pos -= dist*int(pos/dist);
    assert(pos>=0 and pos<=dist);

    if (c.timeSal>currentTime) vo = c.velSal;  //estamos en "salida"
    else {
      //miramos si hay choque

      //calculamos la G de los neumaticos
      double radi = 1/(circuit.getCurv(pos)+0.00001); // en metres
      double vel = c.vel/3.6;                         // en metres/s
      double G = vel*vel/radi;

      if (malaSuerte(G)) {
	//recupera durante un cierto tiempo a 10 m/s
	c.velSal = 10;
	c.timeSal = currentTime + max(2.0, 2*(G-1.0)); //min: 2 seg.
	vo = c.velSal; 
      }
      else vo = c.velObj;
    }

    double newvel = acelera(c.vel, vo);
    double v = (c.vel+newvel)/2;  //velocidad actual
    double advDist = v*step/3600.0; //km/h -> m/s; ms -> s 

    if (pos+advDist>dist) { //cambio de vuelta
      //primero: calculo del tiempo de entrada en meta
      double ratio = (dist-pos)/advDist;
      c.vueTimes.push_back((currentTime+ratio*step)/1000.0);
      c.vueAct++;
    }
    c.pos += advDist;

    //aceleracion
    c.vel = newvel;
  }
  currentTime += step;
}


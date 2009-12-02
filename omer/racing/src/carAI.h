// -*- mode: c++; -*-
#include <iostream>
#include <QThread>

using namespace std;

class CarAI : public QThread {
  Q_OBJECT

public:
  /// Metodos (llamados desde otro thread) para
  /// devolver datos y reactivar el QWaitCondition.
  void respVoid();
  void respInt(int i);
  void respDouble(double d);
  
protected slots:
  void correSlot();

protected:
  void corre() = 0;          ///<Programa que se ejecuta.
  string name() = 0;

private:
  QMutex mutex;
  QWaitCondition qwc;
  int rInt;
  double rDouble;

  double getLongCircuito() { ///<longitud circuito (en metros)
    return preguntaDouble("LongCircuito", 0);
  }
  int geNumVueltas() {       ///<num vueltas
    return preguntaInt("NumVueltas", 0);
  }

  int getVuelta() {         ///<vuelta actual
    return preguntaInt("Vuelta", 0);
  }

  ///crono vuelta (1..n). Si la vuelta no ha acabado,
  ///el crono actual de la vuelta; si la vuelta no ha empezado,
  ///devuelve 0.
  double getCronoVuelta(int vuelta=-1) {
    return preguntaDouble("CronoVuelta", vuelta);
  }

  double getCrono() {       ///<tiempo actual (en segundos)
    return preguntaDouble("Crono", 0);
  }
  double getPos() {         ///<posicion actual (en metros)
    return preguntaDouble("Pos", 0);
  }


  double getVel() {         ///<velocidad actual (en km/h)
    return preguntaDouble("Vel", 0);
  }
  double getVelObjetivo() {  ///<velocidad objetivo (en km/h)
    return preguntaDouble("VelObjetivo", 0);
  }

  ///velocidad maxima en un punto x metros por delante (0<=x<=200)
  double getMaxVel(double x = 0) {
    return preguntaDouble("MaxVel", x);
  }

  void setVel(double v) {   ///<velocidad objetivo (en km/h)
    return preguntaVoid("SetVel", v);
  }
  void espera(double s) {   ///<espera s segundos
    return preguntaVoid("Espera", s);
  }

  //-------------------------

  void preguntaVoid(const string &s, double par1);
  int preguntaInt(const string &s, double par1);
  double preguntaDouble(const string &s, double par1);

signals:
  void signalPregunta(const string &s, double par1);
  void signalCorreEnded();
};

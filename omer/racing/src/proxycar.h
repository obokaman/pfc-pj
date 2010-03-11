#include <iostream>
#include <algorithm>
#include <vector>
#include <map>

using namespace std;

struct MonoCar;

class ProxyCar {
 private:
  MonoCar *_realcar; 

 public:
  ProxyCar(MonoCar *_r): _realcar(_r) {}

  static ProxyCar *get(MonoCar *_r);
  virtual void corre() = 0; 

  //interface del proxy
 protected:
  double getLongCircuito(); ///<longitud circuito (en metros)
  int getNumVueltas();      ///<num vueltas
  int getVuelta();          ///<vuelta actual (1-numvueltas)

  
  ///crono vuelta (1..n). Si la vuelta no ha acabado,
  ///el crono actual de la vuelta; si la vuelta no ha empezado,
  ///devuelve 0.
  double getCronoVuelta(int vuelta);

  double getCrono();        ///<tiempo actual (en segundos)
  double getPos();          ///<posicion actual (en km)
  double getVel();          ///<velocidad actual (en km/h)
  double getVelObjetivo();  ///<velocidad objetivo (en km/h)

  ///velocidad maxima en un punto x metros por delante (0<=x<=200), en km/h
  double getMaxVel(double x = 0);
  
  void setVel(double v);    ///<velocidad objetivo (en km/h)
  void espera(double s);    ///<espera s segundos
};

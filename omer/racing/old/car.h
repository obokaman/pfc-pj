

class itfCar {
 public:
  double getLength();                  //longitud del circuito en metros
  int getNumLaps();                    //numero de vueltas de la carrera
  int getLap();                        //vuelta actual (inicial: vuelta 0)
  double getCurv(double relpos = 0);   //curvatura en el punto pos
  double getVel();                     //velocidad actual del vehiculo
  double getAimVel();                  //velocidad objetivo actual
  int getTimeMs();                     //tiempo actual en milisegundos

  void setAimVel();                    //fija velocidad objetivo

  virtual void sim();                  //
};

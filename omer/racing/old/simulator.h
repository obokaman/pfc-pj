

struct Car {
  double pos, vel, aimvel;
  
  itfCar *ic;

  Car(): pos(0), vel(0), aimvel(0), ic(NULL) {}
  Car(itfCar *_ic): pos(0), vel(0), aimvel(0), ic(_ic) {}
};


class Simulator {
  Circuito circ;
  vector<Car> vc;
  
  int time; //en ms

  void simula_step();
};

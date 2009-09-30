// -*- mode: c++; -*-
#include <QApplication>
#include <fstream>
#include "QMainView.h"

int main(int argc, char *argv[])
{
  QApplication app(argc, argv);
  if (argc!=2 and argc!=1) {
    cerr << "Usage: " << argv[0] << " <circuit.txt>" << endl;
    return 1;
  }
  
  MainView window;
  if (argc==2) {
    window.loadCircuit(string(argv[1]));
  }

  window.show();
  return app.exec();

  

  //  return app.exec();
}

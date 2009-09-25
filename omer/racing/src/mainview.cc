// -*- mode: c++; -*-
#include <QApplication>
#include <fstream>
#include "corbes.h"
#include "QCircuitView.h"

int main(int argc, char *argv[])
{
  QApplication app(argc, argv);
  if (argc!=2) {
    cerr << "Usage: " << argv[0] << " <circuit.txt>" << endl;
    return 1;
  }

  ifstream ifs(argv[1]);
  if (!ifs) {
    cerr << "Circuit file '" << argv[1] << "' can not be opened." << endl;
    return 1;
  }
  
  CircuitView view;
  Circuit c;
  if (not (ifs >> c)) {
    cerr << "Circuit file '" << argv[1] << "' does not make sense." << endl;
    return 1;
  }
  view.load(c);
  view.show();
  return app.exec();
}

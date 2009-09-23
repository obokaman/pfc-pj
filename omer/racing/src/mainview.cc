// -*- mode: c++; -*-
#include <QApplication>
#include <fstream>
#include "bezier.h"
#include "Qview.h"

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
  
  View view;
  Circuito c;
  if (not (ifs >> c)) {
    cerr << "Circuit file '" << argv[1] << "' does not make sense." << endl;
    return 1;
  }
  view.load(c);
  view.show();
  return app.exec();
}

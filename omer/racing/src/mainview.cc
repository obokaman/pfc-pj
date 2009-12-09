// -*- mode: c++; -*-
#include <QApplication>
#include <fstream>
#include <sstream>
#include <cassert>
#include "QMainView.h"

using namespace std;

int main(int argc, char *argv[])
{
  QApplication app(argc, argv); 
  
  MainView window;
  if (argc>=2) {
    window.loadCircuit(string(argv[1]));
  }
  if (argc==3 or argc>4) {
    cerr << "Usage: " << argv[0] << " <circuit.txt> <capture.png> <width x height>" << endl;
  }
  if (argc==4) {
    istringstream iss(argv[3]);
    int width, height;
    char kk;
    iss >> width >> kk >> height;
    assert(width>10 and height>10 and width<10000 and height<10000);
    window.saveImage(string(argv[2]), width, height);
    return 0;
  }

  window.show();
  return app.exec();
}

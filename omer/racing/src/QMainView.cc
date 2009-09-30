// -*- mode: c++; -*-
#include <fstream>
#include <QMenuBar>
#include <QFileDialog>
#include "QMainView.h"
#include "corbes.h"

using namespace std;

MainView::MainView() {
  filename = "";

  setMinimumSize(500,500);
  setWindowTitle(QString("racing-view"));

  qcv = new CircuitView(this);

  if (filename != "") loadCircuit(filename);
  
  loadAct = new QAction(tr("&Load"), this);
  loadAct->setShortcut(tr("Ctrl+L"));
  loadAct->setStatusTip(tr("Load a circuit"));
  connect(loadAct, SIGNAL(triggered()), this, SLOT(loadFile()));

  saveAct = new QAction(tr("&Save as"), this);
  saveAct->setShortcut(tr("Ctrl+S"));
  saveAct->setStatusTip(tr("Save the current circuit"));
  connect(saveAct, SIGNAL(triggered()), this, SLOT(saveFile()));

  fileMenu = menuBar()->addMenu(tr("&File"));
  fileMenu->addAction(loadAct);
  fileMenu->addAction(saveAct);
}

void MainView::loadFile() {
  QString loadFileName =
    QFileDialog::getOpenFileName(this,
				 tr("Load Circuit"),
				 "",
				 tr("Circuit files (*.txt)"));
  loadCircuit(loadFileName.toStdString());
  setWindowTitle(QString(("racing-view: <" + filename+">").c_str()));
}

void MainView::loadCircuit(const string &fname) {
  filename = fname;
  Circuit c;
  ifstream ifs(filename.c_str());
  if (not ifs) {
    cerr << "Circuit file '" << filename << "' does not exist." << endl;
  }
  if (not (ifs >> c)) {
    cerr << "Circuit file '" << filename << "' does not make sense." << endl;
  }

  qcv->load(c);
  update();
}

void MainView::saveFile() {
  QString saveFileName =
    QFileDialog::getSaveFileName(this,
				 tr("Save Circuit"),
				 QString(filename.c_str()),
				 tr("Circuit files (*.txt)"));
  saveCircuit(saveFileName.toStdString());
  setWindowTitle(QString(("racing-view: <" + filename+">").c_str()));
}

void MainView::saveCircuit(const string &fname) {
  filename = fname;
  ofstream ofs(filename.c_str());
  if (not ofs) {
    cerr << "Circuit file '" << filename << "' cannot be opened." << endl;
  }
  ofs << qcv->getCircuit();
  update();
}

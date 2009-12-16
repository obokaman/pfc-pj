// -*- mode: c++; -*-
#include <fstream>
#include <QMenuBar>
#include <QTimer>
#include <QFileDialog>
#include <QPainter>
#include "QMainView.h"
#include "corbes.h"

using namespace std;

//ineficient, pero nomes es un parxe
void MainView::getCircuitSize(int &width, int &height) {
  Circuit c = qcv->getCircuit();
  width = c.width;
  height = c.height;
}

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

  playAct = new QAction(tr("&Play trace"), this);
  playAct->setShortcut(tr("Ctrl+P"));
  playAct->setStatusTip(tr("Play a trace"));
  connect(playAct, SIGNAL(triggered()), this, SLOT(playTrace()));

  fileMenu = menuBar()->addMenu(tr("&File"));
  fileMenu->addAction(loadAct);
  fileMenu->addAction(saveAct);
  fileMenu->addAction(playAct);

  timer = new QTimer(this);
  connect(timer, SIGNAL(timeout()), this, SLOT(avanzaCar()));
  
}

void MainView::saveImage(const string &name, int width, int height) {
  QPixmap qp(width, height);
  qp.fill( qRgb(128, 255, 128) );

  QPainter p(&qp);
  qcv->showControlPoints = false;
  qcv->drawCircuit(&p);

  qp.save(QString(name.c_str())); 
}

void MainView::avanzaCar() {
  if (icar>=traceToPlay.x.size()) {
    icar = 0;
  }
  qcv->setCar( traceToPlay.x[icar],
	       traceToPlay.y[icar],
	       traceToPlay.alpha[icar] );     
  ++icar;
  update();
}

void MainView::playTrace() {
  QString traceFileName =
    QFileDialog::getOpenFileName(this,
				 tr("Load Trace"),
				 "",
				 tr("Trace files (*.trc)"));
  loadTrace(traceFileName.toStdString());

  qcv->setCar( traceToPlay.x[0], traceToPlay.y[0], traceToPlay.alpha[0] );
  icar = 1;
  qcv->drawCar(true);
  timer->start(traceToPlay.timestep); 
}

void MainView::loadTrace(const string &trace) {
  ifstream ifs(trace.c_str());
  if (not ifs) {
    cerr << "Trace file '" << trace << "' does not exist." << endl;
    return;
  }
  ifs >> traceToPlay;
  timer->start(traceToPlay.timestep);
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

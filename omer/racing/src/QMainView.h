// -*- mode: c++; -*-
#ifndef QMAINVIEW_H
#define QMAINVIEW_H

#include <QApplication>
#include <QMainWindow>
#include <QAction>
#include <QMenu>
#include <iostream>
#include "QCircuitView.h"
#include "trace.h"

using namespace std;

class MainView : public QMainWindow {
  Q_OBJECT

public:
  MainView();
  void loadCircuit(const string &fname);
  void saveCircuit(const string &fname);
  void loadTrace(const string &trace);

  void saveImage(const string &name, int width, int height);
  void getCircuitSize(int &width, int &height);

private:
  string filename;
  CircuitView *qcv;
  Trace traceToPlay;

  QMenu *fileMenu;
  QAction *loadAct;
  QAction *saveAct;
  QAction *playAct;
  QTimer *timer;
  int icar;

private slots:
  void loadFile();
  void saveFile();
  void playTrace();
  void avanzaCar();
};

#endif


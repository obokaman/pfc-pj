// -*- mode: c++; -*-
#ifndef QMAINVIEW_H
#define QMAINVIEW_H

#include <QApplication>
#include <QMainWindow>
#include <QAction>
#include <QMenu>
#include <iostream>
#include "QCircuitView.h"

using namespace std;

class MainView : public QMainWindow {
  Q_OBJECT

public:
  MainView();
  void loadCircuit(const string &fname);
  void saveCircuit(const string &fname);

private:
  string filename;
  CircuitView *qcv;

  QMenu *fileMenu;
  QAction *loadAct;
  QAction *saveAct;

private slots:
  void loadFile();
  void saveFile();
};

#endif


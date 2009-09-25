// -*- mode: c++; -*-
#ifndef QCIRCUITVIEW_H
#define QCIRCUITVIEW_H

#include <QWidget>
#include "corbes.h"

using namespace std;

class CircuitView: public QWidget {
  Q_OBJECT
  
  Circuit c;
  bool showControlPoints;

  public:
  CircuitView(QWidget *parent = 0);
  void load(const Circuit &extc);
  void paintEvent(QPaintEvent *);
};

#endif

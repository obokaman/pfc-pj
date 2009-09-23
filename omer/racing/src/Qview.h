// -*- mode: c++; -*-
#ifndef QVIEW_H
#define QVIEW_H

#include <QWidget>
#include "bezier.h"

using namespace std;

class View: public QWidget {
  Q_OBJECT
  
  Circuito c;

  public:
  View(QWidget *parent = 0);
  void load(const Circuito &extc);
  void paintEvent(QPaintEvent *);
};

#endif

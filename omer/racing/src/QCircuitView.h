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
  void drawCircuit(QPainter *painter);
  Circuit getCircuit();
  void setCar(double x, double y, double alpha) {
    carx = x, cary = y, caralpha = alpha;
    update();
  }
  void drawCar(bool flag) {
    drawcar = flag;
    update();
  }
	      

protected:
  void mousePressEvent(QMouseEvent *event);
  void mouseMoveEvent(QMouseEvent *event);
  void mouseReleaseEvent(QMouseEvent *event);
  void paintEvent(QPaintEvent *);
  void resizeEvent(QResizeEvent *event);
  void updateImage(const QSize &newSize);

private:
  QImage image;
  QTransform transform;
  bool pressed;
  int pressx, pressy;
  int presspoint;

  double carx, cary, caralpha;
  bool drawcar;
};

#endif

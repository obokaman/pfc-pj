// -*- mode: c++; -*-
#include <QtGui>
#include "QCircuitView.h"
#include "common.h"

CircuitView::CircuitView(QWidget *parent): QWidget(parent) {
  setMinimumSize(500,500);
  setWindowTitle("racing-view");
  showControlPoints = true;
}

void CircuitView::load(const Circuit &extc) {
  c = extc;
}

void CircuitView::paintEvent(QPaintEvent *) {
  const double ANCHO_CARRETERA = 30;
  const double ANCHO_BORDILLO = 8;

  QPainter painter(this);
  QPen pblack = painter.pen();
  pblack.setColor(QColor("dimgray"));
  pblack.setWidth(ANCHO_CARRETERA*2 - 2);
  painter.setPen(pblack);

  QPen pred = painter.pen();
  pred.setColor(QColor("red"));
  pred.setWidth(ANCHO_BORDILLO);

  QPen pwhite = painter.pen();
  pwhite.setColor(QColor("white"));
  pwhite.setWidth(ANCHO_BORDILLO);

  QPen pfullblack = painter.pen();
  pfullblack.setColor(QColor("black"));
  pfullblack.setWidth(ANCHO_BORDILLO/4.0);

  int side = min(width(), height());
  
  painter.translate((width()-side)/2, (height()-side)/2);
  painter.scale(side/1000.0, side/1000.0);

  double STEP = 2.5;
  double d = STEP;
  Punt oldp = c.getPos(d);
  double dist = c.getDist();

  painter.setRenderHint(QPainter::Antialiasing, false);
  painter.setPen(pblack);
  while (d<dist) {
    Punt p(c.getPos(d));

    painter.drawLine(oldp.x, oldp.y, p.x, p.y);

    oldp = p;
    d += STEP;
  }

  //segunda pasada
  d = STEP;
  oldp = c.getPos(0);
  Punt olddir(c.getDir(0));
  Punt oldperp(-olddir.y, olddir.x);

  painter.setRenderHint(QPainter::Antialiasing);
  int i = 0;
  while (d<dist) {
    Punt p = c.getPos(d);
    Punt dir = c.getDir(d);
    Punt perp(-dir.y, dir.x);

    if (++i%6 > 2) painter.setPen(pwhite);
    else painter.setPen(pred);
    painter.drawLine(oldp.x+oldperp.x*(ANCHO_CARRETERA+4),
		     oldp.y+oldperp.y*(ANCHO_CARRETERA+4),
		     p.x+perp.x*(ANCHO_CARRETERA+4),
		     p.y+perp.y*(ANCHO_CARRETERA+4));

    painter.drawLine(oldp.x-oldperp.x*(ANCHO_CARRETERA+4),
		     oldp.y-oldperp.y*(ANCHO_CARRETERA+4),
		     p.x-perp.x*(ANCHO_CARRETERA+4),
		     p.y-perp.y*(ANCHO_CARRETERA+4));

    painter.setPen(pfullblack);
    painter.drawLine(oldp.x,
		     oldp.y,
		     p.x,
		     p.y);

    oldperp = perp;
    oldp = p;
    d += STEP;
  }

  //puntos de control
  if (showControlPoints) {
    For(i, c.n) {
      painter.setPen(pfullblack);
      For(j, 3) {
	const Punt &p = c.vp[3*i+j];
	painter.drawLine(p.x+10, p.y+10,
			 p.x-10, p.y-10);
	painter.drawLine(p.x+10, p.y-10,
			 p.x-10, p.y+10);
      }
      painter.setPen(pfullblack);
      Punt p1 = c.vp[3*i], p2 = c.vp[3*i+1];
      painter.drawLine(p1.x, p1.y, p2.x, p2.y);

      p1 = c.vp[3*i+2], p2 = c.vp[3*i+3];
      painter.drawLine(p1.x, p1.y, p2.x, p2.y);

      //      painter.setPen(pred);
      //      p1 = c.vp[3*i+1], p2 = c.vp[3*i+2];
      //      painter.drawLine(p1.x, p1.y, p2.x, p2.y);
    }
  }
}

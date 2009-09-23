// -*- mode: c++; -*-
#include <QtGui>
#include "Qview.h"
#include "common.h"

View::View(QWidget *parent): QWidget(parent) {
  setMinimumSize(600,600);
  setWindowTitle("racing-view");
}

void View::load(const Circuito &extc) {
  c = extc;
}

void View::paintEvent(QPaintEvent *) {
  const double ANCHO_CARRETERA = 30;
  const double ANCHO_BORDILLO = 10;

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
  Punto oldp = c.coord(0);

  painter.setRenderHint(QPainter::Antialiasing, false);
  painter.setPen(pblack);
  while (d<c.dist()) {
    double t = c.d2t(d);
    Punto p = c.coord(t);
    //    Punto dir = c.circdir(t);
    //    Punto perp(-dir.y, dir.x);

    painter.drawLine(oldp.x, oldp.y, p.x, p.y);

    oldp = p;
    d += STEP;
  }

  //segunda pasada
  d = STEP;
  oldp = c.coord(0);
  Punto olddir = c.circdir(0);
  Punto oldperp(-olddir.y, olddir.x);

  painter.setRenderHint(QPainter::Antialiasing);
  int i = 0;
  while (d<c.dist()) {
    double t = c.d2t(d);
    Punto p = c.coord(t);
    Punto dir = c.circdir(t);
    Punto perp(-dir.y, dir.x);

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




  /*  d = 0;
  while (d<c.dist()) {
    double t = c.d2t(d);
    Punto p = c.coord(t);


    if (int(d)%20<10) painter.setPen(pred);
    else painter.setPen(pwhite);

    painter.drawLine(perpdir.x*(ANCHO+2)+p.x, 1000-(perpdir.y*(ANCHO+2)+p.y),
		     perpdir.x*(ANCHO-2)+p.x, 1000-(perpdir.y*(ANCHO-2)+p.y));
    painter.drawLine(-perpdir.x*(ANCHO+2)+p.x, 1000-(-perpdir.y*(ANCHO+2)+p.y),
		     -perpdir.x*(ANCHO-2)+p.x, 1000-(-perpdir.y*(ANCHO-2)+p.y));

    d += STEP;
    }*/
}

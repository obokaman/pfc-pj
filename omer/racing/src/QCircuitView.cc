// -*- mode: c++; -*-
#include <QtGui>
#include "QCircuitView.h"
#include "common.h"

CircuitView::CircuitView(QWidget *parent): QWidget(parent) {
  setMinimumSize(500,500);
  showControlPoints = true;
  pressed = false;
}

Circuit CircuitView::getCircuit() {return c;}

void CircuitView::load(const Circuit &extc) {
  c = extc;
  updateImage(QSize(width(), height()));
}

void CircuitView::drawCircuit(QPainter *painter) {
  const double ANCHO_CARRETERA = 30;
  const double ANCHO_BORDILLO = 8;

  QPen pblack = painter->pen();
  pblack.setColor(QColor("dimgray"));
  pblack.setWidth(ANCHO_CARRETERA*2 - 2);
  painter->setPen(pblack);

  QPen pred = painter->pen();
  pred.setColor(QColor("red"));
  pred.setWidth(ANCHO_BORDILLO);

  QPen pwhite = painter->pen();
  pwhite.setColor(QColor("white"));
  pwhite.setWidth(ANCHO_BORDILLO);

  QPen pfullblack = painter->pen();
  pfullblack.setColor(QColor("black"));
  pfullblack.setWidth(ANCHO_BORDILLO/4.0);

  int side = min(painter->device()->width(),
		 painter->device()->height());
  
  painter->translate((painter->device()->width()-side)/2,
		     (painter->device()->height()-side)/2);
  painter->scale(side/1000.0, side/1000.0);

  double STEP = 2.5;
  double d = STEP;
  Punt oldp = c.getPos(d);
  double dist = c.getDist();

  painter->setRenderHint(QPainter::Antialiasing, false);
  painter->setPen(pblack);
  while (d<dist) {
    Punt p(c.getPos(d));

    painter->drawLine(oldp.x, oldp.y, p.x, p.y);

    oldp = p;
    d += STEP;
  }

  //segunda pasada
  d = STEP;
  oldp = c.getPos(0);
  Punt olddir(c.getDir(0));
  Punt oldperp(-olddir.y, olddir.x);

  painter->setRenderHint(QPainter::Antialiasing);
  int i = 0;
  while (d<dist) {
    Punt p = c.getPos(d);
    Punt dir = c.getDir(d);
    Punt perp(-dir.y, dir.x);

    if (++i%6 > 2) painter->setPen(pwhite);
    else painter->setPen(pred);
    painter->drawLine(oldp.x+oldperp.x*(ANCHO_CARRETERA+4),
		     oldp.y+oldperp.y*(ANCHO_CARRETERA+4),
		     p.x+perp.x*(ANCHO_CARRETERA+4),
		     p.y+perp.y*(ANCHO_CARRETERA+4));

    painter->drawLine(oldp.x-oldperp.x*(ANCHO_CARRETERA+4),
		     oldp.y-oldperp.y*(ANCHO_CARRETERA+4),
		     p.x-perp.x*(ANCHO_CARRETERA+4),
		     p.y-perp.y*(ANCHO_CARRETERA+4));

    painter->setPen(pfullblack);
    painter->drawLine(oldp.x,
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
      painter->setPen(pfullblack);
      For(j, 3) {
	const Punt &p = c.vp[3*i+j];
	painter->drawLine(p.x+10, p.y+10,
			 p.x-10, p.y-10);
	painter->drawLine(p.x+10, p.y-10,
			 p.x-10, p.y+10);
      }
      painter->setPen(pfullblack);
      Punt p1 = c.vp[3*i], p2 = c.vp[3*i+1];
      painter->drawLine(p1.x, p1.y, p2.x, p2.y);

      p1 = c.vp[3*i+2], p2 = c.vp[3*i+3];
      painter->drawLine(p1.x, p1.y, p2.x, p2.y);

      //      painter.setPen(pred);
      //      p1 = c.vp[3*i+1], p2 = c.vp[3*i+2];
      //      painter.drawLine(p1.x, p1.y, p2.x, p2.y);
    }
  }
}

void CircuitView::paintEvent(QPaintEvent *) {
  QPainter painter(this);
  painter.drawImage(QPoint(0,0), image);
  if (pressed) {
    Punt p;
    transform.map(c.vp[presspoint].x, c.vp[presspoint].y,
		  &p.x, &p.y);
    painter.setPen(QPen(QColor(0,0,255), 2));
    painter.drawLine(p.x+10, p.y+10,
		     p.x-10, p.y-10);
    painter.drawLine(p.x+10, p.y-10,
		     p.x-10, p.y+10);

    painter.setPen(QPen(QColor(0,255,255), 2));
    painter.drawEllipse(QRectF(pressx-5, pressy-5, 10, 10));
  }
}


void CircuitView::resizeEvent(QResizeEvent *event) {
  //  if (width() > image.width() || height() > image.height()) {
  //    resizeImage(&image, QSize(width()+128, height()+128));
  //  }
  QWidget::resizeEvent(event);
}

void CircuitView::updateImage(const QSize &newSize) {
  QImage newImage(newSize, QImage::Format_RGB32);
  newImage.fill(qRgb(128, 255, 128));
  QPainter painter(&newImage);

  //actually drawing the image, with the corresponding sizes
  drawCircuit(&painter);

  //store the world transform
  transform = painter.worldTransform();
  //update the image
  image = newImage;
}

void CircuitView::mousePressEvent(QMouseEvent *event) {
  pressed = false;
  pressx = event->x();
  pressy = event->y();
  
  int mapx, mapy;
  transform.inverted().map(pressx, pressy, &mapx, &mapy);
  int bestp = -1;
  double bestdist;
  For(i, c.vp.size()-1) {
    double dist = (mapx-c.vp[i].x)*(mapx-c.vp[i].x) + 
      (mapy-c.vp[i].y)*(mapy-c.vp[i].y); 
    if (dist < 2500 and 
	(bestp == -1 or dist < bestdist)) {
      bestp = i;
      bestdist = dist;
    }
  }
  if (bestp!=-1) {
    presspoint = bestp;
    pressed = true;
    update();
  }

}

void CircuitView::mouseReleaseEvent(QMouseEvent *event) {
  if (pressed) {
    int mapx, mapy;
    transform.inverted().map(event->x(), event->y(), &mapx, &mapy);
    if (presspoint%3 == 0) {
      c.vpd[presspoint/3].p.x = mapx;
      c.vpd[presspoint/3].p.y = mapy;
      c.precalcula();
      updateImage(QSize(width(), height()));
    } else if (presspoint%3 == 1) {
      Punt p(c.vpd[presspoint/3].p.x, c.vpd[presspoint/3].p.y);
      Punt q(mapx, mapy);
      
      c.vpd[presspoint/3].alpha = atan2(q.y-p.y, q.x-p.x);
      c.vpd[presspoint/3].fpost = (q-p).mida() /
	(c.vpd[presspoint/3].p - c.vpd[(presspoint/3+1)%c.n].p).mida();
      c.precalcula();
      updateImage(QSize(width(), height()));
    } else {
      Punt p(c.vpd[(presspoint/3+1)%c.n].p.x,
	     c.vpd[(presspoint/3+1)%c.n].p.y);
      Punt q(mapx, mapy);
      
      c.vpd[(presspoint/3+1)%c.n].alpha = atan2(p.y-q.y, p.x-q.x);
      c.vpd[(presspoint/3+1)%c.n].fpre = (q-p).mida() /
	(c.vpd[presspoint/3].p - c.vpd[(presspoint/3+1)%c.n].p).mida();
      c.precalcula();
      updateImage(QSize(width(), height()));
    }    
    pressed = false;
    update();
  }
}

void corre() {
  while(1) {
    double maxvel = getMaxVel(100);
    double maxvel2 = getMaxVel(0);
    setVel(min(maxvel, maxvel2)-20);
  }
}


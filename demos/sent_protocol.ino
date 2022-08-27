// SENT protocol demonstration code written by Dmitriy Levchenkov.
// This code comes with no guarantees or assurances.
// You are free to use and modify this code by retaining the following line:
// *** Based on code produced by http://arithmechanics.com/software/SENTDemo/ ***

const byte pinOut = 3;

const byte idleState = HIGH;

void setup() {
  pinMode(pinOut, OUTPUT);
  digitalWrite(pinOut,idleState);
}

void loop() {
  noInterrupts();
  //Sync pulse
  digitalWrite(pinOut, !idleState);
  delayMicroseconds(150);
  digitalWrite(pinOut, idleState);
  delayMicroseconds(1530);

  //Status nibble
  digitalWrite(pinOut, !idleState);
  delayMicroseconds(150);
  digitalWrite(pinOut, idleState);
  delayMicroseconds(480);

  //Data nibble 1
  digitalWrite(pinOut, !idleState);
  delayMicroseconds(150);
  digitalWrite(pinOut, idleState);
  delayMicroseconds(210);

  //Data nibble 2
  digitalWrite(pinOut, !idleState);
  delayMicroseconds(150);
  digitalWrite(pinOut, idleState);
  delayMicroseconds(210);

  //Data nibble 3
  digitalWrite(pinOut, !idleState);
  delayMicroseconds(150);
  digitalWrite(pinOut, idleState);
  delayMicroseconds(240);

  //Data nibble 4
  digitalWrite(pinOut, !idleState);
  delayMicroseconds(150);
  digitalWrite(pinOut, idleState);
  delayMicroseconds(630);

  //Data nibble 5
  digitalWrite(pinOut, !idleState);
  delayMicroseconds(150);
  digitalWrite(pinOut, idleState);
  delayMicroseconds(660);

  //Data nibble 6
  digitalWrite(pinOut, !idleState);
  delayMicroseconds(150);
  digitalWrite(pinOut, idleState);
  delayMicroseconds(660);

  //CRC nibble
  digitalWrite(pinOut, !idleState);
  delayMicroseconds(150);
  digitalWrite(pinOut, idleState);
  delayMicroseconds(510);

  //Pause pulse
  digitalWrite(pinOut, !idleState);
  delayMicroseconds(150);
  digitalWrite(pinOut, idleState);
  interrupts();
  delayMicroseconds(2850);

  delay(1000);
}

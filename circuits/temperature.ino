/**************************************************************
Project: Measure Temperature
Description:
    Monitor temperature using DS18B20 (Digital Temperature Sensor)
    connected to an Arduino

Reference:
https://create.arduino.cc/projecthub/TheGadgetBoy/ds18b20-digital-temperature-sensor-and-arduino-9cc806

Copyright © 2016 — 2022 Randolph Ledesma https://gitlab.com/randop
**************************************************************/

/***
 * Install OneWire and DallasTemperature libraries on Arduino IDE
 ***/
#include <OneWire.h>
#include <DallasTemperature.h>

/***
 * Setup sensor at digital pin 2 and initialize DallasTemperature
 ***/
#define ONE_WIRE_BUS 2
OneWire oneWire(ONE_WIRE_BUS);
DallasTemperature sensors(&oneWire);

// Variables
float celsius=0;
float fahrenheit=0;
char buffer[100];
char str_celsius[10];
char str_fahrenheit[10];

void setup(void)
{
  Serial.begin(115200);

  /***
   * Start up the library
   ***/
   sensors.begin();
}

void loop(void)
{

 /***
  * Invoke sensors.requestTemperatures() function
  * to get the temperature from the sensor
  ***/
  sensors.requestTemperatures();
  celsius = sensors.getTempCByIndex(0);
  fahrenheit = sensors.toFahrenheit(celsius);
  dtostrf(celsius, 6, 3, str_celsius);
  dtostrf(fahrenheit, 6, 3, str_fahrenheit);
  sprintf(buffer, "The temperature is %s celsius %s fahrenheit", str_celsius, str_fahrenheit);
  Serial.println(buffer);
  delay(1000);
}

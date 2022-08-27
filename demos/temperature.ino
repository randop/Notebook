/********************************************************************/
#include <OneWire.h>
#include <DallasTemperature.h>
/********************************************************************/
// Data wire is plugged into pin 2 on the Arduino
#define ONE_WIRE_BUS 2
/********************************************************************/
// Setup a oneWire instance to communicate with any OneWire devices
// (not just Maxim/Dallas temperature ICs)
OneWire oneWire(ONE_WIRE_BUS);
/********************************************************************/
// Pass our oneWire reference to Dallas Temperature.
DallasTemperature sensors(&oneWire);
/********************************************************************/

float celsius=0;
float fahrenheit=0;
char buffer[100];

char str_celsius[10];
char str_fahrenheit[10];

void setup(void)
{
 // start serial port
 Serial.begin(115200);
 // Start up the library
 sensors.begin();
}

void loop(void)
{
  // call sensors.requestTemperatures() to issue a global temperature
  // request to all devices on the bus
  /********************************************************************/
  sensors.requestTemperatures(); // Send the command to get temperature readings
  celsius = sensors.getTempCByIndex(0);
  fahrenheit = sensors.toFahrenheit(celsius);
  dtostrf(celsius, 6, 3, str_celsius);
  dtostrf(fahrenheit, 6, 3, str_fahrenheit);
  sprintf(buffer, "The temperature is %s celsius %s fahrenheit", str_celsius, str_fahrenheit);
  Serial.println(buffer);
  delay(1000);
} 

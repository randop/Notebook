# C++ program for connecting Arduino UNO to USB 2.0 UART TTL 6 pins module (CP2102) using serial communication in MacOS or Linux
## 1.) C++ Program
```
/**

A simple C++ application to read values from serial port of USB UART TTL module.

Project setup:
1.) Create a new project in Xcode
2.) Select macOS -> Command Line Tool

References:
https://www.cmrr.umn.edu/~strupp/serial.html
https://www.rapidtables.com/code/text/ascii-table.html

*/
```
```cpp
#include <iostream>
#include <termios.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/ioctl.h>

#define BAUDRATE9600 B9600

#define PORT "/dev/tty.usbserial-0001"

using namespace std;

int main ()
{
    int fd;
    fd = open(PORT, O_RDWR | O_NOCTTY | O_NDELAY);
    fcntl(fd, F_SETFL, FNDELAY);
    
    struct termios options;
    tcgetattr(fd, &options);
    cfsetispeed(&options, B9600);
    cfsetospeed(&options, B9600);
    options.c_cflag |= (CLOCAL | CREAD);
    options.c_cflag &= ~CRTSCTS;
    options.c_cflag &= ~CSIZE; /* Mask the character size bits */
    options.c_cflag |= CS8;    /* Select 8 data bits */
    options.c_iflag &= ~(IXON | IXOFF | IXANY);
    options.c_cc[VMIN]  = 0;
    options.c_cc[VTIME] = 10;
    tcsetattr(fd, TCSANOW, &options);
    
    char buffer[6];  /* Input buffer */
    int  nbytes;     /* Number of bytes read */
    bool isDoneReading(false);
    unsigned int nSize = (int) sizeof(buffer);
    do {
        nbytes = read(fd, buffer, nSize);
        for(int i=0; i<strlen(buffer); i++){
            std::cout << " " << (int) buffer[i];
        }
        std::cout << " " << (int) buffer[nSize - 1];
        if ((int) buffer[0]==1 && (int) buffer[nSize - 1]==0) {
            isDoneReading = true;
        } else {
            usleep(1500000); //Read interval of 15 seconds
        }
    } while (!isDoneReading);

    std::cout << std::endl;
    
    close(fd);
    
    std::cout << std::endl << "DONE" << std::endl;
    return 0;
}
```
## 2.) Arduino Sketch
```
Project setup:
1.) Create a new Arduino Sketch on the IDE
2.) Disconnect any pins connected on the Arduino
3.) Upload this sketch
4.) Connect the Arduino TX pin to USB UART RX pin
5.) Connect the Arduino RX pin to USB UART TX pin
```
```c
#include <SoftwareSerial.h>

byte buf[6]={1,37,67,12,76,0};

void setup() {
  
  // put your setup code here, to run once:
  Serial.begin(9600);  
  while (!Serial) {
    ; // wait for serial port to connect. Needed for Native USB only
  }
  pinMode(LED_BUILTIN, OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
  digitalWrite(LED_BUILTIN, HIGH); 
  Serial.write(buf, sizeof(buf));
  delay(250);
  digitalWrite(LED_BUILTIN, LOW);
}
```
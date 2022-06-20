/**
 * https://www.geeksforgeeks.org/cc-program-shutdown-system/
 */

// C program to shutdown in Linux
#include <stdio.h>
#include <stdlib.h>
  
int main()
{
   // Running Linux OS command using system
   system("shutdown -P now");
    
   return 0;
}
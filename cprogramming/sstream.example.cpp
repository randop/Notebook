/**
 * Reference:
 * https://www.cprogramming.com/tutorial/c++-iostreams.html
 * 
 * To compile:
 * g++ sstream.example.cpp -o sstream.bin
 */

#include <iostream>
#include <sstream>
 
using namespace std;
 
int main() {
        stringstream the_stream(ios::in | ios::out);
        string dat("This is an example string with a double data: 74.79 .");
 
        the_stream.str(dat);
        the_stream.seekg(-7, ios::end);
 
        double val;
        the_stream >> val;
 
        the_stream.seekp(-7, ios::end);
        the_stream << val;
 
        string new_val = the_stream.str();
        cout << new_val << endl;
 
        return 0;
}

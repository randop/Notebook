# [SOLVED] Check if a stringstream is empty or null in C++
## Source code
```cpp
#include <iostream>
#include <sstream>

int main()
{
    std::stringstream string1;
    if (string1.tellp() == std::streampos(0)) {
        std::cout << "string1 is empty" << std::endl;
    } else {
        std::cout << "string1 is not empty" << std::endl;
    }
    
    std::stringstream string2;
    string2 << "Hello";
    if (string2.tellp() == std::streampos(0)) {
        std::cout << "string2 is empty" << std::endl;
    } else {
        std::cout << "string2 is not empty" << std::endl;
    }
    
    std::stringstream string3;
    string3 << "";
    if (string3.tellp() == std::streampos(0)) {
        std::cout << "string3 is empty" << std::endl;
    } else {
        std::cout << "string3 is not empty" << std::endl;
    }

    return 0;
}
```
---
## Output
```
string1 is empty
string2 is not empty
string3 is empty
```
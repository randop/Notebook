# char

### Character array assignment
```cpp
// Option 1 — classic, idiomatic for C-string buffers
std::strcpy(result.ip, "172.17.0.1");

// Option 2 — bounds-safe (C++23)
std::strncpy(result.ip, "172.17.0.1", sizeof(result.ip) - 1);
result.ip[sizeof(result.ip) - 1] = '\0';
```

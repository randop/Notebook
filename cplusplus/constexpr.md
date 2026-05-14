# constexpr

`constexpr` implies `const` since C++11

```cpp
constexpr int x = 42;        // correct
constexpr const int x = 42;  // redundant, but not wrong
```

The only edge case is pointers:
```cpp
constexpr const char *p = "hello";  // p is constexpr, pointee is const char
constexpr char *p = "hello";        // p is constexpr, pointee is char (writable)
```

It is not redundant on the const applies to what p points to, not to p itself

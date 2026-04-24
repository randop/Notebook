# Errors

### Using `std::error_code`
```cpp
// default-constructs to "no error" (value 0)
std::error_code ec{};

// resets to value=0, category=system_category
ec.clear();
```

### Using `std::errc `
```cpp
// zero-initialized, which is "no error"
std::errc ec{};

// assign back to zero
ec = std::errc{};

// or cast from 0:
ec = static_cast<std::errc>(0);

if (ec == std::errc{})   // no error
if (ec != std::errc{})   // some error

if (std::make_error_code(ec))   // true = error
```

#### Remove anchor tags on string
```php
$str = "<a>bing.com</a>";
echo preg_replace('#<a.*?>([^>]*)</a>#i', '$1', $str)
```
***
#### Using Reflection to access protected properties
```php
$class = new ReflectionClass($classInstance);
$property = $class->getProperty("properties");
$property->setAccessible(true);
$properties = $property->getValue($number);
```
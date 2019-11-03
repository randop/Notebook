#### Remove anchor tags on string
```php
$str = "<a>bing.com</a>";
echo preg_replace('#<a.*?>([^>]*)</a>#i', '$1', $str)
```
***

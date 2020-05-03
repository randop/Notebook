Error:
android.view.ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.

Solution:
Prefer the Main dispatcher for root coroutine

Example:


References:
https://proandroiddev.com/kotlin-coroutines-patterns-anti-patterns-f9d12984c68e
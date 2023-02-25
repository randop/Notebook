```
Message message = new Message("/remote/object/path", "MethodName", arg1, arg2);
Connection connection = getBusConnection();
connection.send(message);
Message reply = connection.waitForReply(message);
if (reply.isError()) {

} else {
    Object returnValue = reply.getReturnValue();
}

Proxy proxy = new Proxy(getBusConnection(), "/remote/object/path");
Object returnValue = proxy.MethodName(arg1, arg2);
```

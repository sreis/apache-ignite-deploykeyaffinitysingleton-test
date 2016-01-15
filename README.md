# apache-ignite-deploykeyaffinitysingleton-test
Test case that shows deployKeyAffinitySingleton failing when running in a single node.

### Build package

```
mvn package
```


### Run Instructions

```
mvn exec:java
```

This starts an Apache Ignite node and attempts to run a service.

It should fail with the following exception

```
java.lang.reflect.InvocationTargetException
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:497)
	at org.codehaus.mojo.exec.ExecJavaMojo$1.run(ExecJavaMojo.java:293)
	at java.lang.Thread.run(Thread.java:745)
Caused by: java.lang.RuntimeException: Service not deployed.
	at com.example.testcase.Application.test(Application.java:19)
	at com.example.testcase.Application.main(Application.java:37)
```

In another terminal start a standalone node with `mvn exec:java -Dexec.args=standalone` and run `mvn exec:java` again. You
should see something like the following:

```
2016-01-15 15:42:14 [srvc-deploy-#55%null%] INFO  com.example.testcase.TestService - Service init!
2016-01-15 15:42:14 [ignite-#63%null%] INFO  com.example.testcase.TestService - Service execute!
2016-01-15 15:42:15 [ignite-#63%null%] INFO  com.example.testcase.TestService - Service executing something important...
```

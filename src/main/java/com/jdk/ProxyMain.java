package com.jdk;

import java.lang.reflect.Proxy;

public class ProxyMain {

    public static void main(String[] args) {
        IHelloService obj=new HelloService();

        IHelloService targetObj=(IHelloService) Proxy.newProxyInstance(obj.getClass().getClassLoader(),new Class[]{IHelloService.class},new HelloProxy(obj));
        targetObj.sayHello("bbb");
    }
}

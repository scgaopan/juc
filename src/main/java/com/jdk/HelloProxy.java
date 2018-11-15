package com.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HelloProxy implements InvocationHandler{
    private  IHelloService target;

    public HelloProxy(IHelloService service){
        this.target=service;

    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        Object obj=method.invoke(target,args);
        System.out.println("after");
        return obj;
    }

}

package com.jdk;

public class HelloService implements  IHelloService{
    public void sayHello(String str) {
        System.out.println("hello:"+str);
    }
}

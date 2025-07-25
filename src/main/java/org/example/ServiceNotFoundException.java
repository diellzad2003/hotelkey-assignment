package org.example;

public class ServiceNotFoundException extends Exception {
    public ServiceNotFoundException() { super(); }
    public ServiceNotFoundException(String msg) { super(msg); }
}


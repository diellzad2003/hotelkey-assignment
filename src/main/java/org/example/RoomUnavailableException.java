package org.example;
public class RoomUnavailableException extends Exception {
    public RoomUnavailableException() { super(); }
    public RoomUnavailableException(String msg) { super(msg); }
}
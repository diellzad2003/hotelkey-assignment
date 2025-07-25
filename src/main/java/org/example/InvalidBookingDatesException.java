package org.example;
public class InvalidBookingDatesException extends Exception {
    public InvalidBookingDatesException() { super(); }
    public InvalidBookingDatesException(String msg) { super(msg); }
}

package org.example;

public class DuplicateGuestBookingException extends Exception {
    public DuplicateGuestBookingException(String guestId) {
        super("Duplicate booking found for guest ID: " + guestId);
    }
}

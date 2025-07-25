package org.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Booking {
    private String bookingId;
    private Room room;
    private Guest guest;
    private LocalDate checkIn;
    private LocalDate checkOut;

    public Booking(String bookingId, Room room, Guest guest, LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date.");
        }

        if (room.isBooked()) {
            throw new IllegalStateException("Room " + room.getId() + " is already booked.");
        }

        this.bookingId = bookingId;
        this.room = room;
        this.guest = guest;
        this.checkIn = checkIn;
        this.checkOut = checkOut;

        room.book();
    }

    public String getBookingId() {
        return bookingId;
    }

    public Room getRoom() {
        return room;
    }

    public Guest getGuest() {
        return guest;
    }

    public LocalDate getCheckInDate() {
        return checkIn;
    }

    public LocalDate getCheckOutDate() {
        return checkOut;
    }

    public BigDecimal getTotalCost() {
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        return BigDecimal.valueOf(room.getRate()).multiply(BigDecimal.valueOf(nights));

    }

    @Override
    public String toString() {
        return String.format(
                "Booking{id=%s, guest=%s, room=%s, from=%s to=%s, total=%.2f}",
                bookingId,
                guest.getFullName(),
                room.getId(),
                checkIn,
                checkOut,
                getTotalCost()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;
        Booking booking = (Booking) o;
        return bookingId.equals(booking.bookingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId);
    }
}

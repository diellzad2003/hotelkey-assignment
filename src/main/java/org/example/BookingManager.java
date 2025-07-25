package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class BookingManager {
    private List<Booking> bookings;

    public BookingManager() {
        this.bookings = new ArrayList<>();
    }

    public void addBooking(Booking booking) {
        if (booking == null) {
            System.out.println("Cannot add null booking.");
            return;
        }
        bookings.add(booking);
        System.out.println("Booking added: " + booking);
    }

    public Room findFirstAvailableRoom(Room[] rooms) {
        for (Room room : rooms) {
            boolean isBooked = false;

            for (Booking booking : bookings) {
                if (booking.getRoom().equals(room)) {
                    isBooked = true;
                    break;
                }
            }

            if (!isBooked) {
                return room;
            }
        }

        System.out.println("All rooms are occupied.");
        return null;
    }

    public List<Booking> getBookings() {
        return bookings;
    }
    public void printBookedRoomsGuests(Room[] rooms) {
        for (Room room : rooms) {
            for (Booking booking : bookings) {
                if (booking.getRoom().equals(room)) {
                    Guest guest = booking.getGuest();
                    System.out.println("Room " + room.getId() + " is booked by: " +
                            guest.getFullName());
                    break;
                }
            }
        }
    }
    public void validateBookingDates(String[] checkInDates, String[] checkOutDates) throws InvalidBookingDatesException {
        if (checkInDates.length != checkOutDates.length) {
            throw new InvalidBookingDatesException("Mismatched array lengths: check-in and check-out arrays must have the same length.");
        }

        for (int i = 0; i < checkInDates.length; i++) {
            try {
                LocalDate checkIn = LocalDate.parse(checkInDates[i]);
                LocalDate checkOut = LocalDate.parse(checkOutDates[i]);

                if (!checkOut.isAfter(checkIn)) {
                    throw new InvalidBookingDatesException(
                            "Invalid booking dates at index " + i + ": Check-out (" + checkOut + ") is not after check-in (" + checkIn + ")."
                    );
                }
            } catch (DateTimeParseException e) {
                throw new InvalidBookingDatesException("Invalid date format at index " + i + ": " + e.getMessage());
            }
        }


    }
    public Room getEarliestUnbookedRoom(Room[] rooms, LocalDate startDate) {
        for (Room room : rooms) {

            if (room.isAvailable(startDate, startDate.plusDays(1))) {
                return room;
            }
        }
        System.out.println("No available room found starting from " + startDate);
        return null;
    }


}

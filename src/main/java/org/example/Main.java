package org.example;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        BookingManager bookingManager = new BookingManager();

        Room r1 = new Room("101", "Standard", 1989.09);
        Room r2 = new Room("102", "Deluxe", 2003.97);

        Room[] allRooms = { r1, r2 };
        try {
            hotel.addRoomWithCheck(r1);
            hotel.addRoomWithCheck(r2);

            hotel.addRoomWithCheck(r1);
        } catch (DuplicateRoomException e) {
            System.out.println("Failed to add room: " + e.getMessage());
        }
        hotel.addRoom(r1);
        hotel.addRoom(r2);
        List<Room> allRoomsAvailable = hotel.getAllAvailableRooms();


        System.out.println("Hotel Guests: Taylor Swift and Artemis Star");


        try {
            hotel.addRoomWithCheck(r1);
            hotel.addRoomWithCheck(r1);

        } catch (DuplicateRoomException e) {
            System.out.println("Your booking failed: " + e.getMessage());
        }

        try {
            hotel.bookRoom(101);
        } catch (RoomUnavailableException e) {
            System.out.println("Your booking failed: " + e.getMessage());
        }


        Guest guest = new Guest("G1", "Taylor", "Swift", "tswift@email.com", "Singer");

        HotelService spa = new SpaTreatment("S1", "Massage", 80, guest);
        HotelService laundry = new LaundryService("L1", "Dresses and Jeans", 20,guest);
        char[] discountCodesToValidate = {'A', '*', 'Z', '3'};
        spa.validateDiscountCodes(discountCodesToValidate);

        hotel.addService(spa);
        hotel.addService(laundry);

        Staff frontDesk = new FrontDeskStaff("F1", "Regina", "Reception");
        Staff housekeeping = new HousekeepingStaff("H1", "Maria", "Cleaning");
        hotel.addStaff(frontDesk);
        hotel.addStaff(housekeeping);

        hotel.displayRooms();
        hotel.displayServices();
        hotel.displayStaff();

        List<Chargeable> charges = new ArrayList<>();
        charges.add(r1);
        charges.add(spa);
        charges.add(laundry);
        System.out.println("Your total Charges: " + hotel.calculateTotalCharges(charges));

        frontDesk.performDuties();
        housekeeping.performDuties();

        boolean[] weekOccupancy = {false, false, false, true, false, true, true};
        boolean result = r1.wasVacantForThreeDays(weekOccupancy);
        System.out.println("Room " + r1.getId() + " vacant for 3+ days: " + result);

        char[] template = {'*', '-', '#'};
        System.out.println(guest.buildGreeting(template));

        int[] taskPriorities = {5, 3, 1, 4, 2};
        int highestIndex = frontDesk.getHighestPriorityTaskIndex(taskPriorities);
        System.out.println("Highest priority task is at index: " + highestIndex + " (priority = " + taskPriorities[highestIndex] + ")");

        System.out.println("Original rate: " + r1.getRate());

        char[] discountCodes = {'A', 'B'};
        r1.applyDiscounts(discountCodes);

        System.out.println("Discounted rate: " + r1.getRate());

        LocalDate checkIn1 = LocalDate.now();
        LocalDate checkOut1 = LocalDate.now().plusDays(2);

        if (r1.isAvailable(checkIn1, checkOut1)) {
            r1.markOccupied(checkIn1, checkOut1);
            Booking booking1 = new Booking("B1", r1, guest, checkIn1, checkOut1);
            hotel.addBooking(booking1);
            hotel.makeBooking(booking1);
            System.out.println("Booking B1 confirmed for: " + guest.getFullName());
        } else {
            System.out.println("Room " + r1.getId() + " is not available from " + checkIn1 + " to " + checkOut1);
        }


        hotel.printRoomsBookedByGuest(guest);

        Booking booking2 = new Booking("B2", r2, guest, LocalDate.now(), LocalDate.now().plusDays(3));
        bookingManager.addBooking(booking2);
        bookingManager.printBookedRoomsGuests(allRooms);

        Room availableRoom = bookingManager.findFirstAvailableRoom(allRooms);
        if (availableRoom != null) {
            System.out.println("First available room: " + availableRoom.getId());
        }

        HotelService[] allServices = new HotelService[] { spa, laundry };
        BigDecimal totalForTaylor = hotel.calculateTotalServiceCostForGuest(allServices, guest);
        System.out.println("Total service cost for " + guest.getFullName() + ": $" + totalForTaylor);

        int dirtyRooms = ((HousekeepingStaff) housekeeping).countRoomsNeedingCleaning(new char[] {'C', 'D', 'C', 'D'});
        System.out.println("Rooms needing cleaning: " + dirtyRooms);

        String[] complaints = {"wifi", "noise", "cleanliness", "breakfast"};
        ((FrontDeskStaff) frontDesk).handleGuestComplaints(complaints);
        ((LaundryService) laundry).checkWeightLimit(new double[] {5.0, 7.3, 10.2});

        char[] steps = {'-', 'P', 'C'};
        r1.markStepsCompleted(steps);
        System.out.println("Steps after marking completed: " + java.util.Arrays.toString(steps));

        try {
            hotel.validateRoomCapacities(allRooms, 3);
            System.out.println("All rooms are within allowed capacity.");
        } catch (RoomCapacityExceededException e) {
            System.out.println("Capacity validation error: " + e.getMessage());
        }

        String[] checkIns = { "2025-08-10", "2025-07-15", "2025-07-20" };
        String[] checkOuts = { "2025-07-14", "2025-07-14", "2025-07-22" };

        try {
            bookingManager.validateBookingDates(checkIns, checkOuts);
            System.out.println("All booking dates are valid.");
        } catch (InvalidBookingDatesException e) {
            System.out.println("Date validation error: " + e.getMessage());
        }
        double[] serviceCosts = {100, 150, 200, 120};
        char[] tiers = {'A', 'B', 'Z', 'C'};

        HotelService.applyTierDiscounts(serviceCosts, tiers);

        try {
            hotel.checkForDuplicateGuestBookings(allRooms);
        } catch (DuplicateGuestBookingException e) {
            System.out.println("Duplicate found: " + e.getMessage());
        }

        LocalDate desiredCheckIn = LocalDate.of(2025, 7, 7);
        LocalDate desiredCheckOut = LocalDate.of(2025, 7, 27);

        List<Room> availableRooms = hotel.getAvailableRooms(desiredCheckIn, desiredCheckOut);

        System.out.println("Available Rooms from " + desiredCheckIn + " to " + desiredCheckOut + ":");
        availableRooms.forEach(System.out::println);


        List<Room> deluxeRooms = hotel.getRoomsByType("Deluxe");
        System.out.println("Deluxe Rooms:");
        for (Room r : deluxeRooms) {
            System.out.println(r);
        }


        Guest taylor = new Guest("G1", "Taylor", "Swift", "tswift@email.com", "Singer");
        Guest artemis = new Guest("G2", "Artemis", "Star", "artemis@email.com", "Astronomer");
        Guest duplicateTaylor = new Guest("G1", "Taylor", "Swift", "tswift@email.com", "Singer");

        hotel.registerGuest(taylor);
        hotel.registerGuest(artemis);
        hotel.registerGuest(duplicateTaylor);


        System.out.println("Total unique registered guests: " + hotel.getTotalNumberOfGuests());



        Booking retrieved = hotel.getBookingById("B1");
        if (retrieved != null) {
            System.out.println("Retrieved Booking: " + retrieved);
        } else {
            System.out.println("Booking not found.");
        }
        List<Room> expensiveRooms = Room.getRoomsAboveRate(allRoomsAvailable, 2000.0);
        System.out.println("Rooms with rate above $2000:");
        for (Room r : expensiveRooms) {
            System.out.println(r);
        }
        List<String> guestNames = hotel.getAllGuestNames();
        System.out.println("Registered Guest Names:");
        for (String name : guestNames) {
            System.out.println(name);
        }
        System.out.println("Total revenue from bookings: $" + hotel.calculateTotalRevenue());

        Map<Guest, List<Booking>> map = hotel.getBookingsByGuest();

        for (Guest guest1 : map.keySet()) {
            System.out.println("Bookings for " + guest1.getFullName() + ":");
            for (Booking booking : map.get(guest1)) {
                System.out.println("  " + booking);
            }
        }

        String popularType = hotel.getMostFrequentRoomTypeBooked();
        System.out.println("Most frequently booked room type: " + popularType);


        Set<Guest> frequentGuests = hotel.getGuestsWithMultipleBookings();
        System.out.println("Guests with multiple bookings:");
        for (Guest guest2 : frequentGuests) {
            System.out.println(guest2.getFullName());
        }
        Map<Staff, Integer> staffTaskMap = hotel.getStaffTaskCounts();
        System.out.println("Tasks completed by each staff member:");
        for (Map.Entry<Staff, Integer> entry : staffTaskMap.entrySet()) {
            System.out.println(entry.getKey().getName() + ": " + entry.getValue());
        }
        Map<String, Double> discountCodes1 = new HashMap<>();
        discountCodes1.put("SUMMER20", 0.20);
        discountCodes1.put("WELCOME15", 0.15);

        spa.setDiscountRules(discountCodes1);
        spa.applyDiscount("SUMMER20");

        laundry.setDiscountRules(discountCodes1);
        laundry.applyDiscount("INVALIDCODE");

        LocalDate startSearch = LocalDate.of(2025, 8, 1);
        Room earliestFreeRoom = bookingManager.getEarliestUnbookedRoom(allRooms, startSearch);

        if (earliestFreeRoom != null) {
            System.out.println("Earliest unbooked room from " + startSearch + ": Room " + earliestFreeRoom.getId());
        } else {
            System.out.println("No unbooked rooms available from " + startSearch);
        }
        List<Room> unbookedRooms = hotel.getRoomsWithNoBookings();
        System.out.println("Rooms with no bookings:");
        for (Room r : unbookedRooms) {
            System.out.println(r);
        }


        System.out.println("Cancelling booking for Room " + r1.getId() + " from " + checkIn1 + " to " + checkOut1);
        r1.cancelOccupancy(checkIn1, checkOut1);


        boolean isNowAvailable = r1.isAvailable(checkIn1, checkOut1);
        System.out.println("Is Room " + r1.getId() + " available after cancellation?  " + isNowAvailable);
        try {
            RoomService roomService = new RoomService();



            System.out.println("\n--- JDBC Test: Saving r1 and r2 ---");
            roomService.saveRoom(r1);
            roomService.saveRoom(r2);


            Room fetchedRoom = roomService.getRoomById("101");
            System.out.println("Fetched Room (from DB): " + fetchedRoom);


            List<Room> dbRooms = roomService.getAllRooms();
            System.out.println("All Rooms (from DB):");
            for (Room dbRoom : dbRooms) {
                System.out.println(dbRoom);
            }


            Room updatedRoom = new Room("101", "VIP Suite", 2999.99);
            updatedRoom.book();
            roomService.updateRoomById("101", updatedRoom);
            System.out.println("Updated Room 101 in DB.");

            // Delete room
            roomService.deleteRoomById("102");
            System.out.println("Deleted Room 102 from DB.");

        } catch (SQLException e) {
            System.out.println("Error during JDBC RoomService operation:");
            e.printStackTrace();
        }


    }
}

package org.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class Hotel {
    private List<HotelService> services = new ArrayList<>();
    private List<Staff> staffMembers = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private List<Booking>bookings=new ArrayList<>();
    private Set<Guest> registeredGuests = new HashSet<>();
    private Map<String, Booking> bookingsById = new HashMap<>();

    public void addService(HotelService service) { services.add(service); }
    public void addStaff(Staff staff) { staffMembers.add(staff); }
    public void addRoom(Room room) { rooms.add(room); }

    public void displayServices() {
        for (HotelService s : services) System.out.println(s);
    }

    public void displayStaff() {
        for (Staff s : staffMembers) System.out.println(s);
    }

    public void displayRooms() {
        for (Room r : rooms) System.out.println(r);
    }
    public void addBooking(Booking booking) {
        if (booking == null) {
            System.out.println("Cannot add a null booking.");
            return;
        }
        bookings.add(booking);
        System.out.println("Booking added: " + booking);
    }


    public void bookRoom(int number) throws RoomUnavailableException {
        for (Room r : rooms) {
            if (r.toString().contains("Room " + number)) {
                if (r.isBooked()) throw new RoomUnavailableException("Room " + number + " is already booked.");
                r.book();
                return;
            }
        }
        throw new RoomUnavailableException("Room " + number + " not found.");
    }

    public double calculateTotalCharges(List<Chargeable> items) {
        double total = 0;
        for (Chargeable item : items) total += item.getCharge();
        return total;
    }
    public void printRoomsBookedByGuest(Guest guest) {
        boolean found = false;
        for (Booking booking : bookings) {
            if (booking.getGuest().getId().equals(guest.getId())) {
                System.out.println("Room booked: " + booking.getRoom());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No rooms booked by guest with ID: " + guest.getId());
        }
    }

    public BigDecimal calculateTotalServiceCostForGuest(HotelService[] services, Guest guest) {
        BigDecimal total = BigDecimal.ZERO;

        for (HotelService service : services) {
            if (service.getGuest().getId().equals(guest.getId())) {
                total = total.add(BigDecimal.valueOf(service.getBaseCost()));
            }
        }

        return total;
    }
    public void addRoomWithCheck(Room room) throws DuplicateRoomException {
        for (Room r : rooms) {
            if (r.getId().equals(room.getId())) {
                throw new DuplicateRoomException("Room with number " + room.getId() + " already exists.");
            }
        }
        rooms.add(room);
    }

    public void validateRoomCapacities(Room[] roomArray, int maxAllowedCapacity) throws RoomCapacityExceededException {
        for (Room room : roomArray) {
            if (room.getBedCount() > maxAllowedCapacity) {
                throw new RoomCapacityExceededException(
                        "Room " + room.getId() + " exceeds maximum allowed capacity with " + room.getBedCount() + " beds.");
            }
        }
    }
    public void checkForDuplicateGuestBookings(Room[] roomArray) throws DuplicateGuestBookingException {
        Set<String> guestIds = new HashSet<>();

        for (Room room : roomArray) {
            for (Booking booking : bookings) {
                if (booking.getRoom().equals(room)) {
                    String guestId = booking.getGuest().getId();
                    if (!guestIds.add(guestId)) {
                        throw new DuplicateGuestBookingException(guestId);
                    }
                }
            }
        }

        System.out.println("No duplicate guest bookings found.");
    }

    public List<Room> getAllAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (!room.isBooked()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }




    public List<Room> getAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        List<Room> availableRooms = new ArrayList<>();

        for (Room room : rooms) {
            boolean isAvailable = true;

            for (Booking booking : bookings) {
                if (booking.getRoom().equals(room)) {

                    if (!(checkOut.isBefore(booking.getCheckInDate()) || checkIn.isAfter(booking.getCheckOutDate()))) {
                        isAvailable = false;
                        break;
                    }
                }
            }

            if (isAvailable) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }



    public List<Room> getRoomsByType(String roomType) {
        List<Room> matchingRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getType().equalsIgnoreCase(roomType)) {
                matchingRooms.add(room);
            }
        }
        return matchingRooms;
    }
    public boolean registerGuest(Guest guest) {
        if (guest == null) {
            System.out.println("Cannot register a null guest.");
            return false;
        }
        boolean added = registeredGuests.add(guest);
        if (added) {
            System.out.println("Guest newly registered: " + guest.getFullName());
        } else {
            System.out.println("Guest is already registered: " + guest.getFullName());
        }
        return added;
    }


    public int getTotalNumberOfGuests() {
        return registeredGuests.size();
    }
    public void makeBooking(Booking newBooking) {
        if (newBooking == null || newBooking.getBookingId() == null) {
            System.out.println("Cannot make a booking with null ID or null object.");
            return;
        }

        Room roomToBook = newBooking.getRoom();
        LocalDate newStart = newBooking.getCheckInDate();
        LocalDate newEnd = newBooking.getCheckOutDate();


        for (Booking existing : bookings) {
            if (existing.getRoom().equals(roomToBook)) {
                LocalDate existingStart = existing.getCheckInDate();
                LocalDate existingEnd = existing.getCheckOutDate();

                boolean overlaps = !(newEnd.isBefore(existingStart) || newStart.isAfter(existingEnd));
                if (overlaps) {
                    System.out.println("Cannot make booking. Room " + roomToBook.getId() +
                            " is already booked from " + existingStart + " to " + existingEnd + ".");
                    return;
                }
            }
        }

        bookings.add(newBooking);
        bookingsById.put(newBooking.getBookingId(), newBooking);
        System.out.println("Booking made successfully: " + newBooking);
    }

    public Booking getBookingById(String bookingId) {
        return bookingsById.get(bookingId);
    }
    public List<String> getAllGuestNames() {
        List<String> names = new ArrayList<>();
        for (Guest guest : registeredGuests) {
            names.add(guest.getFullName());
        }
        return names;
    }


    public BigDecimal calculateTotalRevenue() {
        BigDecimal total = BigDecimal.ZERO;

        for (Booking booking : bookings) {
            total = total.add(booking.getTotalCost());
        }

        return total;
    }

    public Map<Guest, List<Booking>> getBookingsByGuest() {
        Map<Guest, List<Booking>> bookingsByGuest = new HashMap<>();

        for (Booking booking : bookings) {
            Guest guest = booking.getGuest();


            bookingsByGuest.computeIfAbsent(guest, k -> new ArrayList<>());


            bookingsByGuest.get(guest).add(booking);
        }

        return bookingsByGuest;
    }
    public String getMostFrequentRoomTypeBooked() {
        Map<String, Integer> roomTypeCounts = new HashMap<>();


        for (Booking booking : bookings) {
            String roomType = booking.getRoom().getType();
            roomTypeCounts.put(roomType, roomTypeCounts.getOrDefault(roomType, 0) + 1);
        }


        String mostFrequentType = null;
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : roomTypeCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequentType = entry.getKey();
            }
        }

        return mostFrequentType != null ? mostFrequentType : "No frequent bookings are found!";
    }

    public Set<Guest> getGuestsWithMultipleBookings() {
        Set<Guest> result = new HashSet<>();
        Map<Guest, List<Booking>> bookingsByGuest = getBookingsByGuest();

        for (Map.Entry<Guest, List<Booking>> entry : bookingsByGuest.entrySet()) {
            if (entry.getValue().size() > 1) {
                result.add(entry.getKey());
            }
        }

        return result;
    }
    public Map<Staff, Integer> getStaffTaskCounts() {
        Map<Staff, Integer> taskCounts = new HashMap<>();
        for (Staff staff : staffMembers) {
            taskCounts.put(staff, staff.getTasksCompleted());
        }
        return taskCounts;
    }
    public List<Room> getRoomsWithNoBookings() {
        Set<Room> bookedRooms = new HashSet<>();
        for (Booking booking : bookings) {
            bookedRooms.add(booking.getRoom());
        }

        List<Room> noBookingRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (!bookedRooms.contains(room)) {
                noBookingRooms.add(room);
            }
        }

        return noBookingRooms;
    }









}

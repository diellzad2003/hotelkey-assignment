package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Room implements Bookable, Chargeable {
    private String id;
    private String type;
    private double rate;
    private boolean booked;
    private int bedCount;
    private Map<LocalDate, Boolean> occupancyMap = new HashMap<>();


    public int getBedCount() {
        return bedCount;
    }


    public Room(String id, String type, double rate) {
        this.id = id;
        this.type = type;
        this.rate = Math.max(0, rate);
        this.booked = false;
    }

    public String getId() { return id; }
    public String getType() { return type; }
    public double getRate() { return rate; }

    @Override
    public boolean isBooked() { return booked; }

    @Override
    public void book() { this.booked = true; }

    @Override
    public double getCharge() { return rate; }

    @Override
    public String toString() {
        return "Room{id=" + id + ", type=" + type + ", rate=" + rate + ", booked=" + booked + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room room)) return false;
        return id.equals(room.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
    public boolean wasVacantForThreeDays(boolean[] occupancy) {
        int count = 0;
        for (boolean occupied : occupancy) {
            if (!occupied) {
                count++;
                if (count >= 3) return true;
            } else {
                count = 0;
            }
        }
        return false;
    }
    public void applyDiscounts(char[] codes) {
        for (char code : codes) {
            switch (code) {
                case 'A': rate *= 0.9; break;
                case 'B': rate *= 0.85; break;
                case 'C': rate *= 0.8; break;
                default: System.out.println("Invalid code: " + code);
            }
        }
    }
    public void markStepsCompleted(char[] steps) {
        int i = 0;
        while (i < steps.length) {
            steps[i] = 'X';
            i++;
        }
    }

    public static List<Room> getRoomsAboveRate(List<Room> rooms, double minRate) {
        List<Room> result = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getRate() > minRate) {
                result.add(room);
            }
        }
        return result;
    }
    public boolean isAvailable(LocalDate checkIn, LocalDate checkOut) {
        LocalDate date = checkIn;
        while (!date.isAfter(checkOut)) {
            if (occupancyMap.getOrDefault(date, false)) {
                return false; // Already booked on this date
            }
            date = date.plusDays(1);
        }
        return true;
    }
    public void markOccupied(LocalDate checkIn, LocalDate checkOut) {
        LocalDate date = checkIn;
        while (!date.isAfter(checkOut)) {
            occupancyMap.put(date, true);
            date = date.plusDays(1);
        }
    }
    public void cancelOccupancy(LocalDate checkIn, LocalDate checkOut) {
        LocalDate date = checkIn;
        while (!date.isAfter(checkOut)) {
            occupancyMap.remove(date);
            date = date.plusDays(1);
        }
    }



}

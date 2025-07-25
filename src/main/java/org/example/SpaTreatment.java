package org.example;

public class SpaTreatment extends HotelService implements Bookable {
    private boolean booked;

    public SpaTreatment(String id, String description, double baseCost,Guest guest) {
        super(id, description, baseCost,guest);
        this.booked = false;
    }

    @Override
    public double getCharge() {
        return getBaseCost() + 20;
    }

    @Override
    public boolean isBooked() { return booked; }

    @Override
    public void book() { booked = true; }

    @Override
    public String toString() {
        return "SpaTreatment {" + getId() + "} - " + getDescription() + ", Charge: " + getCharge();
    }
}
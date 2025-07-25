package org.example;

public class LaundryService extends HotelService {
    public LaundryService(String id, String description, double baseCost,Guest guest) {
        super(id, description, baseCost,guest);
    }

    @Override
    public double getCharge() {
        return getBaseCost() * 1.1;
    }

    @Override
    public String toString() {
        return "LaundryService {" + getId() + "} - " + getDescription() + ", Charge: " + getCharge();
    }
    public void checkWeightLimit(double[] itemWeights) {
        double totalWeight = 0.0;
        for (double weight : itemWeights) {
            totalWeight += weight;
        }
        if (totalWeight > 20.0) {
            System.out.println("Warning: Total laundry weight exceeds the 20 kg limit!");
        }
    }

}

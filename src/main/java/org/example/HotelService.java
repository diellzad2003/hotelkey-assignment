package org.example;

import java.util.HashMap;
import java.util.Map;

public abstract class HotelService implements Chargeable {
    private String id;
    private String description;
    private double baseCost;
    private Guest guest;
    private Map<String, Double> discountRules = new HashMap<>();


    public HotelService(String id, String description, double baseCost, Guest guest) {
        this.id = id;
        this.description = description;
        this.baseCost = baseCost;
        this.guest = guest;
    }
    public void setDiscountRules(Map<String, Double> rules) {
        this.discountRules = rules;
    }


    public String getId() { return id; }
    public String getDescription() { return description; }
    public double getBaseCost() { return baseCost; }
    public Guest getGuest() { return guest; }

    public abstract double getCharge();
    public abstract String toString();

    public void validateDiscountCodes(char[] discountCodes) {
        for (char code : discountCodes) {
            if (code < 'A' || code > 'Z') {
                System.out.println("Invalid discount code: " + code);
            }
        }
    }
    public static void applyTierDiscounts(double[] costs, char[] discountTiers) {
        if (costs.length != discountTiers.length) {
            System.out.println("Error: Arrays must be of the same length.");
            return;
        }

        for (int i = 0; i < costs.length; i++) {
            double original = costs[i];
            double finalCost = original;
            char tier = discountTiers[i];

            switch (tier) {
                case 'A':
                    finalCost = original * 0.8;
                    break;
                case 'B':
                    finalCost = original * 0.9;
                    break;
                case 'C':
                    finalCost = original * 0.95;
                    break;
                default:
                    System.out.println("Unrecognized discount tier '" + tier + "' at index " + i + ". No discount applied.");
                    continue;
            }

            System.out.printf("Original cost: $%.2f | Tier: %c | Final cost: $%.2f%n", original, tier, finalCost);
        }
    }
    public void applyDiscount(String discountCode) {
        if (discountRules.containsKey(discountCode)) {
            double discount = discountRules.get(discountCode);
            double oldCost = baseCost;
            baseCost = baseCost * (1 - discount);
            System.out.printf("Applied discount '%s' (%.0f%%): $%.2f → $%.2f%n",
                    discountCode, discount * 100, oldCost, baseCost);
        } else {
            System.out.println("Discount code '" + discountCode + "' not found. No discount applied.");
        }
    }


}


package org.example;

public class FrontDeskStaff extends Staff {
    public FrontDeskStaff(String id, String name, String department) {
        super(id, name, department);
    }

    public void greet() {
        System.out.println("Welcome to our hotel!");
    }

    public void greet(String guestName) {
        System.out.println("Welcome, " + guestName + "!");
    }

    @Override
    public void performDuties() {
        System.out.println("Checking-in the hotel guests and answering inquiries.");
        incrementTasksCompleted();
    }

    @Override
    public String toString() {
        return "FrontDeskStaff {" + getStaffId() + "} - " + getName();
    }
    public void handleGuestComplaints(String[] complaints) {
        for (String complaint : complaints) {
            switch (complaint.toLowerCase()) {
                case "wifi":
                    System.out.println("We're sorry for the WiFi issues. We'll fix it in a moment.");
                    break;
                case "cleanliness":
                    System.out.println("We apologize for any cleanliness problems. We'll address this issue immediately.");
                    break;
                case "noise":
                    System.out.println("Sorry for the noise disturbance. We will look into it.");
                    break;
                default:
                    System.out.println("Thank you for your honest feedback about '" + complaint + "'. We will work to be better.");
                    break;
            }
        }
    }
}


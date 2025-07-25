package org.example;

public class HousekeepingStaff extends Staff {
    public HousekeepingStaff(String id, String name, String department) {
        super(id, name, department);
    }

    @Override
    public void performDuties() {
        System.out.println("Cleaning out the rooms and providing fresh towels.");
        incrementTasksCompleted();
    }

    @Override
    public String toString() {
        return "HousekeepingStaff {" + getStaffId() + "} - " + getName();
    }
    public int countRoomsNeedingCleaning(char[] cleanlinessStatus) {
        int count = 0;
        for (char status : cleanlinessStatus) {
            if (status == 'D') {
                count++;
            }
        }
        return count;
    }
}

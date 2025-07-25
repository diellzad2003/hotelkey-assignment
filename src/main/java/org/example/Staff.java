package org.example;

public abstract class Staff {
    private String staffId;
    private String name;
    private String department;
    private int tasksCompleted = 0; //

    public Staff(String staffId, String name, String department) {
        this.staffId = staffId;
        this.name = name;
        this.department = department;
    }

    public String getStaffId() { return staffId; }
    public String getName() { return name; }
    public String getDepartment() { return department; }


    public void incrementTasksCompleted() {
        tasksCompleted++;
    }

    public int getTasksCompleted() {
        return tasksCompleted;
    }

    public abstract void performDuties();
    public abstract String toString();

    public int getHighestPriorityTaskIndex(int[] priorities) {
        int minIndex = 0;
        for (int i = 1; i < priorities.length; i++) {
            if (priorities[i] < priorities[minIndex]) {
                minIndex = i;
            }
        }
        return minIndex;
    }
}


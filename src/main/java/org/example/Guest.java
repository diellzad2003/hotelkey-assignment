package org.example;


import java.util.Objects;

public class Guest {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    public Guest(String id, String firstName, String lastName, String email,String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role=role;
    }

    public String getId() { return id; }
    public void setGuestId(String id) { this.id = id; }
    public String getGuestName() { return firstName; }

    public void setGuestName(String firstName) { this.firstName = firstName; }

    public String getGuestLastname() { return lastName; }
    public void setGuestLastname(String lastName) { this.lastName = lastName; }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getGuestEmail() {
        return email;
    }
    public void setGuestEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Guest{id=" + id + ", name=" + getFullName() + ", email=" + email + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Guest guest)) return false;
        return id.equals(guest.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    public String buildGreeting(char[] template) {
        StringBuilder message = new StringBuilder();
        for (char ch : template) {
            if (ch == '*') {
                message.append(Character.toUpperCase(role.charAt(0)));
            } else if (ch == '#') {
                message.append(firstName);
            } else {
                message.append(ch);
            }
        }
        return message.toString();
    }
}


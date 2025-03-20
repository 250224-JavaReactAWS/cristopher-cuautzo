package com.revature.models;

public class User implements Comparable<User>{
    private int userId;
    private String first_name;
    private String last_name;
    private String username;
    private String email;
    private String phone_number;
    private String password;
    private Role role;

    private static int userIdCounter = 1;

    // Constructor
    public User(String first_name, String last_name,
                String email, String phone_number,
                String password, String username) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.email = email;
        this.phone_number = phone_number;
        this.password = password;

        // Set default role to USER
        this.role = Role.USER;

        // Automatic unique Id
        this.userId = userIdCounter;
        userIdCounter++;
    }

    public User(){}

    // Getters and Setters
    public int getUserId() { return userId; }

    public void setUserId(int user_id) { this.userId = userId; }

    public String getFirst_name() { return first_name; }

    public void setFirst_name(String first_name) { this.first_name = first_name; }

    public String getLast_name() { return last_name; }

    public void setLast_name(String last_name) { this.last_name = last_name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhone_number() { return phone_number; }

    public void setPhone_number(String phone_number) { this.phone_number = phone_number; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }

    public void setRole(Role role) { this.role = role; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    @Override
    public String toString() {
        return "User {" +
                "userID = " + userId + "'" +
                ", first_name = '" + first_name + "'" +
                ", last_name = '" + last_name + "'" +
                ", email = '" + email + "'" +
                ", role = " + role +
                "}";
    }

    @Override
    public int compareTo(User o) {
        if (this.getUserId() > o.getUserId()){
            return 1;
        } else if( this.getUserId() < o.getUserId()){
            return -1;
        } else{
            return 0;
        }
    }
}

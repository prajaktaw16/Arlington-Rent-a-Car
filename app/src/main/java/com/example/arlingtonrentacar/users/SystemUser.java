/**
 * Refactored & Maintained: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar.users;

import com.example.arlingtonrentacar.AAUtil;

public class SystemUser {
    private String username;
    private String password;
    private String lastName;
    private String firstName;
    private String role;
    private String phone;
    private String email;
    private String streetAddress;
    private String city;
    private String state;
    private String zip;
    private int utaID, aaaMemberStatus, userStatus;

    public SystemUser() {
    }

    public SystemUser(String username, String password, String lastName, String firstName,
                      String role, int utaID, String phone, String email, String address,
                      String city, String state, String zip, int aaaMemStat, int userStat) {
        this.username = username;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.role = role;
        this.phone = phone;
        this.email = email;
        this.streetAddress = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.utaID = utaID;
        this.aaaMemberStatus = aaaMemStat;
        this.userStatus = userStat;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setUtaID(int utaID) {
        this.utaID = utaID;
    }

    public void setAaaMemberStatus(int aaaMemberStatus) {
        this.aaaMemberStatus = aaaMemberStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getRole() {
        return role;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public int getUtaID() {
        return utaID;
    }

    public int getAaaMemberStatus() {
        return aaaMemberStatus;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public static String validateData(String username, String password, String lastName, String firstName,
                               String utaID, String role, String phone, String email, String address,
                               String city, String state, String zip){
        final String EMPTY = "";
        String msg = "";
        if(username.equals(EMPTY) ||
                password.equals(EMPTY) ||
                lastName.equals(EMPTY) ||
                firstName.equals(EMPTY) ||
                !AAUtil.isNumeric(utaID) ||
                role.equals(EMPTY) ||
                phone.equals(EMPTY) ||
                email.equals(EMPTY) ||
                address.equals(EMPTY) ||
                city.equals(EMPTY) ||
                state.equals(EMPTY) ||
                zip.equals(EMPTY)){
            msg = "Invalid Data.";
        }
        return msg;
    }

    @Override
    public String toString() {
        return "SystemUser{\n" +
                "username=" + username + '\n' +
                "password=" + password + '\n' +
                "lastName=" + lastName + '\n' +
                "firstName=" + firstName + '\n' +
                "role=" + role + '\n' +
                "phone=" + phone + '\n' +
                "email=" + email + '\n' +
                "streetAddress=" + streetAddress + '\n' +
                "city=" + city + '\n' +
                "state=" + state + '\n' +
                "zip=" + zip + '\n' +
                "utaID=" + utaID +
                "\naaaMemberStatus=" + aaaMemberStatus +
                "\nuserStatus=" + userStatus +
                "\n}";
    }
}

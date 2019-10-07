package com.example.fundoonotes.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String username;
    private String firstName;
    private String lastName;

    LoggedInUserView(String username, String displayName, String lastName) {
        this.username = username;
        this.firstName = displayName;
        this.lastName = lastName;
    }

    String getUsername() {
        return username;
    }

    String getDisplayName() {
        return firstName;
    }

    String getLastName() {
        return lastName;
    }
}

package com.example.fundoonotes.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class LoginFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer firstNameError;
    @Nullable
    private Integer lastNameError;
    @Nullable
    private Integer passwordError;

    private boolean isDataValid;

    LoginFormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    LoginFormState(@Nullable Integer usernameError, @Nullable Integer firstNameError, @Nullable Integer lastNameError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.firstNameError = firstNameError;
        this.lastNameError = lastNameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }


    LoginFormState(boolean isDataValid) {
        this.usernameError = null;
        this.firstNameError = null;
        this.lastNameError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getFirstNameError() {
        return firstNameError;
    }

    @Nullable
    Integer getLastNameError() {
        return lastNameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}

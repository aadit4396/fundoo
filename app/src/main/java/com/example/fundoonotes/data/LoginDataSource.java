package com.example.fundoonotes.data;

import android.content.Context;
import android.util.Log;

import com.example.fundoonotes.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> register(String username, String firstName, String lastName, String password, Context context) {
        try{

            DataBaseTools dbTools = new DataBaseTools(context);
            LoggedInUser oldUser = dbTools.getUser(username);
            if(oldUser.userId > 0) {
                throw new IOException();
            }
            LoggedInUser newUser = new LoggedInUser(username, firstName, lastName, password);
            newUser = dbTools.insertUser(newUser);
            return new Result.Success<>(newUser);
        } catch (Exception e) {
            return new Result.Error(new IOException());
        }
    }

    public Result<LoggedInUser> login(String username, String password, Context context) {

        try {
            // TODO: handle loggedInUser authentication
            DataBaseTools dbTools = new DataBaseTools(context);
            LoggedInUser myUser = dbTools.getUser(username);
            if(myUser.userId > 0) {
                if(AESCrypt.decrypt(myUser.getPassword()).equals(password)) {
                    return new Result.Success<>(myUser);
                }
                else
                    return new Result.Error(new IOException());
            }
            else
                return new Result.Error(new IOException());
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}

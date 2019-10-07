package com.example.fundoonotes.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Context;
import android.util.Patterns;

import com.example.fundoonotes.data.LoginRepository;
import com.example.fundoonotes.data.Result;
import com.example.fundoonotes.data.model.LoggedInUser;
import com.example.fundoonotes.R;

import java.util.regex.Pattern;

public class LoginViewModel extends ViewModel {
    private static final Pattern Password_Pattern = Pattern.compile( "(?=.*[0-9])" + "(?=.*[a-z])" + "(?=.*[A-Z])" + "(?=.*[@$#%+&])" + "(?=\\S+$)" + ".{6,}" );
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void register(String username, String firstName, String lastName, String password,String confirm, Context context) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.register(username, firstName, lastName, password, confirm, context);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getUsername(), data.getFirstName(), data.getLastName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void login(String username, String password, Context context) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password, context);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getUsername(), data.getFirstName(), data.getLastName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    public void loginDataChanged(String username, String firstName, String lastName, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username,null,null,null));
        } else if (!isFirstNameValid(firstName)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_first_name,null,null));
        } else if (!isLastNameValid(lastName)) {
            loginFormState.setValue(new LoginFormState(null,null, R.string.invalid_last_name,null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, null,null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        else {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        }
    }

    private boolean isFirstNameValid(String firstName) {
        return firstName != null && firstName.matches("[A-Z][a-z]+");
    }

    private boolean isLastNameValid(String lastName) {
        return lastName != null && lastName.matches("[A-Z][a-z]+");
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 7 &&
                password.matches(String.valueOf(Password_Pattern));
    }
}

package com.example.fundoonotes.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fundoonotes.R;
import com.example.fundoonotes.data.AESCrypt;
import com.example.fundoonotes.ui.login.LoginViewModel;
import com.example.fundoonotes.ui.login.LoginViewModelFactory;

public class RegisterActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText firstNameEditText = findViewById(R.id.firstName);
        final EditText lastNameEditText = findViewById(R.id.lastName);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button registerButton = findViewById(R.id.register);
        final Button backButton = findViewById(R.id.back);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final EditText confirmPass = findViewById(R.id.confirm);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                registerButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getFirstNameError() != null) {
                    firstNameEditText.setError(getString(loginFormState.getFirstNameError()));
                }
                if (loginFormState.getLastNameError() != null) {
                    lastNameEditText.setError(getString(loginFormState.getLastNameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                    finish();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                setResult(Activity.RESULT_OK);
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(), firstNameEditText.getText().toString(),
                        lastNameEditText.getText().toString(), passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        firstNameEditText.addTextChangedListener(afterTextChangedListener);
        lastNameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        confirmPass.addTextChangedListener(afterTextChangedListener);
        confirmPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    try {
                        loginViewModel.register(usernameEditText.getText().toString(), firstNameEditText.getText().toString(),
                                lastNameEditText.getText().toString(), AESCrypt.encrypt(passwordEditText.getText().toString()), confirmPass.getText().toString(), getApplicationContext());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                try {
                    loginViewModel.register(usernameEditText.getText().toString(), firstNameEditText.getText().toString(),
                            lastNameEditText.getText().toString(), AESCrypt.encrypt(passwordEditText.getText().toString()), confirmPass.getText().toString(), getApplicationContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.registered);
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}

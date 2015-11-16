package com.everlesslycoding.revinote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.StackView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.Serializable;
import java.sql.Ref;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    EditText EmailInput;
    EditText PasswordInput;
    Button LoginButton;
    Button SignUpButton;
    Button ForgotPassBtn;
    LinearLayout ButtonLayout;
    ProgressBar LoadingBar;

    Firebase rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(getApplicationContext());

        SetupPage();

        rootRef = new Firebase("https://revinote.firebaseio.com/");

        if (rootRef.getAuth() != null) {
            rootRef.unauth();
        }

        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_path), Context.MODE_PRIVATE);

        final String userName = prefs.getString("Username", null);
        final String password = prefs.getString("Password", null);

        try {
            EmailInput.setVisibility(View.INVISIBLE);
            PasswordInput.setVisibility(View.INVISIBLE);
            showProgressBar();
            if (userName != null && password != null) {
                rootRef.authWithPassword(userName, password, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        // Authenticated successfully with payload authData
                        hideProgressBar();
                        EmailInput.setVisibility(View.VISIBLE);
                        PasswordInput.setVisibility(View.VISIBLE);
                        LoadHomePage();
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError error) {
                        hideProgressBar();
                        EmailInput.setVisibility(View.VISIBLE);
                        PasswordInput.setVisibility(View.VISIBLE);
                        Toast.makeText(getBaseContext(), "[SU-LI] ERROR: " + error.getMessage() + " " + userName, Toast.LENGTH_LONG).show();
                    }
                });
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            recreate();
        }
    }

    private void SetupPage() {

        EmailInput = (EditText)findViewById(R.id.EmailInput);
        PasswordInput = (EditText)findViewById(R.id.PasswordInput);
        LoginButton = (Button)findViewById(R.id.LoginButton);
        ForgotPassBtn = (Button) findViewById(R.id.ForgotPassword);
        SignUpButton = (Button) findViewById(R.id.SignUpButton);

        ButtonLayout = (LinearLayout) findViewById(R.id.buttonsLayout);
        LoadingBar = (ProgressBar) findViewById(R.id.progressBar);

        hideProgressBar();

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = EmailInput.getText().toString();
                final String Password = PasswordInput.getText().toString();

                showProgressBar();

                Firebase.AuthResultHandler LoginHandler = new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        hideProgressBar();
                        setUserPrefs(Email, Password);
                        LoadHomePage();
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError error) {
                        hideProgressBar();
                        Toast.makeText(getBaseContext(), "[LI] ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                };
                Log.d("LOGIN DATA", "Email: " + Email + " Password: " + Password);
                rootRef.authWithPassword(Email, Password, LoginHandler);
            }
        });

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = EmailInput.getText().toString();
                final String Password = PasswordInput.getText().toString();

                showProgressBar();

                final Firebase.ResultHandler SignUpHandler = new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {
                        rootRef.authWithPassword(Email, Password, new Firebase.AuthResultHandler() {
                            @Override
                            public void onAuthenticated(AuthData authData) {
                                // Authenticated successfully with payload authData
                                setUserPrefs(Email, Password);
                                hideProgressBar();
                                LoadHomePage();
                            }

                            @Override
                            public void onAuthenticationError(FirebaseError error) {
                                hideProgressBar();
                                Toast.makeText(getBaseContext(), "[SU-LI] ERROR: " + error.getMessage() + " " + Email, Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onError(FirebaseError error) {
                        hideProgressBar();
                        Toast.makeText(getBaseContext(), "[SU] ERROR: " + error.getMessage() + " " + Email, Toast.LENGTH_LONG).show();
                    }
                };

                rootRef.createUser(Email, Password, SignUpHandler);
            }
        });

        ForgotPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ForgotPassword.class);
                startActivity(i);
            }
        });
    }

    void showProgressBar () {
        ButtonLayout.setVisibility(View.GONE);
        LoadingBar.setVisibility(View.VISIBLE);
    }

    void hideProgressBar () {
        ButtonLayout.setVisibility(View.VISIBLE);
        LoadingBar.setVisibility(View.GONE);
    }

    void setUserPrefs(String name, String pass) {
        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_path), Context.MODE_PRIVATE);

        prefs.edit().putString("Username", name).apply();
        prefs.edit().putString("Password", pass).apply();
    }

    void LoadHomePage() {
        Intent i = new Intent(this, HomePage.class);

        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}

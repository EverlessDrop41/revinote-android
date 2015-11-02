package com.everlesslycoding.revinote;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    EditText EmailInput;
    EditText PasswordInput;
    Button LoginButton;
    Button ForgotPassBtn;
    LinearLayout ButtonLayout;
    ProgressBar LoadingBar;

    Firebase rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(getApplicationContext());

        rootRef = new Firebase("https://revinote.firebaseio.com/");

        AuthData authData = rootRef.getAuth();

        if (authData != null && true) {
            LoadHomePage();
        } else {
            EmailInput = (EditText)findViewById(R.id.EmailInput);
            PasswordInput = (EditText)findViewById(R.id.PasswordInput);
            LoginButton = (Button)findViewById(R.id.LoginButton);
            ForgotPassBtn = (Button) findViewById(R.id.ForgotPassword);

            ButtonLayout = (LinearLayout) findViewById(R.id.buttonsLayout);
            LoadingBar = (ProgressBar) findViewById(R.id.progressBar);

            hideProgressBar();

            LoginButton.setOnClickListener(new View.OnClickListener() {
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
                                    Toast.makeText(getBaseContext(), (String)authData.getProviderData().get("email"), Toast.LENGTH_LONG).show();
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

                    Firebase.AuthResultHandler LoginHandler = new Firebase.AuthResultHandler() {
                        @Override
                        public void onAuthenticated(AuthData authData) {
                            hideProgressBar();
                            // Authenticated successfully with payload authData
                            Toast.makeText(getBaseContext(), (String)authData.getProviderData().get("email"), Toast.LENGTH_LONG).show();
                            LoadHomePage();
                        }
                        @Override
                        public void onAuthenticationError(FirebaseError error) {
                            hideProgressBar();
                            if (error.getCode() == FirebaseError.USER_DOES_NOT_EXIST) {
                                rootRef.createUser(Email, Password, SignUpHandler);
                            } else {
                                Toast.makeText(getBaseContext(), "[LI] ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    };
                    Log.d("LOGIN DATA","Email: " + Email + " Password: " + Password);
                    rootRef.authWithPassword(Email, Password, LoginHandler);
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
    }

    void showProgressBar () {
        ButtonLayout.setVisibility(View.INVISIBLE);
        LoadingBar.setVisibility(View.VISIBLE);
    }

    void hideProgressBar () {
        ButtonLayout.setVisibility(View.VISIBLE);
        LoadingBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onResume(){
        super.onResume();
        Firebase.setAndroidContext(getApplicationContext());

        rootRef = new Firebase("https://revinote.firebaseio.com/");

        AuthData authData = rootRef.getAuth();
        if (authData != null) {
            rootRef.unauth();
        }
    }

    void LoadHomePage() {
        Intent i = new Intent(this, HomePage.class);
        //i.putExtra("FireRef", (Serializable) rootRef);
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

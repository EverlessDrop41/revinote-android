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
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    EditText EmailInput;
    EditText PasswordInput;
    Button LoginButton;
    Firebase rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(getApplicationContext());

        rootRef = new Firebase("https://revinote.firebaseio.com/");

        EmailInput = (EditText)findViewById(R.id.EmailInput);
        PasswordInput = (EditText)findViewById(R.id.PasswordInput);
        LoginButton = (Button)findViewById(R.id.LoginButton);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = EmailInput.getText().toString();
                final String Password = PasswordInput.getText().toString();

                final Firebase.ResultHandler SignUpHandler = new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {
                        rootRef.authWithPassword(Email, Password, new Firebase.AuthResultHandler() {
                            @Override
                            public void onAuthenticated(AuthData authData) {
                                // Authenticated successfully with payload authData
                                Toast.makeText(getBaseContext(), (String)authData.getProviderData().get("email"), Toast.LENGTH_LONG).show();
                                LoadHomePage();
                            }
                            @Override
                            public void onAuthenticationError(FirebaseError error) {
                                Toast.makeText(getBaseContext(), "[SU-LI] ERROR: " + error.getMessage() + " " + Email, Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onError(FirebaseError error) {
                        Toast.makeText(getBaseContext(), "[SU] ERROR: " + error.getMessage() + " " + Email, Toast.LENGTH_LONG).show();
                    }
                };

                 Firebase.AuthResultHandler LoginHandler = new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        // Authenticated successfully with payload authData
                        Toast.makeText(getBaseContext(), (String)authData.getProviderData().get("email"), Toast.LENGTH_LONG).show();
                        LoadHomePage();
                    }
                    @Override
                    public void onAuthenticationError(FirebaseError error) {
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

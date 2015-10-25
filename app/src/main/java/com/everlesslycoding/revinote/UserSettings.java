package com.everlesslycoding.revinote;

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

public class UserSettings extends AppCompatActivity {

    Firebase rootRef;

    //Change Password
    EditText ChangePassOldPass;
    EditText ChangePassNewPass;
    Button ChangePassBtn;

    //Change Email
    EditText ChangeEmailNewEmail;
    EditText ChangeEmailPass;
    Button ChangeEmailBtn;

    //Delete Account
    EditText DeleteAccPass;
    Button DeleteAccBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        ChangePassOldPass = (EditText) findViewById(R.id.ChangePasswordOldPass);
        ChangePassNewPass = (EditText) findViewById(R.id.ChangePasswordNewPass);
        ChangePassBtn = (Button) findViewById(R.id.ChangePasswordButton);

        ChangeEmailNewEmail = (EditText) findViewById(R.id.ChangeEmailNewEmail);
        ChangeEmailPass = (EditText) findViewById(R.id.ChangeEmailPass);
        ChangeEmailBtn = (Button) findViewById(R.id.ChangeEmailButton);

        DeleteAccPass = (EditText) findViewById(R.id.DeleteAccountPass);
        DeleteAccBtn = (Button) findViewById(R.id.DeleteAccountButton);

        rootRef = new Firebase("https://revinote.firebaseio.com/");

        AuthData authData = rootRef.getAuth();
        final String UserEmaill = authData.getProviderData().get("email").toString();

        ChangePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String oldPass = ChangePassOldPass.getText().toString();
                String newPass = ChangePassNewPass.getText().toString();

                Log.d("USER EMAIL", UserEmaill);

                rootRef.changePassword(UserEmaill, oldPass, newPass, new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "Successfully Changed Password", Toast.LENGTH_SHORT).show();

                        ChangePassOldPass.setText("");
                        ChangePassNewPass.setText("");
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Toast.makeText(getApplicationContext(), firebaseError.getMessage(), Toast.LENGTH_SHORT).show();

                        if (firebaseError.getCode() == FirebaseError.INVALID_PASSWORD)
                        {
                            ChangePassOldPass.setText("");
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_settings, menu);
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
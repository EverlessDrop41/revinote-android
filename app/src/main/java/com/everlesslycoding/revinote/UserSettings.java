package com.everlesslycoding.revinote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class UserSettings extends AppCompatActivity {

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

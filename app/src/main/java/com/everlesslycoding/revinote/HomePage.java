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
import android.widget.TextView;

import com.everlesslycoding.revinote.Subjects.SubjectsList;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

import java.util.Map;

public class HomePage extends AppCompatActivity {

    TextView NameLabel;
    Button SubjectsButton;
    Button SettingsButton;
    Button LogoutButton;

    Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Firebase.setAndroidContext(getApplicationContext());

        NameLabel = (TextView) findViewById(R.id.NameTextView);
        SubjectsButton = (Button) findViewById(R.id.SubjectsButton);
        SettingsButton = (Button) findViewById(R.id.AccountSettingBtn);
        LogoutButton = (Button) findViewById(R.id.LogoutBtn);

        ref = new Firebase("https://revinote.firebaseio.com/");
        if (ref != null) {
            AuthData auth = ref.getAuth();
            Map <String, Object> providerData = auth.getProviderData();

            NameLabel.setText(providerData.get("email").toString());
        } else {
            Log.d("[HOMEPAGE]", "Firebase reference is null");
            finish();
        }
        SettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadSettingsPage();
            }
        });

        SubjectsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadSubjectsPage();
            }
        });

        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });
    }

    private void Logout() {
        ref.unauth();

        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_path), Context.MODE_PRIVATE);
        prefs.edit().remove("Username").remove("Password").apply();

        finish();
    }

    void LoadSettingsPage() {
        Intent i = new Intent(HomePage.this, UserSettings.class);
        startActivity(i);
    }

    void LoadSubjectsPage() {
        Intent i = new Intent(HomePage.this, SubjectsList.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
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
            LoadSettingsPage();
        } else if (id == R.id.action_subjects) {
            LoadSubjectsPage();
        }

        return super.onOptionsItemSelected(item);
    }
}

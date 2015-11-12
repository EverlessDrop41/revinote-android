package com.everlesslycoding.revinote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class HomePage extends AppCompatActivity {

    TextView NameLabel;
    Button SubjectsButton;
    Button SettingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Firebase.setAndroidContext(getApplicationContext());

        NameLabel = (TextView) findViewById(R.id.NameTextView);
        SubjectsButton = (Button) findViewById(R.id.SubjectsButton);
        SettingsButton = (Button) findViewById(R.id.AccountSettingBtn);

        Firebase ref = new Firebase("https://revinote.firebaseio.com/");
        if (ref != null) {
            NameLabel.setText(ref.getAuth().getProviderData().get("email").toString());
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

package com.everlesslycoding.revinote.Notes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.everlesslycoding.revinote.R;
import com.everlesslycoding.revinote.Subjects.Subject;
import com.firebase.client.Firebase;

/**
 * Created by emilyperegrine on 25/11/2015.
 */
public class NoteView extends AppCompatActivity {

    public static final String DEFAULT_URL = "http://www.revinote.com/subjects/markdown/-K0ucgoBxgGRSZf-o3yq";
    public static final String BASE_URL = "http://www.revinote.com/subjects/";

    Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);
        Firebase.setAndroidContext(getApplicationContext());

        ref = new Firebase("https://revinote.firebaseio.com/");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_subjects_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static String getNoteUrl(String subj, String id) {
        return BASE_URL + subj + '/' + id;
    }

    public static String getNoteUrl(Subject subj, String id) {
        return getNoteUrl(subj.getName(), id);
    }

    /*
    public static String getNoteUrl (Subject subj, Note id) {
        return getNoteUrl(subj, id.)
    }*/
}

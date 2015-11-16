package com.everlesslycoding.revinote.Notes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.everlesslycoding.revinote.R;
import com.everlesslycoding.revinote.Subjects.Subject;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class NotesList extends AppCompatActivity {

    ListView mNotesList;

    NoteAdapter mAdapter;
    ArrayList<Note> mNotes = new ArrayList<>();

    Firebase ref;
    AuthData auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        Firebase.setAndroidContext(getApplicationContext());
        ref = new Firebase("https://revinote.firebaseio.com/");
        auth = ref.getAuth();

        mNotesList = (ListView) findViewById(R.id.NotesList);

        //TODO: switch to using a value that isn't hardcoded
        final Subject subject = new Subject("Biology", "Life 'n Stuff");

        if (auth != null) {
            String uid = auth.getUid();

            Firebase userBase = ref.child(uid);
            Firebase subjectBase = userBase.child("notes");
            Firebase noteBase = subjectBase.child(subject.getName());

            noteBase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Log.d("[Subjects List]", "There are " + snapshot.getChildrenCount() + " subjects: " + snapshot.getValue());

                    mNotes.clear();

                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        try {
                            String title   = postSnapshot.child("title").getValue(String.class);
                            String content = postSnapshot.child("content").getValue(String.class);
                            Note note = new Note(title, content, subject);
                            Log.d("[Notes List]", note.toString() );
                            mNotes.add(note);
                            mAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Log.e("[Notes List]", "The read failed: " + firebaseError.getMessage());
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notes_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_subjects) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.everlesslycoding.revinote.Subjects;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.everlesslycoding.revinote.R;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class SubjectsList extends AppCompatActivity {

    ListView mSubjectsList;

    //ArrayAdapter<String> mAdapter;
    SubjectAdapter mAdapter;
    ArrayList<Subject> mSubjects = new ArrayList<>();

    Firebase ref;
    AuthData auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects_list);
        Firebase.setAndroidContext(getApplicationContext());

        ref = new Firebase("https://revinote.firebaseio.com/");

        mSubjectsList = (ListView) findViewById(R.id.SubjectsList);

        mAdapter = new SubjectAdapter(getApplicationContext(), mSubjects);

        mSubjectsList.setAdapter(mAdapter);

        auth = ref.getAuth();

        mSubjectsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Subject subjectToView = mAdapter.getSubjectAtPoistion(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(SubjectsList.this);

                builder.setMessage(subjectToView.getDescription()).setTitle(subjectToView.getName());

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        if (auth != null) {
            String uid = auth.getUid();

            Firebase userBase = ref.child(uid);
            Firebase subjectBase = userBase.child("subjects");

            subjectBase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Log.d("[Subjects List]", "There are " + snapshot.getChildrenCount() + " subjects: " + snapshot.getValue());

                    mSubjects.clear();

                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        try {
                            Subject subject = postSnapshot.getValue(Subject.class);
                            Log.d("[Subject List]", subject.toString() );
                            mSubjects.add(subject);
                            mAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Log.e("[Subjects List]", "The read failed: " + firebaseError.getMessage());
                }
            });
        }


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
}

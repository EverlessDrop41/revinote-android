package com.everlesslycoding.revinote;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class SubjectsList extends AppCompatActivity {

    ListView mSubjectsList;

    ArrayAdapter<String> mAdapter;
    ArrayList<String> mSubjects = new ArrayList<>();

    Firebase ref;
    AuthData auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects_list);
        Firebase.setAndroidContext(getApplicationContext());

        ref = new Firebase("https://revinote.firebaseio.com/");

        mSubjectsList = (ListView) findViewById(R.id.SubjectsList);

        mAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, mSubjects) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

                textView.setTextColor(Color.BLACK);

                return view;
            }
        };

        mSubjectsList.setAdapter(mAdapter);

        /*mSubjects.add("English");
        mSubjects.add("Maths");
        mSubjects.add("Chemistry");
        mAdapter.notifyDataSetChanged();*/

        auth = ref.getAuth();

        if (auth != null) {
            String uid = auth.getUid();
            Toast.makeText(getApplicationContext(),uid, Toast.LENGTH_LONG).show();

            Firebase userBase = ref.child(uid);
            Firebase subjectBase = userBase.child("subjects");

            subjectBase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Log.d("[Subjects List]", "There are " + snapshot.getChildrenCount() + " subjects: " + snapshot.getValue());

                    mSubjects.clear();

                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        try {
                            String subject = postSnapshot.child("name").getValue(String.class) + "";
                            Log.d("[Subject List]", subject );
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
        return super.onOptionsItemSelected(item);
    }
}

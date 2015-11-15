package com.everlesslycoding.revinote.Notes;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import com.everlesslycoding.revinote.Subjects.Subject;

import java.util.ArrayList;

/**
 * Created by emilyperegrine on 15/11/2015.
 */
public class NoteAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Note> mNotes;

    public NoteAdapter(Context context, ArrayList<Note> Notes) {
        mNotes = Notes;
        mContext = context;
    }

    public void Add(String Name, String Description, Subject Subj) {
        Log.d("Note Adapter", "Adding Note");
        mNotes.add(new Note(Name, Description, Subj));
    }

    public void Add(Note Note) {
        Log.d("Note Adapter", "Adding Note");
        mNotes.add(Note);
    }

    @Override
    public int getCount() {
        return mNotes.size();
    }

    @Override
    public Object getItem(int position) {
        return mNotes.get(position);
    }

    public Note getNoteAtPoistion (int position) { return mNotes.get(position); }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TwoLineListItem twoLineListItem;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            twoLineListItem = (TwoLineListItem) inflater.inflate(
                    android.R.layout.simple_list_item_2, null);
        } else {
            twoLineListItem = (TwoLineListItem) convertView;
        }

        TextView text1 = twoLineListItem.getText1();
        TextView text2 = twoLineListItem.getText2();

        text1.setTextColor(Color.DKGRAY);
        text2.setTextColor(Color.GRAY);

        Note note = mNotes.get(position);

        text1.setText(note.getTitle());
        text2.setText(note.getSnippet());

        return twoLineListItem;
    }
}
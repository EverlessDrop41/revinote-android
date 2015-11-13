package com.everlesslycoding.revinote.Subjects;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.util.ArrayList;

/**
 * Created by Emily on 13/11/2015.
 */
public class SubjectAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Subject> mSubjects;

    public SubjectAdapter(Context context, ArrayList<Subject> subjects) {
        mSubjects = subjects;
        mContext = context;
    }

    public void Add(String Name, String Description) {
        Log.d("Subject Adapter", "Adding Subject");
        mSubjects.add(new Subject(Name, Description));
    }

    public void Add(Subject subject) {
        Log.d("Subject Adapter", "Adding Subject");
        mSubjects.add(subject);
    }

    @Override
    public int getCount() {
        return mSubjects.size();
    }

    @Override
    public Object getItem(int position) {
        return mSubjects.get(position);
    }

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

        Subject subj = mSubjects.get(position);

        text1.setText(subj.getName());
        text2.setText(subj.getDescription());

        return twoLineListItem;
    }
}

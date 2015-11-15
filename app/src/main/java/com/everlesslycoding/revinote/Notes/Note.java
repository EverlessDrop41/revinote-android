package com.everlesslycoding.revinote.Notes;

import com.everlesslycoding.revinote.Subjects.Subject;

/**
 * Created by emilyperegrine on 15/11/2015.
 */
public class Note {
    String mTitle;
    String mDescription;
    Subject mSubject;

    public Note(String title, String description, Subject subject) {
        mTitle = title;
        mDescription = description;
        mSubject = subject;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSnippet() {
        return mDescription.split("[\r?\n]+")[0];
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Subject getSubject() {
        return mSubject;
    }

    public void setSubject(Subject subject) {
        mSubject = subject;
    }
}

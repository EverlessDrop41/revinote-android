package com.everlesslycoding.revinote.Notes;

import com.everlesslycoding.revinote.Subjects.Subject;

import java.io.Serializable;

/**
 * Created by emilyperegrine on 15/11/2015.
 */
public class Note implements Serializable {
    String mTitle;
    String mDescription;
    Subject mSubject;
    String mId;

    public Note(String title, String description, Subject subject, String id) {
        mTitle = title;
        mDescription = description;
        mSubject = subject;
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
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

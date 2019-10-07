package com.example.fundoonotes.data;

public class Notes {
    public String title, notes;
    public boolean pinned;
    public boolean archived;
    public boolean reminder;

    public String getTitle(){
        return title;
    }
    public String getNotes(){
        return notes;
    }
    public String isArchived(){
        if(archived)
            return "yes";
        else
            return "no";
    }

    public String isPinned() {
        if(pinned)
            return "yes";
        else
            return "no";
    }

    public String isReminder() {
        if(reminder)
            return "yes";
        else
            return "no";
    }
}

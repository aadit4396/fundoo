package com.example.fundoonotes.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.fundoonotes.R;

public class NotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        getActionBar().setTitle("Notes");
    }
}

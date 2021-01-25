package com.kuzaev.notes.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.kuzaev.notes.object.Note;
import com.kuzaev.notes.database.NotesDatabase;
import com.kuzaev.notes.R;

public class AddNoteActivity extends AppCompatActivity {

    EditText editTextTitle;
    EditText editTextDescription;
    Spinner spinnerDaysOfWeek;
    RadioGroup radioGroupPriority;

    private NotesDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        database = NotesDatabase.getInstance(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }


        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        spinnerDaysOfWeek = findViewById(R.id.spinnerDaysOfWeek);
        radioGroupPriority = findViewById(R.id.radioGroupProirity);
    }

    public void onClickAddNote(View view) {

        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        int dayOfWeek = spinnerDaysOfWeek.getSelectedItemPosition();
        int radioButtonId = radioGroupPriority.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioButtonId);
        int priority = Integer.parseInt(radioButton.getText().toString());
        if (isFilled(title, description)) {

            Note note =  new Note(title, description, dayOfWeek, priority);
            database.notesDAO().insertNote(note);
            startActivity(new Intent(this, MainActivity.class));
        }else{
            Toast.makeText(this, R.string.toast_is_filled, Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isFilled(String title, String description) {
        return !title.isEmpty() && !description.isEmpty();
    }
}
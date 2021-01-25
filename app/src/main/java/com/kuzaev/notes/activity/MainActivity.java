package com.kuzaev.notes.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.kuzaev.notes.database.NotesDatabase;
import com.kuzaev.notes.adapter.NoteAdapter;
import com.kuzaev.notes.object.Note;
import com.kuzaev.notes.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Note> notes = new ArrayList<>();
    private NotesDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        database = NotesDatabase.getInstance(this);

        recyclerView = findViewById(R.id.recyclerViewNote);
        NoteAdapter noteAdapter = new NoteAdapter(notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        getData();
        recyclerView.setAdapter(noteAdapter);
        noteAdapter.setOnNoteClickListener(new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                Toast.makeText(getApplicationContext(), "Позиция номер: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {
                removeIntPosition(position, noteAdapter);
            }

        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeIntPosition(viewHolder.getAdapterPosition(), noteAdapter);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void removeIntPosition(int position, NoteAdapter noteAdapter) {
        Note note = notes.get(position);
        database.notesDAO().deletedNote(note);
        getData();
        noteAdapter.notifyDataSetChanged();
    }

    public void onClickAddNote(View view) {
        startActivity(new Intent(this, AddNoteActivity.class));
    }

    private void getData() {
       List<Note> notesFromDB = database.notesDAO().getAllNote();
        notes.clear();
        notes.addAll(notesFromDB);
    }
}
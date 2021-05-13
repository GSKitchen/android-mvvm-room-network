package com.example.roomtask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.roomtask.activity.AddNoteActivity;
import com.example.roomtask.adapter.NoteAdapter;
import com.example.roomtask.entity.Note;
import com.example.roomtask.viewmodel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fabAddNote = findViewById(R.id.fab_add);
        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("NOTE_ADD", "init Addnote activity");
                Intent intent = new Intent(getApplicationContext(), AddNoteActivity.class);
                Log.d("NOTE_ADD", "inited Addnote activity");
                startActivityForResult(intent, ADD_NOTE_REQUEST);
                Log.d("NOTE_ADD", "init Addnote activity ended");
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NoteAdapter noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

        noteViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(NoteViewModel.class);

        noteViewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                Log.i("ON_CHANGE", "On chagned method excuted");

                noteAdapter.setNotes(notes);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("NOTE_ADD", "on actity resutl: " + data);
        Log.d("NOTE_ADD", "on actity resutl req code: " + requestCode);
        Log.d("NOTE_ADD", "on actity resutl resp code: " + resultCode);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == -1){
            String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String desc = data.getStringExtra(AddNoteActivity.EXTRA_DESC);
            int priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 1);

            Note note = new Note(title, desc, priority);
            noteViewModel.insert(note);

            Toast.makeText(this, "Note saved successfully", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }
}
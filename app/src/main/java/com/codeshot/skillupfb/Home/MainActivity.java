package com.codeshot.skillupfb.Home;

import android.content.Intent;
import android.os.Bundle;

import com.codeshot.skillupfb.AddNote.AddNoteActivity;
import com.codeshot.skillupfb.DB.DBConnection;
import com.codeshot.skillupfb.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //View
    private TextView tvYouDontHaveNotes;
    private RecyclerView rvNotes;
    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        initializationsView();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToAddNoteActivity();
            }
        });


        rvNotes.setAdapter(notesAdapter);
    }

    private void initializationsView() {
        tvYouDontHaveNotes=findViewById(R.id.tvYouDontHaveNotes);
        rvNotes=findViewById(R.id.rvNotes);
        rvNotes.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        DBConnection dbConnection=new DBConnection(this);
        notesAdapter=new NotesAdapter(dbConnection.getAllNotes());



    }

    private void sendToAddNoteActivity(){
        startActivity(new Intent(MainActivity.this, AddNoteActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (notesAdapter.getItemCount()==0){
            tvYouDontHaveNotes.setVisibility(View.VISIBLE);
        }else tvYouDontHaveNotes.setVisibility(View.GONE);
    }


}

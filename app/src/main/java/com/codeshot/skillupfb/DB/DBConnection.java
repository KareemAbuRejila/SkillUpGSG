package com.codeshot.skillupfb.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.codeshot.skillupfb.Model.Note;

import java.util.ArrayList;
import java.util.List;

public class DBConnection extends SQLiteOpenHelper {

    private String createNoteTable="CREATE TABLE IF NOT EXISTS Notes(id INTEGER,title TEXT,location TEXT,time TEXT,des TEXT)";
    private String dropNoteTable="DROP TABLE IF EXISTS Notes";

    private static String dbName="notesDataBase.db";
    private static int dbVersion=1;

    public DBConnection(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(createNoteTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropNoteTable);
        onCreate(db);
    }

    public void addNote(Note note){
        SQLiteDatabase wDB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put("title",note.getTitle());
        contentValues.put("location",note.getLocation());
        contentValues.put("time",note.getTime());
        contentValues.put("des",note.getDes());

        wDB.insert("Notes",null,contentValues);

    }

    public List<Note>  getAllNotes(){
        List<Note> notes=new ArrayList<>();
        SQLiteDatabase rDB=this.getReadableDatabase();

        Cursor result=rDB.rawQuery("SELECT * FROM Notes",null);
        result.moveToFirst();

        while (!result.isAfterLast()){
            String title,location,time,des;
            title=result.getString(result.getColumnIndex("title"));
            location=result.getString(result.getColumnIndex("location"));
            time=result.getString(result.getColumnIndex("time"));
            des=result.getString(result.getColumnIndex("des"));
            Note note=new Note(title,location,time,des);
            notes.add(note);
            result.moveToNext();
        }
        rDB.close();
        return notes;
    }
}

package com.codeshot.skillupfb.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codeshot.skillupfb.Model.Note;
import com.codeshot.skillupfb.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteAdapterViewHolder> {

    private Context context;
    private List<Note> notes;

    NotesAdapter(List<Note> notes) {
        this.notes = notes;
    }


    @NonNull
    @Override
    public NoteAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note,parent,false);
        return new NoteAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapterViewHolder holder, int position) {
        Note note=notes.get(position);
        holder.tvTitle.setText(note.getTitle());
        holder.tvDes.setText(note.getDes());
        holder.tvTime.setText(note.getTime());
        holder.tvLocation.setText(note.getLocation());



    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NoteAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle,tvDes,tvTime,tvLocation;
        NoteAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tvNoteTitleItem);
            tvDes=itemView.findViewById(R.id.tvNoteDesItem);
            tvTime=itemView.findViewById(R.id.tvNoteTimeItem);
            tvLocation=itemView.findViewById(R.id.tvNoteLocationItem);

        }
    }
}

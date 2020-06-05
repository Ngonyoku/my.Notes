package com.RickProjects.notekeeper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.RickProjects.notekeeper.Models.Notes;
import com.RickProjects.notekeeper.R;

import java.util.List;
import java.util.UUID;

public class NotesRVAdapter extends RecyclerView.Adapter<NotesRVAdapter.NotesViewHolder> {
    private List<Notes> notesList;
    private Context context;
    private OnClick listener;

    public interface OnClick {
        void onItemClick(int position, UUID uuid);
    }

    public void setOnClick(OnClick listener) {
        this.listener = listener;
    }

    public NotesRVAdapter(Context context, List<Notes> notesList) {
        this.notesList = notesList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(
                LayoutInflater
                        .from(context)
                        .inflate(R.layout.item_notes_list, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, final int position) {
        holder.bind(notesList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.notesList.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView title, dateCreated;
        private UUID uuid;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_title_item);
            dateCreated = itemView.findViewById(R.id.tv_dateCreated_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getLayoutPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position, uuid);
                        }
                    }
                }
            });
        }

        public void bind(Notes notes) {
            this.uuid = notes.getIdentifier();
            title.setText(notes.getNoteTitle());
            dateCreated.setText(notes.getDateCreated());
        }
    }
}

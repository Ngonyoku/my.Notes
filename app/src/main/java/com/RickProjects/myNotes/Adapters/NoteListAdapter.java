package com.RickProjects.myNotes.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.RickProjects.myNotes.Models.Note;
import com.RickProjects.myNotes.R;

public class NoteListAdapter extends ListAdapter<Note, NoteListAdapter.NoteListViewHolder> {
    private OnClickListener mListener;

    public interface OnClickListener {
        void onClick(Note note);
    }

    public NoteListAdapter() {
        super(DIFF_CALL_BACK);
    }

    public void setOnItemClick(OnClickListener listener) {
        mListener = listener;
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALL_BACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getDateCreated().equals(newItem.getDateCreated())
                    && oldItem.getDescription().equals(newItem.getDescription())
                    && oldItem.getTitle().equals(newItem.getTitle());
        }
    };

    @NonNull
    @Override
    public NoteListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteListViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_note_list, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListViewHolder holder, int position) {
        Note currentNote = getItem(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getDescription());
        holder.textViewDate.setText(currentNote.getDateCreated());
    }

    public Note getNoteAt(int position) {
        return getItem(position);
    }

    class NoteListViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle, textViewDescription, textViewDate;

        public NoteListViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textView_noteTitle);
            textViewDescription = itemView.findViewById(R.id.textView_description);
            textViewDate = itemView.findViewById(R.id.textView_date);

            itemView.setOnClickListener(v -> {
                int position = getLayoutPosition();
                if (position != RecyclerView.NO_POSITION && mListener != null) {
                    mListener.onClick(getItem(position));
                }
            });
        }
    }
}

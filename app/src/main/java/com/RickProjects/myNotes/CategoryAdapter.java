package com.RickProjects.myNotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends ListAdapter<Category, CategoryAdapter.CategoryViewHolder> {

    public CategoryAdapter() {
        super(
                new DiffUtil.ItemCallback<Category>() {
                    @Override
                    public boolean areItemsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
                        return oldItem.getCategoryId() == newItem.getCategoryId();
                    }

                    @Override
                    public boolean areContentsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
                        return oldItem.getCategoryName().equals(newItem.getCategoryName());
                    }
                }
        );
    }

    public Category getItemAt(int position) {
        return getItem(position);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_category_list, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category current = getItem(position);
        holder.textViewCategoryName.setText(current.getCategoryName());
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewCategoryName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewCategoryName = itemView.findViewById(R.id.textview_category_name);
        }
    }
}

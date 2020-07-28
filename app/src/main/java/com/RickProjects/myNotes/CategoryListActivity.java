package com.RickProjects.myNotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class CategoryListActivity extends AppCompatActivity {
    public static final String CATEGORY_ID = "com.RickProjects.myNotes.CATEGORY_ID";
    private CategoryViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        mViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        CategoryAdapter adapter = new CategoryAdapter();
        RecyclerView recyclerView = findViewById(R.id.recycler_category_list);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mViewModel.getCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                adapter.submitList(categories);
            }
        });

        adapter.setOnItemClicked(
                category -> {
                    startActivity(
                            new Intent(CategoryListActivity.this, NoteListActivity.class)
                                    .putExtra(CATEGORY_ID, category.getCategoryId())
                    );
                }
        );
    }
}
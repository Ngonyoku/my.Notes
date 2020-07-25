package com.RickProjects.myNotes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private CategoryRepository mCategoryRepository;
    private LiveData<List<Category>> categories;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        mCategoryRepository = new CategoryRepository(application);
        categories = mCategoryRepository.getCategories();
    }

    public void insert(Category category) {
        mCategoryRepository.insert(category);
    }

    public void delete(Category category) {
        mCategoryRepository.delete(category);
    }

    LiveData<List<Category>> getCategories() {
        return categories;
    }
}

package com.RickProjects.myNotes;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CategoryRepository {
    private CategoryDAO mDAO;
    private LiveData<List<Category>> categories;

    public CategoryRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        mDAO = database.categoryDAO();
        categories = mDAO.getCategories();
    }

    void insert(Category category) {
        new InsertTask(mDAO).execute(category);
    }

    void delete(Category category) {

    }

    LiveData<List<Category>> getCategories() {
        return categories;
    }

    private class InsertTask extends AsyncTask<Category, Void, Void> {
        private CategoryDAO mCategoryDAO;

        public InsertTask(CategoryDAO categoryDAO) {
            mCategoryDAO = categoryDAO;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            mCategoryDAO.insert(categories[0]);
            return null;
        }
    }
}

package com.epam.themes.collectionviews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.epam.cleancodetest.R;
import com.epam.themes.backend.IWebService;
import com.epam.themes.backend.StudentsWebService;
import com.epam.themes.backend.entities.Student;
import com.epam.themes.collectionviews.recyclerview.ItemTouchCallback;
import com.epam.themes.collectionviews.recyclerview.StudentsAdapter;
import com.epam.themes.util.ICallback;

import java.util.List;

public class StudentsActivity extends AppCompatActivity {

    public static final int PAGE_SIZE = 2;
    public static final int MAX_VISIBLE_ITEMS = 18;
    private final IWebService<Student> studentsWebService = new StudentsWebService();

    private boolean isLoading = false;
    private StudentsAdapter adapter;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.recycler_view);
        actionBar.setDisplayShowTitleEnabled(true);

        recyclerView = findViewById(android.R.id.list);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new StudentsAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = layoutManager.getItemCount();

                if (totalItemCount > MAX_VISIBLE_ITEMS) {
                    adapter.setShowLastViewAsLoading(false);

                    return;
                }

                int visibleItemCount = layoutManager.getChildCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= PAGE_SIZE) {
                        loadMoreItems(totalItemCount, totalItemCount + PAGE_SIZE);
                    }
                }
            }
        });

        loadMoreItems(0, PAGE_SIZE);

        new ItemTouchHelper(new ItemTouchCallback(recyclerView, adapter))
                .attachToRecyclerView(recyclerView);
    }

    private void loadMoreItems(final int pStartPosition, final int pEndPosition) {
        isLoading = true;
        adapter.setShowLastViewAsLoading(true);
        studentsWebService.getEntities(pStartPosition, pEndPosition, new ICallback<List<Student>>() {

            @Override
            public void onResult(List<Student> result) {
                adapter.addItems(result);
                isLoading = false;
            }
        });
    }
}
